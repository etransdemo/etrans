package com.etrans.VideoAlerts.DAO;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;

import javax.swing.text.Document;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.etrans.VideoAlerts.model.Alerts;
import com.etrans.VideoAlerts.schedule.ScheduleJobs;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@Repository
public class SearchAlertRepository {
	private static final Logger logger = LoggerFactory.getLogger(SearchAlertRepository.class);
	
	@Autowired
	MongoTemplate mongoTemplate;

	public List<Alerts> searchUrlAlerts(String[] vehicleListArray) {
		
		
		Query basicQuery = new Query();
		Calendar date=Calendar.getInstance();
		date.add(Calendar.MINUTE, - 30);	
		DBObject obj = new BasicDBObject("$gt", date.getTime());
		DBObject objVehicle = new BasicDBObject("$in", vehicleListArray);
		
		basicQuery.addCriteria(Criteria.where("VehicleId").is(objVehicle));
		basicQuery.addCriteria(Criteria.where("video_attached").is(true));
		basicQuery.addCriteria(Criteria.where("attachment_time").is(obj));
		logger.info("DVR Query in mongo to get last 30 minutes video alerts - "+basicQuery);
		return mongoTemplate.find(basicQuery,Alerts.class);		
	}
	
	public void saveNVRAlerts(List<Alerts> alerts) {
		logger.info("Going to save NVR ALERT documents in mongo. List size - "+alerts.size());
		//alerts.stream().map(s -> s.getRefURL()).forEach(System.out::println);
		for(Alerts alert:alerts) {
			alert.setDtCreated(LocalDateTime.now());
			mongoTemplate.save(alert, "nvr_video_alerts");	
		}
	}
	
	public List<Alerts> getNVRAlerts(){
		List<Alerts> list = null;
		Calendar date=Calendar.getInstance();
		date.add(Calendar.HOUR, - 24);
		Query basicQuery = new Query();
		DBObject obj = new BasicDBObject("$gt", date.getTime());
		basicQuery.addCriteria(Criteria.where("dt_created").is(obj));
		logger.info("Mongo Query to get NVR Alerts in last 24 hours - "+basicQuery);
		list = mongoTemplate.find(basicQuery, Alerts.class, "nvr_video_alerts");
		logger.info("Got no of NVR alerts from mongo  - "+list.size());
		return list;
	}
	
}