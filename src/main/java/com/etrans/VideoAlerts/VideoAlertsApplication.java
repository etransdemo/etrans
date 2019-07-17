package com.etrans.VideoAlerts;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class VideoAlertsApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(VideoAlertsApplication.class, args);
	}

}
