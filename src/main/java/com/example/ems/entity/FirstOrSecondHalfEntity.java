package com.example.ems.entity;

import jakarta.persistence.*;

@Entity(name = "first_or_second_half")
public class FirstOrSecondHalfEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "first_second_full_day")
    private String first_second_full_day;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_second_full_day() {
        return first_second_full_day;
    }

    public void setFirst_second_full_day(String first_second_full_day) {
        this.first_second_full_day = first_second_full_day;
    }

    @Override
    public String toString() {
        return "FirstOrSecondHalfEntity{" +
                "id=" + id +
                ", first_second_full_day='" + first_second_full_day + '\'' +
                '}';
    }
}
