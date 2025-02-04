package com.runnerapplication.user.util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeParseException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import com.google.gson.*;
import com.runnerapplication.user.entity.RunnerStravaEntity;
import com.runnerapplication.user.entity.YearDataEntitity;
import com.runnerapplication.user.model.YearDataModel;

@Component
public class StravaExtractor {
	private static final String ACCESS_TOKEN = "82e690a88a9a6030fefdda7a13154c0348a69342";
	private static final String ACTIVITIES_API_URL = "https://www.strava.com/api/v3/athlete/activities";
	private static final String REFRESH_API_URL="https://www.strava.com/oauth/token";
	private static final String CLIENTID = "125589";
	private  static final String CLIENTSECRECT = "8a343f2463827c3ca561b538627eaec1d37a723e";

	public static void main(String[] args) {}

	public YearDataEntitity updateTodayDetails(YearDataEntitity yearDataEntitity) {
		List<RunnerStravaEntity> runnerStravaEntityList=getActivities(yearDataEntitity.getCode());
		String startDate = "2024-01-01T00:00:00Z";
		String endDate = "2024-12-31T00:00:00Z";
		final DecimalFormat decfor = new DecimalFormat("0.00");  
		try {
			Instant date1 = Instant.parse(startDate);
			Instant date2 = Instant.parse(endDate);
			runnerStravaEntityList=runnerStravaEntityList.stream().filter(val-> Instant.parse(val.getStartDate()).isAfter(date1) && Instant.parse(val.getStartDate()).isBefore(date2)).collect(Collectors.toList());
			if(yearDataEntitity.getAttributes().size() == 0) {
				yearDataEntitity=findingMonthlySplitWhole(runnerStravaEntityList,yearDataEntitity);
			}else {
				yearDataEntitity=findingCurrentMonthWhole(runnerStravaEntityList,yearDataEntitity);

			}
		} catch (DateTimeParseException e) {
			e.printStackTrace();
		}
		double totalKilometer=0.00;
		String totalKilometerString="";
		int activedays=0;
		for(int itr=0;itr<runnerStravaEntityList.size();itr++) {
			totalKilometer+=runnerStravaEntityList.get(itr).getDecimalKilometer();
			activedays++;
		}
		totalKilometer=totalKilometer/1000;
		totalKilometer=Double.parseDouble(decfor.format(totalKilometer));
		totalKilometerString=String.valueOf(totalKilometer)+" km";
		yearDataEntitity.setDecimalKilometer(totalKilometer);
		yearDataEntitity.setTotalKilometer(totalKilometerString);
		yearDataEntitity.setActiveDays(activedays);
		return yearDataEntitity;
	}

	/**	private YearDataEntitity findingMonthlySplit(YearDataEntitity yearDataEntitity, List<RunnerStravaEntity> runnerStravaEntityList) {
		String janStart = "2024-01-01T00:00:00Z";
		String janEnd = "2024-02-01T00:00:00Z";
		final DecimalFormat decfor = new DecimalFormat("0.00"); 
		List<RunnerStravaEntity> runnerStravaEntityListVal= new ArrayList<RunnerStravaEntity>();
		try {
			Instant date1 = Instant.parse(janStart);
			Instant date2 = Instant.parse(janEnd);
			runnerStravaEntityListVal=runnerStravaEntityList.stream().filter(val-> Instant.parse(val.getStartDate()).isAfter(date1) && Instant.parse(val.getStartDate()).isBefore(date2)).collect(Collectors.toList());

		} catch (DateTimeParseException e) {
			e.printStackTrace();
		}
		double totalKilometer=0.00;
		int count=0;
		for(int itr=0;itr<runnerStravaEntityListVal.size();itr++) {
			totalKilometer+=runnerStravaEntityListVal.get(itr).getDecimalKilometer();
			count++;
		}
		totalKilometer=totalKilometer/1000;
		totalKilometer=Double.parseDouble(decfor.format(totalKilometer));
		Map<String,Object> monthlyMap= yearDataEntitity.getAttributes();
		monthlyMap.put("Jan", totalKilometer);
		yearDataEntitity.setAttributes(monthlyMap);
		yearDataEntitity.setActiveDays(count);
		String febStart = "2024-02-01T00:00:00Z";
		String febEnd = "2024-03-01T00:00:00Z";
		try {
			Instant date1 = Instant.parse(febStart);
			Instant date2 = Instant.parse(febEnd);
			runnerStravaEntityListVal=runnerStravaEntityList.stream().filter(val-> Instant.parse(val.getStartDate()).isAfter(date1) && Instant.parse(val.getStartDate()).isBefore(date2)).collect(Collectors.toList());

		} catch (DateTimeParseException e) {
			e.printStackTrace();
		}
		totalKilometer=0.00;
		count=0;
		for(int itr=0;itr<runnerStravaEntityListVal.size();itr++) {
			totalKilometer+=runnerStravaEntityListVal.get(itr).getDecimalKilometer();
			count++;
		}
		totalKilometer=totalKilometer/1000;
		totalKilometer=Double.parseDouble(decfor.format(totalKilometer));
		monthlyMap= yearDataEntitity.getAttributes();
		monthlyMap.put("Feb", totalKilometer);
		yearDataEntitity.setAttributes(monthlyMap);
		yearDataEntitity.setActiveDays(count);
		String marStart = "2024-03-01T00:00:00Z";
		String marEnd = "2024-04-01T00:00:00Z";
		try {
			Instant date1 = Instant.parse(marStart);
			Instant date2 = Instant.parse(marEnd);
			runnerStravaEntityListVal=runnerStravaEntityList.stream().filter(val-> Instant.parse(val.getStartDate()).isAfter(date1) && Instant.parse(val.getStartDate()).isBefore(date2)).collect(Collectors.toList());

		} catch (DateTimeParseException e) {
			e.printStackTrace();
		}
		totalKilometer=0.00;
		count=0;
		for(int itr=0;itr<runnerStravaEntityListVal.size();itr++) {
			totalKilometer+=runnerStravaEntityListVal.get(itr).getDecimalKilometer();
			count++;
		}
		totalKilometer=totalKilometer/1000;
		totalKilometer=Double.parseDouble(decfor.format(totalKilometer));
		monthlyMap= yearDataEntitity.getAttributes();
		monthlyMap.put("Mar", totalKilometer);
		yearDataEntitity.setAttributes(monthlyMap);
		yearDataEntitity.setActiveDays(count);
		String aprStart = "2024-04-01T00:00:00Z";
		String aprEnd = "2024-05-01T00:00:00Z";
		try {
			Instant date1 = Instant.parse(aprStart);
			Instant date2 = Instant.parse(aprEnd);
			runnerStravaEntityListVal=runnerStravaEntityList.stream().filter(val-> Instant.parse(val.getStartDate()).isAfter(date1) && Instant.parse(val.getStartDate()).isBefore(date2)).collect(Collectors.toList());

		} catch (DateTimeParseException e) {
			e.printStackTrace();
		}
		totalKilometer=0.00;
		count=0;
		for(int itr=0;itr<runnerStravaEntityListVal.size();itr++) {
			totalKilometer+=runnerStravaEntityListVal.get(itr).getDecimalKilometer();
			count++;
		}
		totalKilometer=totalKilometer/1000;
		totalKilometer=Double.parseDouble(decfor.format(totalKilometer));
		monthlyMap= yearDataEntitity.getAttributes();
		monthlyMap.put("Apr", totalKilometer);
		yearDataEntitity.setAttributes(monthlyMap);
		yearDataEntitity.setActiveDays(count);
		String mayStart = "2024-05-01T00:00:00Z";
		String mayEnd = "2024-06-01T00:00:00Z";
		try {
			Instant date1 = Instant.parse(mayStart);
			Instant date2 = Instant.parse(mayEnd);
			runnerStravaEntityListVal=runnerStravaEntityList.stream().filter(val-> Instant.parse(val.getStartDate()).isAfter(date1) && Instant.parse(val.getStartDate()).isBefore(date2)).collect(Collectors.toList());

		} catch (DateTimeParseException e) {
			e.printStackTrace();
		}
		totalKilometer=0.00;
		count=0;
		for(int itr=0;itr<runnerStravaEntityListVal.size();itr++) {
			totalKilometer+=runnerStravaEntityListVal.get(itr).getDecimalKilometer();
			count++;
		}
		totalKilometer=totalKilometer/1000;
		totalKilometer=Double.parseDouble(decfor.format(totalKilometer));
		monthlyMap= yearDataEntitity.getAttributes();
		monthlyMap.put("May", totalKilometer);
		yearDataEntitity.setAttributes(monthlyMap);
		yearDataEntitity.setActiveDays(count);
		String junStart = "2024-06-01T00:00:00Z";
		String junEnd = "2024-07-01T00:00:00Z";
		try {
			Instant date1 = Instant.parse(junStart);
			Instant date2 = Instant.parse(junEnd);
			runnerStravaEntityListVal=runnerStravaEntityList.stream().filter(val-> Instant.parse(val.getStartDate()).isAfter(date1) && Instant.parse(val.getStartDate()).isBefore(date2)).collect(Collectors.toList());

		} catch (DateTimeParseException e) {
			e.printStackTrace();
		}
		totalKilometer=0.00;
		count=0;
		for(int itr=0;itr<runnerStravaEntityListVal.size();itr++) {
			totalKilometer+=runnerStravaEntityListVal.get(itr).getDecimalKilometer();
			count++;
		}
		totalKilometer=totalKilometer/1000;
		totalKilometer=Double.parseDouble(decfor.format(totalKilometer));
		monthlyMap= yearDataEntitity.getAttributes();
		monthlyMap.put("Jun", totalKilometer);
		yearDataEntitity.setAttributes(monthlyMap);
		yearDataEntitity.setActiveDays(count);
		String julStart = "2024-07-01T00:00:00Z";
		String julEnd = "2024-08-01T00:00:00Z";
		try {
			Instant date1 = Instant.parse(julStart);
			Instant date2 = Instant.parse(julEnd);
			runnerStravaEntityListVal=runnerStravaEntityList.stream().filter(val-> Instant.parse(val.getStartDate()).isAfter(date1) && Instant.parse(val.getStartDate()).isBefore(date2)).collect(Collectors.toList());

		} catch (DateTimeParseException e) {
			e.printStackTrace();
		}
		totalKilometer=0.00;
		count=0;
		for(int itr=0;itr<runnerStravaEntityListVal.size();itr++) {
			totalKilometer+=runnerStravaEntityListVal.get(itr).getDecimalKilometer();
			count++;
		}
		totalKilometer=totalKilometer/1000;
		totalKilometer=Double.parseDouble(decfor.format(totalKilometer));
		monthlyMap= yearDataEntitity.getAttributes();
		monthlyMap.put("Jul", totalKilometer);
		yearDataEntitity.setAttributes(monthlyMap);
		yearDataEntitity.setActiveDays(count);
		String augStart = "2024-08-01T00:00:00Z";
		String augEnd = "2024-09-01T00:00:00Z";
		try {
			Instant date1 = Instant.parse(augStart);
			Instant date2 = Instant.parse(augEnd);
			runnerStravaEntityListVal=runnerStravaEntityList.stream().filter(val-> Instant.parse(val.getStartDate()).isAfter(date1) && Instant.parse(val.getStartDate()).isBefore(date2)).collect(Collectors.toList());

		} catch (DateTimeParseException e) {
			e.printStackTrace();
		}
		totalKilometer=0.00;
		count=0;
		for(int itr=0;itr<runnerStravaEntityListVal.size();itr++) {
			totalKilometer+=runnerStravaEntityListVal.get(itr).getDecimalKilometer();
			count++;
		}
		totalKilometer=totalKilometer/1000;
		totalKilometer=Double.parseDouble(decfor.format(totalKilometer));
		monthlyMap= yearDataEntitity.getAttributes();
		monthlyMap.put("Aug", totalKilometer);
		yearDataEntitity.setAttributes(monthlyMap);
		yearDataEntitity.setActiveDays(count);
		String sepStart = "2024-09-01T00:00:00Z";
		String sepEnd = "2024-10-01T00:00:00Z";
		try {
			Instant date1 = Instant.parse(sepStart);
			Instant date2 = Instant.parse(sepEnd);
			runnerStravaEntityListVal=runnerStravaEntityList.stream().filter(val-> Instant.parse(val.getStartDate()).isAfter(date1) && Instant.parse(val.getStartDate()).isBefore(date2)).collect(Collectors.toList());

		} catch (DateTimeParseException e) {
			e.printStackTrace();
		}
		totalKilometer=0.00;
		count=0;
		for(int itr=0;itr<runnerStravaEntityListVal.size();itr++) {
			totalKilometer+=runnerStravaEntityListVal.get(itr).getDecimalKilometer();
			count++;
		}
		totalKilometer=totalKilometer/1000;
		totalKilometer=Double.parseDouble(decfor.format(totalKilometer));
		monthlyMap= yearDataEntitity.getAttributes();
		monthlyMap.put("Sep", totalKilometer);
		yearDataEntitity.setAttributes(monthlyMap);
		yearDataEntitity.setActiveDays(count);
		String octStart = "2024-10-01T00:00:00Z";
		String octEnd = "2024-11-01T00:00:00Z";
		try {
			Instant date1 = Instant.parse(octStart);
			Instant date2 = Instant.parse(octEnd);
			runnerStravaEntityListVal=runnerStravaEntityList.stream().filter(val-> Instant.parse(val.getStartDate()).isAfter(date1) && Instant.parse(val.getStartDate()).isBefore(date2)).collect(Collectors.toList());

		} catch (DateTimeParseException e) {
			e.printStackTrace();
		}
		totalKilometer=0.00;
		count=0;
		for(int itr=0;itr<runnerStravaEntityListVal.size();itr++) {
			totalKilometer+=runnerStravaEntityListVal.get(itr).getDecimalKilometer();
			count++;
		}
		totalKilometer=totalKilometer/1000;
		totalKilometer=Double.parseDouble(decfor.format(totalKilometer));
		monthlyMap= yearDataEntitity.getAttributes();
		monthlyMap.put("Oct", totalKilometer);
		yearDataEntitity.setAttributes(monthlyMap);
		yearDataEntitity.setActiveDays(count);
		String novStart = "2024-11-01T00:00:00Z";
		String novEnd = "2024-12-01T00:00:00Z";
		try {
			Instant date1 = Instant.parse(novStart);
			Instant date2 = Instant.parse(novEnd);
			runnerStravaEntityListVal=runnerStravaEntityList.stream().filter(val-> Instant.parse(val.getStartDate()).isAfter(date1) && Instant.parse(val.getStartDate()).isBefore(date2)).collect(Collectors.toList());

		} catch (DateTimeParseException e) {
			e.printStackTrace();
		}
		totalKilometer=0.00;
		count=0;
		for(int itr=0;itr<runnerStravaEntityListVal.size();itr++) {
			totalKilometer+=runnerStravaEntityListVal.get(itr).getDecimalKilometer();
			count++;
		}
		totalKilometer=totalKilometer/1000;
		totalKilometer=Double.parseDouble(decfor.format(totalKilometer));
		monthlyMap= yearDataEntitity.getAttributes();
		monthlyMap.put("Nov", totalKilometer);
		yearDataEntitity.setAttributes(monthlyMap);
		yearDataEntitity.setActiveDays(count);
		String decStart = "2024-12-01T00:00:00Z";
		String decEnd = "2025-01-01T00:00:00Z";
		try {
			Instant date1 = Instant.parse(decStart);
			Instant date2 = Instant.parse(decEnd);
			runnerStravaEntityListVal=runnerStravaEntityList.stream().filter(val-> Instant.parse(val.getStartDate()).isAfter(date1) && Instant.parse(val.getStartDate()).isBefore(date2)).collect(Collectors.toList());

		} catch (DateTimeParseException e) {
			e.printStackTrace();
		}
		totalKilometer=0.00;
		count=0;
		for(int itr=0;itr<runnerStravaEntityListVal.size();itr++) {
			totalKilometer+=runnerStravaEntityListVal.get(itr).getDecimalKilometer();
			count++;
		}
		totalKilometer=totalKilometer/1000;
		totalKilometer=Double.parseDouble(decfor.format(totalKilometer));
		monthlyMap= yearDataEntitity.getAttributes();
		monthlyMap.put("Dec", totalKilometer);
		yearDataEntitity.setAttributes(monthlyMap);
		yearDataEntitity.setActiveDays(count);
		System.out.println("Jan "+monthlyMap.get("Jan"));
		System.out.println("Feb "+monthlyMap.get("Feb"));
		System.out.println("Mar "+monthlyMap.get("Mar"));
		System.out.println("Apr "+monthlyMap.get("Apr"));
		System.out.println("May "+monthlyMap.get("May"));
		System.out.println("Jun "+monthlyMap.get("Jun"));
		System.out.println("Jul "+monthlyMap.get("Jul"));
		System.out.println("Jul "+monthlyMap.get("Aug"));
		System.out.println("Jul "+monthlyMap.get("Sep"));
		System.out.println("Jul "+monthlyMap.get("Oct"));
		System.out.println("Jul "+monthlyMap.get("Nov"));
		System.out.println("Jul "+monthlyMap.get("Dec"));
		return yearDataEntitity;
	} */

	private YearDataEntitity findingCurrentMonthWhole(List<RunnerStravaEntity> runnerStravaEntityList, YearDataEntitity yearDataEntitity) {
		List<String> monthList= new ArrayList<String>();
		monthList.add("Jan");
		monthList.add("Feb");
		monthList.add("Mar");
		monthList.add("Apr");
		monthList.add("May");
		monthList.add("Jun");
		monthList.add("Jul");
		monthList.add("Aug");
		monthList.add("Sep");
		monthList.add("Oct");
		monthList.add("Nov");
		monthList.add("Dec");
		monthList.add("Jan");
		LocalDate currentdate = LocalDate.now();
		int currentMonth = currentdate.getMonthValue();
		String startDate="";
		String endDate="";
		if(String.valueOf(currentMonth-1).length()>1) {
			startDate=String.valueOf(currentMonth-1);
		}else {
			startDate="0"+String.valueOf(currentMonth-1);
		}
		int nextMonth=currentMonth;
		if(String.valueOf(nextMonth).length()>1) {
			endDate=String.valueOf(nextMonth);
		}else {
			endDate="0"+String.valueOf(nextMonth);
		}
		String janStart = "2024-"+startDate+"-01T00:00:00Z";
		String janEnd =  "2024-"+endDate+"-01T00:00:00Z";
		if(nextMonth>12) {
			janEnd =  "2025-01-01T00:00:00Z";
		}
		final DecimalFormat decfor = new DecimalFormat("0.00"); 
		List<RunnerStravaEntity> runnerStravaEntityListVal= new ArrayList<RunnerStravaEntity>();
		try {
			Instant date1 = Instant.parse(janStart);
			Instant date2 = Instant.parse(janEnd);
			runnerStravaEntityListVal=runnerStravaEntityList.stream().filter(val-> Instant.parse(val.getStartDate()).isAfter(date1) && Instant.parse(val.getStartDate()).isBefore(date2)).collect(Collectors.toList());

		} catch (DateTimeParseException e) {
			e.printStackTrace();
		}
		double totalKilometer=0.00;
		int count=0;
		for(int itr=0;itr<runnerStravaEntityListVal.size();itr++) {
			totalKilometer+=runnerStravaEntityListVal.get(itr).getDecimalKilometer();
			count++;
		}
		totalKilometer=totalKilometer/1000;
		totalKilometer=Double.parseDouble(decfor.format(totalKilometer));
		Map<String,Object> monthlyMap= yearDataEntitity.getAttributes();
		monthlyMap.put(monthList.get(Integer.parseInt(String.valueOf(currentMonth-1))), totalKilometer);
		yearDataEntitity.setAttributes(monthlyMap);
		Map<String,Object> activeMap=yearDataEntitity.getActiveDaysattributes();
		activeMap.put(monthList.get(Integer.parseInt(String.valueOf(currentMonth-1))), count);
		yearDataEntitity.setActiveDaysattributes(activeMap);
		System.out.println(monthList.get(Integer.parseInt(String.valueOf(currentMonth-1)))+monthlyMap.get(monthList.get(Integer.parseInt(String.valueOf(currentMonth-1)))));
		return yearDataEntitity;
	}

	private YearDataEntitity findingMonthlySplitWhole(List<RunnerStravaEntity> runnerStravaEntityList, YearDataEntitity yearDataEntitity) {
		List<String> monthList= new ArrayList<String>();
		List<String> dateList= new ArrayList<String>();
		monthList.add("Jan");
		monthList.add("Feb");
		monthList.add("Mar");
		monthList.add("Apr");
		monthList.add("May");
		monthList.add("Jun");
		monthList.add("Jul");
		monthList.add("Aug");
		monthList.add("Sep");
		monthList.add("Oct");
		monthList.add("Nov");
		monthList.add("Dec");
		dateList.add("2024-01-01T00:00:00Z");
		dateList.add("2024-02-01T00:00:00Z");
		dateList.add("2024-03-01T00:00:00Z");
		dateList.add("2024-04-01T00:00:00Z");
		dateList.add("2024-05-01T00:00:00Z");
		dateList.add("2024-06-01T00:00:00Z");
		dateList.add("2024-07-01T00:00:00Z");
		dateList.add("2024-08-01T00:00:00Z");
		dateList.add("2024-09-01T00:00:00Z");
		dateList.add("2024-10-01T00:00:00Z");
		dateList.add("2024-11-01T00:00:00Z");
		dateList.add("2024-12-01T00:00:00Z");
		dateList.add("2025-01-01T00:00:00Z");
		for(int itrVal=0;itrVal<monthList.size();itrVal++) {
			String janStart = dateList.get(itrVal);
			String janEnd =  dateList.get(itrVal+1);
			final DecimalFormat decfor = new DecimalFormat("0.00"); 
			List<RunnerStravaEntity> runnerStravaEntityListVal= new ArrayList<RunnerStravaEntity>();
			try {
				Instant date1 = Instant.parse(janStart);
				Instant date2 = Instant.parse(janEnd);
				runnerStravaEntityListVal=runnerStravaEntityList.stream().filter(val-> Instant.parse(val.getStartDate()).isAfter(date1) && Instant.parse(val.getStartDate()).isBefore(date2)).collect(Collectors.toList());

			} catch (DateTimeParseException e) {
				e.printStackTrace();
			}
			double totalKilometer=0.00;
			int count=0;
			for(int itr=0;itr<runnerStravaEntityListVal.size();itr++) {
				totalKilometer+=runnerStravaEntityListVal.get(itr).getDecimalKilometer();
				count++;
			}
			totalKilometer=totalKilometer/1000;
			totalKilometer=Double.parseDouble(decfor.format(totalKilometer));
			Map<String,Object> monthlyMap= yearDataEntitity.getAttributes();
			monthlyMap.put(monthList.get(itrVal), totalKilometer);
			yearDataEntitity.setAttributes(monthlyMap);
			Map<String,Object> activeMap=yearDataEntitity.getActiveDaysattributes();
			activeMap.put(monthList.get(itrVal), count);
			yearDataEntitity.setActiveDaysattributes(activeMap);
			System.out.println(monthList.get(itrVal)+monthlyMap.get(monthList.get(itrVal)));
		}
		return yearDataEntitity;
	}

	public List<RunnerStravaEntity> getActivities(String accesstoken) {
		List<RunnerStravaEntity> runnerStravaEntityList= new ArrayList<RunnerStravaEntity>();
		try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
			int count=0;
			int page = 1;
			int perPage = 200;
			boolean hasMoreData = true;
			while (hasMoreData) {
				URIBuilder uriBuilder = new URIBuilder(ACTIVITIES_API_URL);
				uriBuilder.setParameter("per_page", String.valueOf(perPage));  // Example parameter
				uriBuilder.setParameter("page", String.valueOf(page));
				HttpGet request = new HttpGet(uriBuilder.build());
				request.addHeader("Authorization", "Bearer " + accesstoken);
				try (CloseableHttpResponse response = httpClient.execute(request)) {
					String json = EntityUtils.toString(response.getEntity());
					JsonElement jsonElement = JsonParser.parseString(json);
					System.out.println(jsonElement);
					for(int itr=0;itr<jsonElement.getAsJsonArray().size();itr++) {
						RunnerStravaEntity runnerStravaEntity= new RunnerStravaEntity();
						System.out.println((++count)+" - "+jsonElement.getAsJsonArray().get(itr));
						if(String.valueOf(jsonElement.getAsJsonArray().get(itr).getAsJsonObject().get("type").getAsString()).equals("Run")) {
							runnerStravaEntity.setDecimalKilometer(jsonElement.getAsJsonArray().get(itr).getAsJsonObject().get("distance").getAsFloat());
							runnerStravaEntity.setAthleteId(jsonElement.getAsJsonArray().get(itr).getAsJsonObject().get("athlete").getAsJsonObject().get("id").getAsLong());
							runnerStravaEntity.setStartDate(jsonElement.getAsJsonArray().get(itr).getAsJsonObject().get("start_date").getAsString());
							runnerStravaEntityList.add(runnerStravaEntity);
						}
					}
					hasMoreData = json.contains("[") && json.length() > 2;
				}
				page++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return runnerStravaEntityList;
	}

	public YearDataEntitity generateAccessTokenUsingRefresh(YearDataEntitity yearDataEntitity) {
		try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
			HttpPost postRequest = new HttpPost(REFRESH_API_URL);
			postRequest.setHeader("Content-Type", "application/json");

			JsonObject json = new JsonObject();
			json.addProperty("client_id", CLIENTID);
			json.addProperty("client_secret", CLIENTSECRECT);
			json.addProperty("grant_type", "refresh_token");
			json.addProperty("refresh_token", yearDataEntitity.getRefreshToken());

			StringEntity entity = new StringEntity(json.toString());
			postRequest.setEntity(entity);

			try (CloseableHttpResponse response = httpClient.execute(postRequest)) {
				String responseBody = EntityUtils.toString(response.getEntity());
				JsonObject responseJson = JsonParser.parseString(responseBody).getAsJsonObject();

				String newAccessToken = responseJson.get("access_token").getAsString();
				String newRefreshToken = responseJson.get("refresh_token").getAsString();
				yearDataEntitity.setCode(newAccessToken);
				yearDataEntitity.setRefreshToken(newRefreshToken);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return yearDataEntitity;
	}
}
