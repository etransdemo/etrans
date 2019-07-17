package com.etrans.VideoAlerts.services;

import java.util.List;

import com.etrans.VideoAlerts.model.Alerts;

public interface IMessageService {

	public void sendMail(List<Alerts> alerts,int eid);
	
}
