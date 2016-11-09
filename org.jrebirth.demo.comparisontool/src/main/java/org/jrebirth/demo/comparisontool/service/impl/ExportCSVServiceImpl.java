package org.jrebirth.demo.comparisontool.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.jrebirth.af.api.module.Register;
import org.jrebirth.af.api.wave.Wave;
import org.jrebirth.af.core.exception.ServiceException;
import org.jrebirth.af.core.service.DefaultService;
import org.jrebirth.demo.comparisontool.bean.FileComparison;
import org.jrebirth.demo.comparisontool.service.ExportCSVService;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
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
        listen(DO_EXPORT_CSV);
    }

    @Override
    public File doExportCsv(File exportedFile, List<FileComparison> content, Wave wave) throws InterruptedException {

        updateTitle(wave, "Export to CSV file");

        updateMessage(wave, "Export comparison results");

        CSVPrinter csvFilePrinter = null;

        // Create the CSVFormat object with "\n" as a record delimiter
        final CSVFormat csvFileFormat = CSVFormat.EXCEL.withDelimiter(';');

        try (FileWriter fileWriter = new FileWriter(exportedFile)) {

            csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);

            final Object[] csvHeaders = {
                    "Source Name",
                    "Source Version",
                    "Source Qualifier",
                    "Source Date",
                    "Status",
                    "Reference Name",
                    "Reference Version",
                    "Reference Qualifier",
                    "Reference Date"
            };

            csvFilePrinter.printRecord(csvHeaders);

            updateProgress(wave, 0, content.size());

            int j = 0;
            // Write a new student object list to the CSV file
            for (final FileComparison fc : content) {

                csvFilePrinter.printRecord(
                                           fc.sourceName(),
                                           fc.sourceVersion(),
                                           fc.sourceQualifier(),
                                           fc.sourceDate(),
                                           fc.status(),
                                           fc.targetName(),
                                           fc.targetVersion(),
                                           fc.targetQualifier(),
                                           fc.targetDate());

                j++;
                updateMessage(wave, String.format("Exporting items %s", Math.round(j * 100 / content.size())) + "%");
                updateProgress(wave, j, content.size());
                Thread.sleep(1);
            }

            LOGGER.debug(exportedFile.getAbsolutePath() + " was created successfully.");

        } catch (final FileNotFoundException f) {

            LOGGER.warn("Impossible to access to file " + exportedFile.getAbsolutePath(), f);

            throw new ServiceException(f/* ,"Impossible to access to file " + exportedFile.getAbsolutePath() */);

        } catch (final IOException e) {
            LOGGER.error("Impossible to write CSV file", e);
        }

        return exportedFile;
    }

}
