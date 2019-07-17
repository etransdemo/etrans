package com.etrans.VideoAlerts.model;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@Document(collection = "dvr_video_alerts")
public class Alerts {
	
	@Id
	private ObjectId id;
	
	@Field("Company_Name")
	private String company;
	
	@Field("Country_Code")
	private String countryCode;
	
	@Field("Device_Id")
	private String deviceId;
	
	@Field("AlertTypeId")
	private int alertTypeId;
	
	@Field("VehicleId")
	private String vehicleId;
	
	@Field("AlertDateTime")
	private String alertDateTime;
	
	@Field("Latitude")
	private double latitude;
	
	@Field("Longnitude")
	private double longnitude;
	
	@Field("Location")
	private String location;
	
	@Field("Speed")
	private float speed;
	
	@Field("RefURL")
	private String refURL;
	
	@Field("DriverID")
	private String driverID;
	
	@Field("dt_created")
	private LocalDateTime dtCreated;
	
	@Field("video_attached")
	private boolean videoAttached;
	
	@Field("attachment_time")
	private LocalDateTime attachmentTime;
	
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public int getAlertTypeId() {
		return alertTypeId;
	}
	public void setAlertTypeId(int alertTypeId) {
		this.alertTypeId = alertTypeId;
	}
	public String getVehicleId() {
		return vehicleId;
	}
	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}
	public String getAlertDateTime() {
		return alertDateTime;
	}
	public void setAlertDateTime(String alertDateTime) {
		this.alertDateTime = alertDateTime;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongnitude() {
		return longnitude;
	}
	public void setLongnitude(double longnitude) {
		this.longnitude = longnitude;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public float getSpeed() {
		return speed;
	}
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	public String getRefURL() {
		return refURL;
	}
	public void setRefURL(String refURL) {
		this.refURL = refURL;
	}
	public String getDriverID() {
		return driverID;
	}
	public void setDriverID(String driverID) {
		this.driverID = driverID;
	}
	public LocalDateTime getDtCreated() {
		return dtCreated;
	}
	public void setDtCreated(LocalDateTime dtCreated) {
		this.dtCreated = dtCreated;
	}
	public boolean isVideoAttached() {
		return videoAttached;
	}
	public void setVideoAttached(boolean videoAttached) {
		this.videoAttached = videoAttached;
	}
	public LocalDateTime getAttachmentTime() {
		return attachmentTime;
	}
	public void setAttachmentTime(LocalDateTime attachmentTime) {
		this.attachmentTime = attachmentTime;
	}
	
	
	
	
	
	

}
