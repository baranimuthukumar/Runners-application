package com.runnerapplication.user.entity;

import java.util.HashMap;
import java.util.Map;

public class YearDataEntitity {

	public long athleteId;
	public String userName;
	public String totalKilometer;
	public double decimalKilometer;
	public String code;
	public long expiresAt;
	public String refreshToken;
	public int activeDays;
	public Map<String, Object> attributes= new HashMap<String, Object>();
	public Map<String, Object> activeDaysattributes= new HashMap<String, Object>();
	
	
	public Map<String, Object> getActiveDaysattributes() {
		return activeDaysattributes;
	}
	public void setActiveDaysattributes(Map<String, Object> activeDaysattributes) {
		this.activeDaysattributes = activeDaysattributes;
	}
	public int getActiveDays() {
		return activeDays;
	}
	public void setActiveDays(int activeDays) {
		this.activeDays = activeDays;
	}
	public Map<String, Object> getAttributes() {
		return attributes;
	}
	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public long getExpiresAt() {
		return expiresAt;
	}
	public void setExpiresAt(long expiresAt) {
		this.expiresAt = expiresAt;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public double getDecimalKilometer() {
		return decimalKilometer;
	}
	public void setDecimalKilometer(double decimalKilometer) {
		this.decimalKilometer = decimalKilometer;
	}
	public long getAthleteId() {
		return athleteId;
	}
	public void setAthleteId(long athleteId) {
		this.athleteId = athleteId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getTotalKilometer() {
		return totalKilometer;
	}
	public void setTotalKilometer(String totalKilometer) {
		this.totalKilometer = totalKilometer;
	}
	
}
