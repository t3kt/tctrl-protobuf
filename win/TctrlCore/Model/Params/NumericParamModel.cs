using System;
using Tctrl.Core.Common;
using Tctrl.Core.Schema.Params;

namespace Tctrl.Core.Model.Params {
    internal class NumericParamModel<TValue>
        : ScalarParamModel<TValue, ScalarParamSchema<TValue>>,
            INumericParamModel<TValue>
        where TValue : struct, IComparable<TValue> {

        internal NumericParamModel(ScalarParamSchema<TValue> schema) : base(schema) { }

        public ValueRange<TValue> NormalizedRange { get; protected set; }

        public ValueRange<TValue> LimitRange { get; protected set; }

        public override bool IsValidValue(TValue value) => LimitRange.Contains(value);

        public bool TrySetNormalizedValue(double value) {
            return TrySetValue(NormalizedRange.DenormalizeValue(value));
        }

        public double? NormalizedValue => Value == null
            ? (double?) null
            : NormalizedRange.NormalizedValue(Value.Value);

    }
}