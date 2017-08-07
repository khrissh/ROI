package com.test.service;

import com.test.model.InputData;
import com.test.model.JiraMonthData;
import com.test.utils.jira.JiraUtil;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class TestMock {


   public static void main(String[] args) throws InterruptedException, ExecutionException, URISyntaxException, IOException {

      InputData inputData = new InputData();
      inputData.setRunsPerPeriod(15);
      inputData.setJenkinsJobName("UI TESTS Smoke suite - maintenance stable");
      inputData.setAverageTimeToPassManualTestCase(19);
      inputData.setTotalNumberOfManualTestCases(904);
      inputData.setJiraUrl("test");
      inputData.setNewTestCaseTicketsFilter("\"Epic Link\"=TXT-1108&status=Released");
      inputData.setMaintenanceTicketsFilter("summary~\"Investigate Failed builds on Jenkins UI suite\"&assignee=dan&status=released");
      inputData.setReleaseSupportTicketsFilter("labels=Release_Support");
      inputData.setUsername("");
      inputData.setPassword("");
      inputData.setMaxResults(200);
      inputData.setAutoCostsPerHour(20);
      inputData.setManualCostsPerHour(15);


      new RoiService().calculateRoi(inputData);
   }

   public static List<JiraMonthData> generateIssueCreationData() {
      List<JiraMonthData> list = new ArrayList<>();

      List<String> periods = Arrays.asList("April 2017", "June 2017", "July 2017", "August 2017");

      List<List<Double>> newTestTime = generateNewTestTime();
      List<List<Double>> maintenanceTime = generateMaintenanceTime();
      List<List<Double>> releaseTime = generateReleaseSupportTime();

      for (int i = 0; i < periods.size(); i++) {
         JiraMonthData data = new JiraMonthData();
         data.setAssignee(Arrays.asList("dan"));
         data.setMonth(periods.get(i));
         data.setTimeSpent(newTestTime.get(i), JiraUtil.TicketType.NEW_TEST);
         data.setTimeSpent(maintenanceTime.get(i), JiraUtil.TicketType.MAINTENANCE);
         data.setTimeSpent(releaseTime.get(i), JiraUtil.TicketType.RELEASE_SUPPORT);
         list.add(data);
      }
      return list;
   }

   public static List<List<Double>> generateNewTestTime() {
      List<Double> first = Arrays.asList(4.0, 5.0, 8.0);
      List<Double> second = Arrays.asList(5.0, 7.0);
      List<Double> third = Arrays.asList(12.0, 4.0, 2.0, 12.0, 2.0);
      List<Double> forth = Arrays.asList(12.0, 6.0, 4.5, 8.0);
      return Arrays.asList(first, second, third, forth);
   }

   public static List<List<Double>> generateMaintenanceTime() {
      List<Double> first = Arrays.asList(4.0, 5.0);
      List<Double> second = Arrays.asList(5.0, 3.0);
      List<Double> third = Arrays.asList(2.0, 4.0, 2.0, 2.0, 2.0);
      List<Double> forth = Arrays.asList(2.0, 1.0, 1.0);
      return Arrays.asList(first, second, third, forth);
   }

   public static List<List<Double>> generateReleaseSupportTime() {
      List<Double> first = Arrays.asList(2.0);
      List<Double> second = Arrays.asList(4.0);
      List<Double> third = Arrays.asList(5.0);
      List<Double> forth = Arrays.asList(8.0);
      return Arrays.asList(first, second, third, forth);
   }

}
