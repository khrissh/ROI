package com.test.utils;


import com.test.model.InputData;
import com.test.model.JiraMonthData;
import com.test.model.OutputData;
import com.test.utils.jira.JiraUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.test.utils.GeneralUtil.parseMonthStringToDate;

public class Calculation {

   private final static double MINUTES_IN_HOUR = 60.0;

   /*
   * Auto team capacity per month = number of working days in month * 8 (working hours) * team size,
   *
   * where:
   *
   * Number of working days in month = number of weekdays - number of official day offs in Ukraine (assuming
   * that Easter is celebrating in April and Pentecost is celebrating in June)
   *
   * Team size = number of unique assignees of all jira tickets per month
   *
   * */

   public static int calculateAutoTeamCapacity(JiraMonthData jiraData) {
      LocalDate periodDate = parseMonthStringToDate(jiraData.getMonth());
      return GeneralUtil.calculateWorkingDaysInMonth(periodDate) * 8 * jiraData.getAssignee().size();

   }

   public static int calculateMonthCapacityPerOneEngineer(JiraMonthData jiraData) {
      LocalDate periodDate = parseMonthStringToDate(jiraData.getMonth());
      return GeneralUtil.calculateWorkingDaysInMonth(periodDate) * 8;

   }

   /*
   * Maintenance time per month = sum of time spent on each jira maintenance ticket
   *
   * Release support time per month = sum of time spent on each jira release support ticket
   *
   * Time for new tests creation per month = sum of time spent on each jira ticket on new test creation
   *
   * */
   public static Double calculateTimePerMonthOn(JiraUtil.TicketType ticketType, JiraMonthData jiraData) {
      if (jiraData.getTimeSpent(ticketType) != null) {
         return jiraData.getTimeSpent(ticketType).stream()
               .mapToDouble(p -> p).sum();
      } else {
         return 0.0;
      }

   }

   /*
   * Total number of test cases = sum of jira tickets on new test creation
   *
   * */
   public static int calculateTotalNumberOfTestCases(List<OutputData> dataList) {
      return dataList.stream().mapToInt(d -> d.getNumberOfTestCasesPerMonth()).sum();
   }

   /*
   * Manual hours per month without automation = total number of test cases * number of runs per month *
   *                             average time to pass test case manually (in hours)
   *
   * */
   public static double calculateManualHourPerMonthWithoutAutomation(int totalNumberOfTestCases, InputData inputData, int runsPerMonth) {
      if (inputData.getTotalNumberOfManualTestCases() != 0) {
         return inputData.getTotalNumberOfManualTestCases() * runsPerMonth
               * inputData.getAverageTimeToPassManualTestCase() / MINUTES_IN_HOUR;
      } else {
         return totalNumberOfTestCases * runsPerMonth
               * inputData.getAverageTimeToPassManualTestCase() / MINUTES_IN_HOUR;
      }
   }

   public static int calculateRunsPerMonth(Map<String, Integer> runsPerMonthMap, int runsPerMonth, String month) {
      if (runsPerMonthMap != null) {
         Optional<String> runsData = runsPerMonthMap.keySet().stream().filter(k -> k.equals(month)).findFirst();
         if (runsData.isPresent()) {
            return runsPerMonthMap.get(runsData.get());
         } else {
            int averageRunsPerMonth = runsPerMonthMap.keySet().stream().mapToInt(k -> runsPerMonthMap.get(k)).sum()
                  / runsPerMonthMap.size();
            return averageRunsPerMonth;
         }
      }
      return runsPerMonth;
   }


   public static int calculateCumulativeNumberOfTestCasesPerMonth(OutputData outputData, List<OutputData> outputDataList, int i) {
      int cumulativeNumberOfTestCases = outputData.getNumberOfTestCasesPerMonth();
      if (i > 0) {
         cumulativeNumberOfTestCases = cumulativeNumberOfTestCases +
               outputDataList.get(i - 1).getCumulativeNumberOfTestCasesPerMonth();
      }
      return cumulativeNumberOfTestCases;
   }

   public static double calculateSavingsPerMonth(int cumulativeNumberOfTestCases, int averageTimeToPassManualTestCase,
                                                 int runsPerPeriod) {
      return runsPerPeriod * averageTimeToPassManualTestCase / MINUTES_IN_HOUR * cumulativeNumberOfTestCases;
   }

   public static double calculateSavingDifference(double pureSavingsPercent, List<OutputData> outputDataList, int i) {
      double savingDifference = 0;
      if (i > 0) {
         savingDifference = pureSavingsPercent - outputDataList.get(i - 1).getPureSavingsPercent();
      }
      return savingDifference;
   }

   public static double calculateAutoCostPerMonth(InputData inputData, double manualHoursPerMonth,
                                                  double autoHoursPerMonth) {
      return inputData.getManualCostsPerHour() * manualHoursPerMonth +
            inputData.getAutoCostsPerHour() * autoHoursPerMonth;
   }

   public static double calculateCumulativeAutoCostPerMonth(double autoCostPerMonth, List<OutputData> outputDataList, int i) {
      double cumulativeAutoCostPerMonth = autoCostPerMonth;
      if (i > 0) {
         cumulativeAutoCostPerMonth = outputDataList.get(i - 1).getCumulativeCostPerMonthWithAutomation() + autoCostPerMonth;
      }
      return cumulativeAutoCostPerMonth;
   }

   public static double calculateCumulativeManualCostPerMonth(double manualCostPerMonth, List<OutputData> outputDataList, int i) {
      double cumulativeManualCostPerMonth = manualCostPerMonth;
      if (i > 0) {
         cumulativeManualCostPerMonth = outputDataList.get(i - 1).getCumulativeCostPerMonthWithoutAutomation() +
               manualCostPerMonth;
      }
      return cumulativeManualCostPerMonth;
   }

   public static double calculateTotalAutoCostPerMonth(OutputData outputData, InputData inputData, double manualHoursPerMonth) {
      return outputData.getAutoTeamCapacity() * inputData.getAutoCostsPerHour()
            + inputData.getManualCostsPerHour() * manualHoursPerMonth;
   }

   public static double calculateTotalCumulativeAutoCostPerMonth(double totalAutoCostPerMonth, List<OutputData> outputDataList, int i) {
      double totalCumulativeAutoCostPerMonth = totalAutoCostPerMonth;
      if (i > 0) {
         totalCumulativeAutoCostPerMonth = outputDataList.get(i - 1).getTotalCumulativeAutoCostPerMonth() +
               totalAutoCostPerMonth;
      }
      return totalCumulativeAutoCostPerMonth;
   }


}
