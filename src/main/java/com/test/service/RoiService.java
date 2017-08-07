package com.test.service;

import com.test.model.InputData;
import com.test.model.JiraMonthData;
import com.test.model.OutputData;
import com.test.model.OutputDataBuilder;
import com.test.utils.jira.JiraUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.ExecutionException;

import static com.test.utils.Calculation.*;
import static com.test.utils.GeneralUtil.parseMonthStringToDate;
import static com.test.utils.jira.JiraUtil.getListOfMonthData;

@Service("roi")
public class RoiService {

   private Map<String, Integer> getRunsPerPeriodBasedOnJenkins(String jobName) throws IOException {
      RestTemplate client = new RestTemplate();
      if (jobName != null && !jobName.isEmpty()) {
         Properties properties = new Properties();
         properties.load(RoiService.class.getResourceAsStream("/application.properties"));

         return client.getForObject(properties.getProperty("jenkins.notification") +
               "/api/getBuilds?jobName=" + jobName, Map.class);
      } else {
         return null;
      }
   }

   public List<OutputData> calculateRoi(InputData inputData) throws InterruptedException, ExecutionException, URISyntaxException, IOException {
      List<OutputData> outputDataList = getMonthsBasicData(inputData);


      int totalNumberOfTestCases = calculateTotalNumberOfTestCases(outputDataList);
      Map<String, Integer> runsPerMonthMap = getRunsPerPeriodBasedOnJenkins(inputData.getJenkinsJobName());

      for (int i = 0; i < outputDataList.size(); i++) {
         OutputData currentData = outputDataList.get(i);

         int runsPerMonth = calculateRunsPerMonth(runsPerMonthMap, inputData.getRunsPerPeriod(), currentData.getMonth());
         double manualHourPerMonthWithoutAutomation = calculateManualHourPerMonthWithoutAutomation(totalNumberOfTestCases,
               inputData, runsPerMonth);
         int cumulativeNumberOfTestCasesPerMonth = calculateCumulativeNumberOfTestCasesPerMonth(currentData,
               outputDataList, i);
         double savingsPerMonth = calculateSavingsPerMonth(cumulativeNumberOfTestCasesPerMonth,
               inputData.getAverageTimeToPassManualTestCase(), runsPerMonth);
         double pureSavingsHours = savingsPerMonth - currentData.getMaintenancePerMonth();
         double pureSavingsPercent = pureSavingsHours / currentData.getMonthlyCapacityPerOneEngineer();
         double savingDifference = calculateSavingDifference(pureSavingsPercent,
               outputDataList, i);

         double autoHoursPerMonth = currentData.getAutoHoursPerMonth();
         double manualHoursPerMonthWithAutomation = manualHourPerMonthWithoutAutomation - savingsPerMonth;

         double costsPerMonthWithAutomation = calculateAutoCostPerMonth(inputData, manualHoursPerMonthWithAutomation, autoHoursPerMonth);
         double costsPerMonthWithoutAutomation = manualHourPerMonthWithoutAutomation * inputData.getManualCostsPerHour();

         double cumulativeAutoCostPerMonth = calculateCumulativeAutoCostPerMonth(costsPerMonthWithAutomation,
               outputDataList, i);
         double cumulativeManualCostPerMonth = calculateCumulativeManualCostPerMonth(costsPerMonthWithoutAutomation,
               outputDataList, i);

         double totalAutoCostPerMonth = calculateTotalAutoCostPerMonth(currentData, inputData, manualHoursPerMonthWithAutomation);
         double totalCumulativeAutoCostPerMonth = calculateTotalCumulativeAutoCostPerMonth(totalAutoCostPerMonth,
               outputDataList, i);

         new OutputDataBuilder(currentData)
               .setCumulativeNumberOfTestCasesPerMonth(cumulativeNumberOfTestCasesPerMonth)
               .setSavingsPerMonth(savingsPerMonth)
               .setPureSavingsHours(pureSavingsHours)
               .setPureSavingsPercent(pureSavingsPercent)
               .setSavingDifference(savingDifference)
               .setManualHoursPerMonthWithAutomation(manualHoursPerMonthWithAutomation)
               .setManualHourPerMonthWithoutAutomation(manualHourPerMonthWithoutAutomation)
               .setCostsPerMonthWithAutomation(costsPerMonthWithAutomation)
               .setCostsPerMonthWithoutAutomation(costsPerMonthWithoutAutomation)
               .setCumulativeAutoCostPerMonth(cumulativeAutoCostPerMonth)
               .setCumulativeManualCostPerMonth(cumulativeManualCostPerMonth)
               .setTotalAutoCostPerMonth(totalAutoCostPerMonth)
               .setTotalCumulativeAutoCostPerMonth(totalCumulativeAutoCostPerMonth)
               .setPriceDifferencePureAutoHours(cumulativeManualCostPerMonth - cumulativeAutoCostPerMonth)
               .setPriceDifferenceFullAutoHours(cumulativeManualCostPerMonth - totalCumulativeAutoCostPerMonth)
               .build();
      }

      return outputDataList;
   }

   private List<OutputData> getMonthsBasicData(InputData inputData) throws InterruptedException, ExecutionException, URISyntaxException {
      List<OutputData> outputDataList = new ArrayList<>();

      List<JiraMonthData> listOfMonthData = getListOfMonthData(inputData);

      listOfMonthData.stream()
            .sorted(Comparator.comparing(p -> parseMonthStringToDate(p.getMonth())))
            .forEach(monthData -> {
               int capacityOfAutoTeam = calculateAutoTeamCapacity(monthData);
               int monthCapacityPerOneEngineer = calculateMonthCapacityPerOneEngineer(monthData);

               Double maintenanceTimePerMonth = calculateTimePerMonthOn(JiraUtil.TicketType.MAINTENANCE,
                     monthData);

               Double timeForReleaseSupport = calculateTimePerMonthOn(JiraUtil.TicketType.RELEASE_SUPPORT,
                     monthData);

               Double timeForTestCreation = calculateTimePerMonthOn(JiraUtil.TicketType.NEW_TEST,
                     monthData);

               int numberOfTestCases = 0;
               if (monthData.getTimeSpent(JiraUtil.TicketType.NEW_TEST) != null) {
                  numberOfTestCases = monthData.getTimeSpent(JiraUtil.TicketType.NEW_TEST).size();
               }

               OutputData outputData = new OutputDataBuilder()
                     .setMonth(monthData.getMonth())
                     .setNumberOfTestCasesPerMonth(numberOfTestCases)
                     .setAutoTeamCapacity(capacityOfAutoTeam)
                     .setTimeForTestCreation(timeForTestCreation)
                     .setMaintenancePerPeriod(maintenanceTimePerMonth)
                     .setMaintenancePercentage(maintenanceTimePerMonth * 100 / capacityOfAutoTeam)
                     .setTimeForReleaseSupport(timeForReleaseSupport)
                     .setAutoHoursPerMonth(maintenanceTimePerMonth + timeForReleaseSupport + timeForTestCreation)
                     .setMonthlyCapacityPerOneEngineer(monthCapacityPerOneEngineer)
                     .build();

               outputDataList.add(outputData);

            });

      return outputDataList;
   }
}
