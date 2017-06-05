using System;
using Tctrl.Core.Schema.Params;
using Tctrl.Schema;

namespace Tctrl.Core.Model.Params {
    public abstract class ParamModel<TSchema> : ModelNode<TSchema>, IParamModel where TSchema : ParamSchema {
        protected ParamModel(TSchema schema) : base(schema) { }

        public ParamType ParamType => Schema.ParamType;

        public bool TryResetValue() => false;
        public bool CanResetValue => false;

        public abstract bool IsSingle { get; }
        public bool IsVector => !IsSingle;
        public abstract bool IsUnsupported { get; }

        public ModuleModel ParentModule { get; internal set; }
    }

    public static class ParamModels {
        public static IParamModel ForParam(ParamSchema schema) {
            var type = schema.ParamType;
            switch (type) {
                case ParamType.Int:
                    return ForNumber((ScalarParamSchema<int>) schema);
                case ParamType.Float:
                    return ForNumber((ScalarParamSchema<double>) schema);
            }
            throw new NotImplementedException();
        }

        public static NumericParamModel<TValue> ForNumber<TValue>(ScalarParamSchema<TValue> schema)
            where TValue : struct, IComparable<TValue> {
            return new NumericParamModel<TValue>(schema);
        }
    }
}