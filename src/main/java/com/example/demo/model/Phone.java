package com.example.demo.model;

import javax.persistence.*;

@Entity
public class Phone {

    @Id
    @GeneratedValue
    @Column(name="phone_id")
    private int phoneId;

    @ManyToOne
    @JoinColumn(name = "contact_id")
    private Contact contact;

    @Column(name="phone_type")
    private String phoneType;

    @Column(name="area_code")
    private String areaCode;

    @Column(name="number")
    private String number;

    public Phone() {
    }

    public int getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(int phoneId) {
        this.phoneId = phoneId;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public String getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(String phoneType) {
        this.phoneType = phoneType;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}

