package com.test.model;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

public class OutputData {

   private String month;
   private int numberOfTestCasesPerMonth;
   private int cumulativeNumberOfTestCasesPerMonth;

   private int runsPerMonth;

   private double maintenancePercentage;
   private double savingsPerMonth;
   private double maintenancePerMonth;
   private double pureSavingsHours;
   private double pureSavingsPercent;
   private double savingDifference;

   private double autoHoursPerMonth;
   private double manualHoursPerMonthWithAutomation;
   private double costPerMonthWithAutomation;

   private double manualHourPerMonthWithoutAutomation;
   private double costPerMonthWithoutAutomation;

   private double cumulativeCostPerMonthWithoutAutomation;
   private double cumulativeCostPerMonthWithAutomation;
   private double priceDifferencePureAutoHours;

   private double totalAutoCostPerMonth;
   private double totalCumulativeAutoCostPerMonth;
   private double priceDifferenceFullAutoHours;

   private double timeForTestCreation;
   private double timeForReleaseSupport;

   private int autoTeamCapacity;
   private int monthlyCapacityPerOneEngineer;

   public int getMonthlyCapacityPerOneEngineer() {
      return monthlyCapacityPerOneEngineer;
   }

   public void setMonthlyCapacityPerOneEngineer(int monthlyCapacityPerOneEngineer) {
      this.monthlyCapacityPerOneEngineer = monthlyCapacityPerOneEngineer;
   }

   public double getPriceDifferencePureAutoHours() {
      return priceDifferencePureAutoHours;
   }

   public void setPriceDifferencePureAutoHours(double priceDifferencePureAutoHours) {
      this.priceDifferencePureAutoHours = roundDouble(priceDifferencePureAutoHours);
   }

   public double getPriceDifferenceFullAutoHours() {
      return priceDifferenceFullAutoHours;
   }

   public void setPriceDifferenceFullAutoHours(double priceDifferenceFullAutoHours) {
      this.priceDifferenceFullAutoHours = roundDouble(priceDifferenceFullAutoHours);
   }

   public int getRunsPerMonth() {
      return runsPerMonth;
   }

   public void setRunsPerMonth(int runsPerMonth) {
      this.runsPerMonth = runsPerMonth;
   }

   public int getCumulativeNumberOfTestCasesPerMonth() {
      return cumulativeNumberOfTestCasesPerMonth;
   }

   public void setCumulativeNumberOfTestCasesPerMonth(int cumulativeNumberOfTestCasesPerMonth) {
      this.cumulativeNumberOfTestCasesPerMonth = cumulativeNumberOfTestCasesPerMonth;
   }

   public double getTotalAutoCostPerMonth() {
      return totalAutoCostPerMonth;
   }

   public void setTotalAutoCostPerMonth(double totalAutoCostPerMonth) {
      this.totalAutoCostPerMonth = roundDouble(totalAutoCostPerMonth);
   }

   public double getTotalCumulativeAutoCostPerMonth() {
      return totalCumulativeAutoCostPerMonth;
   }

   public void setTotalCumulativeAutoCostPerMonth(double totalCumulativeAutoCostPerMonth) {
      this.totalCumulativeAutoCostPerMonth = roundDouble(totalCumulativeAutoCostPerMonth);
   }

   public int getAutoTeamCapacity() {
      return autoTeamCapacity;
   }

   public void setAutoTeamCapacity(int autoTeamCapacity) {
      this.autoTeamCapacity = autoTeamCapacity;
   }

   public double getTimeForTestCreation() {
      return timeForTestCreation;
   }

   public void setTimeForTestCreation(double timeForTestCreation) {
      this.timeForTestCreation = roundDouble(timeForTestCreation);
   }

   public double getAutoHoursPerMonth() {
      return autoHoursPerMonth;
   }

   public void setAutoHoursPerMonth(double autoHoursPerMonth) {
      this.autoHoursPerMonth = roundDouble(autoHoursPerMonth);
   }

   public double getManualHoursPerMonthWithAutomation() {
      return manualHoursPerMonthWithAutomation;
   }

   public void setManualHoursPerMonthWithAutomation(double manualHoursPerMonthWithAutomation) {
      this.manualHoursPerMonthWithAutomation = roundDouble(manualHoursPerMonthWithAutomation);
   }

   public double getManualHourPerMonthWithoutAutomation() {
      return manualHourPerMonthWithoutAutomation;
   }

   public void setManualHourPerMonthWithoutAutomation(double manualHourPerMonthWithoutAutomation) {
      this.manualHourPerMonthWithoutAutomation = roundDouble(manualHourPerMonthWithoutAutomation);
   }

   public double getCostPerMonthWithoutAutomation() {
      return costPerMonthWithoutAutomation;
   }

   public void setCostPerMonthWithoutAutomation(double costPerMonthWithoutAutomation) {
      this.costPerMonthWithoutAutomation = roundDouble(costPerMonthWithoutAutomation);
   }

   public double getCostPerMonthWithAutomation() {
      return costPerMonthWithAutomation;
   }

   public void setCostPerMonthWithAutomation(double costPerMonthWithAutomation) {
      this.costPerMonthWithAutomation = roundDouble(costPerMonthWithAutomation);
   }

   public double getCumulativeCostPerMonthWithoutAutomation() {
      return cumulativeCostPerMonthWithoutAutomation;
   }

   public void setCumulativeCostPerMonthWithoutAutomation(double cumulativeCostPerMonthWithoutAutomation) {
      this.cumulativeCostPerMonthWithoutAutomation = roundDouble(cumulativeCostPerMonthWithoutAutomation);
   }

   public double getCumulativeCostPerMonthWithAutomation() {
      return cumulativeCostPerMonthWithAutomation;
   }

   public void setCumulativeCostPerMonthWithAutomation(double cumulativeCostPerMonthWithAutomation) {
      this.cumulativeCostPerMonthWithAutomation = roundDouble(cumulativeCostPerMonthWithAutomation);
   }

   public double getTimeForReleaseSupport() {
      return timeForReleaseSupport;
   }

   public void setTimeForReleaseSupport(double timeForReleaseSupport) {
      this.timeForReleaseSupport = roundDouble(timeForReleaseSupport);
   }

   public String getMonth() {
      return month;
   }

   public void setMonth(String periodId) {
      this.month = periodId;
   }

   public int getNumberOfTestCasesPerMonth() {
      return numberOfTestCasesPerMonth;
   }

   public void setNumberOfTestCasesPerMonth(int numberOfTestCasesPerMonth) {
      this.numberOfTestCasesPerMonth = numberOfTestCasesPerMonth;
   }

   public double getMaintenancePercentage() {
      return maintenancePercentage;
   }

   public void setMaintenancePercentage(double maintenancePercentage) {
      this.maintenancePercentage = roundDouble(maintenancePercentage);
   }

   public double getSavingsPerMonth() {
      return savingsPerMonth;
   }

   public void setSavingsPerMonth(double savingsPerMonth) {
      this.savingsPerMonth = roundDouble(savingsPerMonth);
   }

   public double getMaintenancePerMonth() {
      return maintenancePerMonth;
   }

   public void setMaintenancePerMonth(double maintenancePerMonth) {
      this.maintenancePerMonth = roundDouble(maintenancePerMonth);
   }

   public double getPureSavingsHours() {
      return pureSavingsHours;
   }

   public void setPureSavingsHours(double pureSavingsHours) {
      this.pureSavingsHours = roundDouble(pureSavingsHours);
   }

   public double getPureSavingsPercent() {
      return pureSavingsPercent;
   }

   public void setPureSavingsPercent(double pureSavingsPercent) {
      this.pureSavingsPercent = roundDouble(pureSavingsPercent);
   }

   public double getSavingDifference() {
      return savingDifference;
   }

   public void setSavingDifference(double savingDifference) {
      this.savingDifference = roundDouble(savingDifference);
   }


   @Override
   public String toString() {
      return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
   }

   private double roundDouble(double value) {
      return BigDecimal.valueOf(value).setScale(2, BigDecimal.ROUND_HALF_EVEN).doubleValue();
   }
}
