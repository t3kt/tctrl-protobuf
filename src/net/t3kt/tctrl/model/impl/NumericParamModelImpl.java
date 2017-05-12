package net.t3kt.tctrl.model.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Range;
import net.t3kt.tctrl.model.params.NumericParamModel;
import net.t3kt.tctrl.schema.params.NumericParamSchema;

abstract class NumericParamModelImpl<T extends Number & Comparable> extends ScalarParamModelImpl<T, NumericParamSchema<T>> implements NumericParamModel<T> {
    private final Range<T> normalizedRange;
    private final Range<T> limitRange;

    private NumericParamModelImpl(
            NumericParamSchema<T> schema,
            ModuleModelImpl parentModule,
            Range<T> normalizedRange,
            Range<T> limitRange) {
        super(schema, parentModule);
        Preconditions.checkArgument(normalizedRange.hasLowerBound() && normalizedRange.hasUpperBound());
        this.normalizedRange = normalizedRange;
        this.limitRange = limitRange;
    }

    @Override
    public Range<T> getLimitRange() {
        return limitRange;
    }

    @Override
    public Range<T> getNormalizedRange() {
        return normalizedRange;
    }

    @Override
    public boolean isValidValue(T value) {
        return limitRange.contains(value);
    }

    static NumericParamModelImpl<Float> forFloat(
            NumericParamSchema<Float> schema,
            ModuleModelImpl parentModule) {
        Range<Float> normalizedRange = NumberRanges.create(schema.getMinNorm(), schema.getMaxNorm());
        Range<Float> limitRange = NumberRanges.create(schema.getMinLimit(), schema.getMaxLimit());
        return new NumericParamModelImpl<Float>(
                schema,
                parentModule,
                normalizedRange,
                limitRange) {

            @Override
            public float getNormalizedValue() {
                return map(getValue(), normalizedRange.lowerEndpoint(), normalizedRange.upperEndpoint(), 0.0f, 1.0f);
            }

            @Override
            public boolean trySetNormalizedValue(float normValue) {
                if (normValue < 0.0f || normValue > 1.0f) {
                    return false;
                }
                return trySetValue(map(normValue, 0.0f, 1.0f, normalizedRange.lowerEndpoint(), normalizedRange.upperEndpoint()));
            }
        };
    }

    private static float map(float x, float in_min, float in_max, float out_min, float out_max) {
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }

    static NumericParamModelImpl<Integer> forInteger(
            NumericParamSchema<Integer> schema,
            ModuleModelImpl parentModule) {
        Range<Integer> normalizedRange = NumberRanges.create(schema.getMinNorm(), schema.getMaxNorm());
        Range<Integer> limitRange = NumberRanges.create(schema.getMinLimit(), schema.getMaxLimit());
        return new NumericParamModelImpl<Integer>(
                schema,
                parentModule,
                normalizedRange,
                limitRange) {
            @Override
            public float getNormalizedValue() {
                return map((float) getValue(), normalizedRange.lowerEndpoint().floatValue(), normalizedRange.upperEndpoint().floatValue(), 0.0f, 1.0f);
            }

            @Override
            public boolean trySetNormalizedValue(float normValue) {
                if (normValue < 0.0f || normValue > 1.0f) {
                    return false;
                }
                return trySetValue(Math.round(map(normValue, 0.0f, 1.0f, normalizedRange.lowerEndpoint().floatValue(), normalizedRange.upperEndpoint().floatValue())));
            }
        };
    }
}
