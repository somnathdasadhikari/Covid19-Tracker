package com.example.covidtracker.utils;


import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import com.example.covidtracker.data.UtilData;

class UtilTest {
	private static JSONObject jsonObject;

	/*
	 * @Test void test() { fail("Not yet implemented"); }
	 */
	
	@BeforeAll
	public static void setupData() {
		jsonObject = UtilData.readJson(UtilData.GLOBAL_SUMMARY_FILE_PATH);
	}
	
	// The date should be formatted as dd/MM/yyyy
    @Test
    public void dateCorrectlyFormatted() throws JSONException {
        String date = jsonObject.getString(SummaryConst.DATE);
        Assertions.assertEquals("09-10-2020", Util.formatDate(date));
    }

}
