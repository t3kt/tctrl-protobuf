using System.Linq;
using Tctrl.Core.Schema;

namespace Tctrl.Core.Model {
    public class ModuleModel : ParentModelNode<ModuleSchema> {
        public ModuleModel(ModuleSchema schema) : base(schema,
            schema.ChildModules.Select(module => new ModuleModel(module))) { }
    }
}