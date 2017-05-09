package net.t3kt.tctrl.schema.params;

import com.google.protobuf.Value;
import net.t3kt.tctrl.schema.TctrlSchemaProto.ParamSpec;

import java.util.Optional;

public final class BoolParamSchema extends ScalarParamSchema<Boolean> {
    BoolParamSchema(ParamSpec spec) {
        super(spec);
    }

    public String getOffHelp() {
        return spec.getOffHelp();
    }

    public String getButtonText() {
        return spec.getButtonText();
    }

    public String getButtonOffText() {
        return spec.getButtonOffText();
    }

    @Override
    protected Optional<Boolean> convert(Value value) {
        return Values.toBool(value);
    }
}
