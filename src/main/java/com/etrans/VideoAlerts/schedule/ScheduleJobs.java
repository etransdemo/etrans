package com.etrans.VideoAlerts.schedule;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.etrans.VideoAlerts.model.Alerts;
import com.etrans.VideoAlerts.model.Mail;
import com.etrans.VideoAlerts.model.Vehicle;
import com.etrans.VideoAlerts.services.IMessageService;
import com.etrans.VideoAlerts.services.IVideoAlertService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ScheduleJobs {
	
	private static final Logger logger = LoggerFactory.getLogger(ScheduleJobs.class);
	
	@Value("${entity.alert}")
	private String entityAlert;
	
	@Value("${entity.list}")
	private int[] entityList;
	
	@Autowired
	IVideoAlertService services;
	
	@Autowired
	IMessageService messageService;
	
	@Autowired
	Mail mail;

	
	@Value("${vehicle.list}")
	private String[] vehicleListArray;

	//Scheduled at 30 minutes interval
	@Scheduled(fixedRateString = "${schedule.time}")
	private void VideoAlertsMail() {
		logger.info("DVR Video alerts started at "+LocalDateTime.now());
		
		mail.setMailFrom("kmanohar@etranssolutions.com");
		try {
			List<Alerts> list = services.fetchAlerts(vehicleListArray);
			logger.info("DVR Alert List Size :: "+list.size());
			messageService.sendMail(list,17741);//only for PRAXAIR
		}catch(Exception e) {
			//e.printStackTrace();
			logger.error(e.getMessage());
		}		
	}
	
	
	//Scheduled at 30 minutes interval
	@Scheduled(fixedRateString = "${schedule.time}")
	private void NVRVideoAlertsMail() throws JsonParseException, JsonMappingException, IOException {
		logger.info("NVR Video alerts started at "+LocalDateTime.now());
		
		//mail.setMailFrom("kmanohar@etranssolutions.com");
		ObjectMapper mapper = new ObjectMapper();
		Map<String,String[]> map = mapper.readValue(entityAlert, new TypeReference<Map<String,String[]>>() {});
		
		
		for(int eid : entityList) {
			List<Vehicle> veicleList = services.getVehicleDetails(eid);
			String[] alertList=map.get(String.valueOf(eid));
			try {
				List<Alerts> list = services.fetchNVRAlerts();
				List<Alerts> videoList = services.fetchNVRVideoAlerts(alertList);
				List<Alerts> finalList = services.getFinalNVRList(list,videoList,veicleList);
				if(finalList.size() > 0) {
					messageService.sendMail(finalList,eid);	
				}
				
			}catch(Exception e) {
				//e.printStackTrace();
				logger.error(e.getMessage());
			}	
			
		}
		
		
		
		
		
		
				
	}
}
