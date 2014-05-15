package edu.fdu.raysmond.store.util;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class JSONUtil {
	public static final String SUCCESS_TEXT = "success";
	public static final String FAIL_TEXT = "fail";

	/**
	 * Return a success JSON
	 */
	public static JSONObject resultJson() throws JSONException {
		return new JSONObject().put("result", SUCCESS_TEXT);
	}

	/**
	 * Return a fail JSON
	 */
	public static JSONObject resultFail() throws JSONException {
		return new JSONObject().put("result", FAIL_TEXT);
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

}
