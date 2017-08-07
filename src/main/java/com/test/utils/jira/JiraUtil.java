package com.test.utils.jira;


import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.User;
import com.test.model.InputData;
import com.test.model.JiraMonthData;
import org.apache.commons.lang.StringUtils;

import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ExecutionException;

import static com.test.utils.jira.JiraUtil.JiraFields.getJiraTicketFieldsToRetrieve;

public class JiraUtil {

   public static List<JiraMonthData> getListOfMonthData(InputData inputData) throws InterruptedException, ExecutionException, URISyntaxException {

      List<JiraMonthData> jiraMonthDataList = new ArrayList<>();

      getJiraMonthDataListForTicketType(TicketType.NEW_TEST, jiraMonthDataList, inputData);
      getJiraMonthDataListForTicketType(TicketType.MAINTENANCE, jiraMonthDataList, inputData);
      getJiraMonthDataListForTicketType(TicketType.RELEASE_SUPPORT, jiraMonthDataList, inputData);

      return jiraMonthDataList;
   }

   private static List<JiraMonthData> getJiraMonthDataListForTicketType(TicketType ticketType,
                                                                        List<JiraMonthData> jiraMonthDataList,
                                                                        InputData inputData) throws ExecutionException, InterruptedException, URISyntaxException {
      JiraRestClient jiraRestClient = JiraClient
            .getJiraClient(inputData.getJiraUrl(), inputData.getUsername(), inputData.getPassword());

      Iterable<Issue> issues = jiraRestClient.getSearchClient()
            .searchJql(inputData.getTicketsFilter(ticketType),
                  inputData.getMaxResults(), 0, getJiraTicketFieldsToRetrieve()).get().getIssues();

      issues.forEach(t -> {

         String month = StringUtils.capitalize(getMonthFromJiraTicket(t).toLowerCase());
         Double timeSpent = getTimeSpentFromJiraTicket(t);
         String assignee = getAssigneeFromJiraTicket(t);

         addJiraTicketDataToIssuesList(jiraMonthDataList, ticketType, month, timeSpent, assignee);

      });

      return jiraMonthDataList;

   }

   private static String getMonthFromJiraTicket(Issue ticket) {
      String month = LocalDate.now().getMonth().name() + " " + LocalDate.now().getYear();
      String resolutionDate = (String) ticket.getField("resolutiondate").getValue();
      if (resolutionDate != null) {
         String dateString = StringUtils.substringBeforeLast(resolutionDate, "T");
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
         LocalDate date = LocalDate.parse(dateString, formatter);
         month = date.getMonth().name() + " " + date.getYear();
      }
      return month;
   }

   private static Double getTimeSpentFromJiraTicket(Issue ticket) {
      Integer timeSpentOriginal = (Integer) ticket.getField("timespent").getValue();
      Double timeSpent = 0.0;
      if (timeSpentOriginal != null) {
         timeSpent = Double.valueOf(timeSpentOriginal) / 3600;
      }
      return timeSpent;
   }

   private static String getAssigneeFromJiraTicket(Issue ticket) {
      String assignee = "default";
      User assigneeOriginal = ticket.getAssignee();
      if (assigneeOriginal != null) {
         assignee = assigneeOriginal.getName();
      }
      return assignee;
   }

   private static void addJiraTicketDataToIssuesList(List<JiraMonthData> jiraMonthDataList, TicketType ticketType,
                                                     String month, Double timeSpent, String assignee) {

      Optional<JiraMonthData> jiraMonthData = jiraMonthDataList.stream()
            .filter(d -> d.getMonth().equals(month)).findFirst();

      if (jiraMonthData.isPresent()) {
         JiraMonthData foundMonthData = jiraMonthData.get();

         List<Double> timeSpentList = foundMonthData.getTimeSpent(ticketType);
         if (timeSpentList != null) {
            timeSpentList.add(timeSpent);
         } else {
            List<Double> timeSpentListNew = new ArrayList<>();
            timeSpentListNew.add(timeSpent);
            foundMonthData.setTimeSpent(timeSpentListNew, ticketType);
         }


         boolean assigneeAlreadyAdded = foundMonthData.getAssignee().stream()
               .filter(a -> a.equals(assignee)).findFirst().isPresent();
         if (!assigneeAlreadyAdded) {
            foundMonthData.getAssignee().add(assignee);
         }

      } else {
         JiraMonthData jiraIssueData = new JiraMonthData();

         jiraIssueData.setMonth(month);

         List<Double> timeSpentList = new ArrayList<>();
         timeSpentList.add(timeSpent);
         jiraIssueData.setTimeSpent(timeSpentList, ticketType);

         List<String> assigneeList = new ArrayList<>();
         assigneeList.add(assignee);
         jiraIssueData.setAssignee(assigneeList);

         jiraMonthDataList.add(jiraIssueData);
      }

   }

   public enum TicketType {
      NEW_TEST,
      MAINTENANCE,
      RELEASE_SUPPORT
   }

   static class JiraFields {
      private static Set<String> fields = new HashSet<>();

      public static Set<String> getJiraTicketFieldsToRetrieve() {
         if (fields.isEmpty()) {
            List fieldsList = Arrays.asList("timespent", "resolutiondate", "components", "summary",
                  "issuetype", "created", "updated", "project", "status", "assignee");
            fieldsList.forEach(f -> fields.add((String) f));
         }
         return fields;
      }
   }

}
