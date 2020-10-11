package com.example.covidtracker.domain;


public class CountryDeathsTotal extends CountryTotal {

    public CountryDeathsTotal(int cases, String date, String countryName) {
        super(countryName, date, cases);
    }

    @Override
    public String toString() {
        return "CountryDeathsTotal{" +
                "name=" + super.getCountryName() +
                ", cases=" + super.getNumberOfCases() +
                ", date='" + super.getDate() + '\'' +
                '}';
    }
}