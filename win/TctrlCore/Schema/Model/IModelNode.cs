using System;
using Google.Protobuf;

namespace Tctrl.Core.Schema.Model {
    public interface IModelNode {
        string Key { get; }
        string Label { get; }
        string Path { get; }
    }

    public interface IModelNode<out S> : IModelNode where S : ISchemaNode {
        S Schema { get; }
    }
}