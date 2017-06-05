using System.Collections.Generic;
using System.Linq;
using Tctrl.Core.Schema.Params;
using Tctrl.Schema;

namespace Tctrl.Core.Schema {
    public class ModuleSchema : ParentSchemaNode<ModuleSpec> {

        public ModuleSchema(ModuleSpec spec) : base(spec, spec.ChildModule) {
            Params = spec.Param.Select(ParamSchema.forParam).ToList().AsReadOnly();
        }
        
        public override string Key => Spec.Key;
        public override string Label => Spec.Key;
        public override string Path => Spec.Path;
        
        public IReadOnlyList<ParamSchema> Params { get; private set; }
    }
}