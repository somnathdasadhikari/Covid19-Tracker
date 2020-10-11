package com.example.covidtracker.statistics;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.json.JSONArray;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.example.covidtracker.data.UtilData;
import com.example.covidtracker.domain.CountryCasesTotal;
import com.example.covidtracker.domain.CountryDeathsTotal;
import com.example.covidtracker.utils.CaseType;
import com.example.covidtracker.utils.CountryName;
import java.lang.reflect.InvocationTargetException;
import com.example.covidtracker.domain.CountryTotal;

class DayOneTotalStatsTest {
	private static DayOneTotalStats<CountryCasesTotal> countryBasedPerDayConfirmedStats;
    private static DayOneTotalStats<CountryDeathsTotal> countryBasedPerDayDeathStats;
    
    @BeforeAll
    public static void setupData() {
    	JSONArray confirmedCasesJson = UtilData.readJsonArray(UtilData.COUNTRY_CONFIRMED_FILE_PATH);
    	countryBasedPerDayConfirmedStats = new DayOneTotalStats<>(CountryName.INDIA, CaseType.CONFIMRED);
    	JSONArray deathCasesJson = UtilData.readJsonArray(UtilData.COUNTRY_DEATHS_FILE_PATH);
    	countryBasedPerDayDeathStats = new DayOneTotalStats<>(CountryName.INDIA, CaseType.DEATHS);
    	 try {
    		 Method fetchTotalConfirmedCasesPerDay = countryBasedPerDayConfirmedStats.getClass().getDeclaredMethod("fetchDayOneTotal", JSONArray.class, CaseType.class);
    		 fetchTotalConfirmedCasesPerDay.setAccessible(true);
    		 ArrayList<CountryCasesTotal> caseTotal = (ArrayList<CountryCasesTotal>) fetchTotalConfirmedCasesPerDay.invoke(countryBasedPerDayConfirmedStats, confirmedCasesJson, CaseType.CONFIMRED);
    		 FieldUtils.writeField(countryBasedPerDayConfirmedStats, "caseTotals", caseTotal, true);
    		 
    		 Method fetchTotalDeathsPerDay = countryBasedPerDayDeathStats.getClass().getDeclaredMethod("fetchDayOneTotal", JSONArray.class, CaseType.class);
    		 fetchTotalDeathsPerDay.setAccessible(true);
    		 ArrayList<CountryDeathsTotal> deathTotal = (ArrayList<CountryDeathsTotal>) fetchTotalDeathsPerDay.invoke(countryBasedPerDayDeathStats, deathCasesJson, CaseType.DEATHS);
    		 FieldUtils.writeField(countryBasedPerDayDeathStats, "deathTotals", deathTotal, true);
         } 
    	 catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
             e.printStackTrace();
         }
    	 Assumptions.assumeTrue(confirmedCasesJson != null && deathCasesJson != null);
    }
    
    @Test
    public void validateCountryBasedDailyConfirmedCasesReportUrl() {
        String actualUrl = "https://api.covid19api.com/total/dayone/country/india/status/confirmed";
        try {
            Method method = countryBasedPerDayConfirmedStats.getClass().getDeclaredMethod("getUrl");
            method.setAccessible(true);
            String expectedUrl = (String) method.invoke(countryBasedPerDayConfirmedStats);
            Assertions.assertEquals(actualUrl, expectedUrl);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void validateCountryBasedDailyDeathsReportUrl() {
        String actualUrl = "https://api.covid19api.com/total/dayone/country/india/status/deaths";
        try {
            Method method = countryBasedPerDayDeathStats.getClass().getDeclaredMethod("getUrl");
            method.setAccessible(true);
            String expectedUrl = (String) method.invoke(countryBasedPerDayDeathStats);
            Assertions.assertEquals(actualUrl, expectedUrl);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void validateCountryBasedDailyConfirmedCases() {
    	Integer lastUpdatedTotalConfirmedCases = 6906151;
    	ArrayList<CountryCasesTotal> countryBasedDailyConfirmedCases = countryBasedPerDayConfirmedStats.getTotalConfirmedCases();
    	CountryTotal countryBasedLastUpdatedConfirmedCases = countryBasedDailyConfirmedCases.get(countryBasedDailyConfirmedCases.size() - 1);
    	Assertions.assertEquals(lastUpdatedTotalConfirmedCases, countryBasedLastUpdatedConfirmedCases.getNumberOfCases());
    }
    
    @Test
    public void validateCountryBasedDailyDeathCases() {
    	Integer lastUpdatedDeathCases = 106490;
    	ArrayList<CountryDeathsTotal> countryBasedDailyDeathCases = countryBasedPerDayDeathStats.getTotalDeaths();
    	CountryTotal countryBasedLastUpdatedDeathCases = countryBasedDailyDeathCases.get(countryBasedDailyDeathCases.size() - 1);
    	Assertions.assertEquals(lastUpdatedDeathCases, countryBasedLastUpdatedDeathCases.getNumberOfCases());
    }
}
