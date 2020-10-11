package com.example.covidtracker.view;

import com.example.covidtracker.utils.CaseType;
import com.example.covidtracker.domain.CountryTotal;

public class ConfirmedCaseComparisonView<T extends CountryTotal> extends ComparisonView<T> {
    public ConfirmedCaseComparisonView() {
        super(CaseType.CONFIMRED);
        add(createResetAndCountryComboBox());
    }
}
