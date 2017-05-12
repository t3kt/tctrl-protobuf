package net.t3kt.tctrl.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.t3kt.tctrl.schema.ParentSchemaNode;

public interface ParentModelNode<S extends ParentSchemaNode<?>> extends ModelNode<S> {

    ImmutableList<ModuleModel> getChildModules();

    ImmutableMap<String, ModelNodeGroup<ModuleModel>> getChildGroups();
}
