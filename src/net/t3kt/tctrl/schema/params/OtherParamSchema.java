package net.t3kt.tctrl.schema.params;

import net.t3kt.tctrl.schema.TctrlSchemaProto.ParamSpec;

public final class OtherParamSchema extends ParamSchema {
    OtherParamSchema(ParamSpec spec) {
        super(spec);
    }

    public String getOtherType() {
        return spec.getOtherType();
    }
}
