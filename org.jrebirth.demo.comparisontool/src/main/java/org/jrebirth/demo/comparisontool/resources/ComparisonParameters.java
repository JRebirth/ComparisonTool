package org.jrebirth.demo.comparisontool.resources;

import static org.jrebirth.af.core.resource.Resources.create;

import org.jrebirth.af.api.resource.parameter.ParameterItem;
import org.jrebirth.af.core.resource.Resources;
import org.jrebirth.af.core.resource.parameter.ObjectParameter;

public interface ComparisonParameters {

    ParameterItem<String> sourcePath = Resources.create(new ObjectParameter<>("sourcePath", ""));

    ParameterItem<String> targetPath = create(new ObjectParameter<>("targetPath", ""));
    
    ParameterItem<Integer> sizeTolerance = create(new ObjectParameter<>("sizeTolerance", 100));

}
