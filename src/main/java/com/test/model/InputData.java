package com.test.model;


import com.test.utils.jira.JiraUtil;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class InputData {

    private int runsPerPeriod;
    private String jenkinsJobName;

    private int averageTimeToPassManualTestCase;
    private int totalNumberOfManualTestCases;

    private double manualCostsPerHour;
    private double autoCostsPerHour;

    private String jiraUrl;
    private String newTestCaseTicketsFilter;
    private String maintenanceTicketsFilter;
    private String releaseSupportTicketsFilter;
    private String username;
    private String password;
    private int maxResults;


    public String getJenkinsJobName() {
        return jenkinsJobName;
    }

    public void setJenkinsJobName(String jenkinsJobName) {
        this.jenkinsJobName = jenkinsJobName;
    }

    public int getTotalNumberOfManualTestCases() {
        return totalNumberOfManualTestCases;
    }

    public void setTotalNumberOfManualTestCases(int totalNumberOfManualTestCases) {
        this.totalNumberOfManualTestCases = totalNumberOfManualTestCases;
    }

    public String getNewTestCaseTicketsFilter() {
        return newTestCaseTicketsFilter;
    }

    public String getMaintenanceTicketsFilter() {
        return maintenanceTicketsFilter;
    }

    public String getReleaseSupportTicketsFilter() {
        return releaseSupportTicketsFilter;
    }

    public double getManualCostsPerHour() {
        return manualCostsPerHour;
    }

    public void setManualCostsPerHour(double manualCostsPerHour) {
        this.manualCostsPerHour = manualCostsPerHour;
    }

    public double getAutoCostsPerHour() {
        return autoCostsPerHour;
    }

    public void setAutoCostsPerHour(double autoCostsPerHour) {
        this.autoCostsPerHour = autoCostsPerHour;
    }

    public String getTicketsFilter(JiraUtil.TicketType ticketType) {
        switch (ticketType) {
            case NEW_TEST:
                return newTestCaseTicketsFilter;
            case MAINTENANCE:
                return maintenanceTicketsFilter;
            case RELEASE_SUPPORT:
                return releaseSupportTicketsFilter;
        }
        return newTestCaseTicketsFilter;
    }

    public void setNewTestCaseTicketsFilter(String newTestCaseTicketsFilter) {
        this.newTestCaseTicketsFilter = newTestCaseTicketsFilter;
    }

    public void setMaintenanceTicketsFilter(String maintenanceTicketsFilter) {
        this.maintenanceTicketsFilter = maintenanceTicketsFilter;
    }

    public void setReleaseSupportTicketsFilter(String releaseSupportTicketsFilter) {
        this.releaseSupportTicketsFilter = releaseSupportTicketsFilter;
    }

    public int getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }

    public String getJiraUrl() {
        return jiraUrl;
    }

    public void setJiraUrl(String jiraUrl) {
        this.jiraUrl = jiraUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRunsPerPeriod() {
        return runsPerPeriod;
    }

    public void setRunsPerPeriod(int runsPerPeriod) {
        this.runsPerPeriod = runsPerPeriod;
    }

    public int getAverageTimeToPassManualTestCase() {
        return averageTimeToPassManualTestCase;
    }

    public void setAverageTimeToPassManualTestCase(int averageTimeToPassManualTestCase) {
        this.averageTimeToPassManualTestCase = averageTimeToPassManualTestCase;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
