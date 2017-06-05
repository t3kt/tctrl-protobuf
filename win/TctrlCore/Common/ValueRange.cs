using System;

namespace Tctrl.Core.Common {
    public static class ValueRanges {
        public static ValueRange<double> ForDoubles(double? lowerEndpoint, double? upperEndpoint) =>
            new DoubleValueRange(lowerEndpoint, upperEndpoint);

        public static ValueRange<int> ForInt(int? lowerEndpoint, int? upperEndpoint) => new IntValueRange(lowerEndpoint,
            upperEndpoint);

        internal static double MapValue(double x, double in_min, double in_max, double out_min, double out_max) {
            return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
        }
    }

    internal sealed class IntValueRange : ValueRange<int> {
        public IntValueRange(int? lowerEndpoint, int? upperEndpoint) : base(lowerEndpoint, upperEndpoint) { }

        public override double NormalizedValue(int value) {
            if (LowerEndpoint == null || UpperEndpoint == null) {
                throw new NotSupportedException();
            }
            return ValueRanges.MapValue(value, LowerEndpoint.Value, UpperEndpoint.Value, 0.0, 1.0);
        }

        public override int DenormalizeValue(double value) {
            if (LowerEndpoint == null || UpperEndpoint == null) {
                throw new NotSupportedException();
            }
            return (int) Math.Round(ValueRanges.MapValue(value, 0.0, 1.0, LowerEndpoint.Value, UpperEndpoint.Value));
        }
    }

    internal sealed class DoubleValueRange : ValueRange<double> {
        public DoubleValueRange(double? lowerEndpoint, double? upperEndpoint) : base(lowerEndpoint, upperEndpoint) { }

        public override double NormalizedValue(double value) {
            if (LowerEndpoint == null || UpperEndpoint == null) {
                throw new NotSupportedException();
            }
            return ValueRanges.MapValue(value, LowerEndpoint.Value, UpperEndpoint.Value, 0.0, 1.0);
        }

        public override double DenormalizeValue(double value) {
            if (LowerEndpoint == null || UpperEndpoint == null) {
                throw new NotSupportedException();
            }
            return ValueRanges.MapValue(value, 0.0, 1.0, LowerEndpoint.Value, UpperEndpoint.Value);
        }
    }

    public abstract class ValueRange<TValue> where TValue : struct, IComparable<TValue> {
        public TValue? LowerEndpoint { get; }
        public TValue? UpperEndpoint { get; }

        internal ValueRange(TValue? lowerEndpoint, TValue? upperEndpoint) {
            LowerEndpoint = lowerEndpoint;
            UpperEndpoint = upperEndpoint;
        }

        public bool Contains(TValue value) {
            return (!LowerEndpoint.HasValue || LowerEndpoint.Value.CompareTo(value) >= 0) &&
                   (!UpperEndpoint.HasValue || UpperEndpoint.Value.CompareTo(value) <= 0);
        }

        public abstract double NormalizedValue(TValue value);
        public abstract TValue DenormalizeValue(double value);
    }
}