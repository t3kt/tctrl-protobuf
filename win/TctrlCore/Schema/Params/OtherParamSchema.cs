using System;
using Tctrl.Schema;

namespace Tctrl.Core.Schema.Params
{
    public sealed class OtherParamSchema : SingleParamSchema
    {
        public OtherParamSchema(ParamSpec spec) : base(spec)
        {
        }

        public String OtherType => Spec.OtherType;
    }
}