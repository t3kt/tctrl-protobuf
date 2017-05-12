package net.t3kt.tctrl.model;

import net.t3kt.tctrl.schema.SchemaNode;

public interface ModelNode<S extends SchemaNode<?>> {

    S getSchema();

    String getKey();

    String getLabel();

    String getPath();
}
