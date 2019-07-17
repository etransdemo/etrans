package com.etrans.VideoAlerts.services;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.print.attribute.standard.PrinterLocation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etrans.VideoAlerts.DAO.SearchAlertRepository;
import com.etrans.VideoAlerts.DAO.VehicleRepository;
import com.etrans.VideoAlerts.model.Alerts;
import com.etrans.VideoAlerts.model.Vehicle;
import com.etrans.VideoAlerts.schedule.ScheduleJobs;
import com.etrans.VideoAlerts.util.Utility;

import ch.qos.logback.core.net.SyslogOutputStream;

@Service
public class VideoAlertServices implements IVideoAlertService {
	private static final Logger logger = LoggerFactory.getLogger(VideoAlertServices.class);
	
	@Autowired
	SearchAlertRepository searchAlert;
	
	@Autowired 
	VehicleRepository vehicleRepo;
	
	@Autowired
	Utility util;

	@Override
	public List<Alerts> fetchAlerts(String[] vehicleListArray) {
		return searchAlert.searchUrlAlerts(vehicleListArray);
	}

	public List<Alerts> fetchNVRAlerts(){
		return searchAlert.getNVRAlerts();
		
	}
	
	@Override
	public List<Alerts> fetchNVRVideoAlerts(String[] alertArray) {

		
		
		
		List<Alerts> alertlist = new ArrayList<Alerts>();
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYYMMdd");
		String currDate = formatter.format(LocalDate.now());
		
		DateTimeFormatter file_formatter = DateTimeFormatter.ofPattern("dd_MMM_YYYY");
		String fileDate = file_formatter.format(LocalDate.now());
		
		DateTimeFormatter date_formatter = DateTimeFormatter.ofPattern("dd_MMM_YYYY_hh_mm_ss");
		
		
		String host = "dvr.etranssolutions.com";
        String user = "device";
        String pass = "Bhabteraho*";
        String dirPath = "/NVR_ALERTS/"+currDate;
        String videoURL = "http://dvr.etranssolutions.com/videoalerts/";
        
        try {
        	List<String> list = util.connectFTP(host, user, pass, dirPath);
        	
        	
        	
        	if(list != null) {
        		List<String> strList = list.stream()
        								.filter(x -> x.contains(fileDate))
        								//.filter(x -> x.contains("distracted") || x.contains("fatigue") || x.contains("sbrk"))
        								.filter(x -> Arrays.stream(alertArray).anyMatch(y -> x.contains(y)))
        								.collect(Collectors.toList());
        		
        		logger.info("NVR Alert List Size from FTP is "+ strList.size());
        		
        		ListIterator<String> itr = strList.listIterator();
        		while(itr.hasNext()) {
        			
        			
        			String str = itr.next();
        			String deviceId = str.split("_")[0];
        			String vUrl  = videoURL+currDate+"/"+str; 
        			
        			int alertType = 0; 
        			if(str.contains("distracted")) {
        				alertType = 1790;
        			}else if(str.contains("fatigue")) {
        				alertType = 1740;        				
        			}else if(str.contains("sbrk")) {
        				alertType = 1750;        				
        			}
        			
        			String alertDate = str.substring(str.length()-24).replace(".mp4","");
         			Date date_time=new SimpleDateFormat("dd_MMM_yyyy_hh_mm_ss").parse(alertDate);
        			String dtStr = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date_time);
        			
        			Alerts alert = new Alerts();
        			alert.setVehicleId(deviceId);
        			alert.setDeviceId(deviceId);
        			alert.setAlertDateTime(dtStr);
        			alert.setAlertTypeId(alertType);
        			alert.setRefURL(vUrl);
        		
        			alertlist.add(alert); 
        		}
        		
        	}
        }catch(IOException e) {
        	//e.printStackTrace();
        	logger.error(e.getMessage());
        } catch (ParseException e) {
			//e.printStackTrace();
        	logger.error(e.getMessage());
		}
		return alertlist;
	}

	@Override
	public List<Alerts> getFinalNVRList(List<Alerts> dataList,List<Alerts> videoList,List<Vehicle> vehicleList) {
		
		List<Alerts> list = videoList.stream()
							.filter(s -> dataList.stream().allMatch(x -> !x.getRefURL().equals(s.getRefURL())))
							.collect(Collectors.toList());
		
		ListIterator<Alerts> itr = list.listIterator();
		while(itr.hasNext()) {
			Alerts l = itr.next();
			System.out.println("URL - "+l.getRefURL());
			boolean flag = false;
			for(Vehicle v:vehicleList) {
				if(v.getDeviceId().equals(l.getDeviceId())) {
					flag = true;
					l.setVehicleId(v.getVehicleId());
					l.setCompany(v.getEntityName());
				}
			}
			if(!flag) {
				itr.remove();
			}
			
		}
		
		
		logger.info("final alert list size - "+list.size());
		if(list.size() > 0)
			searchAlert.saveNVRAlerts(list);
				
		return list;
	}

	
	
	@Override
	public List<Vehicle> getVehicleDetails(int parentEid) {
		return vehicleRepo.getVehicleDetails(parentEid);
		
	}

	
	
}
