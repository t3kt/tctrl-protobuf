package net.t3kt.tctrl.model.impl;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ListMultimap;
import java.util.HashMap;
import java.util.Map;
import net.t3kt.tctrl.model.ModelNode;
import net.t3kt.tctrl.model.ModelNodeGroup;
import net.t3kt.tctrl.schema.Groupable;
import net.t3kt.tctrl.schema.TctrlSchemaProto.GroupInfo;

final class ModelNodeGroupList<N extends ModelNode<?> & Groupable> {
    private final ImmutableMap<String, ModelNodeGroup<N>> groups;

    private ModelNodeGroupList(ImmutableMap<String, ModelNodeGroup<N>> groups) {
        this.groups = groups;
    }

    ImmutableCollection<ModelNodeGroup<N>> getGroups() {
        return groups.values();
    }

    ModelNodeGroup<N> getGroup(String key) {
        return groups.get(key);
    }

    static <N extends ModelNode<?> &Groupable> Builder<N> builder() {
        return new Builder<>();
    }

    static final class Builder<N extends ModelNode<?> & Groupable> {
        private final Map<String, GroupInfo> groupSpecs = new HashMap<>();
        private final ListMultimap<String, N> groupNodes = ArrayListMultimap.create();

        private Builder() {}

        Builder<N> addGroup(GroupInfo spec) {
            groupSpecs.put(spec.getKey(), spec);
            return this;
        }

        Builder<N> addNode(N node) {
            String key = node.getGroup();
            if (key == null) {
                key = "";
            }
            if (!groupSpecs.containsKey(key)) {
                groupSpecs.put(key, GroupInfo.newBuilder()
                        .setKey(key)
                        .setLabel(key.isEmpty() ? "(default)" : key)
                        .build());
            }
            groupNodes.put(key, node);
            return this;
        }

        ModelNodeGroupList<N> build() {
            ImmutableMap.Builder<String, ModelNodeGroup<N>> groups = ImmutableMap.builder();
            for (GroupInfo group : groupSpecs.values()) {
                groups.put(group.getKey(), ModelNodeGroup.create(group, groupNodes.get(group.getKey())));
            }
            return new ModelNodeGroupList<>(groups.build());
        }
    }
}
