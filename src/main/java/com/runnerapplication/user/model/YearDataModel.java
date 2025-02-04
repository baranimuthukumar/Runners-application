package com.runnerapplication.user.model;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Entity
@Table(name="YEARDATA_MODEL")
public class YearDataModel {
	
	@Id
	public long athleteId;
	public String userName;
	public String totalKilometer;
	public double decimalKilometer;
	public String code;
	public long expiresAt;
	public String refreshToken;
	public int activeDays;
	
	@Column(columnDefinition = "TEXT")
    private String attributesJson;
	
	@Column(columnDefinition = "TEXT")
    private String activeDaysJson;
	
	

	@Transient
	private Map<String, Object> attributes=new HashMap<String, Object>();
	
	@Transient
	private Map<String, Object> activeAttributes=new HashMap<String, Object>();

	@PostLoad
    private void postLoad() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
        	attributes = objectMapper.readValue(attributesJson, new TypeReference<Map<String, Object>>() {});
        	activeAttributes = objectMapper.readValue(activeDaysJson, new TypeReference<Map<String, Object>>() {});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
	

//    @PrePersist
//    @PreUpdate
//    private void prePersist() {
//        ObjectMapper objectMapper = new ObjectMapper();
//        try {
//        	System.out.println("Barani-1 "+attributes);
//        	System.out.println("Barani-2 "+getAttributes());
//        	attributesJson = objectMapper.writeValueAsString(attributes);
//        	System.out.println("Serialized JSON: " + attributes);
//            
//        } catch (JsonProcessingException e) {
//        	attributesJson = "{}";
//            e.printStackTrace();
//        }
//    }
	
	public String getActiveDaysJson() {
		return activeDaysJson;
	}

	public void setActiveDaysJson(String activeDaysJson) {
		this.activeDaysJson = activeDaysJson;
	}

	public Map<String, Object> getActiveAttributes() {
		return activeAttributes;
	}

	public void setActiveAttributes(Map<String, Object> activeAttributes) {
		this.activeAttributes = activeAttributes;
	}

	public double getDecimalKilometer() {
		return decimalKilometer;
	}
	public void setDecimalKilometer(double decimalKilometer) {
		this.decimalKilometer = decimalKilometer;
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
	
	public String getAttributesJson() {
		return attributesJson;
	}

	public void setAttributesJson(String attributesJson) {
		this.attributesJson = attributesJson;
	}

}
