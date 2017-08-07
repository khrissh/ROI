package com.test.model;

public class OutputDataBuilder {

   OutputData outputData;

   public OutputDataBuilder(OutputData outputData) {
      this.outputData = outputData;
   }

   public OutputDataBuilder() {
      this.outputData = new OutputData();
   }

   public OutputDataBuilder setMonth(String month) {
      outputData.setMonth(month);
      return this;
   }

   public OutputDataBuilder setRunsPerMonth(int runsPerMonth) {
      outputData.setRunsPerMonth(runsPerMonth);
      return this;
   }

   public OutputDataBuilder setNumberOfTestCasesPerMonth(int numberOfTestCasesPerMonth) {
      outputData.setNumberOfTestCasesPerMonth(numberOfTestCasesPerMonth);
      return this;
   }

   public OutputDataBuilder setAutoTeamCapacity(int autoTeamCapacity) {
      outputData.setAutoTeamCapacity(autoTeamCapacity);
      return this;
   }

   public OutputDataBuilder setTimeForTestCreation(double timeForTestCreation) {
      outputData.setTimeForTestCreation(timeForTestCreation);
      return this;
   }

   public OutputDataBuilder setMaintenancePerPeriod(double maintenanceTimePerMonth) {
      outputData.setMaintenancePerMonth(maintenanceTimePerMonth);
      return this;
   }

   public OutputDataBuilder setMaintenancePercentage(double maintenancePercentage) {
      outputData.setMaintenancePercentage(maintenancePercentage);
      return this;
   }

   public OutputDataBuilder setTimeForReleaseSupport(double timeForReleaseSupport) {
      outputData.setTimeForReleaseSupport(timeForReleaseSupport);
      return this;
   }

   public OutputDataBuilder setCumulativeNumberOfTestCasesPerMonth(int cumulativeNumberOfTestCasesPerMonth) {
      outputData.setCumulativeNumberOfTestCasesPerMonth(cumulativeNumberOfTestCasesPerMonth);
      return this;
   }

   public OutputDataBuilder setSavingsPerMonth(double savingsPerMonth) {
      outputData.setSavingsPerMonth(savingsPerMonth);
      return this;
   }

   public OutputDataBuilder setPureSavingsHours(double pureSavingsHours) {
      outputData.setPureSavingsHours(pureSavingsHours);
      return this;
   }

   public OutputDataBuilder setPureSavingsPercent(double pureSavingsPercent) {
      outputData.setPureSavingsPercent(pureSavingsPercent);
      return this;
   }

   public OutputDataBuilder setSavingDifference(double savingDifference) {
      outputData.setSavingDifference(savingDifference);
      return this;
   }

   public OutputDataBuilder setAutoHoursPerMonth(double autoHoursPerMonth) {
      outputData.setAutoHoursPerMonth(autoHoursPerMonth);
      return this;
   }

   public OutputDataBuilder setMonthlyCapacityPerOneEngineer(int monthCapacityPerOneEngineer) {
      outputData.setMonthlyCapacityPerOneEngineer(monthCapacityPerOneEngineer);
      return this;
   }

   public OutputDataBuilder setManualHoursPerMonthWithAutomation(double manualHoursPerMonth) {
      outputData.setManualHoursPerMonthWithAutomation(manualHoursPerMonth);
      return this;
   }

   public OutputDataBuilder setManualHourPerMonthWithoutAutomation(double manualHourPerMonthWithoutAutomation) {
      outputData.setManualHourPerMonthWithoutAutomation(manualHourPerMonthWithoutAutomation);
      return this;
   }

   public OutputDataBuilder setCostsPerMonthWithAutomation(double autoCostPerMonth) {
      outputData.setCostPerMonthWithAutomation(autoCostPerMonth);
      return this;
   }

   public OutputDataBuilder setCostsPerMonthWithoutAutomation(double manualCostPerMonth) {
      outputData.setCostPerMonthWithoutAutomation(manualCostPerMonth);
      return this;
   }

   public OutputDataBuilder setCumulativeAutoCostPerMonth(double cumulativeAutoCostPerMonth) {
      outputData.setCumulativeCostPerMonthWithAutomation(cumulativeAutoCostPerMonth);
      return this;
   }

   public OutputDataBuilder setCumulativeManualCostPerMonth(double cumulativeManualCostPerMonth) {
      outputData.setCumulativeCostPerMonthWithoutAutomation(cumulativeManualCostPerMonth);
      return this;
   }

   public OutputDataBuilder setTotalAutoCostPerMonth(double totalAutoCostPerMonth) {
      outputData.setTotalAutoCostPerMonth(totalAutoCostPerMonth);
      return this;
   }

   public OutputDataBuilder setTotalCumulativeAutoCostPerMonth(double totalCumulativeAutoCostPerMonth) {
      outputData.setTotalCumulativeAutoCostPerMonth(totalCumulativeAutoCostPerMonth);
      return this;
   }

   public OutputDataBuilder setPriceDifferencePureAutoHours(double priceDifferencePureAutoHours) {
      outputData.setPriceDifferencePureAutoHours(priceDifferencePureAutoHours);
      return this;
   }

   public OutputDataBuilder setPriceDifferenceFullAutoHours(double priceDifferenceFullAutoHours) {
      outputData.setPriceDifferenceFullAutoHours(priceDifferenceFullAutoHours);
      return this;
   }

   public OutputData build() {
      return outputData;
   }


}
