using System.Collections.Generic;
using System.Linq;
using Tctrl.Schema;

namespace Tctrl.Core.Schema {
    public class AppSchema : ParentSchemaNode<AppSpec> {

        public AppSchema(AppSpec spec) : base(spec, spec.ChildModule) {
        }
        
        public override string Key => Spec.Key;
        public override string Label => Spec.Key;
        public override string Path => Spec.Path;
    }
}