package ru.javawebinar.topjava.util;

import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static <T extends Comparable<T>> boolean isBetweenHalfOpen(T lt, T start, T end) {
        return lt.compareTo(start) >= 0 && lt.compareTo(end) < 0;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }

    public static LocalDate parseLocalDate(String localDate) {
        return StringUtils.isEmpty(localDate) ? null : LocalDate.parse(localDate);
    }

    public static LocalTime parseLocalTime(String localTime) {
        return StringUtils.isEmpty(localTime) ? null : LocalTime.parse(localTime);
    }

    public static LocalDate getFilteredMinDate(LocalDate date) {
        return date == null ? LocalDate.MIN : date.minusDays(1);
    }

    public static LocalDate getFilteredMaxDate(LocalDate date) {
        return date == null ? LocalDate.MAX : date.plusDays(1);
    }

    public static LocalTime getFilteredMinTime(LocalTime time) {
        return time == null ? LocalTime.MIN : time.minusNanos(1);
    }

    public static LocalTime getFilteredMaxTime(LocalTime time) {
        return time == null ? LocalTime.MAX : time.plusNanos(1);
    }


}

