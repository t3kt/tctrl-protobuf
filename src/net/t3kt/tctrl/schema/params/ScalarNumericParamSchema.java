package net.t3kt.tctrl.schema.params;

import java.util.Optional;
import net.t3kt.tctrl.schema.TctrlSchemaProto.ParamSpec;

public abstract class ScalarNumericParamSchema<T extends Number> extends ScalarParamSchema<T> {

    ScalarNumericParamSchema(ParamSpec spec) {
        super(spec);
    }

    public Optional<T> getMinLimit() {
        return convert(spec.getMinLimit());
    }

    public Optional<T> getMaxLimit() {
        return convert(spec.getMaxLimit());
    }

    public Optional<T> getMinNorm() {
        return convert(spec.getMinNorm());
    }

    public Optional<T> getMaxNorm() {
        return convert(spec.getMaxNorm());
    }
}
