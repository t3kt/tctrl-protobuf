package net.t3kt.tctrl.schema.params;

import net.t3kt.tctrl.schema.TctrlSchemaProto.ParamSpec;

public final class TriggerParamSchema extends ParamSchema {
    TriggerParamSchema(ParamSpec spec) {
        super(spec);
    }

    public String getButtonText() {
        return spec.getButtonText();
    }
}
