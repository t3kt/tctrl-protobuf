using System.Linq;
using Tctrl.Schema;

namespace Tctrl.Core.Model {
    public class AppModel : ParentModelNode<AppSpec> {

        public AppModel(AppSpec schema) : base(schema,
            schema.ChildModule.Select(ModelImpl.CreateModule)) { }

        public override string Key => Schema.Key;
        public override string Label => Schema.Key;
        public override string Path => Schema.Path;

    }
}