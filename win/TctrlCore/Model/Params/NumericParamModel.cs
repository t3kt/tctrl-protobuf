using System;
using Tctrl.Core.Schema.Params;

namespace Tctrl.Core.Model.Params {
    public class NumericParamModel<TValue> : ScalarParamModel<TValue, ScalarParamSchema<TValue>>
        where TValue : struct, IComparable<TValue> {
        internal NumericParamModel(ScalarParamSchema<TValue> schema) : base(schema) { }

        public override bool TrySetNormalizedValue(double value) {
            return TrySetValue(NormalizedRange.DenormalizeValue(value));
        }

        public override double? NormalizedValue => Value == null
            ? (double?) null
            : NormalizedRange.NormalizedValue(Value.Value);
    }
}