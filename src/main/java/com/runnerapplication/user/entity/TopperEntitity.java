package com.runnerapplication.user.entity;

import java.util.Map;

public class TopperEntitity {

	int rank;
	String name;
	double distance;
	Map<String,Object> monthlyMap;
	Map<String,Object> activeMap;
	public Map<String, Object> getActiveMap() {
		return activeMap;
	}
	public void setActiveMap(Map<String, Object> activeMap) {
		this.activeMap = activeMap;
	}
	String selectMonth;
	int activeDays;
	
	public int getActiveDays() {
		return activeDays;
	}
	public void setActiveDays(int activeDays) {
		this.activeDays = activeDays;
	}
	public String getSelectMonth() {
		return selectMonth;
	}
	public void setSelectMonth(String selectMonth) {
		this.selectMonth = selectMonth;
	}
	public Map<String, Object> getMonthlyMap() {
		return monthlyMap;
	}
	public void setMonthlyMap(Map<String, Object> monthlyMap) {
		this.monthlyMap = monthlyMap;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
}
