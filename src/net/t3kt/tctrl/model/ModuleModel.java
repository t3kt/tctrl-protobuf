package net.t3kt.tctrl.model;

import net.t3kt.tctrl.schema.Groupable;
import net.t3kt.tctrl.schema.ModuleSchema;

public interface ModuleModel extends ParentModelNode<ModuleSchema>, Groupable {

    ParamModel getParamByKey(String key);
}
