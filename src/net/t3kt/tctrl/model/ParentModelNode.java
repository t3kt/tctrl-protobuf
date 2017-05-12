package net.t3kt.tctrl.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.t3kt.tctrl.schema.ParentSchemaNode;

public abstract class ParentModelNode<S extends ParentSchemaNode<?>> extends ModelNode<S> {
    ParentModelNode(S schemaNode) {
        super(schemaNode);
    }

    public abstract ImmutableList<ModuleModel> getChildModules();

    public abstract ImmutableMap<String, ModelNodeGroup<ModuleModel>> getChildGroups();
}
