package com.etrans.VideoAlerts.model;

public class Vehicle {

	private String vehicleId;
	private String deviceId;
	private int entityId;
	private int parentEntityId;
	private String entityName;
	
	
	public Vehicle() {};
	
	public Vehicle(String vehicleId, String deviceId, int entityId, int parentEntityId, String entityName) {
		this.vehicleId = vehicleId;
		this.deviceId = deviceId;
		this.entityId = entityId;
		this.parentEntityId = parentEntityId;
		this.entityName = entityName;
	}
	
	public String getVehicleId() {
		return vehicleId;
	}
	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public int getEntityId() {
		return entityId;
	}
	public void setEntityId(int entityId) {
		this.entityId = entityId;
	}
	public int getParentEntityId() {
		return parentEntityId;
	}
	public void setParentEntityId(int parentEntityId) {
		this.parentEntityId = parentEntityId;
	}
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	
	@Override
	public String toString() {
		return "Vehicle [vehicleId=" + vehicleId + ", deviceId=" + deviceId + ", entityId=" + entityId
				+ ", parentEntityId=" + parentEntityId + ", entityName=" + entityName + "]";
	}
	
	
	
	
}
