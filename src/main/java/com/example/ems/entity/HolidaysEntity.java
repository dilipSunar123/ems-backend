package com.example.ems.entity;

import jakarta.persistence.*;

@Entity(name = "holidays")
public class HolidaysEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int holidayId;

    @Column(name = "holiday_type")
    private String holiday_type;

    @Column(name = "name")
    private String name;

    @Column(name = "date")
    private String date;

    public int getHolidayId() {
        return holidayId;
    }

    public void setHolidayId(int holidayId) {
        this.holidayId = holidayId;
    }

    public String getHoliday_type() {
        return holiday_type;
    }

    public void setHoliday_type(String holiday_type) {
        this.holiday_type = holiday_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "HolidaysEntity{" +
                "holidayId=" + holidayId +
                ", holiday_type='" + holiday_type + '\'' +
                ", name='" + name + '\'' +
                ", date=" + date +
                '}';
    }
}
