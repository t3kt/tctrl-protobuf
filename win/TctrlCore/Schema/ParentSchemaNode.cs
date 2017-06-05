using System.Collections.Generic;
using System.Linq;
using Google.Protobuf;
using Tctrl.Schema;

namespace Tctrl.Core.Schema {
    public abstract class ParentSchemaNode<S> : SchemaNode<S> where S : IMessage {
        protected ParentSchemaNode(S spec, IEnumerable<ModuleSpec> childModules) : base(spec) {
            ChildModules = childModules.Select(moduleSpec => new ModuleSchema(moduleSpec)).ToList().AsReadOnly();
        }
        public IReadOnlyList<ModuleSchema> ChildModules { get; }
    }
}