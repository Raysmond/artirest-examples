package edu.fdu.raysmond.store.util;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class JSONUtil {
	public static final String SUCCESS_TEXT = "success";
	public static final String FAIL_TEXT = "fail";

	public static final JSONObject SUCCESS = resultSuccess();
	public static final JSONObject FAILURE = resultFail();
	public static final JSONObject WRONG_STATE = wrongState();

	/**
	 * Return a success JSON
	 */
	public static JSONObject resultSuccess() {
		try {
			return new JSONObject().put("result", SUCCESS_TEXT);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Return a fail JSON
	 */
	public static JSONObject resultFail() {
		try {
			return new JSONObject().put("result", FAIL_TEXT);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Return a wrong state result
	 */
	public static JSONObject wrongState(){
		try {
			return new JSONObject().put("result", "fail").put("reason", "Wrong state.");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Whether the JSON object has all listed fields
	 */
	public static boolean hasFields(JSONObject json, String... fields) {
		for (String field : fields) {
			if (!json.has(field))
				return false;
		}
		return true;
	}

	/**
	 * Whether all fields in a JSON object are not empty
	 */
	public static boolean notEmptyStrings(JSONObject json, String... fields) throws JSONException {
		if (!hasFields(json, fields))
			return false;

		for (String field : fields) {
			if (json.getString(field).trim().equals(""))
				return false;
		}

		return true;
	}

	public static JSONArray createInputs(String... fields) {
		JSONArray arr = new JSONArray();
		for (String field : fields) {
			arr.put(field);
		}
		return arr;
	}

}
