package net.t3kt.tctrl.schema.params;

import com.google.protobuf.Value;
import net.t3kt.tctrl.schema.TctrlSchemaProto.ParamSpec;

import java.util.Optional;

public abstract class ScalarNumericParamSpec<T extends Number> extends ScalarParamSpec<T> {

    public static ScalarNumericParamSpec<Integer> forInteger(ParamSpec spec) {
        return new ScalarNumericParamSpec<Integer>(spec) {
            @Override
            protected Optional<Integer> convert(Value value) {
                return Values.toInt(value);
            }
        };
    }

    public static ScalarNumericParamSpec<Float> forFloat(ParamSpec spec) {
        return new ScalarNumericParamSpec<Float>(spec) {
            @Override
            protected Optional<Float> convert(Value value) {
                return Values.toFloat(value);
            }
        };
    }

    private ScalarNumericParamSpec(ParamSpec spec) {
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
