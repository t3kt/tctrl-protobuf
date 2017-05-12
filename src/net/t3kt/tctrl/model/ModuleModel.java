package net.t3kt.tctrl.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import javax.annotation.Nullable;
import net.t3kt.tctrl.schema.Groupable;
import net.t3kt.tctrl.schema.ModuleSchema;

public interface ModuleModel extends ParentModelNode<ModuleSchema>, Groupable {

    @Nullable
    ModuleModel getParentModule();

    ParamModel getParamByKey(String key);

    ImmutableList<ParamModel> getParams();

    ImmutableList<SingleParamModel> getFlatParams();

    ImmutableMap<String, ModelNodeGroup<ParamModel>> getParamGroups();
}
