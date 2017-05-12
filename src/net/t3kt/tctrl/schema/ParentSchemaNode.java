package net.t3kt.tctrl.schema;

import com.google.common.collect.ImmutableList;
import com.google.protobuf.Message;

public abstract class ParentSchemaNode<S extends Message> extends SchemaNode<S> {
    protected ParentSchemaNode(S spec) {
        super(spec);
    }

    public abstract SchemaGroupList getChildGroups();

    public abstract ImmutableList<ModuleSchema> getChildModules();
}
