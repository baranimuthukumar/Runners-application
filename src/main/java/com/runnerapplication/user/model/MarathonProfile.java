package com.runnerapplication.user.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="MARATHON_PROFILE")
public class MarathonProfile {

	@Id
	@GeneratedValue
	public long registerId;
	public long marathonId;
	public String eventName;
	public long profileId;
	public String year;
	public String bestTime;
	public String futureOrpast;
	public String distance;
	public String paymentReference;
	public String podium;
	
	public String getPodium() {
		return podium;
	}
	public void setPodium(String podium) {
		this.podium = podium;
	}
	public long getRegisterId() {
		return registerId;
	}
	public void setRegisterId(long registerId) {
		this.registerId = registerId;
	}
	public long getMarathonId() {
		return marathonId;
	}
	public void setMarathonId(long marathonId) {
		this.marathonId = marathonId;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public long getProfileId() {
		return profileId;
	}
	public void setProfileId(long profileId) {
		this.profileId = profileId;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getBestTime() {
		return bestTime;
	}
	public void setBestTime(String bestTime) {
		this.bestTime = bestTime;
	}
	public String getFutureOrpast() {
		return futureOrpast;
	}
	public void setFutureOrpast(String futureOrpast) {
		this.futureOrpast = futureOrpast;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public String getPaymentReference() {
		return paymentReference;
	}
	public void setPaymentReference(String paymentReference) {
		this.paymentReference = paymentReference;
	}
	
}
