package net.t3kt.tctrl.schema.params;

import net.t3kt.tctrl.schema.TctrlSchemaProto.ParamSpec;

public abstract class SingleParamSchema extends ParamSchema {
    SingleParamSchema(ParamSpec spec) {
        super(spec);
    }

    @Override
    public boolean isVector() {
        return false;
    }
}
