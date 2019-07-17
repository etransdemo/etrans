package com.etrans.VideoAlerts.services;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.etrans.VideoAlerts.DAO.MailRepository;
import com.etrans.VideoAlerts.model.Alerts;
import com.etrans.VideoAlerts.model.Mail;
import com.etrans.VideoAlerts.schedule.ScheduleJobs;
import com.etrans.VideoAlerts.util.Utility;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class MessageService implements IMessageService{
	private static final Logger logger = LoggerFactory.getLogger(MessageService.class);

	
	@Value("${mail.from}")
	 private String mailFrom;
	 
	 @Value("${entity.mail}")
	 private String mailToStr;
	 
	 @Value("${mail.cc}")
	 private String mailCc;
	 
	 @Value("${mail.bcc}")
	 private String mailBcc;
	 
	 @Value("${mail.sub}")
	 private String mailSub;
	 
	 
	@Autowired
	MailRepository mailRepository;
	
	@Autowired 
	Mail mail;
	
	@Autowired
	Utility util;
	
	@Override
	public void sendMail(List<Alerts> alerts,int eid) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			Map<String,String[]> map = mapper.readValue(mailToStr, new TypeReference<Map<String,String[]>>() {});
			String[] mailToArray = map.get(String.valueOf(eid));
			
			List<String> mailStr = Arrays.asList(mailToArray);
			String mailTo = String.join(",",mailStr);			
			//logger.info("Mail will be sent to "+mailTo+" for entity - "+eid );
			
			
			if(mailTo.isEmpty() || mailTo == null) {
				return;
			}
			mail.setMailTo(mailTo);
			mail.setMailBcc(mailBcc);
			mail.setMailCc(mailCc);
			mail.setMailFrom(mailFrom);
			mail.setMailSubject(mailSub);
			mailRepository.sendMail(alerts,mail);
			
		} catch (IOException e) {
			//e.printStackTrace();
			logger.error(e.getMessage());
		}
			
	}

}
