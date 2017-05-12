package net.t3kt.tctrl.model.impl;

import java.util.Optional;
import javax.annotation.Nullable;
import net.t3kt.tctrl.model.params.ScalarParamModel;
import net.t3kt.tctrl.model.params.VectorParamModel;
import net.t3kt.tctrl.schema.params.BoolParamSchema;
import net.t3kt.tctrl.schema.params.ScalarParamSchema;

abstract class ScalarParamModelImpl<T, S extends ScalarParamSchema<T>> extends SingleParamModelImpl<S> implements ScalarParamModel<T, S> {
    T value;
    final Optional<T> defaultValue;

    ScalarParamModelImpl(S schema, ModuleModelImpl parentModule) {
        super(schema, parentModule);
        defaultValue = schema.getDefaultValue();
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public boolean trySetValue(T value) {
        if (!isValidValue(value)) {
            return false;
        }
        this.value = value;
        return true;
    }

    @Override
    public boolean tryResetValue() {
        return defaultValue.filter(this::trySetValue).isPresent();
    }

    @Override
    public boolean hasDefaultValue() {
        return getDefaultValue().isPresent();
    }

    @Override
    public Optional<T> getDefaultValue() {
        return defaultValue;
    }

    @Nullable
    @Override
    public VectorParamModel<?> getParentParam() {
        return null;
    }

    static ScalarParamModelImpl<Boolean, BoolParamSchema> forBoolean(
            BoolParamSchema schema,
            ModuleModelImpl parentModule) {
        return new ScalarParamModelImpl<Boolean, BoolParamSchema>(schema, parentModule) {
            @Override
            public boolean isValidValue(Boolean value) {
                return true;
            }
        };
    }
}
