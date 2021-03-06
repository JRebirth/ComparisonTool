package org.jrebirth.demo.comparisontool.service;

import static org.jrebirth.af.core.wave.WBuilder.waveType;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import org.jrebirth.af.api.module.RegistrationPoint;
import org.jrebirth.af.api.service.Service;
import org.jrebirth.af.api.wave.Wave;
import org.jrebirth.af.api.wave.contract.WaveItem;
import org.jrebirth.af.api.wave.contract.WaveType;
import org.jrebirth.af.core.wave.WaveItemBase;
import org.jrebirth.demo.comparisontool.bean.FileComparison;

/**
 * The interface <strong>ExportCSVService</strong>.
 *
 * @author Sébastien Bordes
 */
@RegistrationPoint
public interface ExportCSVService extends Service {

    String EXPORT_CSV_DONE = "EXPORT_CSV_DONE";

    String FILE_NOT_FOUND = "FILE_NOT_FOUND";

    WaveItem<File> EXPORTED_FILE = new WaveItemBase<File>() {
    };

    WaveItem<List<FileComparison>> CONTENT = new WaveItemBase<List<FileComparison>>() {
    };

    /** Perform something. */
    @SuppressWarnings("unchecked")
    WaveType DO_EXPORT_CSV = waveType("EXPORT_CSV")
                                                   .items(EXPORTED_FILE, CONTENT)
                                                   .returnItem(EXPORTED_FILE)
                                                   .returnAction(EXPORT_CSV_DONE)
                                                   .onException(waveType(FILE_NOT_FOUND), FileNotFoundException.class);

    String NEW_LINE_SEPARATOR = "\n";

    /**
     * Do the export of data to CSV file.
     *
     *
     * @param exportedFile the file to write in
     * @param content the data to export
     * @param wave the source wave
     *
     * @return the exported file
     *
     * @throws InterruptedException
     */
    File doExportCsv(final File exportedFile, final List<FileComparison> content, final Wave wave) throws InterruptedException;

}
