package model;

public class StopRecord {
	private String stopBeginTime;
	private String stopEndTime;
	private String stopTime;
	public String getStopBeginTime() {
		return stopBeginTime;
	}
	public void setStopBeginTime(String stopBeginTime) {
		this.stopBeginTime = stopBeginTime;
	}
	public String getStopEndTime() {
		return stopEndTime;
	}
	public void setStopEndTime(String stopEndTime) {
		this.stopEndTime = stopEndTime;
	}
	public String getStopTime() {
		return stopTime;
	}
	public void setStopTime(String stopTime) {
		this.stopTime = stopTime;
	}
	public StopRecord(String stopBeginTime, String stopEndTime, String stopTime) {
		super();
		this.stopBeginTime = stopBeginTime;
		this.stopEndTime = stopEndTime;
		this.stopTime = stopTime;
	}
	public StopRecord() {
		
	}
	@Override
	public String toString() {
		return "StopRecord [stopBeginTime=" + stopBeginTime + ", stopEndTime=" + stopEndTime + ", stopTime=" + stopTime + "]";
	}
	
	
}
