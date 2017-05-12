package net.t3kt.tctrl.model.impl;

import java.util.Optional;
import net.t3kt.tctrl.model.params.TriggerParamModel;
import net.t3kt.tctrl.model.params.VectorParamModel;
import net.t3kt.tctrl.schema.params.TriggerParamSchema;

final class TriggerParamModelImpl extends SingleParamModelImpl<TriggerParamSchema> implements TriggerParamModel {
    private TriggerParamModelImpl(TriggerParamSchema schema, ModuleModelImpl parentModule) {
        super(schema, parentModule);
    }

    @Override
    public void trigger() {
        throw new RuntimeException("NOT IMPLEMENTED");
    }

    @Override
    public boolean tryResetValue() {
        return false;
    }

    @Override
    public boolean hasDefaultValue() {
        return false;
    }

    @Override
    public Optional<VectorParamModel<?>> getParentParam() {
        return Optional.empty();
    }

    static TriggerParamModelImpl forTrigger(TriggerParamSchema schema, ModuleModelImpl parentModule) {
        return new TriggerParamModelImpl(schema, parentModule);
    }
}
