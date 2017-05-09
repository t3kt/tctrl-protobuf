package net.t3kt.tctrl.schema.typedparams;

import net.t3kt.tctrl.schema.ParamTypes;
import net.t3kt.tctrl.schema.TctrlSchemaProto.ParamPartSpec;
import net.t3kt.tctrl.schema.TctrlSchemaProto.ParamType;

import java.util.Optional;

public abstract class TypedParamPartSpecBase<T> extends TypedNodeBase<T> {
//    public static <T> TypedParamPartSpecBase<T> create(Class ParamPartSpec partSpec)
//
//    public static TypedParamPartSpecBase<?> create(ParamType parentType, ParamPartSpec partSpec) {
//        ParamType childType = ParamTypes.getPartType(parentType);
//        switch (childType) {
//            case INT:
//                return new IntParamPartSpec(partSpec);
//        }
//    }

    private final ParamPartSpec partSpec;

    TypedParamPartSpecBase(ParamPartSpec partSpec) {
        this.partSpec = partSpec;
    }

    ParamPartSpec getPartSpec() {
        return partSpec;
    }

    public String getKey() {
        return partSpec.getKey();
    }

    public String getLabel() {
        return partSpec.getLabel();
    }

    public Optional<T> getValue() {
        return convert(partSpec.getValue());
    }

    public Optional<T> getDefaultValue() {
        return convert(partSpec.getDefaultVal());
    }

    public Optional<T> getMinLimit() {
        return convert(partSpec.getMinLimit());
    }

    public Optional<T> getMaxLimit() {
        return convert(partSpec.getMaxLimit());
    }

    public Optional<T> getMinNorm() {
        return convert(partSpec.getMinNorm());
    }

    public Optional<T> getMaxNorm() {
        return convert(partSpec.getMaxNorm());
    }
}
