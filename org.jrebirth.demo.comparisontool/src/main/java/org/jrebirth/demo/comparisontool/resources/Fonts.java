package org.jrebirth.demo.comparisontool.resources;

import static org.jrebirth.af.core.resource.Resources.create;

import org.jrebirth.af.api.resource.font.FontItem;
import org.jrebirth.af.core.resource.font.RealFont;

public interface Fonts {

    /** The splash font. */
    FontItem MAIN = create(new RealFont(FontNames.DoulosSIL, 16));

}
