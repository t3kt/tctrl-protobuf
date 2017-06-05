using System.Collections.Generic;
using Tctrl.Core.Schema;

namespace Tctrl.Core.Model {
    public abstract class ParentModelNode<S> : ModelNode<S> where S : ISchemaNode {
        protected ParentModelNode(S schema, IEnumerable<ModuleModel> childModules) : base(schema) {
            ChildModules = new ModelNodeCollection<ModuleModel>(childModules);
        }
        
        public ModelNodeCollection<ModuleModel> ChildModules { get; private set; }
    }
}