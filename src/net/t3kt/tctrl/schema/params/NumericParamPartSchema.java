package net.t3kt.tctrl.schema.params;

import com.google.common.collect.ImmutableList;
import com.google.protobuf.Value;
import java.util.List;
import java.util.Optional;
import net.t3kt.tctrl.schema.SchemaNode;
import net.t3kt.tctrl.schema.TctrlSchemaProto.ParamPartSpec;

public abstract class NumericParamPartSchema<T extends Number> extends SchemaNode<ParamPartSpec> {

    static NumericParamPartSchema<Integer> forInteger(ParamPartSpec spec) {
        return new NumericParamPartSchema<Integer>(spec) {
            @Override
            protected Optional<Integer> convert(Value value) {
                return Values.toInt(value);
            }
        };
    }

    static NumericParamPartSchema<Float> forFloat(ParamPartSpec spec) {
        return new NumericParamPartSchema<Float>(spec) {
            @Override
            protected Optional<Float> convert(Value value) {
                return Values.toFloat(value);
            }
        };
    }

    static ImmutableList<NumericParamPartSchema<Integer>> forIntegers(List<ParamPartSpec> parts) {
        if (parts.isEmpty()) {
            return ImmutableList.of();
        }
        return parts.stream()
                .map(NumericParamPartSchema::forInteger)
                .collect(ImmutableList.toImmutableList());
    }

    static ImmutableList<NumericParamPartSchema<Float>> forFloats(List<ParamPartSpec> parts) {
        if (parts.isEmpty()) {
            return ImmutableList.of();
        }
        return parts.stream()
                .map(NumericParamPartSchema::forFloat)
                .collect(ImmutableList.toImmutableList());
    }

    private NumericParamPartSchema(ParamPartSpec spec) {
        super(spec);
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
