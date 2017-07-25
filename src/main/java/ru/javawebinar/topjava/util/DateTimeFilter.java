package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Created by bukreev-a on 24.07.2017.
 */
public class DateTimeFilter {
    private LocalDate fromDate;
    private LocalDate toDate;
    private LocalTime fromTime;
    private LocalTime toTime;

    public LocalDate getFromDate() {
        return fromDate;
    }

    public LocalDate takeFromDate() {
        return fromDate==null?LocalDate.MIN:fromDate;
    }
    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public LocalDate takeToDate() {
        return toDate==null?LocalDate.MAX:toDate;
    }
    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public LocalTime getFromTime() {
        return fromTime;
    }

    public LocalTime takeFromTime() {
        return fromTime==null?LocalTime.MIN:fromTime;
    }
    public void setFromTime(LocalTime fromTime) {
        this.fromTime = fromTime;
    }

    public LocalTime getToTime() {
        return toTime;
    }

    public LocalTime takeToTime() {
        return toTime==null?LocalTime.MAX:toTime;
    }
    public void setToTime(LocalTime toTime) {
        this.toTime = toTime;
    }

    public boolean noDateFilter() {
      return fromDate==null && toDate==null;
    }

}
