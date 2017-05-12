package net.t3kt.tctrl.model.impl;

import net.t3kt.tctrl.model.ModuleModel;
import net.t3kt.tctrl.model.params.ParamModel;
import net.t3kt.tctrl.schema.TctrlSchemaProto.ParamType;
import net.t3kt.tctrl.schema.params.BoolParamSchema;
import net.t3kt.tctrl.schema.params.MenuParamSchema;
import net.t3kt.tctrl.schema.params.NumericParamSchema;
import net.t3kt.tctrl.schema.params.OtherParamSchema;
import net.t3kt.tctrl.schema.params.ParamSchema;
import net.t3kt.tctrl.schema.params.TriggerParamSchema;
import net.t3kt.tctrl.schema.params.VectorParamSchema;

abstract class ParamModelImpl<S extends ParamSchema> extends ModelNodeImpl<S> implements ParamModel<S> {
    private final ModuleModelImpl parentModule;

    ParamModelImpl(S schema, ModuleModelImpl parentModule) {
        super(schema);
        this.parentModule = parentModule;
    }

    @Override
    public ParamType getType() {
        return schema.getType();
    }

    public boolean isSingle() {
        return !isVector();
    }

    @Override
    public boolean isUnsupported() {
        return false;
    }

    @Override
    public ModuleModel getParentModule() {
        return parentModule;
    }

    @Override
    public String getGroup() {
        return schema.getGroup();
    }

    static ParamModelImpl<?> create(ParamSchema schema, ModuleModelImpl parentModule) {
        switch (schema.getType()) {
            case BOOL:
                return ScalarParamModelImpl.forBoolean((BoolParamSchema) schema, parentModule);
            case STRING:
                return MenuParamModelImpl.forString((MenuParamSchema) schema, parentModule);
            case MENU:
                return MenuParamModelImpl.forMenu((MenuParamSchema) schema, parentModule);
            case INT:
                return NumericParamModelImpl.forInteger((NumericParamSchema<Integer>) schema, parentModule);
            case FLOAT:
                return NumericParamModelImpl.forFloat((NumericParamSchema<Float>) schema, parentModule);
            case IVEC:
                return VectorParamModelImpl.forIntegers((VectorParamSchema<Integer>) schema, parentModule);
            case FVEC:
                return VectorParamModelImpl.forFloats((VectorParamSchema<Float>) schema, parentModule);
            case TRIGGER:
                return TriggerParamModelImpl.forTrigger((TriggerParamSchema) schema, parentModule);
            case OTHER:
                return UnsupportedParamModelImpl.forUnsupported((OtherParamSchema) schema, parentModule);
            default:
                throw new IllegalArgumentException("Unsupported param schema: " + schema);
        }
    }
}
