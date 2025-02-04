package com.runnerapplication.user.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.lowagie.text.DocumentException;
import com.runnerapplication.user.dao.CommonDao;
import com.runnerapplication.user.entity.EventReport;
import com.runnerapplication.user.entity.RunnerProfileEntity;
import com.runnerapplication.user.services.MarathonProfileService;
import com.runnerapplication.user.util.PDFGenerator;
import com.runnerapplication.user.util.PDFGeneratorEventList;

@CrossOrigin(origins="*")
@Controller
public class ReportsController {
	
	@Autowired
	MarathonProfileService marathonProfileService;
	
	@Autowired
	CommonDao commonDao;
	
	@GetMapping("/downloadPdf/{columnList}")
	public void generatePdf (HttpServletResponse response,@PathVariable("columnList") String[] columnList) throws DocumentException, IOException {
		
		response.setContentType("application/pdf");
		DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD:HH:MM:SS");
		String currentDateTime = dateFormat.format(new Date());
		String headerkey = "Content-Disposition";
		String headervalue = "attachment; filename=Tiruppur_Runners"+" _ "+currentDateTime + ".pdf";
		response.setHeader(headerkey, headervalue);
		
		List<RunnerProfileEntity> studentList = marathonProfileService.getAllRunnerData();
		PDFGenerator generator = new PDFGenerator();
		generator.setAdditionDetailsEntityList(studentList);
		generator.generate(response,columnList);
		
	}
	
	@GetMapping("/downloadEventPdf/{marathonId}")
	public void downloadEventPdf (HttpServletResponse response,@PathVariable("marathonId") String marathonId) throws DocumentException, IOException {
		
		response.setContentType("application/pdf");
		DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD:HH:MM:SS");
		String currentDateTime = dateFormat.format(new Date());
		String headerkey = "Content-Disposition";
		String headervalue = "attachment; filename=Event_Report"+" _ "+currentDateTime + ".pdf";
		response.setHeader(headerkey, headervalue);
		
		List<EventReport> eventList= new ArrayList<EventReport>();
		try {
			eventList = commonDao.getRunnersDataByProfileId(Long.parseLong(marathonId));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		PDFGeneratorEventList generator = new PDFGeneratorEventList();
		generator.setAdditionDetailsEntityList(eventList);
		generator.generate(response);
		
	}

}
