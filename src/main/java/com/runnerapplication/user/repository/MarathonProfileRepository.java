package com.runnerapplication.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.runnerapplication.user.entity.EventReport;
import com.runnerapplication.user.model.MarathonProfile;

@Repository
public interface MarathonProfileRepository extends JpaRepository<MarathonProfile, Long>{

	@Query("SELECT mps FROM MarathonProfile mps WHERE mps.profileId = :profileId")
	List<MarathonProfile> getProfileData(@Param("profileId")long profileId);

	@Query("SELECT mps.profileId FROM MarathonProfile mps WHERE mps.eventName = :eventName and mps.futureOrpast=:futureOrpast")
	List<Long> findAllByEventName(@Param("eventName")String eventName, @Param("futureOrpast")String futureOrpast);

}

