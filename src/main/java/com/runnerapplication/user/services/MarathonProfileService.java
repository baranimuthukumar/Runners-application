package com.runnerapplication.user.services;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.runnerapplication.user.entity.MarathonEventEntity;
import com.runnerapplication.user.entity.MarathonProfileEntity;
import com.runnerapplication.user.entity.RunnerProfileEntity;
import com.runnerapplication.user.helper.AdminHelper;
import com.runnerapplication.user.helper.MarathonProfileHelper;
import com.runnerapplication.user.helper.RunnerProfileHelper;
import com.runnerapplication.user.helper.UserDetailsHelper;
import com.runnerapplication.user.model.MarathonEvent;
import com.runnerapplication.user.model.MarathonProfile;
import com.runnerapplication.user.model.RunnerProfile;
import com.runnerapplication.user.model.UserDetails;
import com.runnerapplication.user.repository.MarathonEventRepository;
import com.runnerapplication.user.repository.MarathonProfileRepository;
import com.runnerapplication.user.repository.RunnerProfileRepository;
import com.runnerapplication.user.repository.UserDetailsRepository;

@Service
public class MarathonProfileService {
	
	@Autowired
	MarathonProfileRepository marathonProfileRepository;
	
	@Autowired
	RunnerProfileRepository runnerProfileRepository;
	
	@Autowired
	UserDetailsRepository userDetailsRepository;
	
	@Autowired
	MarathonProfileHelper marathonProfileHelper;
	
	@Autowired
	RunnerProfileHelper runnerProfileHelper;
	
	@Autowired
	UserDetailsHelper userDetailsHelper;
	
	@Autowired
	MarathonEventRepository marathonEventRepository;
	
	@Autowired
	AdminHelper adminHelper;

	public MarathonProfileEntity saveOrUpdateProfileData(MarathonProfileEntity marathonProfileEntity) {
		RunnerProfile runnerProfile= new RunnerProfile();
		MarathonProfile marathonProfile= marathonProfileHelper.convertToDataBase(marathonProfileEntity);
		if(marathonProfile.getProfileId()>0) {
			runnerProfile=runnerProfileRepository.getById(marathonProfileEntity.getProfileId());
			if(marathonProfileEntity.getBestTime().length()>0 && runnerProfile.getPersonalBest()!=null && marathonProfile.getBestTime()!=null) {
				long val1=convertToLong(runnerProfile.getPersonalBest());
				long val2=convertToLong(marathonProfile.getBestTime());
				if(val2<val1) {
					runnerProfile.setPersonalBest(marathonProfile.getBestTime());
					runnerProfileRepository.save(runnerProfile);	
				}
			}	
		}
		MarathonProfile marathonProfileSaved=marathonProfileRepository.save(marathonProfile);
		return marathonProfileHelper.convertToEntity(marathonProfileSaved);
	}
	
	static long convertToLong(String time) {
		String[] splitVal=time.split(":");
		long val = (Integer.parseInt(splitVal[0])*60*60)+(Integer.parseInt(splitVal[1])*60)+(Integer.parseInt(splitVal[2]));
		return val;
	}

	public RunnerProfileEntity saveOrUpdateRunnerData(RunnerProfileEntity runnerProfileEntity) {
		RunnerProfile runnerProfile= new RunnerProfile();
		UserDetails userDetails = new UserDetails();
		try {
			runnerProfile = runnerProfileHelper.convertToDataBase(runnerProfileEntity);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RunnerProfile runnerProfileSaved=runnerProfileRepository.save(runnerProfile);
		userDetails = userDetailsHelper.convertToDataBaseFromAdm(runnerProfileSaved);
		userDetailsRepository.save(userDetails);
		
		return runnerProfileHelper.convertToEntity(runnerProfileSaved);
	}
	
	public RunnerProfileEntity getProfileData(String profileId) {
		RunnerProfile runnerProfile=runnerProfileRepository.getById(Long.parseLong(profileId));
		List<MarathonProfile> profileList=marathonProfileRepository.getProfileData(Long.parseLong(profileId));
		return runnerProfileHelper.convertToEntity(runnerProfile,profileList);
	}

	public List<RunnerProfileEntity> getAllRunnerData() {
		List<RunnerProfile> runnerProfileList=runnerProfileRepository.getAllData();
		return runnerProfileHelper.convertToEntityList(runnerProfileList);
	}

	public void deleteRunnerData(RunnerProfileEntity runnerProfileEntity) {
		runnerProfileRepository.deleteById(runnerProfileEntity.getProfileId());
	}
	
	public void deleteMarathonData(String registerId) {
		marathonProfileRepository.deleteById(Long.parseLong(registerId));
	}

	public List<RunnerProfileEntity> getAllRunnerFutureData(MarathonProfileEntity marathonProfileEntity) {
		List<Long> ruunerList=marathonProfileRepository.findAllByEventName(marathonProfileEntity.getEventName(),marathonProfileEntity.getFutureOrpast());
		ruunerList.stream().forEach(val->{
			System.out.println("Barani "+val);
		});
		List<RunnerProfile> runnerProfileList=runnerProfileRepository.findAllById(ruunerList);
		return runnerProfileHelper.convertToEntityList(runnerProfileList);
	}

	public List<RunnerProfileEntity> getAllActiveMemberData(boolean activeMember) {
		List<RunnerProfile> runnerProfileList=runnerProfileRepository.getAllActiveMemberData(activeMember);
		return runnerProfileHelper.convertToEntityList(runnerProfileList);
	}
	
	public List<MarathonEventEntity> getAllFutureMarathonData(){
		List<MarathonEvent> eventList=marathonEventRepository.getAllFutureMarathonData();
		return adminHelper.convertToEntityList(eventList);
	}

}
