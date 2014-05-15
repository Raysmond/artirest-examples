package edu.fdu.raysmond.store.util;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class Util {
	public static final JSONObject SUCCESS = successJson();
	public static final JSONObject FAIL = failJson();

	public static JSONObject successJson() {
		try {
			return new JSONObject().put("result", "success");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static JSONObject failJson() {
		try {
			return new JSONObject().put("result", "fail");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static JSONObject wrongState(){
		try {
			return new JSONObject().put("reason", "Wrong state.");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
}
