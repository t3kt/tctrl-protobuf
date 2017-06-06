using System;
using System.Collections.Generic;
using Tctrl.Core.Model.Params;
using Tctrl.Schema;

namespace Tctrl.Core.Model {
    internal static class ModelImpl {

        internal static IModuleModel CreateModule(ModuleSpec schema) {
            throw new NotImplementedException();
        }

        internal static IAppModel CreateApp(AppSpec schema) {
            throw new NotImplementedException();
        }

        internal static IParamModel CreateParam(ParamSpec schema, IModuleModel parentModule) {
            throw new NotImplementedException();
        }

        internal static IGroupedModelNodeCollection<TNode> CreateGroupedModelCollection<TNode>(IEnumerable<TNode> nodes)
            where TNode : IGroupableNode {
            throw new NotImplementedException();
        }

        internal static IModelNodeGroup<TNode> CreateNodeGroup<TNode>(GroupInfo info, IEnumerable<TNode> nodes)
            where TNode : IGroupableNode {
            throw new NotImplementedException();
        }

    }
}