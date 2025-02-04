package com.runnerapplication.user.entity;

public class RunnerStravaEntity {

	public long athleteId;
	public float decimalKilometer;
	public String startDate;
	
	public long getAthleteId() {
		return athleteId;
	}
	public void setAthleteId(long athleteId) {
		this.athleteId = athleteId;
	}
	public float getDecimalKilometer() {
		return decimalKilometer;
	}
	public void setDecimalKilometer(float decimalKilometer) {
		this.decimalKilometer = decimalKilometer;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	
}
