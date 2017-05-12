package net.t3kt.tctrl.model.impl;

import net.t3kt.tctrl.model.ModelNode;
import net.t3kt.tctrl.schema.SchemaNode;

abstract class ModelNodeImpl<S extends SchemaNode<?>> implements ModelNode<S> {

    final S schema;

    ModelNodeImpl(S schema) {
        this.schema = schema;
    }

    @Override
    public S getSchema() {
        return schema;
    }

    @Override
    public String getKey() {
        return schema.getKey();
    }

    @Override
    public String getLabel() {
        return schema.getLabel();
    }

    @Override
    public String getPath() {
        return schema.getPath();
    }
}
