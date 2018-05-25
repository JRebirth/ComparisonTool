package org.jrebirth.demo.comparisontool.bean;

import java.io.File;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jrebirth.af.api.annotation.bean.Field;
import org.jrebirth.af.api.annotation.bean.GeneratorKind;
import org.jrebirth.demo.comparisontool.resources.ComparisonParameters;

public class FileComparison implements Comparable<FileComparison> {

    private static Format sdf = new SimpleDateFormat("yyyy MM dd HH:mm:ss");

    @Field(kind = GeneratorKind.GetterSetter)
    private File source;

    @Field(kind = GeneratorKind.GetterSetter)
    private File target;

    /**
     * @return the source
     */
    public File getSource() {
        return this.source;
    }

    /**
     * @param source the source to set
     */
    public void setSource(final File source) {
        this.source = source;
    }

    /**
     * @return the target
     */
    public File getTarget() {
        return this.target;
    }

    /**
     * @param target the target to set
     */
    public void setTarget(final File target) {
        this.target = target;
    }

    public String sourceName() {
        return this.source != null ? name(this.source.getName()) : "";
    }

    public String targetName() {
        return this.target != null ? name(this.target.getName()) : "";
    }

    public String sourceVersion() {
        return this.source != null ? version(this.source.getName()) : "";
    }

    public String targetVersion() {
        return this.target != null ? version(this.target.getName()) : "";
    }

    public String sourceQualifier() {
        return this.source != null ? qualifier(this.source.getName()) : "";
    }

    public String targetQualifier() {
        return this.target != null ? qualifier(this.target.getName()) : "";
    }

    public String sourceDate() {
        return this.source != null ? sdf.format(new Date(this.source.lastModified())) : "";
    }

    public String targetDate() {
        return this.target != null ? sdf.format(new Date(this.target.lastModified())) : "";
    }
    
    public long sourceSize() {
        return this.source != null ? this.source.length() : 0;
    }

    public long targetSize() {
        return this.target != null ? this.target.length() : 0;
    }

    private String name(final String s) {
        return s.substring(0, s.lastIndexOf("_"));
    }

    private String version(String s) {
        if (s.contains(".jar")) {
            s = s.substring(s.lastIndexOf("_") + 1, s.lastIndexOf(".jar"));
        } else {
            s = s.substring(s.lastIndexOf("_") + 1);
        }
        return s.contains(".") ? s.substring(0, s.lastIndexOf(".")) : s;
    }

    private String qualifier(String s) {
        if (s.contains(".jar")) {
            s = s.substring(s.lastIndexOf("_") + 1, s.lastIndexOf(".jar"));
        } else {
            s = s.substring(s.lastIndexOf("_") + 1);
        }
        return s.contains(".") ? s.substring(s.lastIndexOf(".") + 1) : "";
    }

    public boolean isSame() {
        return this.source != null && this.target != null &&
                sourceName().equals(targetName()) &&
                sourceVersion().equals(targetVersion()) &&
                sourceQualifier().equals(targetQualifier()) &&
                sourceDate().equals(targetDate());
    }

    public boolean isUpdated() {
        return this.source != null && this.target != null &&
                sourceName().equals(targetName()) &&
                sourceVersion().equals(targetVersion()) &&
                sourceQualifier().equals(targetQualifier()) &&
                sourceDate().compareTo(targetDate()) > 0;
    }

    public boolean isMissing() {
        return this.source == null;
    }
    
    public boolean isDifferentSize() {
        return sourceSize() / ComparisonParameters.sizeTolerance.get() != targetSize() / ComparisonParameters.sizeTolerance.get();
    }

    public boolean isNewer() {
        return this.target == null;
    }

    public boolean isUpgraded() {
        return this.source != null && this.target != null &&
                sourceName().equals(targetName()) &&
                (compareVersion(sourceVersion(), targetVersion()) > 0 ||
                        sourceVersion().equals(targetVersion()) && sourceQualifier().compareTo(targetQualifier()) > 0);
    }

    private int compareVersion(final String sourceVersion, final String targetVersion) {
        int res = 0;
        final String[] s = sourceVersion.split("\\.");
        final String[] t = targetVersion.split("\\.");

        for (int i = 0; res == 0 && i < Math.max(s.length, t.length); i++) {

            if (s.length > i && t.length > i) {
                res = s[i].compareTo(t[i]);
            } else {
                return s.length > i ? -1 : 1;
            }
        }

        return res;
    }

    public boolean isDowngraded() {
        return this.source != null && this.target != null &&
                sourceName().equals(targetName()) &&
                (compareVersion(sourceVersion(), targetVersion()) < 0 ||
                        sourceVersion().equals(targetVersion()) && sourceQualifier().compareTo(targetQualifier()) < 0);
    }

    @Override
    public int compareTo(final FileComparison o) {
        return this.source.compareTo(o.getSource());
    }

    public String status() {
    	if (isSame()) {
            return "Same";
        } else if (isDowngraded()) {
            return "Downgraded";
        } else if (isMissing()) {
            return "Missing";
        } else if (isNewer()) {
            return "Newer";
        } else if (isUpdated()) {
            return "Updated";
        } else if (isUpgraded()) {
            return "Upgraded";
        } else if (isDifferentSize()) {
            return "DifferentSize";
        } 

        return "";
    }

}
