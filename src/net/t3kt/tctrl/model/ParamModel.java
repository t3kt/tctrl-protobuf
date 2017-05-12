package net.t3kt.tctrl.model;

import net.t3kt.tctrl.schema.Groupable;
import net.t3kt.tctrl.schema.TctrlSchemaProto.ParamType;
import net.t3kt.tctrl.schema.params.ParamSchema;

public interface ParamModel extends ModelNode<ParamSchema>, Groupable {

    ParamType getType();

    void resetValue();

    boolean isVector();
    boolean isSingle();

    ModuleModel getParentModule();
}
