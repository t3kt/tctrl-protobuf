using System.Collections;
using System.Collections.Generic;
using System.Linq;

namespace Tctrl.Core.Model {
    public class ModelNodeCollection<TNode> : IReadOnlyList<TNode> where TNode : IModelNode {
        private readonly IReadOnlyList<TNode> _nodes;
        private readonly IReadOnlyDictionary<string, TNode> _nodesByKey;
        internal ModelNodeCollection(IEnumerable<TNode> nodes) {
            _nodes = nodes.ToList().AsReadOnly();
            _nodesByKey = _nodes.ToDictionary(node => node.Key);
        }

        public IEnumerator<TNode> GetEnumerator() => _nodes.GetEnumerator();

        IEnumerator IEnumerable.GetEnumerator() => GetEnumerator();

        public int Count => _nodes.Count;

        public TNode this[int index] => _nodes[index];

        public TNode GetByKey(string key) => _nodesByKey[key];
    }
}