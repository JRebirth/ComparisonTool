package org.jrebirth.demo.comparisontool.resources;

import org.jrebirth.af.api.resource.image.ImageExtension;
import org.jrebirth.af.core.resource.image.ImageEnum;

/**
 * The Images enumeration providing all images.
 */
public enum Images implements ImageEnum {

    // @formatter:off
    
    /** The Open icon used to open folder chooser. */
    Open {{ rel("Open", ImageExtension.PNG); }},
    
    /** The Start icon used to trigger the comparison. */
    Start {{ rel("Start", ImageExtension.PNG); }},
    
    /** The Export CSV icon used to export data to a CSV file. */
    ExportCSV {{ rel("ExportCSV", ImageExtension.PNG); }},

}
