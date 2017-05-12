package net.t3kt.tctrl.model.impl;

import net.t3kt.tctrl.model.params.SingleParamModel;
import net.t3kt.tctrl.schema.params.SingleParamSchema;

abstract class SingleParamModelImpl<S extends SingleParamSchema> extends ParamModelImpl<S> implements SingleParamModel<S> {
    SingleParamModelImpl(S schema, ModuleModelImpl parentModule) {
        super(schema, parentModule);
    }

    @Override
    public boolean isVector() {
        return false;
    }
}
