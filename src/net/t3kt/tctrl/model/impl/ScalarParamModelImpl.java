package net.t3kt.tctrl.model.impl;

import net.t3kt.tctrl.model.params.ScalarParamModel;
import net.t3kt.tctrl.schema.params.ScalarParamSchema;

abstract class ScalarParamModelImpl<T, S extends ScalarParamSchema<T>> extends SingleParamModelImpl<T, S> implements ScalarParamModel<T, S> {
    ScalarParamModelImpl(S schema, ModuleModelImpl parentModule) {
        super(schema, parentModule);
    }
}
