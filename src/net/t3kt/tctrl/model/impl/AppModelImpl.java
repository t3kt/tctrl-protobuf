package net.t3kt.tctrl.model.impl;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableMap;
import net.t3kt.tctrl.model.AppModel;
import net.t3kt.tctrl.model.ModuleModel;
import net.t3kt.tctrl.model.ParamModel;
import net.t3kt.tctrl.model.SingleParamModel;
import net.t3kt.tctrl.schema.AppSchema;

final class AppModelImpl extends ParentModelNodeImpl<AppSchema> implements AppModel {

    private ImmutableMap<String, ModuleModel> allModules;
    private ParamModelCollection allParams;

    private AppModelImpl(Builder builder) {
        super(builder.schema);
    }

    void setAllModules(ImmutableMap<String, ModuleModel> allModules) {
        this.allModules = allModules;
    }

    void setAllParams(ParamModelCollection allParams) {
        this.allParams = allParams;
    }

    @Override
    public ImmutableCollection<ModuleModel> getAllModules() {
        return allModules.values();
    }

    @Override
    public ModuleModel getModuleByPath(String path) {
        return allModules.get(path);
    }

    @Override
    public ParamModel getParamByPath(String path) {
        return allParams.getParam(path);
    }

    @Override
    public ImmutableCollection<ParamModel> getAllParams() {
        return allParams.getParams();
    }

    @Override
    public ImmutableCollection<SingleParamModel> getAllFlatParams() {
        return allParams.getFlatParams();
    }

    static final class Builder {
        private final AppSchema schema;

        private Builder(AppSchema schema) {
            this.schema = schema;
        }

        AppModelImpl build() {
            return new AppModelImpl(this);
        }
    }
}
