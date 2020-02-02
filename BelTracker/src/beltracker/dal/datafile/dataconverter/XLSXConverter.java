/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.dal.datafile.dataconverter;

import beltracker.dal.datafile.dataconverter.csvconverter.CSVConverter;
import beltracker.dal.datatransfer.DataTransfer;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.Random;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 *
 * @author Acer
 */
public class XLSXConverter {
    
    CSVConverter csvConverter = new CSVConverter();
    
    public DataTransfer convertFileData(String filePath) throws IOException, ParseException {
        String newPath = csvConverter(filePath);
        DataTransfer dataTransfer = csvConverter.convertFileData(newPath);
        return dataTransfer;
    }
    
    public String csvConverter(String startingFile) throws IOException {
        InputStream inputstream = null;
        String str = new String();
        try 
        {
            inputstream = new FileInputStream(startingFile);
            Workbook wb = WorkbookFactory.create(inputstream);
            Boolean isCounted = false;
            int rowCount = 0;
            Sheet sheet = wb.getSheetAt(0);
            Row row = null;
            for (int i = 0; i < sheet.getLastRowNum() + 1; i++) 
            {
                
                row = sheet.getRow(i);
                String rowString = new String();
                if (!isCounted) 
                {
                    isCounted = true;
                    rowCount = row.getLastCellNum();
                }
                for (int j = 0; j < rowCount; j++) 
                {
                    if (row.getCell(j) == null) 
                    {
                        rowString = rowString + "" + ",";
                    } 
                    else 
                    {
                        rowString = rowString + row.getCell(j) + ",";
                    }
                }
                str = str + rowString.substring(0, rowString.length() - 1) + "\r\n";
            }
        } 
        finally 
        {
            inputstream.close();
        }
        
        return writeCSVFile(str);
    }
    
    private String writeCSVFile(String str) throws IOException {
        
        OutputStream output = null;
        String newFilePath = "data/NewData/" + String.valueOf(randInt(1, 100000)) + ".csv";
        try 
        {
            output = new FileOutputStream(newFilePath);
            output.write(str.getBytes("UTF-8"));
        } 
        finally 
        {
            if (output != null) 
            {
                output.close();
            }
        }
        return newFilePath;
    }
    
    private int randInt(int min, int max) {
        
        Random rand = new Random();
        
        int randomNum = rand.nextInt((max - min) + 1) + min;
        
        return randomNum;
        
    }
}
