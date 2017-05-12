package net.t3kt.tctrl.model;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableMap;
import javax.annotation.Nullable;
import net.t3kt.tctrl.model.params.ParamModel;
import net.t3kt.tctrl.model.params.SingleParamModel;
import net.t3kt.tctrl.schema.Groupable;
import net.t3kt.tctrl.schema.ModuleSchema;

public interface ModuleModel extends ParentModelNode<ModuleSchema>, Groupable {

    @Nullable
    ModuleModel getParentModule();

    ParamModel getParamByKey(String key);

    ImmutableCollection<? extends ParamModel> getParams();

    ImmutableCollection<? extends SingleParamModel> getFlatParams();

    ImmutableCollection<? extends ModelNodeGroup<? extends ParamModel<?>>> getParamGroups();

    ModelNodeGroup<? extends ParamModel<?>> getParamGroup(String key);
}
