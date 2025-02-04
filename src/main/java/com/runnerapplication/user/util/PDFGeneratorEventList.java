package com.runnerapplication.user.util;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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
import com.runnerapplication.user.entity.EventReport;

public class PDFGeneratorEventList {

	// List to hold all AdditionalDetails
	private List<EventReport> eventReportEntity;

	public List<EventReport> getAdditionDetailsEntityList() {
		return eventReportEntity;
	}

	public void setAdditionDetailsEntityList(List<EventReport> eventReportEntitySend) {
		eventReportEntity = eventReportEntitySend;
	}

	public void generate(HttpServletResponse response) throws DocumentException, IOException {

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

		String[] columnList={"Profile Name","Event Name","Year","Room","Travel","Distance","Best Time","Payment Reference"};

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
		for(int itr=0;itr<columnList.length;itr++) {
			cell.setPhrase(new Phrase(columnList[itr], font));
			table.addCell(cell);
		}
		// Iterating over the list of students
		for (EventReport eventReportEntityList : eventReportEntity) {
			table.addCell(String.valueOf(eventReportEntityList.getProfileName()));
			table.addCell(String.valueOf(eventReportEntityList.getEventName()));
			table.addCell(String.valueOf(eventReportEntityList.getYear()));
			table.addCell(String.valueOf(eventReportEntityList.getRoom()));
			table.addCell(String.valueOf(eventReportEntityList.getTravel()));
			table.addCell(String.valueOf(eventReportEntityList.getDistance()));
			table.addCell(String.valueOf(eventReportEntityList.getBestTime()));
			table.addCell(String.valueOf(eventReportEntityList.getPaymentReference()));
		}


		// Adding the created table to document
		document.add(table);

		// Closing the document
		document.close();

	}
}
