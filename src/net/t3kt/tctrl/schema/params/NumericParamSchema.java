package net.t3kt.tctrl.schema.params;

import java.util.Optional;
import net.t3kt.tctrl.schema.TctrlSchemaProto.ParamSpec;

public abstract class NumericParamSchema<T extends Number> extends ScalarParamSchema<T> {

    NumericParamSchema(ParamSpec spec) {
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
