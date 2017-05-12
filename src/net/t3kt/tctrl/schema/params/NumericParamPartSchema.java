package net.t3kt.tctrl.schema.params;

import com.google.common.collect.ImmutableList;
import com.google.protobuf.Value;
import java.util.List;
import java.util.Optional;
import net.t3kt.tctrl.schema.Groupable;
import net.t3kt.tctrl.schema.SchemaNode;
import net.t3kt.tctrl.schema.TctrlSchemaProto.ParamPartSpec;
import net.t3kt.tctrl.schema.TctrlSchemaProto.ParamSpec;
import net.t3kt.tctrl.schema.TctrlSchemaProto.ParamType;

public abstract class NumericParamPartSchema<T extends Number> extends SchemaNode<ParamPartSpec> implements Groupable {

    static NumericParamPartSchema<Integer> forInteger(ParamPartSpec spec, ParamSpec parentSpec) {
        return new NumericParamPartSchema<Integer>(spec, parentSpec) {
            @Override
            public ParamType getType() {
                return ParamType.INT;
            }

            @Override
            protected Optional<Integer> unwrapValue(Value value) {
                return Values.toInt(value);
            }

            @Override
            protected Value wrapValue(Integer value) {
                return Values.fromInteger(value);
            }

            @Override
            protected NumericParamSchema<Integer> wrapParamSchema(ParamSpec spec) {
                return ParamSchema.forInteger(spec);
            }
        };
    }

    static NumericParamPartSchema<Float> forFloat(ParamPartSpec spec, ParamSpec parentSpec) {
        return new NumericParamPartSchema<Float>(spec, parentSpec) {
            @Override
            public ParamType getType() {
                return ParamType.FLOAT;
            }

            @Override
            protected Optional<Float> unwrapValue(Value value) {
                return Values.toFloat(value);
            }

            @Override
            protected Value wrapValue(Float value) {
                return Values.fromFloat(value);
            }

            @Override
            protected NumericParamSchema<Float> wrapParamSchema(ParamSpec spec) {
                return ParamSchema.forFloat(spec);
            }
        };
    }

    static ImmutableList<NumericParamPartSchema<Integer>> forIntegers(ParamSpec parentSpec) {
        if (parentSpec.getPartList().isEmpty()) {
            return ImmutableList.of();
        }
        return parentSpec.getPartList().stream()
                .map((ParamPartSpec p) -> forInteger(p, parentSpec))
                .collect(ImmutableList.toImmutableList());
    }

    static ImmutableList<NumericParamPartSchema<Float>> forFloats(ParamSpec parentSpec) {
        if (parentSpec.getPartList().isEmpty()) {
            return ImmutableList.of();
        }
        return parentSpec.getPartList().stream()
                .map((ParamPartSpec p) -> forFloat(p, parentSpec))
                .collect(ImmutableList.toImmutableList());
    }

    private final ParamSpec parentSpec;

    private NumericParamPartSchema(ParamPartSpec spec, ParamSpec parentSpec) {
        super(spec);
        this.parentSpec = parentSpec;
    }

    @Override
    public String getKey() {
        return spec.getKey();
    }

    @Override
    public String getLabel() {
        return spec.getLabel();
    }

    @Override
    public String getPath() {
        return spec.getPath();
    }

    @Override
    public String getGroup() {
        return parentSpec.getGroup();
    }

    public abstract ParamType getType();

    public Optional<T> getValue() {
        return unwrapValue(spec.getValue());
    }

    public Optional<T> getDefaultValue() {
        return unwrapValue(spec.getDefaultVal());
    }

    public Optional<T> getMinLimit() {
        return unwrapValue(spec.getMinLimit());
    }

    public Optional<T> getMaxLimit() {
        return unwrapValue(spec.getMaxLimit());
    }

    public Optional<T> getMinNorm() {
        return unwrapValue(spec.getMinNorm());
    }

    public Optional<T> getMaxNorm() {
        return unwrapValue(spec.getMaxNorm());
    }

    protected abstract Optional<T> unwrapValue(Value value);

    protected abstract Value wrapValue(T value);

    public NumericParamSchema<T> toParamSchema() {
        ParamSpec.Builder paramSpec = ParamSpec.newBuilder()
                .setKey(getKey())
                .setType(getType());
        if (getLabel() != null) {
            paramSpec.setLabel(getLabel());
        }
        if (getPath() != null) {
            paramSpec.setPath(getPath());
        }
        if (getGroup() != null) {
            paramSpec.setGroup(getGroup());
        }
        getValue().map(this::wrapValue).ifPresent(paramSpec::setValue);
        getDefaultValue().map(this::wrapValue).ifPresent(paramSpec::setDefaultVal);
        getMinNorm().map(this::wrapValue).ifPresent(paramSpec::setMinNorm);
        getMaxNorm().map(this::wrapValue).ifPresent(paramSpec::setMaxNorm);
        getMinLimit().map(this::wrapValue).ifPresent(paramSpec::setMaxLimit);
        getMaxLimit().map(this::wrapValue).ifPresent(paramSpec::setMaxLimit);
        return wrapParamSchema(paramSpec.build());
    }

    protected abstract NumericParamSchema<T> wrapParamSchema(ParamSpec spec);
}
