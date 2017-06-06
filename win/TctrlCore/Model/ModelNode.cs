using Google.Protobuf;

namespace Tctrl.Core.Model {
    public abstract class ModelNode<S> : IModelNode<S>
        where S : IMessage {

        protected ModelNode(S schema) {
            Schema = schema;
        }

        public S Schema { get; }

        public abstract string Key { get; }
        public abstract string Label { get; }
        public abstract string Path { get; }

    }
}