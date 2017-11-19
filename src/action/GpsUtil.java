package action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

public class GpsUtil {
	public static JSONObject gpsConvertBgps(String gps) throws Exception {
		URL resjson = new URL("http://api.map.baidu.com/geoconv/v1/?coords=" + gps + "&from=1&to=5&ak=cnG69M9AWuP8G9X96hGW1rxAaGGb8MyE");
		BufferedReader in = new BufferedReader(new InputStreamReader(resjson.openStream()));
		String res;
		StringBuilder sb = new StringBuilder("");
		while ((res = in.readLine()) != null) {
			sb.append(res.trim());
		}
		in.close();
		String str = sb.toString();
		JSONObject json = JSONObject.parseObject(str);
		String result = json.getString("result");
		List list = json.parseArray(result);
		if ("[]".equals(result)) {
			System.out.println("���ˣ�����������");
			return null;
		} else {
			// System.out.println(list.get(0));
			JSONObject json1 = JSONObject.parseObject(list.get(0).toString());
			// String lon = json1.getString("x");
			// String lat = json1.getString("y");
			return json1;
		}
	}

	/**
	 * @param location
	 * @return
	 * @throws Exception
	 *             location 必须先维度然后在经度，使用逗号分隔 如:
	 */
	@SuppressWarnings({ "rawtypes", "static-access" })
	public static JSONObject gpsConvertAddress(String location) {
		URL resjson = null;
		BufferedReader in = null;
		try {
		    resjson = new URL("http://api.map.baidu.com/geocoder/v2/?callback=renderReverse&location=" + location + "&output=json&pois=1&ak=cnG69M9AWuP8G9X96hGW1rxAaGGb8MyE");
		    in = new BufferedReader(new InputStreamReader(resjson.openStream()));
			String res;
			StringBuilder sb = new StringBuilder("");
			while ((res = in.readLine()) != null) {
				sb.append(res.trim());
			}
			String str = sb.toString();
			str = str.replace("renderReverse&&renderReverse(", "");
			str = str.replace("}})", "}}");
			JSONObject json = JSONObject.parseObject(str);
			return json;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
