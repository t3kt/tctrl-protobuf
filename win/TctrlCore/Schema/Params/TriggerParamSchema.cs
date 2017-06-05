using System;
using Tctrl.Schema;

namespace Tctrl.Core.Schema.Params
{
    public sealed class TriggerParamSchema : SingleParamSchema
    {
        public TriggerParamSchema(ParamSpec spec) : base(spec)
        {
        }

        public String ButtonText => Spec.ButtonText;
    }
}