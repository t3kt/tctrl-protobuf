package net.t3kt.tctrl.model;

import com.google.common.collect.ImmutableCollection;
import net.t3kt.tctrl.schema.AppSchema;

public interface AppModel extends ParentModelNode<AppSchema> {

    ImmutableCollection<ModuleModel> getAllModules();

    ModuleModel getModuleByPath(String path);

    ParamModel getParamByPath(String path);

    ImmutableCollection<ParamModel> getAllParams();

    ImmutableCollection<SingleParamModel> getAllFlatParams();
}
