package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import model.Car;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class BaiduApiUtil {
	static int count = 0;
	public static double getMileage(List<Car> carList) {
		double sumMileage = 0;
		int length = carList.size();
		int group = length / 6;
		int mode = length % 6;

		for (int i = 0; i < group; i++) {
			List<Car> waypoints = carList.subList(i * 6 + 1, (i + 1) * 6);
			Car carStart = carList.get(i * 6);
			Car carEnd = carList.get((i + 1) * 6);
			sumMileage += getMileageOnce(carStart, carEnd, waypoints);
		}
		if (mode != 0) {
			List<Car> waypoints1 = carList.subList(group * 6 + 1, length - 1);
			Car carStart1 = carList.get(group * 6);
			Car carEnd1 = carList.get(length - 1);
			sumMileage += getMileageOnce(carStart1, carEnd1, waypoints1);
		}
		return sumMileage / 1000;
	}

	public static double getMileageOnce(Car carStart, Car carEnd, List<Car> waypointsList) {
		double sumMileage = 0;
		String waypoints = "";
		String url = "";
		String start_lng = carStart.getLON();
		String start_lat = carStart.getLAT();

		String end_lng = carEnd.getLON();
		String end_lat = carEnd.getLAT();
		int length = waypointsList.size();
		for (int i = 0; i < length; i++) {
			waypoints += waypointsList.get(i).getLAT() + "," + waypointsList.get(i).getLON() + "|";
		}
		// String wayString = waypoints.replace("", "");
		if (waypoints != "") {
			url = "http://api.map.baidu.com/direction/v1?mode=driving&origin=" + start_lat + "," + start_lng + "&destination=" + end_lat + "," + end_lng + "&origin_region=大连&destination_region=大连&output=json&waypoints=" + waypoints + "&ak=cnG69M9AWuP8G9X96hGW1rxAaGGb8MyE";
			url = url.replace("|&ak=", "&ak=");
		} else {
			url = "http://api.map.baidu.com/direction/v1?mode=driving&origin=" + start_lat + "," + start_lng + "&destination=" + end_lat + "," + end_lng + "&origin_region=大连&destination_region=大连&output=json&ak=cnG69M9AWuP8G9X96hGW1rxAaGGb8MyE";
		}

		System.out.println("url==" + url);
		String json = loadJSON(url);
		if (json == null || "" == json) {
			return 0;
		}
		int len = json.length();
		// 如果不是合法的json格式
		if (json.indexOf("{") != 0 || json.lastIndexOf("}") != len - 1) {
			return 0;
		}
		JSONObject obj = JSONObject.parseObject(json);

		if ("0".equals(obj.get("status").toString())) {
			JSONObject objResult = obj.getJSONObject("result");
			System.out.println("objResult对象=" + objResult);
			if (objResult != null) {
				JSONArray jsonArray = objResult.getJSONArray("routes");
				System.out.println("JSONArray对象=" + jsonArray);
				if (jsonArray != null) {
					sumMileage = jsonArray.getJSONObject(0).getDouble("distance");
					System.out.println("sumMileage==" + sumMileage);
				}
			}
			System.out.println("----------------------------------------------------");
		}
		count++;
		System.out.println("count=----------------------------------------------------" + count);
		return sumMileage;
	}

	public static String loadJSON(String url) {
		StringBuilder json = new StringBuilder();
		try {
			URL urlObj = new URL(url);
			URLConnection uc = urlObj.openConnection();
			BufferedReader br = new BufferedReader(new InputStreamReader(uc.getInputStream()));
			String inputLine = null;
			while ((inputLine = br.readLine()) != null) {
				json.append(inputLine);
			}
			br.close();
		} catch (MalformedURLException e) {
		} catch (IOException e) {
		}
		return json.toString();
	}

}
