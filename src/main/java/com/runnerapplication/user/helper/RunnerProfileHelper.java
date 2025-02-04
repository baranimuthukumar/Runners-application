package com.runnerapplication.user.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.runnerapplication.user.entity.MarathonProfileEntity;
import com.runnerapplication.user.entity.RunnerProfileEntity;
import com.runnerapplication.user.model.MarathonProfile;
import com.runnerapplication.user.model.RunnerProfile;

@Component
public class RunnerProfileHelper {

	public RunnerProfile convertToDataBase(RunnerProfileEntity runnerProfileEntity) throws ParseException {
		RunnerProfile runnerProfile = new RunnerProfile();
		runnerProfile.setActiveMember(runnerProfileEntity.isActiveMember());
		runnerProfile.setClubName(runnerProfileEntity.getClubName());
		runnerProfile.setContactNumber(runnerProfileEntity.getContactNumber());
		DateTimeFormatter parser = DateTimeFormatter.ofPattern("M/d/yyyy");
		LocalDate localDate =LocalDate.parse(runnerProfileEntity.getDob().toString(), parser);
		Date date=java.sql.Date.valueOf(localDate);
		runnerProfile.setDob(date);
		int age=calculateAge(localDate);
		runnerProfile.setAge(age);
		runnerProfile.setAgeCategory(calculateAgeCategory(age));
		runnerProfile.setPersonalBest(runnerProfileEntity.getPersonalBest());
		runnerProfile.setEmail(runnerProfileEntity.getEmail());
		runnerProfile.setUserName(runnerProfileEntity.getUserName());
		runnerProfile.setPassword(runnerProfileEntity.getPassword());
		runnerProfile.setTshirtIssued(runnerProfileEntity.getTshirtIssued().equals("Yes")?true:false);
		runnerProfile.setTshirtSize(runnerProfileEntity.getTshirtSize());
		if(runnerProfileEntity.getProfileId()>0) {
			runnerProfile.setProfileId(runnerProfileEntity.getProfileId());
		}
		runnerProfile.setEmergencyContactName(runnerProfileEntity.getEmergencyContactName());
		runnerProfile.setEmergencyContactNumber(runnerProfileEntity.getEmergencyContactNumber());
		runnerProfile.setBloodGroup(runnerProfileEntity.getBloodGroup());
		runnerProfile.setProfession(runnerProfileEntity.getProfession());
		runnerProfile.setStravaLink(runnerProfileEntity.getStravaLink());
		runnerProfile.setInterestsOtherThanRunning(runnerProfileEntity.getInterestsOtherThanRunning());
		runnerProfile.setTellAboutyourself(runnerProfileEntity.getTellAboutyourself());
		return runnerProfile;
	}

	public RunnerProfileEntity convertToEntity(RunnerProfile runnerProfile) {
		RunnerProfileEntity runnerProfileEntity = new RunnerProfileEntity();
		runnerProfileEntity.setActiveMember(runnerProfile.isActiveMember());
		runnerProfileEntity.setAge(runnerProfile.getAge());
		runnerProfileEntity.setAgeCategory(runnerProfile.getAgeCategory());
		runnerProfileEntity.setClubName(runnerProfile.getClubName());
		runnerProfileEntity.setContactNumber(runnerProfile.getContactNumber());
		runnerProfileEntity.setDob(String.valueOf(runnerProfile.getDob()));
		runnerProfileEntity.setPersonalBest(runnerProfile.getPersonalBest());
		runnerProfileEntity.setProfileId(runnerProfile.getProfileId());
		runnerProfileEntity.setEmail(runnerProfile.getEmail());
		runnerProfileEntity.setUserName(runnerProfile.getUserName());
		runnerProfileEntity.setPassword(runnerProfile.getPassword());
		runnerProfileEntity.setTshirtIssued(runnerProfile.isTshirtIssued()?"Yes":"No");
		runnerProfileEntity.setTshirtSize(runnerProfile.getTshirtSize());
		runnerProfileEntity.setEmergencyContactName(runnerProfile.getEmergencyContactName());
		runnerProfileEntity.setEmergencyContactNumber(runnerProfile.getEmergencyContactNumber());
		runnerProfileEntity.setProfession(runnerProfile.getProfession());
		runnerProfileEntity.setStravaLink(runnerProfile.getStravaLink());
		runnerProfileEntity.setBloodGroup(runnerProfile.getBloodGroup());
		runnerProfileEntity.setInterestsOtherThanRunning(runnerProfile.getInterestsOtherThanRunning());
		runnerProfileEntity.setTellAboutyourself(runnerProfile.getTellAboutyourself());
		return runnerProfileEntity;
	}

	public RunnerProfileEntity convertToEntity(RunnerProfile runnerProfile, List<MarathonProfile> profileList) {
		RunnerProfileEntity runnerProfileEntity = new RunnerProfileEntity();
		runnerProfileEntity.setActiveMember(runnerProfile.isActiveMember());
		runnerProfileEntity.setAge(runnerProfile.getAge());
		runnerProfileEntity.setAgeCategory(runnerProfile.getAgeCategory());
		runnerProfileEntity.setClubName(runnerProfile.getClubName());
		runnerProfileEntity.setContactNumber(runnerProfile.getContactNumber());
		runnerProfileEntity.setDob(String.valueOf(runnerProfile.getDob()));
		runnerProfileEntity.setPersonalBest(runnerProfile.getPersonalBest());
		runnerProfileEntity.setProfileId(runnerProfile.getProfileId());
		runnerProfileEntity.setEmail(runnerProfile.getEmail());
		runnerProfileEntity.setUserName(runnerProfile.getUserName());
		runnerProfileEntity.setPassword(runnerProfile.getPassword());
		runnerProfileEntity.setTshirtIssued(runnerProfile.isTshirtIssued()?"Yes":"No");
		runnerProfileEntity.setTshirtSize(runnerProfile.getTshirtSize());
		runnerProfileEntity.setEmergencyContactName(runnerProfile.getEmergencyContactName());
		runnerProfileEntity.setEmergencyContactNumber(runnerProfile.getEmergencyContactNumber());
		runnerProfileEntity.setProfession(runnerProfile.getProfession());
		runnerProfileEntity.setBloodGroup(runnerProfile.getBloodGroup());
		runnerProfileEntity.setStravaLink(runnerProfile.getStravaLink());
		runnerProfileEntity.setInterestsOtherThanRunning(runnerProfile.getInterestsOtherThanRunning());
		runnerProfileEntity.setTellAboutyourself(runnerProfile.getTellAboutyourself());
		List<MarathonProfileEntity> profileEntityList= new ArrayList<MarathonProfileEntity>();
		AtomicInteger count= new AtomicInteger(0);
		profileEntityList=profileList.stream().map(val->{
			MarathonProfileEntity marathonProfileEntity= new MarathonProfileEntity();
			marathonProfileEntity.setBestTime(val.getBestTime());
			marathonProfileEntity.setRegisterId(val.getRegisterId());
			marathonProfileEntity.setDistance(val.getDistance());
			marathonProfileEntity.setEventName(val.getEventName());
			marathonProfileEntity.setFutureOrpast(val.getFutureOrpast());
			marathonProfileEntity.setMarathonId(val.getMarathonId());
			marathonProfileEntity.setPaymentReference(val.getPaymentReference());
			marathonProfileEntity.setProfileId(val.getProfileId());
			marathonProfileEntity.setYear(val.getYear());
			marathonProfileEntity.setPodium(val.getPodium());
			marathonProfileEntity.setSerialNo(count.incrementAndGet());
			return marathonProfileEntity;
		}).collect(Collectors.toList());
		runnerProfileEntity.setMarathonProfileList(profileEntityList);
		return runnerProfileEntity;
	}

	public List<RunnerProfileEntity> convertToEntityList(List<RunnerProfile> runnerProfileList) {
		List<RunnerProfileEntity> profileEntityList= new ArrayList<RunnerProfileEntity>();
		AtomicInteger count=new AtomicInteger(0);
		profileEntityList=runnerProfileList.stream().map(val->{
			RunnerProfileEntity runnerProfileEntity = new RunnerProfileEntity();
			runnerProfileEntity.setActiveMember(val.isActiveMember());
			runnerProfileEntity.setAge(val.getAge());
			runnerProfileEntity.setAgeCategory(val.getAgeCategory());
			runnerProfileEntity.setClubName(val.getClubName());
			runnerProfileEntity.setContactNumber(val.getContactNumber());
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
		    String strDate= formatter.format(val.getDob());  
			runnerProfileEntity.setDob(String.valueOf(strDate));
			runnerProfileEntity.setPersonalBest(val.getPersonalBest());
			runnerProfileEntity.setProfileId(val.getProfileId());
			runnerProfileEntity.setEmail(val.getEmail());
			runnerProfileEntity.setUserName(val.getUserName());
			runnerProfileEntity.setPassword(val.getPassword());
			runnerProfileEntity.setTshirtIssued(val.isTshirtIssued()?"Yes":"No");
			runnerProfileEntity.setTshirtSize(val.getTshirtSize());
			runnerProfileEntity.setSerialNo(count.incrementAndGet());
			runnerProfileEntity.setEmergencyContactName(val.getEmergencyContactName());
			runnerProfileEntity.setEmergencyContactNumber(val.getEmergencyContactNumber());
			runnerProfileEntity.setProfession(val.getProfession());
			runnerProfileEntity.setBloodGroup(val.getBloodGroup());
			runnerProfileEntity.setStravaLink(val.getStravaLink());
			runnerProfileEntity.setInterestsOtherThanRunning(val.getInterestsOtherThanRunning());
			runnerProfileEntity.setTellAboutyourself(val.getTellAboutyourself());
			return runnerProfileEntity;
		}).collect(Collectors.toList());
		return profileEntityList;
	}
	
	public int calculateAge(LocalDate localDate) {
		LocalDate curDate = LocalDate.now();  
		//calculates the amount of time between two dates and returns the years  
		if ((localDate != null) && (curDate != null)) {  
			return Period.between(localDate, curDate).getYears();  
		}else {  
			return 0;  
		}  
	}
	
	public String calculateAgeCategory(int age) {
		if(age<=18) {
			return "0-18";
		}else if(age>18 && age<=35) {
			return "19-35";
		}else if(age>35 && age<=50) {
			return "36-50";
		}else if(age>50 && age<=60) {
			return "51-60";
		}else if(age>60) {
			return "Above 60";
		}
		
		return "";
	}

}
