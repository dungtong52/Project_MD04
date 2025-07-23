package entity;

import java.time.LocalDate;

public class StatisticRevenueCost {
    private float totalAmount;
    private LocalDate date;
    private int month;
    private int year;

    public StatisticRevenueCost() {
    }

    public StatisticRevenueCost(float totalAmount, LocalDate date, int month, int year) {
        this.totalAmount = totalAmount;
        this.date = date;
        this.month = month;
        this.year = year;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

}
