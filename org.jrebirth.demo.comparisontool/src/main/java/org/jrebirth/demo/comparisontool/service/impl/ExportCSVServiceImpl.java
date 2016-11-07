package org.jrebirth.demo.comparisontool.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.jrebirth.af.api.module.Register;
import org.jrebirth.af.api.wave.Wave;
import org.jrebirth.af.core.service.DefaultService;
import org.jrebirth.demo.comparisontool.bean.FileComparison;
import org.jrebirth.demo.comparisontool.service.ExportCSVService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Register(ExportCSVService.class)
public final class ExportCSVServiceImpl extends DefaultService implements ExportCSVService {

    /** The class logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ExportCSVServiceImpl.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public void initService() {

        // Define the service method
        listen(DO_EXPORT_CSV);
    }

	@Override
	public boolean doExportCsv(File exportedFile, List<FileComparison> content, Wave wave) throws InterruptedException {
		
		updateTitle(wave, "Export to CSV file");
		
		updateMessage(wave, "Export comparison results");

		
		FileWriter fileWriter = null;
		
		CSVPrinter csvFilePrinter = null;
		
		//Create the CSVFormat object with "\n" as a record delimiter
        CSVFormat csvFileFormat = CSVFormat.EXCEL.withDelimiter(';');
				
		try {
			
			fileWriter = new FileWriter(exportedFile);
			
	        csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);
	        
	        Object [] csvHeaders = {
	        		"Source Name",
            		"Source Version",
            		"Source Qualifier",
            		"Source Date",
            		"Status",
            		"Target Name",
            		"Ttarget Version",
            		"Target Qualifier",
            		"Target Date"
            		};
	        
	        csvFilePrinter.printRecord(csvHeaders);
			
	        updateProgress(wave, 0, content.size());
	        
	        int j = 0;
			//Write a new student object list to the CSV file
			for (FileComparison fc : content) {
				
	            csvFilePrinter.printRecord(
	            		fc.sourceName(),
	            		fc.sourceVersion(),
	            		fc.sourceQualifier(),
	            		fc.sourceDate(),
	            		fc.status(),
	            		fc.targetName(),
	            		fc.targetVersion(),
	            		fc.targetQualifier(),
	            		fc.targetDate()
	            		);
	            
	            j++;
	            updateMessage(wave, String.format("Exporting items %s", Math.round(j * 100 / content.size())) + "%");
	            updateProgress(wave, j, content.size());
	            Thread.sleep(1);
			}

			System.out.println("CSV file was created successfully !!!");
			
		} catch (Exception e) {
			System.out.println("Error in CsvFileWriter !!!");
			e.printStackTrace();
			return false;
		} finally {
			try {
				fileWriter.flush();
				fileWriter.close();
				csvFilePrinter.close();
			} catch (IOException e) {
				System.out.println("Error while flushing/closing fileWriter/csvPrinter !!!");
                e.printStackTrace();
                return false;
			}
		}
		
		
		return true;
	}

}
