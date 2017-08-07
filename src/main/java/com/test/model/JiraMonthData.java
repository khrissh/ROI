package com.test.model;


import com.test.utils.jira.JiraUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public class JiraMonthData {

   String month;

   List<String> assignee;
   List<Double> newTestCasesTimeSpent;
   List<Double> maintenanceTimeSpent;
   List<Double> releaseSupportTimeSpent;

   public List<Double> getTimeSpent(JiraUtil.TicketType ticketType) {
      switch (ticketType) {
         case NEW_TEST:
            return newTestCasesTimeSpent;
         case MAINTENANCE:
            return maintenanceTimeSpent;
         case RELEASE_SUPPORT:
            return releaseSupportTimeSpent;
      }
      return newTestCasesTimeSpent;
   }

   public void setTimeSpent(List<Double> timeSpent, JiraUtil.TicketType ticketType) {
      switch (ticketType) {
         case NEW_TEST:
            this.newTestCasesTimeSpent = timeSpent;
            break;
         case MAINTENANCE:
            this.maintenanceTimeSpent = timeSpent;
            break;
         case RELEASE_SUPPORT:
            this.releaseSupportTimeSpent = timeSpent;
            break;
      }
   }

   public String getMonth() {
      return StringUtils.capitalize(month.toLowerCase());
   }

   public void setMonth(String month) {
      this.month = month;
   }

   public List<String> getAssignee() {
      return assignee;
   }

   public void setAssignee(List<String> assignee) {
      this.assignee = assignee;
   }

   @Override
   public String toString() {
      return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
   }

}
