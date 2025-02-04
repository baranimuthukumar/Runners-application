package com.runnerapplication.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.runnerapplication.user.model.MarathonEvent;
import com.runnerapplication.user.model.MarathonProfile;

@Repository
public interface MarathonEventRepository extends JpaRepository<MarathonEvent, Long>{

	@Query("SELECT mps FROM MarathonEvent mps")
	List<MarathonEvent> getAllFutureMarathonData();
	

}

