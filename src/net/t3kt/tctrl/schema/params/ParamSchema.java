package net.t3kt.tctrl.schema.params;

import com.google.common.base.Preconditions;
import com.google.protobuf.Value;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.t3kt.tctrl.schema.SchemaNode;
import net.t3kt.tctrl.schema.TctrlSchemaProto.ParamSpec;
import net.t3kt.tctrl.schema.TctrlSchemaProto.ParamType;

public abstract class ParamSchema extends SchemaNode<ParamSpec> {
    public static VectorParamSchema<Integer> forIntegerVector(ParamSpec spec) {
        Preconditions.checkArgument(spec.getType() == ParamType.IVEC);
        return new VectorParamSchema<>(spec, NumericParamPartSchema.forIntegers(spec.getPartList()));
    }

    public static VectorParamSchema<Float> forFloatVector(ParamSpec spec) {
        Preconditions.checkArgument(spec.getType() == ParamType.FVEC);
        return new VectorParamSchema<>(spec, NumericParamPartSchema.forFloats(spec.getPartList()));
    }

    public static ScalarNumericParamSchema<Integer> forInteger(ParamSpec spec) {
        Preconditions.checkArgument(spec.getType() == ParamType.INT);
        return new ScalarNumericParamSchema<Integer>(spec) {
            @Override
            protected Optional<Integer> convert(Value value) {
                return Values.toInt(value);
            }
        };
    }

    public static ScalarNumericParamSchema<Float> forFloat(ParamSpec spec) {
        Preconditions.checkArgument(spec.getType() == ParamType.FLOAT);
        return new ScalarNumericParamSchema<Float>(spec) {
            @Override
            protected Optional<Float> convert(Value value) {
                return Values.toFloat(value);
            }
        };
    }

    public static BoolParamSchema forBoolean(ParamSpec spec) {
        Preconditions.checkArgument(spec.getType() == ParamType.BOOL);
        return new BoolParamSchema(spec);
    }

    public static TriggerParamSchema forTrigger(ParamSpec spec) {
        Preconditions.checkArgument(spec.getType() == ParamType.TRIGGER);
        return new TriggerParamSchema(spec);
    }

    public static MenuParamSchema forMenu(ParamSpec spec, @Nullable OptionListProvider optionListProvider) {
        Preconditions.checkArgument(spec.getType() == ParamType.MENU);
        return new MenuParamSchema(spec, optionListProvider);
    }

    public static MenuParamSchema forString(ParamSpec spec, @Nullable OptionListProvider optionListProvider) {
        Preconditions.checkArgument(spec.getType() == ParamType.STRING);
        return new MenuParamSchema(spec, optionListProvider);
    }

    public static OtherParamSchema forOther(ParamSpec spec) {
        return new OtherParamSchema(spec);
    }

    public static ParamSchema forParam(ParamSpec spec, @Nullable OptionListProvider optionListProvider) {
        switch (spec.getType()) {
            case BOOL:
                return forBoolean(spec);
            case STRING:
                return forString(spec, optionListProvider);
            case MENU:
                return forMenu(spec, optionListProvider);
            case INT:
                return forInteger(spec);
            case FLOAT:
                return forFloat(spec);
            case IVEC:
                return forIntegerVector(spec);
            case FVEC:
                return forFloatVector(spec);
            case TRIGGER:
                return forTrigger(spec);
            case OTHER:
            case UNRECOGNIZED:
            default:
                return forOther(spec);
        }
    }

    ParamSchema(ParamSpec spec) {
        super(spec);
    }

    @Override
    public String getKey() {
        return spec.getKey();
    }

    @Override
    public String getLabel() {
        return spec.getLabel();
    }

    @Override
    public String getPath() {
        return spec.getPath();
    }

    public String getOtherType() {
        return spec.getOtherType();
    }

    public String getStyle() {
        return spec.getStyle();
    }

    public String getGroup() {
        return spec.getGroup();
    }

    public List<String> getTags() {
        return spec.getTagList();
    }

    public String getHelp() {
        return spec.getHelp();
    }
}
