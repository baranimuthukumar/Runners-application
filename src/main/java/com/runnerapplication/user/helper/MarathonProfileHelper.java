package com.runnerapplication.user.helper;

import org.springframework.stereotype.Component;

import com.runnerapplication.user.entity.MarathonProfileEntity;
import com.runnerapplication.user.model.MarathonProfile;

@Component
public class MarathonProfileHelper {

	public MarathonProfile convertToDataBase(MarathonProfileEntity marathonProfileEntity) {
		MarathonProfile marathonProfile= new MarathonProfile();
		marathonProfile.setBestTime(marathonProfileEntity.getBestTime());
		if(marathonProfileEntity.getRegisterId()>0) {
			marathonProfile.setRegisterId(marathonProfileEntity.getRegisterId());
		}
		marathonProfile.setDistance(marathonProfileEntity.getDistance());
		marathonProfile.setEventName(marathonProfileEntity.getEventName());
		marathonProfile.setFutureOrpast(marathonProfileEntity.getFutureOrpast());
		marathonProfile.setMarathonId(marathonProfileEntity.getMarathonId());
		marathonProfile.setPaymentReference(marathonProfileEntity.getPaymentReference());
		marathonProfile.setProfileId(marathonProfileEntity.getProfileId());
		marathonProfile.setYear(marathonProfileEntity.getYear());
		marathonProfile.setPodium(marathonProfileEntity.getPodium());
		return marathonProfile;
	}

	public MarathonProfileEntity convertToEntity(MarathonProfile marathonProfile) {
		MarathonProfileEntity marathonProfileEntity= new MarathonProfileEntity();
		marathonProfileEntity.setBestTime(marathonProfile.getBestTime());
		marathonProfileEntity.setDistance(marathonProfile.getDistance());
		marathonProfileEntity.setEventName(marathonProfile.getEventName());
		marathonProfileEntity.setFutureOrpast(marathonProfile.getFutureOrpast());
		marathonProfileEntity.setMarathonId(marathonProfile.getMarathonId());
		marathonProfileEntity.setPaymentReference(marathonProfile.getPaymentReference());
		marathonProfileEntity.setProfileId(marathonProfile.getProfileId());
		marathonProfileEntity.setYear(marathonProfile.getYear());
		marathonProfileEntity.setPodium(marathonProfile.getPodium());
		return marathonProfileEntity;
	}

}
