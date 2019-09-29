package com.share.project.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.opencsv.CSVWriter;
import com.share.project.model.ViewClass;
import com.share.project.repo.Details;

@Service
public class DetailsService {

	@Autowired
	Details details;
	
	private static final String[] header = { "Employee Address","Salary","Experience" ,"Blood Group","Employee Id","Name","Department","Mobile"}; 
	
	public List<ViewClass> getDetails(String type) throws IOException {
    	List<ViewClass> list = details.findAllName();
    	if(type.equalsIgnoreCase("csv")) {
    		writeDataIntoCSV(list);
    	}
    	else if(type.equalsIgnoreCase("xlsx")) {
    		writeDataIntoXlsx(list);
    	}
    	else if(type.equalsIgnoreCase("txt")){
    		writeDataIntoTxt(list);
    	}
    	return list;
    }

	private void writeDataIntoTxt(List<ViewClass> list) {
		
		String filePath = "/home/kuliza-264/Desktop/output.txt";
		try {
			FileWriter fw = new FileWriter(filePath);
			for (String head : header) {
				fw.write(head + "            ");
			}
			fw.write("\n");
			for(int i=0;i<list.size();++i) {
				String[] next = { list.get(i).getEmpaddress(), list.get(i).getSalary().toString(),
						list.get(i).getExperience().toString(), list.get(i).getBloodgroup(),
						list.get(i).getEmpid().toString(), list.get(i).getName(), list.get(i).getDepartment(),
						list.get(i).getMobile().toString() };
				for (String val : next) {
					fw.write(val + "            ");
				}
				fw.write("\n");
			}
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void writeDataIntoCSV(List<ViewClass> list) { 
		
		System.out.println(list);
		String filePath="/home/kuliza-264/Desktop/output.csv";
	    File file = new File(filePath); 
	    try { 
	    
	        FileWriter outputfile = new FileWriter(file); 
	        CSVWriter writer = new CSVWriter(outputfile); 
	        writer.writeNext(header);
	        for(int i=0;i<list.size();++i){
	        	String [] next= {list.get(i).getEmpaddress(),list.get(i).getSalary().toString(),list.get(i).getExperience().toString(),list.get(i).getBloodgroup(),
	        			list.get(i).getEmpid().toString(),list.get(i).getName(),list.get(i).getDepartment(),list.get(i).getMobile().toString()
	        	};
	        	System.out.println(next);
	        	writer.writeNext(next); 
	         }
	        writer.close(); 
	    } 
	    catch (IOException e) { 
	        e.printStackTrace(); 
	    } 
	}
	
	private void writeDataIntoXlsx(List<ViewClass> list) throws IOException {
		
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("Employee");

		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 14);
		headerFont.setColor(IndexedColors.RED.getIndex());

		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);

		Row headerRow = sheet.createRow(0);

		for (int i = 0; i < header.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(header[i]);
			cell.setCellStyle(headerCellStyle);
		}

		int rowNum = 1;
		for(int i=0;i<list.size();++i){
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(list.get(i).getEmpaddress());
			row.createCell(1).setCellValue(list.get(i).getSalary().toString());
			row.createCell(2).setCellValue(list.get(i).getExperience().toString());
			row.createCell(3).setCellValue(list.get(i).getBloodgroup());
			row.createCell(4).setCellValue(list.get(i).getEmpid().toString());
			row.createCell(5).setCellValue(list.get(i).getName());
			row.createCell(6).setCellValue(list.get(i).getDepartment());
			row.createCell(7).setCellValue(list.get(i).getMobile().toString());
		}
		for (int i = 0; i < header.length; i++) {
			sheet.autoSizeColumn(i);
		}
		String filePath="/home/kuliza-264/Desktop/output.xlsx";
		FileOutputStream fileOut = new FileOutputStream(filePath);
		workbook.write(fileOut);
		fileOut.close();
		workbook.close();
	}

}
