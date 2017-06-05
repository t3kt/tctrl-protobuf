using System;
using Google.Protobuf;

namespace Tctrl.Core.Schema
{
    public interface ISchemaNode {
        string Key { get; }
        string Label { get; }
        string Path { get; }
    }
    
    public abstract class SchemaNode<S> : ISchemaNode where S : IMessage
    {
        protected SchemaNode(S spec)
        {
            Spec = spec;
        }

        public S Spec { get; }

        public abstract string Key { get; }

        public abstract string Label { get; }

        public abstract string Path { get; }
    }
}
