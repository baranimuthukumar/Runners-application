package com.runnerapplication.user.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.runnerapplication.user.entity.EventReport;
import com.runnerapplication.user.rowmapper.EventReportMapper;

@Repository
public class CommonDao {

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	//@Query("SELECT rps.profileName,rps.ageCategory,mps.eventName,mps.year,mps.room,mps.travel,mps.distance,mps.bestTime,mps.paymentReference FROM MarathonProfile mps,RunnerProfile rps WHERE mps.marathonId = :marathonId AND mps.profileId=rps.profileId")
	//List<EventReport> getRunnersDataByProfileId(@Param("marathonId")long marathonId);

	public List<EventReport> getRunnersDataByProfileId(long marathonId) throws Exception{

		List<EventReport> eventReport= new ArrayList<EventReport>();
		try {
			String query="SELECT rps.profile_name,rps.age_category,mps.event_name,mps.year,mps.room,mps.travel,mps.distance,mps.best_time,mps.payment_reference FROM runnersapplication.marathon_profile mps,runnersapplication.runner_profile rps WHERE mps.marathon_id = :marathonId AND mps.profile_id=rps.profile_id";
			Map<String,Object> paramMap=new HashMap<String,Object>();
			paramMap.put("marathonId", marathonId);
			eventReport=jdbcTemplate.query(query, paramMap,new EventReportMapper());

		}catch(Exception e) {
			throw new Exception(e);
		}
		return eventReport;
	}
}
