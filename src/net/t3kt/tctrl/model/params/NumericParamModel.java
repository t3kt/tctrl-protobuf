package net.t3kt.tctrl.model.params;

import com.google.common.collect.Range;

public interface NumericParamModel<T extends Number & Comparable> extends ScalarParamModel<T> {

    float getNormalizedValue();
    void setNormalizedValue(float normValue);

    Range<T> getLimitRange();
    Range<T> getNormalizedRange();
}
