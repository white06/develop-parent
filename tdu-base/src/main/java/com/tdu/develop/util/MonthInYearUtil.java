package com.tdu.develop.util;

import java.io.Serializable;

public class MonthInYearUtil implements Serializable {
    private String month;
    private int minute;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
}
