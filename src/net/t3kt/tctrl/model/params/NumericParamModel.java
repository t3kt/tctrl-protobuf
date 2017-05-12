package net.t3kt.tctrl.model.params;

import com.google.common.collect.Range;
import net.t3kt.tctrl.schema.params.NumericParamSchema;

public interface NumericParamModel<T extends Number & Comparable> extends ScalarParamModel<T, NumericParamSchema<T>> {

    float getNormalizedValue();
    boolean trySetNormalizedValue(float normValue);

    Range<T> getLimitRange();
    Range<T> getNormalizedRange();
}
