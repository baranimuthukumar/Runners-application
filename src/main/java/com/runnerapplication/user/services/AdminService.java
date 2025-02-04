package com.runnerapplication.user.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.apache.commons.jcs.JCS;
import org.apache.commons.jcs.access.CacheAccess;
import org.apache.commons.jcs.engine.control.CompositeCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.runnerapplication.user.entity.MarathonEventEntity;
import com.runnerapplication.user.entity.TopperEntitity;
import com.runnerapplication.user.entity.YearDataEntitity;
import com.runnerapplication.user.helper.AdminHelper;
import com.runnerapplication.user.model.MarathonEvent;
import com.runnerapplication.user.model.YearDataModel;
import com.runnerapplication.user.repository.MarathonEventRepository;
import com.runnerapplication.user.repository.YearDataRepository;
import com.runnerapplication.user.util.StravaExtractor;

import javastrava.api.API;
import javastrava.api.AuthorisationAPI;
import javastrava.auth.AuthorisationService;
import javastrava.auth.impl.AuthorisationServiceImpl;
import javastrava.auth.model.Token;
import javastrava.auth.model.TokenResponse;
import javastrava.auth.ref.AuthorisationScope;
import javastrava.model.StravaActivity;
import javastrava.service.Strava;

@Service
public class AdminService {
	
	@Autowired
	MarathonEventRepository marathonEventRepository;
	
	@Autowired
	YearDataRepository yearDataRepository;
	
	@Autowired
	AdminHelper adminHelper;
	
	@Autowired
	StravaExtractor stravaExtractor;

	public MarathonEventEntity saveData(MarathonEventEntity marathonEventEntity) {
		MarathonEvent marathonEvent = adminHelper.convertEntityToTable(marathonEventEntity);
		return adminHelper.convertTableToEntity(marathonEventRepository.save(marathonEvent));
		
	}
	
	public List<TopperEntitity> getTopperDetails() {
		List<TopperEntitity> topperEntitity= new ArrayList<TopperEntitity>();
		List<YearDataModel> yearDataModelList =yearDataRepository.getAllData();
		topperEntitity=adminHelper.convertTableToYear(yearDataModelList);
		return topperEntitity;
	}
	

	public void addWholeYearData(YearDataEntitity yearDataEntitity) throws JsonProcessingException {
		YearDataModel yearDataModel=adminHelper.convertEntityToYearTable(yearDataEntitity);
		yearDataRepository.save(yearDataModel);
		
	}
	
	public List<TopperEntitity> getAllAcesToken() {
		List<YearDataEntitity> yearDataEntitity= new ArrayList<YearDataEntitity>();
		List<YearDataModel> yearDataModelList =yearDataRepository.getAllData();
		yearDataEntitity=adminHelper.convertTableToYearEntity(yearDataModelList);
		yearDataEntitity.stream().forEach(val->{
			long date=val.getExpiresAt();
			Date dateVal= new Date(date*1000);
			Date dateCurrent= new Date();
			if(dateVal.getTime() < dateCurrent.getTime()) {
				val=stravaExtractor.generateAccessTokenUsingRefresh(val);	
 			}
			val=stravaExtractor.updateTodayDetails(val);
			YearDataModel yearDataModel;
			try {
				yearDataModel = adminHelper.convertEntityToYearTable(val);
				System.out.println(yearDataModel.getAttributes());
				System.out.println(yearDataModel.getAttributesJson());
				yearDataRepository.save(yearDataModel);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		return getTopperDetails();
	}
	
	public List<TopperEntitity> sortTopperDetils(List<TopperEntitity> topperEntitityList){
		String selectMonth=topperEntitityList.get(0).getSelectMonth();
		topperEntitityList = topperEntitityList.stream().map(mapper->{
			Object monthval=mapper.getMonthlyMap().get(selectMonth);
			double temp=0.0;
			if (monthval instanceof Number) { 
	            temp= ((Number)monthval).doubleValue(); 
	        } 
			mapper.setDistance(temp);
			Object activedays=mapper.getActiveMap().get(selectMonth);
			int val=0;
			if (activedays instanceof Number) { 
				val= ((Number)activedays).intValue(); 
	        } 
			mapper.setActiveDays(val);
			return mapper;
		}).sorted(Comparator.comparingDouble(TopperEntitity:: getDistance).reversed()).collect(Collectors.toList());
		AtomicInteger atomicInteger= new AtomicInteger(0);
		return  topperEntitityList.stream().map(mapper->{
			mapper.setRank(atomicInteger.incrementAndGet());
			return mapper;
		}).collect(Collectors.toList());
	}
		
}
