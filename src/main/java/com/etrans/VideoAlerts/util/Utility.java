package com.etrans.VideoAlerts.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.etrans.VideoAlerts.model.Alerts;

@Component
public class Utility {
	
	
	@Value("${vehicle.list}")
	private String[] vehicleListArray;
	

	public String getMailBody(Alerts alert) {
		String alertType=getAlertType(alert.getAlertTypeId());
		
		
		if(Arrays.asList(vehicleListArray).contains(alert.getVehicleId())) {
			alert.setCompany("PRAXAIR");
		}
		
		
		StringBuilder sb=new StringBuilder("<html><head><meta charset=\"utf-8\"></head><body>");
		if(alert !=null) {
			sb.append("<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"100%\" align=\"center\">");
			sb.append("<tr><td colspan=\"3\" height=\"8px\"></td></tr>");
			sb.append("<tr><td width=\"5\"></td>");
			sb.append("<td><p><font face=\"arial\" color=\"#000000\" size=\"3\">Dear User,</font></p>");
			sb.append("<p><font face=\"arial\" color=\"#000000\" size=\"3\">Please find the F&amp;D alerts as -</font></p>");
			sb.append("<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"98%\" align=\"center\">");
			sb.append("<tr><td colspan=\"3\" height=\"1px\" bgcolor=\"#cccccc\"></td></tr>");
			sb.append("<tr><td width=\"1px\" bgcolor=\"#cccccc\"></td>");
			sb.append("<td align=\"center\">");
			sb.append("<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"100%\" align=\"center\">");
			sb.append("<tr>");
			sb.append("<td height=\"25px\" align=\"center\" bgcolor=\"#cccccc\"><font face=\"arial\" color=\"#000000\" size=\"2\"><strong>Vehicle</strong></font></td>");
			sb.append("<td width=\"1px\" bgcolor=\"#b8b8b8\"></td>");
			sb.append("<td height=\"25px\" align=\"center\" bgcolor=\"#cccccc\"><font face=\"arial\" color=\"#000000\" size=\"2\"><strong>Transporter</strong></font></td>");
			sb.append("<td width=\"1px\" bgcolor=\"#b8b8b8\"></td>");
			sb.append("<td height=\"25px\" align=\"center\" bgcolor=\"#cccccc\"><font face=\"arial\" color=\"#000000\" size=\"2\"><strong>Alert Type</strong></font></td>");
			sb.append("<td width=\"1px\" bgcolor=\"#b8b8b8\"></td>");
			sb.append("<td align=\"center\" bgcolor=\"#cccccc\"><font face=\"arial\" color=\"#000000\" size=\"2\"><strong>Alert Time</strong></font></td>");
			sb.append("<td width=\"1px\" bgcolor=\"#b8b8b8\"></td>");
			sb.append("<td align=\"center\" bgcolor=\"#cccccc\"><font face=\"arial\" color=\"#000000\" size=\"2\"><strong>Video Link</strong></font></td>");
			sb.append("</tr>");
			sb.append("<tr><td colspan=\"5\" height=\"1px\" bgcolor=\"#cccccc\"></td></tr>");
			
			sb.append("<tr>");
			sb.append("<td height=\"25px\" align=\"center\" valign=\"middle\"><font face=\"arial\" color=\"#000000\" size=\"2\">");
			sb.append(alert.getVehicleId());
			sb.append("</font></td>");
			sb.append("<td width=\"1px\" bgcolor=\"#b8b8b8\"></td>");
			sb.append("<td height=\"25px\" align=\"center\" valign=\"middle\"><font face=\"arial\" color=\"#000000\" size=\"2\">");
			sb.append(alert.getCompany());
			sb.append("</font></td>");
			sb.append("<td width=\"1px\" bgcolor=\"#b8b8b8\"></td>");
			sb.append("<td height=\"25px\" align=\"center\" valign=\"middle\"><font face=\"arial\" color=\"#000000\" size=\"2\">");
			sb.append(alertType);
			sb.append("</font></td>");
			sb.append("<td width=\"1px\" bgcolor=\"#b8b8b8\"></td>");
			sb.append("<td align=\"center\"><font face=\"arial\" color=\"#000000\" size=\"2\"><strong>");
			sb.append(alert.getAlertDateTime());
			sb.append("</strong></font></td>");
			sb.append("<td width=\"1px\" bgcolor=\"#b8b8b8\"></td>");
			sb.append("<td align=\"center\"><font face=\"arial\" color=\"#FF0000\" size=\"2\"><a href=\"");
			sb.append(alert.getRefURL());
			sb.append("\" target=\"_blank\">");
			sb.append(alert.getRefURL());
			sb.append("</a></font></td>");
			sb.append("</tr>");
			sb.append("</table>");
			sb.append("</td><td width=\"1px\" bgcolor=\"#cccccc\"></td></tr><tr><td colspan=\"3\" height=\"1px\" bgcolor=\"#cccccc\"></td></tr></table>");
			sb.append("<p>&nbsp;</p>");
			sb.append("<p align=\"center\"><font face=\"arial\" color=\"#000000\" size=\"2\">This is auto generated mail.</font></p>");
			sb.append("</td><td width=\"5\"></td></tr><tr><td colspan=\"3\" height=\"8px\"></td></tr>");
			sb.append("</table>");
		}else {
			sb.append("</P>NO ALERT </P>");
		}
		sb.append("</body></html>");
		return sb.toString();
	}
	
	public List<String> connectFTP(String host,String user,String pass,String dirPath) throws IOException {
		String ftpUrl = "ftp://%s:%s@%s/%s;type=d";
		String urlString = String.format(ftpUrl, user, pass, host, dirPath);
		URL url = new URL (urlString);
		URLConnection urlc = url.openConnection();
		InputStream is = urlc.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        String line = null;
        System.out.println("--- Inside FTP Program ---");
        List<String> list = new ArrayList<String>();
        while ((line = reader.readLine()) != null) {
            list.add(line);
        }
        System.out.println("--- File List Size :: --"+list.size());
        is.close();
		return list;
	}
	
	public String getAlertType(int alertCode) {
		switch(alertCode) {
			case 1740:
				return "FATIGUE";
			case 1750:
				return "HARSH BREAK";
			case 1790:
				return "DISTRACTION";
			default :
				return String.valueOf(alertCode);
		}
	}
	
	
	public static void main(String args[]) throws ParseException {
		
		Utility obj=new Utility();
		System.out.println(obj.getAlertType(1790));
		
		/*Alerts alert = new Alerts();
		alert.setAlertTypeId(1740);
		alert.setAlertDateTime("2019-06-27");
		alert.setRefURL("www.google.com");
		
		Utility obj=new Utility();
		System.out.println(obj.getMailBody(alert));*/
		
		/*Utility obj=new Utility();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYYMMdd");
		String currDate = formatter.format(LocalDate.now());
		
		DateTimeFormatter file_formatter = DateTimeFormatter.ofPattern("dd_MMM_YYYY");
		String fileDate = file_formatter.format(LocalDate.now());
		System.out.println(fileDate);
		
		
		
		String host = "dvr.etranssolutions.com";
        String user = "device";
        String pass = "Bhabteraho*";
        String dirPath = "/NVR_ALERTS/"+currDate;
        try {
        	List<String> list = obj.connectFTP(host, user, pass, dirPath);
        	if(list != null) {
        		List<String> alertList = list.stream()
        								.filter(x -> x.contains(fileDate))
        								.filter(x -> x.contains("distracted"))
        								.collect(Collectors.toList());
        		ListIterator<String> itr = alertList.listIterator();
        		while(itr.hasNext()) {
        			String str = itr.next();
        			
        			System.out.println(str);
        			
        			
        			String _date = str.substring(str.length()-24).replace(".mp4","");
        			System.out.println(_date);
        			
        			Date date_time=new SimpleDateFormat("dd_MMM_yyyy_hh_mm_ss").parse(_date);
        			System.out.println("Converted Date :: "+date_time);
        			
        			String dtStr = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date_time);
        			System.out.println(dtStr);
        		}
        	}
        }catch(IOException e) {
        	e.printStackTrace();
        }*/
        
        
	}
}
