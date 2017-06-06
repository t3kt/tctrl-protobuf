using System.Collections.Generic;
using Google.Protobuf;

namespace Tctrl.Core.Model {
    public abstract class ParentModelNode<TSchema> : ModelNode<TSchema>, IParentModelNode<TSchema>
    
        where TSchema : IMessage {

        protected ParentModelNode(TSchema schema, IEnumerable<IModuleModel> childModules) : base(schema) {
            ChildModules = ModelImpl.CreateGroupedModelCollection(childModules);
        }

        public IGroupedModelNodeCollection<IModuleModel> ChildModules { get; }

    }
}