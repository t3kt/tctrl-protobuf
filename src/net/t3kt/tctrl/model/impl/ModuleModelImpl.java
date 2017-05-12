package net.t3kt.tctrl.model.impl;

import com.google.common.collect.ImmutableCollection;
import javax.annotation.Nullable;
import net.t3kt.tctrl.model.ModelNodeGroup;
import net.t3kt.tctrl.model.ModuleModel;
import net.t3kt.tctrl.model.params.ParamModel;
import net.t3kt.tctrl.model.params.SingleParamModel;
import net.t3kt.tctrl.schema.ModuleSchema;
import net.t3kt.tctrl.schema.params.ParamSchema;

final class ModuleModelImpl extends ParentModelNodeImpl<ModuleSchema> implements ModuleModel {
    private final ModuleModelImpl parentModule;
    private ParamModelCollection params;

    ModuleModelImpl(
            ModuleSchema schema,
            ModuleModelImpl parentModule) {
        super(schema);
        this.parentModule = parentModule;
    }

    private void setParams(ParamModelCollection params) {
        this.params = params;
    }

    @Nullable
    @Override
    public ModuleModel getParentModule() {
        return parentModule;
    }

    @Override
    public ParamModel getParamByKey(String key) {
        return params.getParam(key);
    }

    @Override
    public ImmutableCollection<? extends ParamModel> getParams() {
        return params.getParams();
    }

    @Override
    public ImmutableCollection<? extends SingleParamModel> getFlatParams() {
        return params.getFlatParams();
    }

    @Override
    public ImmutableCollection<? extends ModelNodeGroup<? extends ParamModel<?>>> getParamGroups() {
        return params.getParamGroups().getGroups();
    }

    @Override
    public ModelNodeGroup<? extends ParamModel<?>> getParamGroup(String key) {
        return params.getParamGroups().getGroup(key);
    }

    @Override
    public String getGroup() {
        return schema.getGroup();
    }

    static final class Builder {
        private final ModuleSchema schema;
        private ModuleModelImpl parentModule;

        Builder(ModuleSchema schema) {
            this.schema = schema;
        }

        Builder setParentModule(ModuleModelImpl parentModule) {
            this.parentModule = parentModule;
            return this;
        }

        ModuleModelImpl build() {
            ModuleModelImpl module = new ModuleModelImpl(schema, parentModule);
            ParamModelCollection.Builder params = ParamModelCollection.moduleLocalBuilder()
                    .addGroups(schema.getParamGroups().getGroups());
            for (ParamSchema paramSchema : schema.getParams()) {
                ParamModelImpl param = ParamModelImpl.create(paramSchema, module);
                params.addParam(param);
            }
            return module;
        }
    }
}
