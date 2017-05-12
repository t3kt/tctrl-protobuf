package net.t3kt.tctrl.model;

import com.google.common.collect.ImmutableList;
import java.util.List;
import net.t3kt.tctrl.schema.Groupable;
import net.t3kt.tctrl.schema.TctrlSchemaProto.GroupInfo;

public class ModelNodeGroup<N extends ModelNode<?> & Groupable> {
    private final GroupInfo spec;
    private final ImmutableList<N> nodes;

    private ModelNodeGroup(GroupInfo spec, ImmutableList<N> nodes) {
        this.spec = spec;
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

    public List<String> getTags() {
        return spec.getTagList();
    }

    public ImmutableList<N> getNodes() {
        return nodes;
    }

    public static <N extends ModelNode<?> & Groupable> Builder<N> builder(GroupInfo spec) {
        return new Builder<>(spec);
    }

    static final class Builder<N extends ModelNode<?> & Groupable> {
        private final GroupInfo spec;
        private final ImmutableList.Builder<N> nodes = ImmutableList.builder();

        private Builder(GroupInfo spec) {
            this.spec = spec;
        }

        public Builder<N> add(N node) {
            nodes.add(node);
            return this;
        }

        public Builder<N> addAll(Iterable<? extends N> nodes) {
            this.nodes.addAll(nodes);
            return this;
        }

        public ModelNodeGroup<N> build() {
            return new ModelNodeGroup<>(spec, nodes.build());
        }
    }
}
