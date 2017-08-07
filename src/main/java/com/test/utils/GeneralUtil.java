package com.test.utils;

import org.apache.commons.lang.StringUtils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

public class GeneralUtil {

   public static int calculateWorkingDaysInMonth(LocalDate date) {
      LocalDate firstDayOfMonth = date.with(TemporalAdjusters.firstDayOfMonth());
      LocalDate lastDayOfMonth = date.with(TemporalAdjusters.lastDayOfMonth());

      int daysCount = 0;
      for (LocalDate currentDate = firstDayOfMonth; currentDate.isBefore(lastDayOfMonth.plusDays(1)); currentDate = currentDate.plusDays(1)) {
         DayOfWeek dayOfWeek = currentDate.getDayOfWeek();
         if (!(dayOfWeek.equals(DayOfWeek.SATURDAY) || dayOfWeek.equals(DayOfWeek.SUNDAY))) {
            daysCount++;
         }
      }

      switch (date.getMonth()) {
         case JANUARY:
            daysCount = daysCount - 2;
            break;
         case MARCH:
            daysCount = daysCount - 1;
            break;
         case APRIL:
            daysCount = daysCount - 1;
            break;
         case MAY:
            daysCount = daysCount - 3;
            break;
         case JUNE:
            daysCount = daysCount - 2;
            break;
         case AUGUST:
            daysCount = daysCount - 1;
            break;
         case OCTOBER:
            daysCount = daysCount - 1;
            break;
      }

      return daysCount;
   }

   public static LocalDate parseMonthStringToDate(String month) {
      return LocalDate.parse(StringUtils.capitalize(month.toLowerCase()) + " 01",
            DateTimeFormatter.ofPattern("MMMM yyyy dd"));
   }
}
