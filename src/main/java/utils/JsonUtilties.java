package utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import spiceJet.constants.FrameWorkConstants;

public class JsonUtilties {

	public Object[] readMultiJsonData(String fileName, String testName) {
		return readMultiJsonData(fileName, testName, false);
	}

	public Object[] readMultiJsonData(String fileName, String testName, boolean directpath) {
		JSONObject jsonObject = null;
		try {
			jsonObject = (JSONObject) new JSONParser()
					.parse(new FileReader(new File(getJsonFilePath(fileName, directpath))));
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		JSONArray dataSets = (JSONArray) jsonObject.get(testName);
		Object[] data = new Object[dataSets.size()];
		for (int i = 0; i < dataSets.size(); i++) {
			data[i] = toMap((JSONObject) dataSets.get(i));
		}
		return data;
	}

	public Object[] readMultiJsonData(String fieldName) {
		return readMultiJsonData(fieldName, "dataset");
	}

	public String getJsonFilePath(String fileName, boolean isDirectPath) {
		String Filepath = FrameWorkConstants.BASE_PATH + fileName;
		return Filepath;

	}

	public static Map<String, Object> toMap(JSONObject jsonObject) throws JSONException {
		Map<String, Object> map = new HashMap<String, Object>();
		@SuppressWarnings("unchecked")
		Set<JSONObject> keys = jsonObject.keySet();
		for (Object KeyObj : keys) {
			String key = KeyObj.toString();
			Object value = jsonObject.get(KeyObj);
			if (value instanceof JSONArray) {
				value = toList((JSONArray) value);
			} else if (value instanceof JSONObject) {
				value = toMap((JSONObject) value);
			}
			map.put(key, value);
		}
		return map;

	}

	public static List<Object> toList(JSONArray array) throws JSONException {
		List<Object> list = new ArrayList<Object>();
		for (int i = 0; i < list.size(); i++) {
			Object value = list.get(i);
			if (value instanceof JSONArray) {
				value = toList((JSONArray) value);
			} else if (value instanceof JSONObject) {
				value = toMap((JSONObject) value);
			}
			list.add(value);
		}

		return list;
	}
}
