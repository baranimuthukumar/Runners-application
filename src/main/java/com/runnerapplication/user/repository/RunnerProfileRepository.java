package com.runnerapplication.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.runnerapplication.user.entity.RunnerProfileEntity;
import com.runnerapplication.user.model.RunnerProfile;

@Repository
public interface RunnerProfileRepository extends JpaRepository<RunnerProfile, Long>{

	@Query("SELECT rps FROM RunnerProfile rps")
	List<RunnerProfile> getAllData();

	@Query("SELECT rps FROM RunnerProfile rps where rps.activeMember=:activeMember")
	List<RunnerProfile> getAllActiveMemberData(@Param("activeMember") boolean activeMember);

}
