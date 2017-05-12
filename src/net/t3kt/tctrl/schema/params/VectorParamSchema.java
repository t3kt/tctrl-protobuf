package net.t3kt.tctrl.schema.params;

import com.google.common.collect.ImmutableList;
import net.t3kt.tctrl.schema.TctrlSchemaProto.ParamSpec;
import net.t3kt.tctrl.schema.TctrlSchemaProto.ParamType;

public final class VectorParamSchema<T extends Number> extends ParamSchema {

    private ImmutableList<NumericParamPartSchema<T>> parts;
    private final ParamType partType;

    VectorParamSchema(ParamSpec spec, ImmutableList<NumericParamPartSchema<T>> parts) {
        super(spec);
        this.parts = parts;
        this.partType = ParamTypes.getPartType(spec.getType());
    }

    public ImmutableList<NumericParamPartSchema<T>> getParts() {
        return parts;
    }

    public ParamType getPartType() {
        return partType;
    }

    @Override
    public boolean isVector() {
        return true;
    }
}
