package util;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import com.alibaba.fastjson.JSONArray;

public class ScriptEngineUtil {
	public static double getMileage(JSONArray jsonArray) {
		Double c = 0.0;
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("javascript");
		
		//engine.put("carArry", jsonArray);
		try {
			String loadString = "function loadScript() {var script = document.createElement(\"script\");script.src = \"http://api.map.baidu.com/api?v=2&ak=cnG69M9AWuP8G9X96hGW1rxAaGGb8MyE\";}";
			System.out.println(loadString + "\n" + getText());
			engine.eval(loadString);
			engine.eval(getText());
			Invocable inv = (Invocable) engine;
			inv.invokeFunction("loadScript");
			inv.invokeFunction("lushuMove", jsonArray);
			c = (Double) engine.get("sum");
			System.out.println("c = " + c);
		} catch (ScriptException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return c / 1000;
	}

	public static String getText() {
		String text = "var map = new BMap.Map(\"allmap\");function lushuMove(carArry) {var points = [];" + 
				"for (var i = 0; i < carArry.length; i++) {var longitude = carArry[i].x;var latitude = carArry[i].y;var point = new BMap.Point(longitude, latitude);points.push(point);points.join(',');}" + 
				"var sum = 0;var group = Math.floor(points.length / 11);var mode = points.length % 11;" + 
				"var drv = new BMap.DrivingRoute(map, {onSearchComplete : function(res) {if (drv.getStatus() == BMAP_STATUS_SUCCESS) {var dis = res.getPlan(0).getRoute(0).getDistance(false);sum += dis;}}});" + 
				"for (var i = 0; i < group; i++) {var waypoints = points.slice(i * 11 + 1, (i + 1) * 11); var pstart = points[i * 11];var pend = points[(i + 1) * 11];drv.search(pstart, pend, {waypoints : waypoints});}"+ 
				"if (mode != 0) {var waypoints = points.slice(group * 11 + 1, points.length - 1);var pstart = points[group * 11];var pend = points[points.length - 1];drv.search(pstart, pend, {waypoints : waypoints});}}";
		return text;
	}
}
