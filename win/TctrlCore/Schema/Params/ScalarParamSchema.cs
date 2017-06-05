using Google.Protobuf.WellKnownTypes;
using Tctrl.Schema;

namespace Tctrl.Core.Schema.Params
{
    public abstract class ScalarParamSchema<T> : SingleParamSchema where T : struct
    {
        protected ScalarParamSchema(ParamSpec spec) : base(spec)
        {
        }

        public T? Value => Convert(Spec.Value);

        public T? DefaultValue => Convert(Spec.DefaultVal);

        protected abstract T? Convert(Value value);
    }
}