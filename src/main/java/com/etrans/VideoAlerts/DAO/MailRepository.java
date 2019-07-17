package com.etrans.VideoAlerts.DAO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.etrans.VideoAlerts.model.Alerts;
import com.etrans.VideoAlerts.model.Mail;
import com.etrans.VideoAlerts.schedule.ScheduleJobs;
import com.etrans.VideoAlerts.util.Utility;

@Repository
public class MailRepository {
	private static final Logger logger = LoggerFactory.getLogger(MailRepository.class);
	
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	Utility util;
	
	public void sendMail(List<Alerts> alerts,Mail mail) {
		StringBuilder sb = new StringBuilder("INSERT INTO SYN_MAIL_OUTBOX");
		sb.append("(i_mail_out_seq,i_entity_id,s_mail_to,s_mail_subject,s_mail_body,c_is_processed,c_is_attached,s_filename,s_file_path,dt_created,s_mail_cc)");
		sb.append(" values(nextval('v4mail.seq_mail_outbox'),100,?,?,?,'N','N',null,null,current_timestamp,?)");
		String sql = sb.toString();
		jdbcTemplate.batchUpdate(sql,new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				Alerts alert = alerts.get(i);
				logger.info(util.getAlertType(alert.getAlertTypeId())+" alert at "+alert.getAlertDateTime()+" with \nurl - "+alert.getRefURL() +" \nmailed to "+mail.getMailTo());
				
				ps.setString(1, mail.getMailTo());
				ps.setString(2, util.getAlertType(alert.getAlertTypeId()) +" "+ mail.getMailSubject());
				ps.setString(3, util.getMailBody(alert));
				ps.setString(4, mail.getMailCc());
			}
			
			@Override
			public int getBatchSize() {
				logger.info("Count of inserted alerts in mail outbox - "+alerts.size());
				return alerts.size();
			}
		});
	}

}
