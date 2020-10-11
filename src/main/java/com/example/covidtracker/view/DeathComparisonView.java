package com.example.covidtracker.view;

import com.example.covidtracker.utils.CaseType;
import com.example.covidtracker.domain.CountryTotal;

public class DeathComparisonView<T extends CountryTotal> extends ComparisonView<T> {
    public DeathComparisonView() {
        super(CaseType.DEATHS);
        add(createResetAndCountryComboBox());
    }
}