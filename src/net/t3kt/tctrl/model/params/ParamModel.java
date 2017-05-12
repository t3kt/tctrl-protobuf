package net.t3kt.tctrl.model.params;

import net.t3kt.tctrl.model.ModelNode;
import net.t3kt.tctrl.model.ModuleModel;
import net.t3kt.tctrl.schema.Groupable;
import net.t3kt.tctrl.schema.TctrlSchemaProto.ParamType;
import net.t3kt.tctrl.schema.params.ParamSchema;

public interface ParamModel<S extends ParamSchema> extends ModelNode<S>, Groupable {

    ParamType getType();

    void resetValue();

    boolean isVector();
    boolean isSingle();

    ModuleModel getParentModule();
}
