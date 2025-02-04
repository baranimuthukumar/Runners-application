package com.runnerapplication.user.entity;

public class MarathonEventEntity {
	
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
