using System.Linq;

namespace Tctrl.Core.Schema.Model {
    public class ModuleModel : ParentModelNode<ModuleSchema> {
        public ModuleModel(ModuleSchema schema) : base(schema,
            schema.ChildModules.Select(module => new ModuleModel(module))) { }
    }
}