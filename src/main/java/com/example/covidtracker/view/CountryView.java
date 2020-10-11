package com.example.covidtracker.view;


import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class CountryView extends VerticalLayout {

    public CountryView() {
        Accordion accordion = new Accordion();
        accordion.setWidthFull();

        VerticalLayout casesAndDeathsView = new CasesAndDeathsView();
        accordion.add("Total confirmed cases and deaths for one country", casesAndDeathsView);

        VerticalLayout caseComparison = new ConfirmedCaseComparisonView();
        accordion.add("Compare total confirmed cases", caseComparison);

        VerticalLayout deathComparison = new DeathComparisonView();
        accordion.add("Compare total COVID-19 deaths", deathComparison);

        add(accordion);
    }
}
