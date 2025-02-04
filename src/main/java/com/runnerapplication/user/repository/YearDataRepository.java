package com.runnerapplication.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.runnerapplication.user.model.YearDataModel;

public interface YearDataRepository extends JpaRepository<YearDataModel, Long>{
	
	@Query("SELECT ydm FROM YearDataModel ydm order by ydm.decimalKilometer desc")
	List<YearDataModel> getAllData();

}
