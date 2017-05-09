package net.t3kt.tctrl.schema.params;

import com.google.common.collect.ImmutableList;
import net.t3kt.tctrl.schema.TctrlSchemaProto.ParamSpec;
import net.t3kt.tctrl.schema.TctrlSchemaProto.ParamType;

public final class VectorParamSpec<T extends Number> extends ParamSpecBase {
    public static VectorParamSpec<Integer> forIntegers(ParamSpec spec) {
        return new VectorParamSpec<>(spec, NumericParamPartSpec.forIntegers(spec.getPartList()));
    }

    public static VectorParamSpec<Float> forFloats(ParamSpec spec) {
        return new VectorParamSpec<>(spec, NumericParamPartSpec.forFloats(spec.getPartList()));
    }

    private ImmutableList<NumericParamPartSpec<T>> parts;
    private final ParamType partType;

    private VectorParamSpec(ParamSpec spec, ImmutableList<NumericParamPartSpec<T>> parts) {
        super(spec);
        this.parts = parts;
        this.partType = ParamTypes.getPartType(spec.getType());
    }

    public ImmutableList<NumericParamPartSpec<T>> getParts() {
        return parts;
    }

    public ParamType getPartType() {
        return partType;
    }
}
