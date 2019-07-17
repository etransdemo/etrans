package com.etrans.VideoAlerts.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.etrans.VideoAlerts.model.Vehicle;
import com.etrans.VideoAlerts.schedule.ScheduleJobs;

@Repository
public class VehicleRepository {
	private static final Logger logger = LoggerFactory.getLogger(VehicleRepository.class);
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	
	public List<Vehicle> getVehicleDetails(int parentEntityId) {
		
		String sql = "select '"+parentEntityId+"' AS parentEntityId, i_entity_id as entityId,s_entity_name as entityName"
				+ ",s_asset_id as vehicleId,s_device_id as deviceId from  syn_lastdata where i_entity_id in (select myentity from fn_child_entity(?))";
		logger.info("Query to get vehicle details for entity -"+ parentEntityId + " is " +sql);
		
		return jdbcTemplate.query(sql, new Object[] {parentEntityId},new BeanPropertyRowMapper(Vehicle.class));
		
		//list.forEach(a -> System.out.println(a.getVehicleId()));
		
	}  

	
	
}
