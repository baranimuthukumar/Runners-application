package com.runnerapplication.user.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.runnerapplication.user.entity.MarathonEventEntity;
import com.runnerapplication.user.entity.MarathonProfileEntity;
import com.runnerapplication.user.entity.TopperEntitity;
import com.runnerapplication.user.entity.YearDataEntitity;
import com.runnerapplication.user.model.MarathonEvent;
import com.runnerapplication.user.model.YearDataModel;

@Component
public class AdminHelper {

	public MarathonEvent convertEntityToTable(MarathonEventEntity marathonEventEntity) {
		MarathonEvent marathonEvent= new MarathonEvent();
		marathonEvent.setEventName(marathonEventEntity.getEventName());
		marathonEvent.setEventYear(marathonEventEntity.getEventYear());
		return marathonEvent;
	}

	public MarathonEventEntity convertTableToEntity(MarathonEvent marathonEvent) {
		MarathonEventEntity marathonEventEntity= new MarathonEventEntity();
		marathonEventEntity.setEventName(marathonEvent.getEventName());
		marathonEventEntity.setEventYear(marathonEvent.getEventYear());
		return marathonEventEntity;
	}

	public List<MarathonEventEntity> convertToEntityList(List<MarathonEvent> eventList) {
		List<MarathonEventEntity> marathonEventEntityList=eventList.stream().map(val->{
			MarathonEventEntity marathonEventEntity= new MarathonEventEntity();
			marathonEventEntity.setEventName(val.getEventName()+" - "+val.getEventYear());
			marathonEventEntity.setEventYear(val.getEventYear());
			marathonEventEntity.setMarathonId(val.getMarathonId());
			return marathonEventEntity;
				}).collect(Collectors.toList());
		return marathonEventEntityList;
		
	}
	
	public YearDataModel convertEntityToYearTable(YearDataEntitity yearDataEntitity) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		YearDataModel yearDataModel= new YearDataModel();
		yearDataModel.setAthleteId(yearDataEntitity.getAthleteId());
		yearDataModel.setTotalKilometer(yearDataEntitity.getTotalKilometer());
		yearDataModel.setUserName(yearDataEntitity.getUserName());
		yearDataModel.setDecimalKilometer(yearDataEntitity.getDecimalKilometer());
		yearDataModel.setCode(yearDataEntitity.getCode());
		yearDataModel.setExpiresAt(yearDataEntitity.getExpiresAt());
		yearDataModel.setAttributes(yearDataEntitity.getAttributes());
		yearDataModel.setAttributesJson(objectMapper.writeValueAsString(yearDataEntitity.getAttributes()));
		yearDataModel.setActiveDaysJson(objectMapper.writeValueAsString(yearDataEntitity.getActiveDaysattributes()));
		yearDataModel.setActiveDays(yearDataEntitity.getActiveDays());
		yearDataModel.setRefreshToken(yearDataEntitity.getRefreshToken());
		return yearDataModel;
	}

	public List<TopperEntitity> convertTableToYear(List<YearDataModel> yearDataModelList) {
		List<TopperEntitity> topperList= new ArrayList<TopperEntitity>();
		AtomicInteger count= new AtomicInteger(0);
		topperList=yearDataModelList.stream().map(val->{
			TopperEntitity topperEntitity= new TopperEntitity();
			topperEntitity.setRank(count.incrementAndGet());
			topperEntitity.setName(val.getUserName());
			topperEntitity.setDistance(val.getDecimalKilometer());
			topperEntitity.setActiveMap(val.getActiveAttributes());
			topperEntitity.setActiveDays(val.getActiveDays());
			topperEntitity.setMonthlyMap(val.getAttributes());
			return topperEntitity;
		}).collect(Collectors.toList());
		return topperList;
	}
	
	public List<YearDataEntitity> convertTableToYearEntity(List<YearDataModel> yearDataModelList) {
		return yearDataModelList.stream().map(val->{
			YearDataEntitity yearDataEntitity= new YearDataEntitity();
			yearDataEntitity.setAthleteId(val.getAthleteId());
			yearDataEntitity.setCode(val.getCode());
			yearDataEntitity.setDecimalKilometer(val.getDecimalKilometer());
			yearDataEntitity.setExpiresAt(val.getExpiresAt());
			yearDataEntitity.setRefreshToken(val.getRefreshToken());
			yearDataEntitity.setAttributes(val.getAttributes());
			yearDataEntitity.setActiveDaysattributes(val.getActiveAttributes());
			yearDataEntitity.setTotalKilometer(val.getTotalKilometer());
			yearDataEntitity.setUserName(val.getUserName());
			return yearDataEntitity;
		}).collect(Collectors.toList());
	}
	

}
