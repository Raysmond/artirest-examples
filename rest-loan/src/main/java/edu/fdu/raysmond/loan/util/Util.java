package edu.fdu.raysmond.loan.util;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class Util {

	// Required application form fields
	public static final String inputs[] = { "user_name", "user_id", "amount" };

	/**
	 * Initial data
	 */
	public static JSONArray initialData() {
		try {
			JSONObject json1 = new JSONObject().put("user_name", "Raysmond")
					.put("user_id", "1001")
					.put("amount", 18000);
			
			JSONObject json2 = new JSONObject().put("user_name", "HelloTissue")
					.put("user_id", "1002")
					.put("amount", 20480);
			
			return new JSONArray().put(json1).put(json2);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
