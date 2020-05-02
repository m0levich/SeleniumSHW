package com.github.m0levich;

public class Company {
    private String company;
    private String contact;
    private String country;

    public Company(String company, String contact, String country) {
        this.company = company;
        this.contact = contact;
        this.country = country;
    }

    public String getCompany() {
        return company;
    }

    public String getContact() {
        return contact;
    }

    public String getCountry() {
        return country;
    }
}
