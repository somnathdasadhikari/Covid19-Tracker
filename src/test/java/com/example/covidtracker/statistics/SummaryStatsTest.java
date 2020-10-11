package com.example.covidtracker.statistics;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.util.TreeMap;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.example.covidtracker.data.UtilData;
import com.example.covidtracker.utils.CountryName;
import com.example.covidtracker.utils.SummaryConst;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

class SummaryStatsTest {

	private static JSONObject jsonObject;
	private static SummaryStats summaryStats;

	@BeforeAll
	public static void setupData() {
		jsonObject = UtilData.readJson(UtilData.GLOBAL_SUMMARY_FILE_PATH);
		summaryStats = new SummaryStats();
		try {
			FieldUtils.writeField(summaryStats, "jsonObject", jsonObject, true);
			summaryStats.getAllCountriesSummary();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		// only run the tests if the json object is not null
		Assumptions.assumeTrue(jsonObject != null);
	}

	@Test
	public void getTimeCorrectlyFormatted() {
		String time = "06:56:52";
		Assertions.assertEquals(summaryStats.getTime(), time);
	}

	@Test
	public void validateNumberOfToltalRecovered() {
		Integer totalRecovered = 25372439;
		Assertions.assertEquals(summaryStats.getTotalRecovered(), totalRecovered);
	}

	@Test
	public void validateNumberOfTotalConfirmedCases() {
		Integer totalConfirmedCases = 36438811;
		Assertions.assertEquals(summaryStats.getTotalConfirmedCases(), totalConfirmedCases);
	}

	@Test
	public void validateNumberOfTotalDeaths() {
		Integer totalDeaths = 1060881;
		Assertions.assertEquals(summaryStats.getTotalDeaths(), totalDeaths);
	}

	@Test
	public void validateIfCacheCorrectlyStoresData() {
		try {
			Cache<String, JSONObject> testCache = Caffeine.newBuilder().build();
			testCache.put(SummaryConst.SUMMARY, jsonObject);

			Field field = SummaryStats.class.getDeclaredField("cache");
			field.setAccessible(true);
			field.set(field, testCache);

			Cache<String, JSONObject> retrievedCache = (Cache<String, JSONObject>) FieldUtils.readStaticField(field);

			Assertions.assertEquals(retrievedCache.getIfPresent(SummaryConst.SUMMARY),
					testCache.getIfPresent(SummaryConst.SUMMARY));
		} catch (IllegalAccessException | NoSuchFieldException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void validateMostCasesCorrectlySet() {
		TreeMap<Integer, String> mostCases = new TreeMap<>();
		mostCases.put(886179, CountryName.COLOMBIA.toString());
		mostCases.put(1253603, CountryName.RUSSIAN_FEDERATION.toString());
		mostCases.put(5028444, CountryName.BRAZIL.toString());
		mostCases.put(6835655, CountryName.INDIA.toString());
		mostCases.put(7605873, CountryName.UNITED_STATES_OF_AMERICA.toString());
		Assertions.assertEquals(summaryStats.getMostCases(), mostCases);
	}
	
	@Test
	public void validateMostDeathsCorrectlySet() {
		TreeMap<Integer, String> mostDeaths = new TreeMap<>();
		mostDeaths.put(42682, CountryName.UNITED_KINGDOM.toString());
		mostDeaths.put(83096, CountryName.MEXICO.toString());
		mostDeaths.put(105526, CountryName.INDIA.toString());
		mostDeaths.put(148957, CountryName.BRAZIL.toString());
		mostDeaths.put(212762, CountryName.UNITED_STATES_OF_AMERICA.toString());
		Assertions.assertEquals(summaryStats.getMostDeaths(), mostDeaths);
	}

}
