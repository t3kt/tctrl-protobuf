using System;
using Google.Protobuf.WellKnownTypes;
using Tctrl.Schema;

namespace Tctrl.Core.Schema.Params {
    public abstract class ParamSchema : SchemaNode<ParamSpec> {

        protected ParamSchema(ParamSpec spec) : base(spec) { }

        public override string Key => Spec.Key;
        public override string Label => Spec.Key;
        public override string Path => Spec.Path;
        public ParamType ParamType => Spec.Type;

        public static ParamSchema ForParam(ParamSpec spec) {
            throw new NotImplementedException();
        }

        public static BoolParamSchema ForBool(ParamSpec spec) {
            return new BoolParamSchema(spec);
        }

        public static ScalarParamSchema<int> ForInt(ParamSpec spec) {
            return new BasicSingleParamSchema<int>(spec, Values.AsInt);
        }

        public static ScalarParamSchema<double> ForFloat(ParamSpec spec) {
            return new BasicSingleParamSchema<double>(spec, Values.AsFloat);
        }

        private delegate T? ValueConverter<T>(Value value)
            where T : struct;

        private sealed class BasicSingleParamSchema<T> : ScalarParamSchema<T>
            where T : struct {

            private readonly ValueConverter<T> _converter;

            public BasicSingleParamSchema(ParamSpec spec, ValueConverter<T> converter) : base(spec) {
                this._converter = converter;
            }

            protected override T? Convert(Value value) {
                return this._converter(value);
            }

        }

    }
}