using System;
using Google.Protobuf;

namespace Tctrl.Core.Schema
{
    abstract class SchemaNode<S> where S : IMessage
    {
        public SchemaNode(S spec) => this.Spec = spec;

        public S Spec { get; private set; }

        public abstract String getKey();

        public abstract String getLabel();

        public abstract String getPath();
    }
}
