package net.t3kt.tctrl.model.impl;

import net.t3kt.tctrl.schema.params.OtherParamSchema;

final class UnsupportedParamModelImpl extends ParamModelImpl<OtherParamSchema> {
    private UnsupportedParamModelImpl(OtherParamSchema schema, ModuleModelImpl parentModule) {
        super(schema, parentModule);
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
    public boolean isVector() {
        return false;
    }

    @Override
    public boolean isUnsupported() {
        return true;
    }

    static UnsupportedParamModelImpl forUnsupported(OtherParamSchema schema, ModuleModelImpl parentModule) {
        return new UnsupportedParamModelImpl(schema, parentModule);
    }
}
