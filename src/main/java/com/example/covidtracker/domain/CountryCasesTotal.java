package com.example.covidtracker.domain;


public class CountryCasesTotal extends CountryTotal {
    public CountryCasesTotal(int cases, String date, String countryName) {
        super(countryName, date, cases);
    }

    @Override
    public String toString() {
        return "CountryCasesTotal{" +
                "country=" + super.getCountryName() +
                ", cases=" + super.getNumberOfCases() +
                ", date='" + getDate() + '\'' +
                '}';
    }
}

