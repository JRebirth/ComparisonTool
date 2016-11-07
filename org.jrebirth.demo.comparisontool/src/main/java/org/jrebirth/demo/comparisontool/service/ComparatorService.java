package org.jrebirth.demo.comparisontool.service;

import static org.jrebirth.af.core.wave.WBuilder.waveType;

import java.io.File;
import java.util.List;

import org.jrebirth.af.api.module.RegistrationPoint;
import org.jrebirth.af.api.service.Service;
import org.jrebirth.af.api.wave.Wave;
import org.jrebirth.af.api.wave.contract.WaveItem;
import org.jrebirth.af.api.wave.contract.WaveType;
import org.jrebirth.af.core.wave.WaveItemBase;
import org.jrebirth.demo.comparisontool.bean.FileComparison;

/**
 * The interface <strong>ComparatorService</strong>.
 * 
 * @author Sébastien Bordes
 */
@RegistrationPoint
public interface ComparatorService extends Service {

    String COMPARISON_DONE = "COMPARISON_DONE";

    WaveItem<File> SOURCE = new WaveItemBase<File>() {
    };

    WaveItem<File> TARGET = new WaveItemBase<File>() {
    };

    WaveItem<List<FileComparison>> RESULT = new WaveItemBase<List<FileComparison>>() {
    };

    /** Perform something. */
    WaveType DO_COMPARE = waveType("COMPARE")
                                             .items(SOURCE, TARGET)
                                             .returnItem(RESULT)
                                             .returnAction(COMPARISON_DONE);

    /**
     * Do Comparison.
     *
     * @param wave the source wave
     * 
     * @throws InterruptedException
     */
    List<FileComparison> doCompare(final File sourceFolder, final File targetFolder, final Wave wave) throws InterruptedException;

}
