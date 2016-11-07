package org.jrebirth.demo.comparisontool.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jrebirth.af.api.module.Register;
import org.jrebirth.af.api.wave.Wave;
import org.jrebirth.af.core.exception.ServiceException;
import org.jrebirth.af.core.service.DefaultService;
import org.jrebirth.demo.comparisontool.bean.FileComparison;
import org.jrebirth.demo.comparisontool.service.ComparatorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Register(ComparatorService.class)
public final class ComparatorServiceImpl extends DefaultService implements ComparatorService {

    /** The class logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ComparatorServiceImpl.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public void initService() {

        // Define the service method
        listen(DO_COMPARE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FileComparison> doCompare(final File sourceFolder, final File targetFolder, final Wave wave) throws InterruptedException {

        LOGGER.trace("Start Comparison.");

        updateTitle(wave, "Compare Paths");

        checkFolder("Source", sourceFolder);
        checkFolder("Target", targetFolder);

        final Set<String> sourceFiles = new HashSet<>();
        final Set<String> targetFiles = new HashSet<>();

        final List<FileComparison> originalFiles = new ArrayList<>();

        updateMessage(wave, "Scan Source Folder");

        for (final File f : sourceFolder.listFiles()) {

            final FileComparison fc = new FileComparison();
            fc.setSource(f);
            originalFiles.add(fc);
            // int end = f.getName().indexOf("v9");
            // files.add(f.getName().substring(0, end > -1 ? end : f.getName().length()-1));

        }

        Collections.sort(originalFiles);
        Thread.sleep(200);

        updateMessage(wave, "Scan Target Folder");

        final List<File> targetFiles2 = new ArrayList<>();

        for (final File f : targetFolder.listFiles()) {
            targetFiles2.add(f);
        }
        Collections.sort(targetFiles2);

        Thread.sleep(200);

        // if(sourceFolder.exists() && sourceFolder.isDirectory()){
        // populate(sourceFiles, sourceFolder);
        // }
        //
        // if(targetFolder.exists() && targetFolder.isDirectory()){
        // populate(targetFiles, targetFolder);
        // }

        updateProgress(wave, 0, originalFiles.size());

        final List<FileComparison> parsedFiles = new ArrayList<>();
        parsedFiles.addAll(originalFiles);
        Collections.reverse(parsedFiles);
        int j = 0;
        for (int i = parsedFiles.size() - 1; i >= 0; i--) {

            final FileComparison fc = parsedFiles.get(i);

            for (final File f : targetFiles2) {

                final int end = f.getName().indexOf("_");

                final String name = f.getName().substring(0, end > -1 ? end : f.getName().length() - 1);

                if (fc.getSource().getName().startsWith(name)) {
                    fc.setTarget(f);
                    targetFiles2.remove(f);
                    break;
                }

            }
            j++;
            updateMessage(wave, String.format("Comparing items %s", Math.round(j * 100 / parsedFiles.size())) + "%");
            updateProgress(wave, j, parsedFiles.size());
            Thread.sleep(1);
        }

        // remaining target files
        for (final File f : targetFiles2) {
            final FileComparison fc = new FileComparison();
            fc.setTarget(f);
            originalFiles.add(fc);
        }

        return originalFiles;
    }

    /**
     * @param folder
     */
    protected void checkFolder(final String folderName, final File folder) {
        if (folder == null) {
            throw new ServiceException(folderName + " folder is not defined");
        }
        if (!folder.exists()) {
            throw new ServiceException(folderName + " folder doesn't exist : " + folder.getAbsolutePath());
        }
        if (!folder.isDirectory()) {
            throw new ServiceException(folderName + " fodler is not a directory : " + folder.getAbsolutePath());
        }

    }

    private void populate(final Set<String> files, final File folder) {

        for (final File f : folder.listFiles()) {

            final int end = f.getName().indexOf("v9");

            files.add(f.getName().substring(0, end > -1 ? end : f.getName().length() - 1));

        }

    }

}
