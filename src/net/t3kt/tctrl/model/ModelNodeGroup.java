package net.t3kt.tctrl.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import net.t3kt.tctrl.schema.Groupable;
import net.t3kt.tctrl.schema.TctrlSchemaProto.GroupInfo;

public final class ModelNodeGroup<N extends ModelNode<?> & Groupable> {
    private final GroupInfo spec;
    private final ImmutableSet<String> tags;
    private final ImmutableList<N> nodes;

    private ModelNodeGroup(GroupInfo spec, ImmutableList<N> nodes) {
        this.spec = spec;
        this.tags = ImmutableSet.copyOf(spec.getTagList());
        this.nodes = nodes;
    }

    public String getKey() {
        return spec.getKey();
    }

    public boolean isDefault() {
        return getKey().isEmpty();
    }

    public String getLabel() {
        return spec.getLabel();
    }

    public ImmutableSet<String> getTags() {
        return tags;
    }

    public ImmutableList<N> getNodes() {
        return nodes;
    }

    public static <N extends ModelNode<?> & Groupable> ModelNodeGroup<N> create(GroupInfo spec, Iterable<N> nodes) {
        return new ModelNodeGroup<>(spec, ImmutableList.copyOf(nodes));
    }
}
