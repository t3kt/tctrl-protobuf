package net.t3kt.tctrl.schema.params;

import com.google.protobuf.Value;
import java.util.Optional;
import net.t3kt.tctrl.schema.TctrlSchemaProto.ParamSpec;

public abstract class ScalarParamSchema<T> extends SingleParamSchema {

    ScalarParamSchema(ParamSpec spec) {
        super(spec);
    }

    public Optional<T> getValue() {
        return convert(spec.getValue());
    }

    public Optional<T> getDefaultValue() {
        return convert(spec.getDefaultVal());
    }

    protected abstract Optional<T> convert(Value value);

    @Override
    public boolean isVector() {
        return false;
    }
}
