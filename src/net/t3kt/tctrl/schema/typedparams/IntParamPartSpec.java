package net.t3kt.tctrl.schema.typedparams;

import com.google.protobuf.Value;
import net.t3kt.tctrl.schema.TctrlSchemaProto.ParamPartSpec;

final class IntParamPartSpec extends TypedParamPartSpecBase<Integer> {
    IntParamPartSpec(ParamPartSpec partSpec) {
        super(partSpec);
    }

    @Override
    protected Integer convertValue(Value value) {
        switch (value.getKindCase()) {
            case NUMBER_VALUE:
                return (int) value.getNumberValue();
            case BOOL_VALUE:
                return value.getBoolValue() ? 1 : 0;
            default:
                throw new IllegalStateException("Unsupported value format: " + value);
        }
    }
}
