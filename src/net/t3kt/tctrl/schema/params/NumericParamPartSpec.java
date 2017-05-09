package net.t3kt.tctrl.schema.params;

import com.google.common.collect.ImmutableList;
import com.google.protobuf.Value;
import net.t3kt.tctrl.schema.TctrlSchemaProto.ParamPartSpec;

import java.util.List;
import java.util.Optional;

public abstract class NumericParamPartSpec<T extends Number> {

    static NumericParamPartSpec<Integer> forInteger(ParamPartSpec spec) {
        return new NumericParamPartSpec<Integer>(spec) {
            @Override
            protected Optional<Integer> convert(Value value) {
                return Values.toInt(value);
            }
        };
    }

    static NumericParamPartSpec<Float> forFloat(ParamPartSpec spec) {
        return new NumericParamPartSpec<Float>(spec) {
            @Override
            protected Optional<Float> convert(Value value) {
                return Values.toFloat(value);
            }
        };
    }

    static ImmutableList<NumericParamPartSpec<Integer>> forIntegers(List<ParamPartSpec> parts) {
        if (parts.isEmpty()) {
            return ImmutableList.of();
        }
        return parts.stream()
                .map(NumericParamPartSpec::forInteger)
                .collect(ImmutableList.toImmutableList());
    }

    static ImmutableList<NumericParamPartSpec<Float>> forFloats(List<ParamPartSpec> parts) {
        if (parts.isEmpty()) {
            return ImmutableList.of();
        }
        return parts.stream()
                .map(NumericParamPartSpec::forFloat)
                .collect(ImmutableList.toImmutableList());
    }

    private final ParamPartSpec spec;

    private NumericParamPartSpec(ParamPartSpec spec) {
        this.spec = spec;
    }

    ParamPartSpec getSpec() {
        return spec;
    }

    public String getKey() {
        return spec.getKey();
    }

    public String getLabel() {
        return spec.getLabel();
    }

    public Optional<T> getValue() {
        return convert(spec.getValue());
    }

    public Optional<T> getDefaultValue() {
        return convert(spec.getDefaultVal());
    }

    public Optional<T> getMinLimit() {
        return convert(spec.getMinLimit());
    }

    public Optional<T> getMaxLimit() {
        return convert(spec.getMaxLimit());
    }

    public Optional<T> getMinNorm() {
        return convert(spec.getMinNorm());
    }

    public Optional<T> getMaxNorm() {
        return convert(spec.getMaxNorm());
    }

    protected abstract Optional<T> convert(Value value);
}
