using System.Linq;
using Tctrl.Core.Schema;

namespace Tctrl.Core.Model {
    public class AppModel : ParentModelNode<AppSchema> {
        public AppModel(AppSchema schema) : base(schema,
            schema.ChildModules.Select(module => new ModuleModel(module))) { }
    }
}