using Tctrl.Core.Schema.Params;

namespace Tctrl.Core.Model.Params {
    internal class SingleParamModel<TSchema> : ParamModel<TSchema>, ISingleParamModel where TSchema : ParamSchema {
        internal SingleParamModel(TSchema schema) : base(schema) { }
        public override bool IsSingle => true;
        public IVectorParamModel ParentParam { get; internal set; }
    }
}