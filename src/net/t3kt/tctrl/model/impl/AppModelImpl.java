package net.t3kt.tctrl.model.impl;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.t3kt.tctrl.model.AppModel;
import net.t3kt.tctrl.model.ModuleModel;
import net.t3kt.tctrl.model.ParamModel;
import net.t3kt.tctrl.model.SingleParamModel;
import net.t3kt.tctrl.schema.AppSchema;

final class AppModelImpl extends ParentModelNodeImpl<AppSchema> implements AppModel {

    private final ImmutableMap<String, ModuleModel> allModules;
    private final ParamModelCollection allParams;

    private AppModelImpl(
            AppSchema schema,
            ImmutableMap<String, ModuleModel> allModules,
            ParamModelCollection allParams,
            ImmutableList<ModuleModelImpl> childModules) {
        super(schema);
        this.allModules = allModules;
        this.allParams = allParams;
        setChildModules(childModules);
        //TODO: child module groups
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
    public ImmutableCollection<? extends ParamModel> getAllParams() {
        return allParams.getParams();
    }

    @Override
    public ImmutableCollection<? extends SingleParamModel> getAllFlatParams() {
        return allParams.getFlatParams();
    }

    static final class Builder {
        private final AppSchema schema;
        private final ImmutableMap.Builder<String, ModuleModel> allModules = ImmutableMap.builder();

        private Builder(AppSchema schema) {
            this.schema = schema;
        }

        private void registerModule(ModuleModelImpl module) {
            allModules.put(module.getPath(), module);
        }

        AppModelImpl build() {
            throw new RuntimeException("NOT IMPLEMENTED");
        }
    }
}
