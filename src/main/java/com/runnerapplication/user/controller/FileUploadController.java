package com.runnerapplication.user.controller;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.runnerapplication.user.model.DatabaseFile;
import com.runnerapplication.user.payload.Response;
import com.runnerapplication.user.services.DatabaseFileService;

@CrossOrigin(origins="*")
@RestController
public class FileUploadController {

    @Autowired
    private DatabaseFileService fileStorageService;

    @PostMapping("/uploadFile")
    public List<Response> uploadFile(@RequestParam("file") MultipartFile file,@RequestParam("profileId") long profileId) {
        fileStorageService.storeFile(file,profileId);
        return getFiles(profileId);
    }
    
    @PostMapping("/files")
    public List<Response> getFiles(@RequestParam("profileId") long profileId) {
    	List<Response> returnFile= new ArrayList<Response>();
        List<DatabaseFile> dbFileList=fileStorageService.getFilebyProfileId(profileId);
        dbFileList.stream().forEach(val->{
        	String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
        			.path("/downloadFile/")
        			.path(val.getId())
        			.toUriString();
        	Response rs=new Response(val.getFileName(), fileDownloadUri,
        			val.getId(), val.getData().length);
        	returnFile.add(rs);
        	
        });
         return returnFile;
    }
    
    @PostMapping("/deleteFile")
    public List<Response> deleteFile(@RequestParam("profileId") long profileId,@RequestParam("fileId") String fileId) {
    	fileStorageService.deletefileById(fileId);
    	return getFiles(profileId);
    }

}