using Tctrl.Core.Schema.Params;

namespace Tctrl.Core.Model.Params {
    public abstract class SingleParamModel<TSchema> : ParamModel<TSchema> where TSchema : ParamSchema {
        protected SingleParamModel(TSchema schema) : base(schema) { }
        public override bool IsSingle => true;
        public override bool IsUnsupported => false;
    }
}