package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Contact {

    @Id
    @GeneratedValue
    @Column (name = "contact_id")
    private Integer contactId;

    @Column(name="fname")
    private String fName;

    @Column(name="mname")
    private String mName;

    @Column(name="lname")
    private String lName;

    public Contact() {
    }

    public Integer getContactId() {
        return contactId;
    }

    public void setContactId(Integer id) {
        this.contactId = id;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }
}
