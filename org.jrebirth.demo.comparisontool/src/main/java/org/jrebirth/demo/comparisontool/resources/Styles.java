package org.jrebirth.demo.comparisontool.resources;

import static org.jrebirth.af.core.resource.Resources.create;

import org.jrebirth.af.api.resource.style.StyleSheetItem;
import org.jrebirth.af.core.resource.style.StyleSheet;

public interface Styles {

    /** The application main style sheet. */
    StyleSheetItem MAIN = create(new StyleSheet("Comparison"));

}
