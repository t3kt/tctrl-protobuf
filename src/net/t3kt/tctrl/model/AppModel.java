package net.t3kt.tctrl.model;

import com.google.common.collect.ImmutableCollection;
import net.t3kt.tctrl.model.impl.Impl;
import net.t3kt.tctrl.schema.AppSchema;

public interface AppModel extends ParentModelNode<AppSchema> {

    static AppModel build(AppSchema schema) {
        return Impl.buildAppModel(schema);
    }

    ImmutableCollection<? extends ModuleModel> getAllModules();

    ModuleModel getModuleByPath(String path);

    ParamModel getParamByPath(String path);

    ImmutableCollection<? extends ParamModel> getAllParams();

    ImmutableCollection<? extends SingleParamModel> getAllFlatParams();
}
