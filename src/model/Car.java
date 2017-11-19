package model;

/**
 * AgencyOrganization entity. @author MyEclipse Persistence Tools
 */

public class Car implements java.io.Serializable {

	// Fields

	private int ID;
	private String LON;
	private String LAT;
	private String CAR_ID;
	private String TIME;
	private String SPEED;
	private String ADDRESS;
	private StopRecord stopRecord;
	private int overSpeedCount;
	private int stopCount;
	private double mileage;
	private String cameraId1;
	private String cameraId2;
	
	// Constructors
	public Car() {

	}

	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public String getLON() {
		return LON;
	}

	public void setLON(String LON) {
		this.LON = LON;
	}

	public String getLAT() {
		return LAT;
	}

	public void setLAT(String LAT) {
		this.LAT = LAT;
	}

	public String getCAR_ID() {
		return CAR_ID;
	}

	public void setCAR_ID(String CAR_ID) {
		this.CAR_ID = CAR_ID;
	}

	public String getTIME() {
		return TIME;
	}

	public void setTIME(String TIME) {
		this.TIME = TIME;
	}

	public String getSPEED() {
		return SPEED;
	}

	public void setSPEED(String SPEED) {
		this.SPEED = SPEED;
	}

	public String getADDRESS() {
		return ADDRESS;
	}

	public void setADDRESS(String ADDRESS) {
		this.ADDRESS = ADDRESS;
	}

	public StopRecord getStopRecord() {
		return stopRecord;
	}

	public void setStopRecord(StopRecord stopRecord) {
		this.stopRecord = stopRecord;
	}

	public int getOverSpeedCount() {
		return overSpeedCount;
	}

	public void setOverSpeedCount(int overSpeedCount) {
		this.overSpeedCount = overSpeedCount;
	}

	public int getStopCount() {
		return stopCount;
	}

	public void setStopCount(int stopCount) {
		this.stopCount = stopCount;
	}

	public double getMileage() {
		return mileage;
	}

	public void setMileage(double mileage) {
		this.mileage = mileage;
	}

	public String getCameraId1() {
		return cameraId1;
	}

	public void setCameraId1(String cameraId1) {
		this.cameraId1 = cameraId1;
	}

	public String getCameraId2() {
		return cameraId2;
	}

	public void setCameraId2(String cameraId2) {
		this.cameraId2 = cameraId2;
	}

	@Override
	public String toString() {
		return "Car{" +
				"ID=" + ID +
				", LON='" + LON + '\'' +
				", LAT='" + LAT + '\'' +
				", CAR_ID='" + CAR_ID + '\'' +
				", TIME='" + TIME + '\'' +
				", SPEED='" + SPEED + '\'' +
				", ADDRESS='" + ADDRESS + '\'' +
				", stopRecord=" + stopRecord +
				", overSpeedCount=" + overSpeedCount +
				", stopCount=" + stopCount +
				", mileage=" + mileage +
				", cameraId1='" + cameraId1 + '\'' +
				", cameraId2='" + cameraId2 + '\'' +
				'}';
	}

}
