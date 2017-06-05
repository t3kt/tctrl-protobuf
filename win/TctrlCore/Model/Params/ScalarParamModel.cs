using System;
using Tctrl.Core.Common;
using Tctrl.Core.Schema.Params;

namespace Tctrl.Core.Model.Params {
    public abstract class ScalarParamModel<TValue, TSchema> : SingleParamModel<TSchema> where TSchema : ScalarParamSchema<TValue> where TValue : struct, IComparable<TValue> {
        protected ScalarParamModel(TSchema schema) : base(schema) { }

        public TValue? Value { get; internal set; }
        
        public ValueRange<TValue> NormalizedRange { get; protected set; }
        
        public ValueRange<TValue> LimitRange { get; protected set; }

        public bool TrySetValue(TValue value) {
            if (!CanSetValue || !IsValidValue(value)) {
                return false;
            }
            Value = value;
            return true;
        }

        public bool IsValidValue(TValue value) => LimitRange.Contains(value);

        public TValue? DefaultValue => Schema.DefaultValue;
        public bool CanSetValue { get; internal set; } = true;

        public abstract bool TrySetNormalizedValue(double value);
        public abstract double? NormalizedValue { get; }
    }
}