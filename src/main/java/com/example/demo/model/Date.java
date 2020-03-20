package com.example.demo.model;

import javax.persistence.*;

@Entity
public class Date {

    @Id
    @GeneratedValue
    @Column(name="date_id")
    private int dateId;

    @ManyToOne
    @JoinColumn(name = "contact_id")
    private Contact contact;

    @Column(name="date_type")
    private String dateType;

    @Column(name="date")
    private String date;


    public Date() {
    }

    public int getDateId() {
        return dateId;
    }

    public void setDateId(int dateId) {
        this.dateId = dateId;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public String getDateType() {
        return dateType;
    }

    public void setDateType(String dateType) {
        this.dateType = dateType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
