package kke.travelplan.util;

import java.util.Calendar;
import java.util.Date;

/**
 * 연도, 월, 일 spinner 3개 쓰는 경우를 위한 도우미 클래스
 */
public class DateSpinnerHelper {
    private Date startDate;
    private Date endDate;
    private Date selectedDate;

    private int yearFrom;
    private int yearTo;
    private int monthFrom;
    private int monthTo;
    private int dayFrom;
    private int dayTo;

    public DateSpinnerHelper(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        setSelectedDate(startDate);
    }

    private void compute() {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.setTime(startDate);
        yearFrom = cal.get(Calendar.YEAR);
        cal.setTime(endDate);
        yearTo = cal.get(Calendar.YEAR);
        cal.setTime(selectedDate);
        int year = cal.get(Calendar.YEAR);

        cal.setTime(startDate);
        int startMonth = cal.get(Calendar.MONTH);
        cal.setTime(endDate);
        int endMonth = cal.get(Calendar.MONTH);
        cal.setTime(selectedDate);
        int month = cal.get(Calendar.MONTH);

        if (year == yearFrom) {
            monthFrom = startMonth;
        } else {
            monthFrom = 0;
        }
        if (year == yearTo) {
            monthTo = endMonth;
        } else {
            monthTo = 11;
        }

        cal.setTime(startDate);
        int startDay = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(endDate);
        int endDay = cal.get(Calendar.DAY_OF_MONTH);

        if (year == yearFrom && month == startMonth) {
            dayFrom = startDay;
        } else {
            dayFrom = 1;
        }
        if (year == yearTo && month == endMonth) {
            dayTo = endDay;
        } else {
            cal.setTime(selectedDate);
            dayTo = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        }
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
        compute();
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
        compute();
    }

    public Date getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(Date selectedDate) {
        this.selectedDate = selectedDate;
        compute();
    }

    public int getYearFrom() {
        return yearFrom;
    }

    public int getYearTo() {
        return yearTo;
    }

    public int getMonthFrom() {
        return monthFrom;
    }

    public int getMonthTo() {
        return monthTo;
    }

    public int getDayFrom() {
        return dayFrom;
    }

    public int getDayTo() {
        return dayTo;
    }
}
