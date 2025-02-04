package com.runnerapplication.user.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.runnerapplication.user.entity.EventReport;

public class EventReportMapper implements RowMapper<EventReport>{

	@Override
	public EventReport mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		EventReport eventReport= new EventReport();
		eventReport.setProfileName(rs.getString("profile_name"));
		eventReport.setAgeCategory(rs.getString("age_category"));
		eventReport.setBestTime(rs.getString("best_time"));
		eventReport.setDistance(rs.getString("distance"));
		eventReport.setEventName(rs.getString("event_name"));
		eventReport.setPaymentReference(rs.getString("payment_reference"));
		eventReport.setRoom(rs.getString("room"));
		eventReport.setTravel(rs.getString("travel"));
		eventReport.setYear(rs.getString("year"));
		return eventReport;
	}

}
