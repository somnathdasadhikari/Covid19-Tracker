package com.example.covidtracker.data;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.util.ResourceUtils;

public class UtilData {
	
	private static final String TEST_RESOURCES = "classpath:";
    public static final String GLOBAL_SUMMARY_FILE_PATH = TEST_RESOURCES + "GlobalSummary.json";
    public static final String COUNTRY_CONFIRMED_FILE_PATH = TEST_RESOURCES + "CountryConfirmedCases.json";
    public static final String COUNTRY_DEATHS_FILE_PATH = TEST_RESOURCES + "CountryDeaths.json";

    public static JSONObject readJson(String filePath) {
        JSONObject jsonObject = null;
        try {
        	File file = ResourceUtils.getFile(filePath);
            String json = new String(Files.readAllBytes(file.toPath()));
            jsonObject = new JSONObject(json);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static JSONArray readJsonArray(String filePath) {
        JSONArray jsonArray = null;
        try {
        	File file = ResourceUtils.getFile(filePath);
            String json = new String(Files.readAllBytes(file.toPath()));
            try {
				jsonArray = new JSONArray(json);
			} catch (JSONException e) {
				e.printStackTrace();
			}
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

}
