using Tctrl.Core.Schema;

namespace Tctrl.Core.Model {
    public abstract class ModelNode<S> : IModelNode<S>
        where S : ISchemaNode {

        protected ModelNode(S schema) => Schema = schema;

        public S Schema { get; }
        public string Key => Schema.Key;
        public string Label => Schema.Label;
        public string Path => Schema.Path;

    }
}