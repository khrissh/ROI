package com.test.data.entity;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class InputPeriod {

    @Id
    private String periodId;
    private String numberOfTestCasesPerPeriod;
    private String maintenancePercentage;

    private String savingsPerPeriod;
    private String maintenancePerPeriod;
    private String pureSavingsHours;
    private String pureSavingsPercent;
    private String savingDifference;

    public String getSavingsPerPeriod() {
        return savingsPerPeriod;
    }

    public void setSavingsPerPeriod(String savingsPerPeriod) {
        this.savingsPerPeriod = savingsPerPeriod;
    }

    public String getMaintenancePerPeriod() {
        return maintenancePerPeriod;
    }

    public void setMaintenancePerPeriod(String maintenancePerPeriod) {
        this.maintenancePerPeriod = maintenancePerPeriod;
    }

    public String getPureSavingsHours() {
        return pureSavingsHours;
    }

    public void setPureSavingsHours(String pureSavingsHours) {
        this.pureSavingsHours = pureSavingsHours;
    }

    public String getPureSavingsPercent() {
        return pureSavingsPercent;
    }

    public void setPureSavingsPercent(String pureSavingsPercent) {
        this.pureSavingsPercent = pureSavingsPercent;
    }

    public String getSavingDifference() {
        return savingDifference;
    }

    public void setSavingDifference(String savingDifference) {
        this.savingDifference = savingDifference;
    }

    public String getPeriodId() {
        return periodId;
    }

    public void setPeriodId(String periodId) {
        this.periodId = periodId;
    }

    public String getNumberOfTestCasesPerPeriod() {
        return numberOfTestCasesPerPeriod;
    }

    public void setNumberOfTestCasesPerPeriod(String numberOfTestCasesPerPeriod) {
        this.numberOfTestCasesPerPeriod = numberOfTestCasesPerPeriod;
    }

    public String getMaintenancePercentage() {
        return maintenancePercentage;
    }

    public void setMaintenancePercentage(String maintenancePercentage) {
        this.maintenancePercentage = maintenancePercentage;
    }

    @Override
    public String toString(){
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
