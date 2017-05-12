package net.t3kt.tctrl.model.impl;

import static com.google.common.collect.ImmutableList.toImmutableList;

import com.google.common.collect.ImmutableList;
import net.t3kt.tctrl.model.params.NumericParamModel;
import net.t3kt.tctrl.model.params.VectorParamModel;
import net.t3kt.tctrl.schema.TctrlSchemaProto.ParamType;
import net.t3kt.tctrl.schema.params.VectorParamSchema;

final class VectorParamModelImpl<T extends Number & Comparable> extends ParamModelImpl<VectorParamSchema<T>> implements VectorParamModel<T> {

    private final ImmutableList<NumericParamModelImpl<T>> parts;

    private VectorParamModelImpl(
            VectorParamSchema<T> schema,
            ModuleModelImpl parentModule,
            ImmutableList<NumericParamModelImpl<T>> parts) {
        super(schema, parentModule);
        this.parts = parts;
        for (NumericParamModelImpl<T> part : parts) {
            part.setParentParam(this);
        }
    }

    @Override
    public ImmutableList<? extends NumericParamModel<T>> getParts() {
        return parts;
    }

    @Override
    public int getPartCount() {
        return parts.size();
    }

    @Override
    public ParamType getPartType() {
        return schema.getPartType();
    }

    @Override
    public boolean tryResetValue() {
        boolean anyReset = false;
        for (NumericParamModelImpl<T> part : parts) {
            if (part.tryResetValue()) {
                anyReset = true;
            }
        }
        return anyReset;
    }

    @Override
    public boolean hasDefaultValue() {
        return parts.stream()
                .anyMatch(ScalarParamModelImpl::hasDefaultValue);
    }

    @Override
    public boolean isVector() {
        return true;
    }

    static VectorParamModelImpl<Float> forFloats(VectorParamSchema<Float> schema, ModuleModelImpl parentModule) {
        ImmutableList<NumericParamModelImpl<Float>> parts = schema.getParts()
                .stream()
                .map(p -> NumericParamModelImpl.forFloat(p.toParamSchema(), parentModule))
                .collect(toImmutableList());
        return new VectorParamModelImpl<>(
                schema,
                parentModule,
                parts);
    }

    static VectorParamModelImpl<Integer> forIntegers(VectorParamSchema<Integer> schema, ModuleModelImpl parentModule) {
        ImmutableList<NumericParamModelImpl<Integer>> parts = schema.getParts()
                .stream()
                .map(p -> NumericParamModelImpl.forInteger(p.toParamSchema(), parentModule))
                .collect(toImmutableList());
        return new VectorParamModelImpl<>(
                schema,
                parentModule,
                parts);
    }
}
