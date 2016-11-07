package org.jrebirth.demo.comparisontool.service;

import static org.jrebirth.af.core.wave.WBuilder.waveType;

import java.io.File;
import java.util.List;

import org.jrebirth.af.api.module.RegistrationPoint;
import org.jrebirth.af.api.service.Service;
import org.jrebirth.af.api.wave.Wave;
import org.jrebirth.af.api.wave.contract.WaveItem;
import org.jrebirth.af.api.wave.contract.WaveType;
import org.jrebirth.af.core.wave.JRebirthItems;
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

    WaveItem<File> EXPORTED_FILE = new WaveItemBase<File>() {
    };

    WaveItem<List<FileComparison>> CONTENT = new WaveItemBase<List<FileComparison>>() {
    };

    /** Perform something. */
    WaveType DO_EXPORT_CSV = waveType("EXPORT_CSV")
                                             .items(EXPORTED_FILE, CONTENT)
                                             .returnItem(JRebirthItems.booleanItem)
                                             .returnAction(EXPORT_CSV_DONE);

    String NEW_LINE_SEPARATOR = "\n";
    
    /**
     * Do the export of data to CSV file.
     *
     *
     * @param exportedFile the file to write in
     * @param content the data to export
     * @param wave the source wave
     * 
     * @throws InterruptedException
     */
    boolean doExportCsv(final File exportedFile, final List<FileComparison> content, final Wave wave) throws InterruptedException;

}
