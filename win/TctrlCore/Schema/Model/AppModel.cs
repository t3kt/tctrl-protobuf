using System.Linq;

namespace Tctrl.Core.Schema.Model {
    public class AppModel : ParentModelNode<AppSchema> {
        public AppModel(AppSchema schema) : base(schema,
            schema.ChildModules.Select(module => new ModuleModel(module))) { }
    }
}