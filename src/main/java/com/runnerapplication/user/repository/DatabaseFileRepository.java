package com.runnerapplication.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.runnerapplication.user.model.DatabaseFile;


public interface DatabaseFileRepository extends JpaRepository<DatabaseFile, String> {

	@Query("SELECT dbf FROM DatabaseFile dbf WHERE dbf.profileId = :profileId")
	List<DatabaseFile> getFilebyProfileId(long profileId);
}
