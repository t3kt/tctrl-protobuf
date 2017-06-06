using System.Collections.Generic;
using Google.Protobuf;
using Tctrl.Core.Model.Params;
using Tctrl.Schema;

namespace Tctrl.Core.Model {
    public interface IModelNode {

        string Key { get; }
        string Label { get; }
        string Path { get; }

    }

    public interface IGroupableNode : IModelNode {

        string Group { get; }

    }

    public interface IModelNode<out TSchema> : IModelNode
        where TSchema : IMessage {

        TSchema Schema { get; }

    }

    public interface IParentModelNode<out TSchema> : IModelNode<TSchema>
        where TSchema : IMessage {

        IGroupedModelNodeCollection<IModuleModel> ChildModules { get; }

    }

    public interface IModuleModel : IParentModelNode<ModuleSpec>, IGroupableNode {

        IGroupedModelNodeCollection<IParamModel> Params { get; }
        IGroupedModelNodeCollection<ISingleParamModel> FlatParams { get; }

    }

    public interface IAppModel : IParentModelNode<AppSpec> { }

    public interface IModelNodeCollection<out TNode> : IReadOnlyList<TNode> {

        TNode this[string key] { get; }

    }

    public interface IModelNodeGroup<out TNode>
        where TNode : IGroupableNode {

        GroupInfo Info { get; }

        IReadOnlyList<TNode> Nodes { get; }

    }

    public interface IGroupedModelNodeCollection<TNode> : IModelNodeCollection<TNode>
        where TNode : IGroupableNode {

        IReadOnlyList<IModelNodeGroup<TNode>> Groups { get; }
        IReadOnlyDictionary<string, IModelNodeGroup<TNode>> GroupsByKey { get; }

    }
}