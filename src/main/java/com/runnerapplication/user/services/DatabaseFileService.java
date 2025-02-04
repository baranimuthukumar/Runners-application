package com.runnerapplication.user.services;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.runnerapplication.user.exception.FileNotFoundException;
import com.runnerapplication.user.exception.FileStorageException;
import com.runnerapplication.user.model.DatabaseFile;
import com.runnerapplication.user.repository.DatabaseFileRepository;


@Service
public class DatabaseFileService {

    @Autowired
    private DatabaseFileRepository dbFileRepository;

    public DatabaseFile storeFile(MultipartFile file, long profileId) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            DatabaseFile dbFile = new DatabaseFile(fileName, file.getContentType(), file.getBytes(),profileId);

            return dbFileRepository.save(dbFile);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }
    
    public List<DatabaseFile> getFilebyProfileId(long profileId) {
    	List<DatabaseFile> dbFileList=dbFileRepository.getFilebyProfileId(profileId);
    	return dbFileList;
    }

    public DatabaseFile getFile(String fileId) {
        return dbFileRepository.findById(fileId)
            .orElseThrow(() -> new FileNotFoundException("File not found with id " + fileId));
    }

	public void deletefileById(String fileId) {
		dbFileRepository.deleteById(fileId);
		
	}
}