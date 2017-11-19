package action;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import model.Car;
import service.CarServiceInterface;
import action.GpsUtil;

public class CarAction extends ActionSupport {

	private CarServiceInterface carServiceInterface;
	private String carId;
	private HttpServletResponse response;
	String start = null;
	String end = null;

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public String getCarId() {
		return carId;
	}

	public void setCarId(String carId) {
		this.carId = carId;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	 
	public CarServiceInterface getCarServiceInterface() {
		return carServiceInterface;
	}

	public void setCarServiceInterface(CarServiceInterface carServiceInterface) {
		this.carServiceInterface = carServiceInterface;
	}

	public String ext() throws Exception {
		System.out.println(this.getClass().getResource("/").getPath());
		Car agency = carServiceInterface.getLastInfoByCarId(carId.trim());
		String json = "{latitude:" + agency.getLAT() + ",longitude:" + agency.getLON() + ",time:" + agency.getTIME() + "}";
		String gps = agency.getLON() + "," + agency.getLAT();
		//String bgps = GpsUtil.gpsConvertBgps(gps);
		//ActionContext.getContext().put("agency", bgps);
		return "code";
	}

	public void getLastInfoByArryCarId() {
		String muiltCarId;
		JSONArray jsonArray = new JSONArray();
		try {
			muiltCarId = new String(carId.trim().getBytes("ISO-8859-1"), "UTF-8");
			String strs[] = muiltCarId.split(",");
			for (int i = 0; i < strs.length; i++) {
				if ("" != strs[i] && strs[i] != null) {
					Car car = carServiceInterface.getLastInfoByCarId(strs[i]);
					if (car != null) {
						JSONObject json1 = new JSONObject();
						json1.put("x", car.getLON());
						json1.put("y", car.getLAT());
						json1.put("carId",  car.getCAR_ID());
						json1.put("time", car.getTIME());
						json1.put("address", car.getADDRESS());
						json1.put("speed", car.getSPEED());
						json1.put("cameraId1", car.getCameraId1());
						json1.put("cameraId2", car.getCameraId2());
						jsonArray.add(i, json1);			
					}
				}
			}
			response = ServletActionContext.getResponse();
			response.setCharacterEncoding("UTF-8");
			PrintWriter writer = response.getWriter();
			writer.print( jsonArray);
			writer.flush();
			writer.close();	
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void getLastInfoByCarId() {
		String carIdTemp;
		try {
			carIdTemp = new String(carId.trim().getBytes("ISO-8859-1"), "UTF-8");
		
			if ("" != carIdTemp && carIdTemp != null) {
				Car car = carServiceInterface.getLastInfoByCarId(carIdTemp);
				System.out.println(car);
				if (car != null) {
					JSONObject json1 = new JSONObject();
					json1.put("x", car.getLON());
					json1.put("y", car.getLAT());
					json1.put("carId",  car.getCAR_ID());
					json1.put("time", car.getTIME());
					json1.put("address", car.getADDRESS());
					json1.put("speed", car.getSPEED());
					json1.put("cameraId1", car.getCameraId1());
					json1.put("cameraId2", car.getCameraId2());
					response = ServletActionContext.getResponse();
					response.setCharacterEncoding("UTF-8");
					PrintWriter writer = response.getWriter();
					writer.print(json1);
					writer.flush();
					writer.close();				
				}
			}
		
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void lonlatlist() throws Exception {
		JSONArray jsonArray = new JSONArray();
	    List<Car> carList = new ArrayList<Car>();
	    String carIdTemp = new String(carId.trim().getBytes("ISO-8859-1"), "UTF-8");
		String beginTimeTemp = dateFmtByReplace(start);
		String endTimeTemp = dateFmtByReplace(end);
	    carList = carServiceInterface.getLonLatList(beginTimeTemp, endTimeTemp,carIdTemp.trim());
		for (int i = 0; i < carList.size(); i++) {
			Car car = (Car) carList.get(i);
			if (car != null) { 
				JSONObject json1 = new JSONObject();
				json1.put("x", car.getLON());
				json1.put("y", car.getLAT());
				json1.put("carId",  car.getCAR_ID());
				json1.put("time", car.getTIME());
				json1.put("address", car.getADDRESS());
				json1.put("speed", car.getSPEED());
				jsonArray.add(i, json1);	
			}
		}
		response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		PrintWriter writer = response.getWriter();
		writer.print(jsonArray);
		writer.flush();
		writer.close();	
	}
	
	public String dateFmtByReplace(String datetime) {
		String rtime = datetime.replace(",", "").replace("-", "").replace(":", "").replace(" ", "");
		return rtime;
	}
}
