package com.example.covidtracker.statistics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.covidtracker.domain.CountryCasesTotal;
import com.example.covidtracker.domain.CountryDeathsTotal;
import com.example.covidtracker.domain.CountryTotal;
import com.example.covidtracker.utils.ApiConst;
import com.example.covidtracker.utils.CaseType;
import com.example.covidtracker.utils.CountryName;
import com.example.covidtracker.utils.DayOneTotalConst;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DayOneTotalStats <T extends CountryTotal> {
    private static final OkHttpClient client;
    private static final Logger logger;

    private final String slug;
    private final CaseType caseType;
    private ArrayList<CountryCasesTotal> caseTotals;
    private ArrayList<CountryDeathsTotal> deathTotals;

    static {
        client = new OkHttpClient().newBuilder()
                .build();

        logger = Logger.getLogger(DayOneTotalStats.class.getName());
        logger.setLevel(Level.INFO);
    }

    public DayOneTotalStats(CountryName countryName, CaseType caseType) {
        this.slug = countryName.getSlug(countryName);
        this.caseType = caseType;

        JSONArray dayOneTotal = fetchDayOneTotalJson();
        if (dayOneTotal != null) {
            switch (caseType) {
                case CONFIMRED:
                    this.caseTotals = (ArrayList<CountryCasesTotal>) fetchDayOneTotal(dayOneTotal, CaseType.CONFIMRED);
                    break;
                case DEATHS:
                    this.deathTotals = (ArrayList<CountryDeathsTotal>) fetchDayOneTotal(dayOneTotal, CaseType.DEATHS);
                    break;
            }
        } else {
            logger.log(Level.FINE, "The retrieved day one total is null");
        }
    }

    private JSONArray fetchDayOneTotalJson() {
        String url = getUrl();
        logger.log(Level.INFO, url);

        Request request = new Request.Builder()
                .url(url)
                .method(ApiConst.method, null)
                .build();
        JSONArray jsonArray = null;
        try {
            Response response = client.newCall(request).execute();
            String data = response.body().string();

            logger.log(Level.INFO, "Day One Total data:\n" + data);
            jsonArray = new JSONArray(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    private String getUrl() {
        String dayOneUrl = ApiConst.apiUrl + DayOneTotalConst.prefixUrl + slug;
        switch (caseType) {
            case CONFIMRED:
                dayOneUrl += DayOneTotalConst.suffixConfirmedUrl;
                break;
            case DEATHS:
                dayOneUrl += DayOneTotalConst.suffixDeathsUrl;
                break;
            case RECOVERED:
                break;
            default:
                logger.log(Level.FINEST, "A new case type has been added but is not handled");
        }
        return dayOneUrl;
    }

    private ArrayList<T> fetchDayOneTotal(JSONArray jsonArray, CaseType caseType) {
        ArrayList<T> caseTotals = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String date = jsonObject.getString(DayOneTotalConst.DATE);

            if (caseType == CaseType.CONFIMRED) {
                int cases = jsonObject.getInt(DayOneTotalConst.CASES);
                String country = jsonObject.getString("Country");
                CountryCasesTotal countryCases = new CountryCasesTotal(cases, date, country);
                caseTotals.add((T) countryCases);
            } else {
                int deaths = jsonObject.getInt(DayOneTotalConst.CASES);
                String country = jsonObject.getString("Country");
                CountryDeathsTotal countryDeaths = new CountryDeathsTotal(deaths, date, country);
                caseTotals.add((T) countryDeaths);
            }
        }
        return caseTotals;
    }

    public ArrayList<CountryCasesTotal> getTotalConfirmedCases() {
        return caseTotals;
    }

    public ArrayList<CountryDeathsTotal> getTotalDeaths() {
        return deathTotals;
    }
}