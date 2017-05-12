package net.t3kt.tctrl.model.impl;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableMap;
import javax.annotation.Nullable;
import net.t3kt.tctrl.model.ModelNodeGroup;
import net.t3kt.tctrl.model.ModuleModel;
import net.t3kt.tctrl.model.ParamModel;
import net.t3kt.tctrl.model.SingleParamModel;
import net.t3kt.tctrl.schema.ModuleSchema;

final class ModuleModelImpl extends ParentModelNodeImpl<ModuleSchema> implements ModuleModel {
    private ModuleModelImpl parentModule;
    private ParamModelCollection params;
    private ImmutableMap<String, ModelNodeGroup<ParamModel>> paramGroups;

    ModuleModelImpl(Builder builder) {
        super(builder.schema);
    }

    void setParentModule(ModuleModelImpl parentModule) {
        this.parentModule = parentModule;
    }

    void setParams(ParamModelCollection params) {
        this.params = params;
    }

    void setParamGroups(ImmutableMap<String, ModelNodeGroup<ParamModel>> paramGroups) {
        this.paramGroups = paramGroups;
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
    public ImmutableMap<String, ModelNodeGroup<ParamModel>> getParamGroups() {
        return paramGroups;
    }

    @Override
    public String getGroup() {
        return schema.getGroup();
    }

    static final class Builder {
        private final ModuleSchema schema;

        Builder(ModuleSchema schema) {
            this.schema = schema;
        }

        ModuleModelImpl build() {
            return new ModuleModelImpl(this);
        }
    }
}
