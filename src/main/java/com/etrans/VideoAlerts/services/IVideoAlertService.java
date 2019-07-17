package com.etrans.VideoAlerts.services;

import java.util.List;

import com.etrans.VideoAlerts.model.Alerts;
import com.etrans.VideoAlerts.model.Vehicle;

public interface IVideoAlertService {
	
	public List<Alerts> fetchAlerts(String[] vehicleListArray);
	
	public List<Alerts> fetchNVRVideoAlerts(String[] alertList);
	
	public List<Alerts> fetchNVRAlerts();
	
	public List<Alerts> getFinalNVRList(List<Alerts> dataList,List<Alerts> videoList,List<Vehicle> vehicleList);
	
	public List<Vehicle> getVehicleDetails(int parentEid);
}
