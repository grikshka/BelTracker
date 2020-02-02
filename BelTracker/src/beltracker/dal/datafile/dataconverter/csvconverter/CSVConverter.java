/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beltracker.dal.datafile.dataconverter.csvconverter;

import beltracker.dal.datatransfer.DataTransfer;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Acer
 */
public class CSVConverter {
    
    public DataTransfer convertFileData(String filePath) throws IOException, ParseException
    {
        DataTransfer dataTransfer = new DataTransfer();
        List<CSVData> beans = new CsvToBeanBuilder(new FileReader(filePath)).withType(CSVData.class).build().parse();
        DataTransfer.Order currentOrder = null;
        for (CSVData bean : beans) 
        {
            if (!bean.getInitials().trim().isEmpty() && !bean.getName().trim().isEmpty()) 
            {
                dataTransfer.addEmployee(bean.getInitials(), bean.getName(), bean.getSalary());
            }
            if (!bean.getOrderNumber().trim().isEmpty() && !bean.getCustomerName().trim().isEmpty()) 
            {
                currentOrder = dataTransfer.addOrder(bean.getOrderNumber(), bean.getCustomerName(), parseDate(bean.getDeliveryTime()));
            }
            if (!bean.getDepartmentName().trim().isEmpty()) 
            {
                dataTransfer.addDepartmentTask(currentOrder, bean.getDepartmentName(), bean.getIsFinished(), parseDate(bean.getStartDate()), parseDate(bean.getEndDate()));
            }
        }
        return dataTransfer;
    }
    
    private LocalDate parseDate(String dateToParse) throws java.text.ParseException {
        
        //Converting from excel to CSV has diffrent date structure. Thus diffrently structured dates need to be parsed.
        Date date1;
        if (dateToParse.contains("-")) {
            dateToParse = dateToParse.replace("-", "/");
            date1 = new SimpleDateFormat("dd/MMM/yyyy").parse(dateToParse);
        } else {
            date1 = new SimpleDateFormat("dd/MM/yyyy").parse(dateToParse);
        }

        LocalDate finalDate = date1.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        return finalDate;
    }
    
}
