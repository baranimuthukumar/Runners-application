package com.runnerapplication.user.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="MARATHON_EVENT")
public class MarathonEvent {
	
	@Id
	@GeneratedValue
	public long marathonId;
	public String eventName;
	public String eventYear;
	
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
	public String getEventYear() {
		return eventYear;
	}
	public void setEventYear(String eventYear) {
		this.eventYear = eventYear;
	}


}
