package com.runnerapplication.user.entity;

import java.util.List;

public class RunnerProfileEntity {
	
	public long serialNo;
	public long profileId;
	public String email;
	public String userName;
	public String password;
	public int age;
	public String dob;
	public String contactNumber;
	public String clubName;
	public boolean activeMember;
	public String ageCategory;
	public String personalBest;
	public String bloodGroup;
	public String tshirtSize;
	public String tshirtIssued;
	public String emergencyContactName;
	public String profession;
	public String stravaLink;
	public String interestsOtherThanRunning;
	public String tellAboutyourself;
	public String emergencyContactNumber;
	
	public String getEmergencyContactName() {
		return emergencyContactName;
	}
	public void setEmergencyContactName(String emergencyContactName) {
		this.emergencyContactName = emergencyContactName;
	}
	public String getProfession() {
		return profession;
	}
	public void setProfession(String profession) {
		this.profession = profession;
	}
	public String getStravaLink() {
		return stravaLink;
	}
	public void setStravaLink(String stravaLink) {
		this.stravaLink = stravaLink;
	}
	public String getInterestsOtherThanRunning() {
		return interestsOtherThanRunning;
	}
	public void setInterestsOtherThanRunning(String interestsOtherThanRunning) {
		this.interestsOtherThanRunning = interestsOtherThanRunning;
	}
	public String getTellAboutyourself() {
		return tellAboutyourself;
	}
	public void setTellAboutyourself(String tellAboutyourself) {
		this.tellAboutyourself = tellAboutyourself;
	}
	public String getEmergencyContactNumber() {
		return emergencyContactNumber;
	}
	public void setEmergencyContactNumber(String emergencyContactNumber) {
		this.emergencyContactNumber = emergencyContactNumber;
	}
	List<MarathonProfileEntity> marathonProfileList;
	
	public List<MarathonProfileEntity> getMarathonProfileList() {
		return marathonProfileList;
	}
	public void setMarathonProfileList(List<MarathonProfileEntity> marathonProfileList) {
		this.marathonProfileList = marathonProfileList;
	}
	public long getProfileId() {
		return profileId;
	}
	public void setProfileId(long profileId) {
		this.profileId = profileId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public String getClubName() {
		return clubName;
	}
	public void setClubName(String clubName) {
		this.clubName = clubName;
	}
	public boolean isActiveMember() {
		return activeMember;
	}
	public void setActiveMember(boolean activeMember) {
		this.activeMember = activeMember;
	}
	public String getAgeCategory() {
		return ageCategory;
	}
	public void setAgeCategory(String ageCategory) {
		this.ageCategory = ageCategory;
	}
	public String getPersonalBest() {
		return personalBest;
	}
	public void setPersonalBest(String personalBest) {
		this.personalBest = personalBest;
	}
	public String getTshirtSize() {
		return tshirtSize;
	}
	public void setTshirtSize(String tshirtSize) {
		this.tshirtSize = tshirtSize;
	}

	public String getTshirtIssued() {
		return tshirtIssued;
	}
	public void setTshirtIssued(String tshirtIssued) {
		this.tshirtIssued = tshirtIssued;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public long getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(long serialNo) {
		this.serialNo = serialNo;
	}
	public String getBloodGroup() {
		return bloodGroup;
	}
	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

}
