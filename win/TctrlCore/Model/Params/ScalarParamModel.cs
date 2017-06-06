using System;
using Tctrl.Core.Schema.Params;

namespace Tctrl.Core.Model.Params {
    internal abstract class ScalarParamModel<TValue, TSchema> : SingleParamModel<TSchema>, IScalarParamModel<TValue>
        where TSchema : ScalarParamSchema<TValue>
        where TValue : struct, IComparable<TValue> {

        protected ScalarParamModel(TSchema schema) : base(schema) { }

        public TValue? Value { get; internal set; }

        public bool TrySetValue(TValue value) {
            if (!CanSetValue || !IsValidValue(value)) {
                return false;
            }
            Value = value;
            return true;
        }

        public abstract bool IsValidValue(TValue value);

        public TValue? DefaultValue => Schema.DefaultValue;
        public bool CanSetValue { get; internal set; } = true;

    }

    internal sealed class BoolParamModel : ScalarParamModel<bool, BoolParamSchema> {

        internal BoolParamModel(BoolParamSchema schema) : base(schema) { }

        public override bool IsValidValue(bool value) => true;

    }
}