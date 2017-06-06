using System;
using Tctrl.Core.Schema.Params;
using Tctrl.Schema;

namespace Tctrl.Core.Model.Params {
    internal abstract class ParamModel<TSchema> : ModelNode<TSchema>, IParamModel where TSchema : ParamSchema {

        protected ParamModel(TSchema schema) : base(schema) { }

        public ParamType ParamType => Schema.ParamType;

        public virtual bool TryResetValue() => false;
        public virtual bool CanResetValue => false;

        public abstract bool IsSingle { get; }
        public bool IsVector => !IsSingle;
        public virtual bool IsUnsupported => false;

        public ModuleModel ParentModule { get; internal set; }

    }

    internal sealed class UnsupportedParamModel : ParamModel<OtherParamSchema>, IUnsupportedParamModel {

        public UnsupportedParamModel(OtherParamSchema schema) : base(schema) { }

        public override bool IsSingle => true;

        public override bool IsUnsupported => true;

        public string OtherType => Schema.OtherType;

    }

    public static class ParamModels {

        public static IParamModel ForParam(ParamSchema schema) {
            var type = schema.ParamType;
            switch (type) {
                case ParamType.Int:
                    return ForNumber((ScalarParamSchema<int>) schema);
                case ParamType.Float:
                    return ForNumber((ScalarParamSchema<double>) schema);
                case ParamType.Bool:
                    return ForBool((BoolParamSchema) schema);
                case ParamType.Ivec:
                    throw new NotImplementedException();
                case ParamType.Fvec:
                    throw new NotImplementedException();
                case ParamType.String:
                    throw new NotImplementedException();
                case ParamType.Menu:
                    throw new NotImplementedException();
                case ParamType.Trigger:
                    throw new NotImplementedException();
                case ParamType.Other:
                default:
                    return ForOther((OtherParamSchema) schema);
            }
            throw new NotImplementedException();
        }

        public static INumericParamModel<TValue> ForNumber<TValue>(ScalarParamSchema<TValue> schema)
            where TValue : struct, IComparable<TValue> => new NumericParamModel<TValue>(schema);

        public static IUnsupportedParamModel ForOther(OtherParamSchema schema) => new UnsupportedParamModel(schema);

        public static IScalarParamModel<bool> ForBool(BoolParamSchema schema) => new BoolParamModel(schema);

    }
}