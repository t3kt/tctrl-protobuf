using System;
using Google.Protobuf;

namespace Tctrl.Core.Schema
{
    public abstract class SchemaNode<S> where S : IMessage
    {
        protected SchemaNode(S spec)
        {
            Spec = spec;
        }

        public S Spec { get; }

        public abstract String Key { get; }

        public abstract String Label { get; }

        public abstract String Path { get; }
    }
}
