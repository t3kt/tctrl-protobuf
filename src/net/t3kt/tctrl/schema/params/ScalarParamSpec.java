package net.t3kt.tctrl.schema.params;

import com.google.protobuf.Value;
import net.t3kt.tctrl.schema.TctrlSchemaProto.ParamSpec;

import java.util.Optional;

public abstract class ScalarParamSpec<T> {

    final ParamSpec spec;

    ScalarParamSpec(ParamSpec spec) {
        this.spec = spec;
    }

    public Optional<T> getValue() {
        return convert(spec.getValue());
    }

    public Optional<T> getDefaultValue() {
        return convert(spec.getDefaultVal());
    }

    protected abstract Optional<T> convert(Value value);

}
