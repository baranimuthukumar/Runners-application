package com.runnerapplication.user.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.CMYKColor;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.runnerapplication.user.entity.RunnerProfileEntity;

public class PDFGenerator {

	// List to hold all AdditionalDetails
	private List<RunnerProfileEntity> runnerProfileEntity;

	public List<RunnerProfileEntity> getAdditionDetailsEntityList() {
		return runnerProfileEntity;
	}

	public void setAdditionDetailsEntityList(List<RunnerProfileEntity> runnerProfileEntitySend) {
		runnerProfileEntity = runnerProfileEntitySend;
	}

	public void generate(HttpServletResponse response, String[] columnValList) throws DocumentException, IOException {

		// Creating the Object of Document
		Document document = new Document(PageSize.A4);

		// Getting instance of PdfWriter
		PdfWriter.getInstance(document, response.getOutputStream());

		// Opening the created document to modify it
		document.open();

		// Creating font
		// Setting font style and size
		Font fontTiltle = FontFactory.getFont(FontFactory.TIMES_ROMAN);
		fontTiltle.setSize(20);

		// Creating paragraph
		Paragraph paragraph = new Paragraph("Tiruppur Runners Details: ", fontTiltle);

		// Aligning the paragraph in document
		paragraph.setAlignment(Paragraph.ALIGN_CENTER);

		// Adding the created paragraph in document
		document.add(paragraph);
		List columnsValList = Arrays.asList(columnValList);
		String[] columnList= new String[columnValList.length];
		boolean bothAvl= false;
		if(columnsValList.contains("activeMember")&& columnsValList.contains("nonActivemember")) {
			columnList= new String[columnValList.length-1];
			bothAvl=true;
			int itr=0;
			int itr2=0;
			while(itr<columnValList.length && itr2<columnList.length) {
				if(!columnValList[itr].equals("nonActivemember")) {
					columnList[itr2]=columnValList[itr];
					itr2++;
				}
				itr++;
			}
		}else {
			int itr=0;
			int itr2=0;
			while(itr<columnValList.length && itr2<columnList.length) {
				columnList[itr2]=columnValList[itr];
				itr2++;
				itr++;
			}
		}

		// Creating a table of 4 columns
		PdfPTable table = new PdfPTable(columnList.length);

		// Setting width of table, its columns and spacing
		table.setWidthPercentage(100f);
		int[] width= new int[columnList.length];
		for(int itr=0;itr<width.length;itr++) {
			width[itr]=3;
		}
		table.setWidths(width);
		table.setSpacingBefore(5);

		// Create Table Cells for table header
		PdfPCell cell = new PdfPCell();

		// Setting the background color and padding
		cell.setBackgroundColor(CMYKColor.LIGHT_GRAY);
		cell.setPadding(5);

		// Creating font
		// Setting font style and size
		Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
		font.setColor(CMYKColor.WHITE);

		// Adding headings in the created table cell/ header
		// Adding Cell to table
		List columnsList = Arrays.asList(columnList);
		
		for(int itr=0;itr<columnList.length;itr++) {
			if(columnsList.contains(columnList[itr])&& columnList[itr].equals("serialNo")) {
				cell.setPhrase(new Phrase("S.No", font));
			}else if(columnsList.contains(columnList[itr])&& columnList[itr].equals("profileName")) {
				cell.setPhrase(new Phrase("Profile Name", font));
			}else if(columnsList.contains(columnList[itr])&& columnList[itr].equals("userName")) {
				cell.setPhrase(new Phrase("User Name", font));
			}else if(columnsList.contains(columnList[itr])&& columnList[itr].equals("age")) {
				cell.setPhrase(new Phrase("Age", font));
			}else if(columnsList.contains(columnList[itr])&& columnList[itr].equals("dob")) {
				cell.setPhrase(new Phrase("DOB", font));
			}else if(columnsList.contains(columnList[itr])&& columnList[itr].equals("contactNumber")) {
				cell.setPhrase(new Phrase("Contact", font));
			}else if(columnsList.contains(columnList[itr])&& columnList[itr].equals("activeMember")||columnsList.contains(columnList[itr])&& columnList[itr].equals("nonActivemember")) {
				cell.setPhrase(new Phrase("Active Member", font));
			}else if(columnsList.contains(columnList[itr])&& columnList[itr].equals("ageCategory")) {
				cell.setPhrase(new Phrase("Age Category", font));
			}else if(columnsList.contains(columnList[itr])&& columnList[itr].equals("personalBest")) {
				cell.setPhrase(new Phrase("Personal Best", font));
			}else if(columnsList.contains(columnList[itr])&& columnList[itr].equals("tshirtSize")) {
				cell.setPhrase(new Phrase("TshirtSize", font));
			}else if(columnsList.contains(columnList[itr])&& columnList[itr].equals("tshirtIssued")) {
				cell.setPhrase(new Phrase("TshirtIssued", font));
			}
			table.addCell(cell);
		}
		List<RunnerProfileEntity> filteredList = new ArrayList<RunnerProfileEntity>();
		filteredList=runnerProfileEntity;
		if(columnsList.contains("activeMember")) {
			filteredList=runnerProfileEntity.stream().filter(val->val.activeMember).collect(Collectors.toList());
		}else if(columnsList.contains("nonActivemember")) {
			filteredList=runnerProfileEntity.stream().filter(val->!val.activeMember).collect(Collectors.toList());
		}
		// Iterating over the list of students
		for (RunnerProfileEntity runnerProfileEntityList : filteredList) {
			if(columnsList.contains("serialNo")) {
				table.addCell(String.valueOf(runnerProfileEntityList.getSerialNo()));
			}
			if(columnsList.contains("userName")) {
				table.addCell(String.valueOf(runnerProfileEntityList.getUserName()));
			}
			if(columnsList.contains("age")) {
				table.addCell(String.valueOf(runnerProfileEntityList.getAge()));
			}
			if(columnsList.contains("dob")) {
				table.addCell(String.valueOf(runnerProfileEntityList.getDob()));
			}
			if(columnsList.contains("contactNumber")) {
				table.addCell(String.valueOf(runnerProfileEntityList.getContactNumber()));
			}
			if(columnsList.contains("activeMember")||columnsList.contains("nonActivemember")) {
				table.addCell(String.valueOf(runnerProfileEntityList.isActiveMember()));
			}
			if(columnsList.contains("ageCategory")) {
				table.addCell(String.valueOf(runnerProfileEntityList.getAgeCategory()));
			}
			if(columnsList.contains("personalBest")) {
				table.addCell(String.valueOf(runnerProfileEntityList.getPersonalBest()));
			}
			if(columnsList.contains("tshirtSize")) {
				table.addCell(String.valueOf(runnerProfileEntityList.getTshirtSize()));
			}
			if(columnsList.contains("tshirtIssued")) {
				table.addCell(String.valueOf(runnerProfileEntityList.getTshirtIssued()));
			}
		}
		
		// Adding the created table to document
		document.add(table);

		// Closing the document
		document.close();

	}
}
