using System;
using Tctrl.Core.Schema.Params;
using Tctrl.Schema;

namespace Tctrl.Core.Model.Params {
    internal abstract class ParamModel : ModelNode<ParamSpec>, IParamModel {

        protected ParamModel(ParamSpec schema) : base(schema) { }

        public override string Key => Schema.Key;
        public override string Label => Schema.Label;
        public override string Path => Schema.Path;
        
        public ParamType ParamType => Schema.Type;
        public string Group => Schema.Group;

        public virtual bool TryResetValue() => false;
        public virtual bool CanResetValue => false;

        public abstract bool IsSingle { get; }
        public bool IsVector => !IsSingle;
        public virtual bool IsUnsupported => false;

        public IModuleModel ParentModule { get; internal set; }

    }

    internal sealed class UnsupportedParamModel : ParamModel, IUnsupportedParamModel {

        public UnsupportedParamModel(ParamSpec schema) : base(schema) { }

        public override bool IsSingle => true;

        public override bool IsUnsupported => true;

        public string OtherType => Schema.OtherType;

    }

    public static class ParamModels {

//        public static IParamModel ForParam(ParamSpec schema) {
//            var type = schema.Type;
//            switch (type) {
//                case ParamType.Int:
//                    return ForNumber<int>(schema);
//                case ParamType.Float:
//                    return ForNumber<double>(schema);
//                case ParamType.Bool:
//                    return ForBool(schema);
//                case ParamType.Ivec:
//                    throw new NotImplementedException();
//                case ParamType.Fvec:
//                    throw new NotImplementedException();
//                case ParamType.String:
//                    throw new NotImplementedException();
//                case ParamType.Menu:
//                    throw new NotImplementedException();
//                case ParamType.Trigger:
//                    throw new NotImplementedException();
//                case ParamType.Other:
//                    return ForOther(schema);
//                default:
//                    throw new NotImplementedException();
//            }
//        }
//
//        public static INumericParamModel<TValue> ForNumber<TValue>(ScalarParamSchema<TValue> schema)
//            where TValue : struct, IComparable<TValue> => new NumericParamModel<TValue>(schema);
//
//        public static IUnsupportedParamModel ForOther(ParamSpec schema) => new UnsupportedParamModel(schema);
//
//        public static IScalarParamModel<bool> ForBool(BoolParamSchema schema) => new BoolParamModel(schema);

    }
}