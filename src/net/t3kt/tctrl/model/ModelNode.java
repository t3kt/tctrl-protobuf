package net.t3kt.tctrl.model;

import net.t3kt.tctrl.schema.SchemaNode;

public abstract class ModelNode<S extends SchemaNode<?>> {

    final S schemaNode;

    ModelNode(S schemaNode) {
        this.schemaNode = schemaNode;
    }

    public S getSchemaNode() {
        return schemaNode;
    }

    public String getKey() {
        return schemaNode.getKey();
    }

    public String getLabel() {
        return schemaNode.getLabel();
    }

    public String getPath() {
        return schemaNode.getPath();
    }
}
