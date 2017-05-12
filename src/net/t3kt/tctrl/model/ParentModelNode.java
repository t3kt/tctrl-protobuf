package net.t3kt.tctrl.model;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableMap;
import net.t3kt.tctrl.schema.ParentSchemaNode;

public interface ParentModelNode<S extends ParentSchemaNode<?>> extends ModelNode<S> {

    ImmutableCollection<? extends ModuleModel> getChildModules();

    ModuleModel getChildModuleByKey(String key);

    ImmutableMap<String, ModelNodeGroup<ModuleModel>> getChildGroups();
}
