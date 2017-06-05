using System;
using Google.Protobuf.WellKnownTypes;

namespace Tctrl.Core.Schema.Params {
    internal static class Values {
        internal static bool? AsBool(this Value value) {
            if (value == null) {
                return null;
            }
            switch (value.KindCase) {
                case Value.KindOneofCase.BoolValue:
                    return value.BoolValue;
                case Value.KindOneofCase.NumberValue:
                    // ReSharper disable once CompareOfFloatsByEqualityOperator
                    return value.NumberValue != 0.0;
                case Value.KindOneofCase.NullValue:
                case Value.KindOneofCase.None:
                    return null;
                default:
                    throw new ArgumentOutOfRangeException("Unsupported value type: " + value.KindCase);
            }
        }
        internal static int? AsInt(this Value value) {
            if (value == null) {
                return null;
            }
            switch (value.KindCase) {
                case Value.KindOneofCase.BoolValue:
                    return value.BoolValue ? 1 : 0;
                case Value.KindOneofCase.NumberValue:
                    return (int) value.NumberValue;
                case Value.KindOneofCase.NullValue:
                case Value.KindOneofCase.None:
                    return null;
                default:
                    throw new ArgumentOutOfRangeException("Unsupported value type: " + value.KindCase);
            }
        }
        internal static double? AsFloat(this Value value) {
            if (value == null) {
                return null;
            }
            switch (value.KindCase) {
                case Value.KindOneofCase.BoolValue:
                    return value.BoolValue ? 1.0 : 0.0;
                case Value.KindOneofCase.NumberValue:
                    return value.NumberValue;
                case Value.KindOneofCase.NullValue:
                case Value.KindOneofCase.None:
                    return null;
                default:
                    throw new ArgumentOutOfRangeException("Unsupported value type: " + value.KindCase);
            }
        }
    }
}