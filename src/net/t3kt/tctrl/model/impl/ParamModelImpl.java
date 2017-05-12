package net.t3kt.tctrl.model.impl;

import net.t3kt.tctrl.model.ModuleModel;
import net.t3kt.tctrl.model.params.ParamModel;
import net.t3kt.tctrl.schema.TctrlSchemaProto.ParamType;
import net.t3kt.tctrl.schema.params.ParamSchema;

abstract class ParamModelImpl<S extends ParamSchema> extends ModelNodeImpl<S> implements ParamModel<S> {
    private final ModuleModelImpl parentModule;

    ParamModelImpl(S schema, ModuleModelImpl parentModule) {
        super(schema);
        this.parentModule = parentModule;
    }

    @Override
    public ParamType getType() {
        return schema.getType();
    }

    public boolean isSingle() {
        return !isVector();
    }

    @Override
    public ModuleModel getParentModule() {
        return parentModule;
    }

    @Override
    public String getGroup() {
        return schema.getGroup();
    }
}
