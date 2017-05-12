package net.t3kt.tctrl.model;

import com.google.common.collect.ImmutableList;
import net.t3kt.tctrl.schema.AppSchema;

public interface AppModel extends ModelNode<AppSchema> {

    ImmutableList<ModuleModel> getAllModules();

    ModuleModel getModuleByPath(String path);

    ParamModel getParamByPath(String path);
}
