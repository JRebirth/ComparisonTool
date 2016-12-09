package org.jrebirth.demo.comparisontool.bean;

import java.io.File;
import java.util.List;

import org.jrebirth.af.api.annotation.bean.Bean;

@Bean("PathToCompare")
public abstract class AbstractPathToCompare {

    protected boolean missing;
    protected boolean newer;
    protected boolean upgraded;
    protected boolean downgraded;
    protected boolean same;
    protected boolean updated;

    protected File sourcePath;
    protected File targetPath;

    protected List<FileComparison> lastResult;

    protected List<FileComparison> filteredContent;

    protected int sameCount;
    protected int updatedCount;
    protected int missingCount;
    protected int newerCount;
    protected int upgradedCount;
    protected int downgradedCount;

}
