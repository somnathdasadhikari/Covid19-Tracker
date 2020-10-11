package com.example.covidtracker.view;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.example.covidtracker.utils.CaseType;
import com.example.covidtracker.utils.CountryName;
import com.vaadin.flow.component.charts.Chart;
import com.example.covidtracker.domain.*;
import com.example.covidtracker.statistics.DayOneTotalStats;

public class CasesAndDeathsView <T extends CountryTotal> extends BaseCaseView<T> {
    private static final Logger logger = Logger.getLogger(CountryView.class.getName());

    private Number[] confirmedCases;
    private ArrayList<CountryCasesTotal> confirmedTotal;
    private ArrayList<CountryDeathsTotal> deathTotal;
    private Number[] deaths;
    private String[] deathDates;
    private String[] confirmedCaseDates;

    public CasesAndDeathsView() {
        add(configureCountryNameComboBox());
    }

    @Override
    protected void createGraph(CountryName countryName) {
        DayOneTotalStats dayOneTotalCases = new DayOneTotalStats(countryName, CaseType.CONFIMRED);
        confirmedTotal = dayOneTotalCases.getTotalConfirmedCases();
        DayOneTotalStats dayOneTotalDeaths = new DayOneTotalStats(countryName, CaseType.DEATHS);
        deathTotal = dayOneTotalDeaths.getTotalDeaths();

        if (isTotalEmpty((ArrayList<T>) confirmedTotal, countryName) ||
                isTotalEmpty((ArrayList<T>) deathTotal, countryName)) {
            logger.log(Level.FINE, "Data was empty");
            return;
        }

        // the following lines must be called before calling setChartConfig()
        setCases();
        setDeaths();

        String chartTitle = "Number of deaths confirmed cases since the first death and confirmed case";
        String yAxisName = "Number of deaths & confirmed cases since day 1";
        setChartConfig(chartTitle, yAxisName, "Confirmed cases", confirmedCases, confirmedCaseDates);
        setChartConfig(chartTitle, yAxisName, "Deaths", deaths, deathDates);

        Chart casesAndDeathsChart = getCasesChart();
        add(casesAndDeathsChart);
    }

    private void setCases() {
        confirmedCaseDates = new String[confirmedTotal.size()];
        confirmedCases = new Number[confirmedTotal.size()];

        for (int i = 0; i < confirmedTotal.size(); i++) {
            CountryCasesTotal countryLive = confirmedTotal.get(i);
            confirmedCaseDates[i] = countryLive.getDate();
            confirmedCases[i] = countryLive.getNumberOfCases();
        }
    }

    private void setDeaths() {
        deathDates = new String[deathTotal.size()];
        deaths = new Number[confirmedTotal.size()];

        for (int i = 0; i < deathTotal.size(); i++) {
            CountryDeathsTotal countryLive = deathTotal.get(i);
            deathDates[i] = countryLive.getDate();
            deaths[i] = countryLive.getNumberOfCases();
        }
    }

	
}

