using System;
using Google.Protobuf.WellKnownTypes;
using Tctrl.Schema;

namespace Tctrl.Core.Schema.Params {
    public sealed class BoolParamSchema : ScalarParamSchema<bool> {
        public BoolParamSchema(ParamSpec spec) : base(spec) { }

        protected override bool? Convert(Value value) => value.AsBool();

        public String OffHelp => Spec.OffHelp;
        public String ButtonText => Spec.ButtonText;
        public String ButtonOffText => Spec.ButtonOffText;

    }
}