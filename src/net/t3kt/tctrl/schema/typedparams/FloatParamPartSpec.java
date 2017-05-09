package net.t3kt.tctrl.schema.typedparams;

import com.google.protobuf.Value;
import net.t3kt.tctrl.schema.TctrlSchemaProto.ParamPartSpec;

final class FloatParamPartSpec extends TypedParamPartSpecBase<Float> {
    FloatParamPartSpec(ParamPartSpec partSpec) {
        super(partSpec);
    }

    @Override
    protected Float convertValue(Value value) {
        switch (value.getKindCase()) {
            case NUMBER_VALUE:
                return (float) value.getNumberValue();
            case BOOL_VALUE:
                return value.getBoolValue() ? 1.0f : 0.0f;
            default:
                throw new IllegalStateException("Unsupported value format: " + value);
        }
    }
}
