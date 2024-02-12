package com.example.ems.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "halfDay_fullDay")
public class HalfDayFullDayEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "halfday_fullday")
    private String halfday_fullday;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHalfday_fullday() {
        return halfday_fullday;
    }

    public void setHalfday_fullday(String halfday_fullday) {
        this.halfday_fullday = halfday_fullday;
    }

    @Override
    public String toString() {
        return "HalfDayFullDayEntity{" +
                "id=" + id +
                ", halfday_fullday=" + halfday_fullday +
                '}';
    }
}
