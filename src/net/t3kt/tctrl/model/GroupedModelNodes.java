package net.t3kt.tctrl.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.util.List;
import net.t3kt.tctrl.schema.Groupable;
import net.t3kt.tctrl.schema.TctrlSchemaProto.GroupInfo;

public final class GroupedModelNodes<N extends ModelNode<?> &Groupable> {
    private final ImmutableMap<String, ModelNodeGroup<N>> groups;
    private final ImmutableList<N> nodes;

    private GroupedModelNodes(ImmutableMap<String, ModelNodeGroup<N>> groups, ImmutableList<N> nodes) {
        this.groups = groups;
        this.nodes = nodes;
    }

    public ImmutableList<N> getAllNodes() {
        return nodes;
    }

    public ImmutableMap<String, ModelNodeGroup<N>> getGroups() {
        return groups;
    }

    public ModelNodeGroup<N> getGroup(String key) {
        return groups.get(key);
    }

    public static final class Builder<N extends ModelNode<?> &Groupable> {
        private ImmutableList.Builder<GroupInfo> groupSpecs;

        public Builder<N> withDefinedGroups(List<GroupInfo> groups) {
            throw new RuntimeException("NOT IMPLEMENTED");
        }

        public GroupedModelNodes<N> build() {
            throw new RuntimeException("NOT IMPLEMENTED");
        }
    }
}
