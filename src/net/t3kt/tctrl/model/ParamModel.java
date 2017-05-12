package net.t3kt.tctrl.model;

import net.t3kt.tctrl.schema.Groupable;
import net.t3kt.tctrl.schema.params.ParamSchema;

public abstract class ParamModel extends ModelNode<ParamSchema> implements Groupable {
    ParamModel(ParamSchema schemaNode) {
        super(schemaNode);
    }

    @Override
    public String getGroup() {
        return schemaNode.getGroup();
    }
}
