using Tctrl.Core.Schema;

namespace Tctrl.Core.Model {
    public interface IModelNode {
        string Key { get; }
        string Label { get; }
        string Path { get; }
    }

    public interface IModelNode<out S> : IModelNode where S : ISchemaNode {
        S Schema { get; }
    }
}