package org.jrebirth.demo.comparisontool.ui;

import java.io.File;

public class FileStringConverter extends javafx.util.StringConverter<File> {

    @Override
    public String toString(final File object) {
        return object != null ? object.getAbsolutePath() : "";
    }

    @Override
    public File fromString(final String string) {
        return new File(string);
    }

}
