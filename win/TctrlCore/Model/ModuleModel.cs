using System.Linq;
using Tctrl.Schema;

namespace Tctrl.Core.Model {
    public class ModuleModel : ParentModelNode<ModuleSpec> {
        public ModuleModel(ModuleSpec schema) : base(schema,
            schema.ChildModule.Select(ModelImpl.CreateModule)) { }

        public override string Key => Schema.Key;
        public override string Label => Schema.Key;
        public override string Path => Schema.Path;

    }
}