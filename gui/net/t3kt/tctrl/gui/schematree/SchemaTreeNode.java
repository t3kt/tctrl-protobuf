package net.t3kt.tctrl.gui.schematree;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.tree.TreeNode;
import net.t3kt.tctrl.schema.AppSchema;
import net.t3kt.tctrl.schema.ModuleSchema;
import net.t3kt.tctrl.schema.ParentSchemaNode;
import net.t3kt.tctrl.schema.SchemaNode;
import net.t3kt.tctrl.schema.params.NumericParamPartSchema;
import net.t3kt.tctrl.schema.params.ParamSchema;
import net.t3kt.tctrl.schema.params.VectorParamSchema;

public abstract class SchemaTreeNode<S extends SchemaNode> implements TreeNode {
    final S schema;
    private final List<SchemaTreeNode> childNodes;
    private SchemaTreeNode parentNode;
    private boolean allowsChildren = true;
    private boolean isLeaf = false;

    SchemaTreeNode(S schema) {
        this.schema = schema;
        this.childNodes = new ArrayList<>();
    }

    private SchemaTreeNode(S schema, Iterable<SchemaTreeNode> childNodes) {
        this.schema = schema;
        this.childNodes = Lists.newArrayList(childNodes);
    }

    protected void setParentNode(SchemaTreeNode parentNode) {
        this.parentNode = parentNode;
    }

    protected void addChildNode(SchemaTreeNode node) {
        childNodes.add(node);
    }

    protected void addChildNodes(Iterable<? extends SchemaTreeNode> nodes) {
        nodes.forEach(this.childNodes::add);
    }

    public S getSchema() {
        return schema;
    }

    public abstract String getInfoText();

    @Override
    public TreeNode getChildAt(int childIndex) {
        return childNodes.get(childIndex);
    }

    @Override
    public int getChildCount() {
        return childNodes.size();
    }

    @Override
    public TreeNode getParent() {
        return parentNode;
    }

    @Override
    public int getIndex(TreeNode node) {
        return childNodes.indexOf(node);
    }

    @Override
    public boolean getAllowsChildren() {
        return allowsChildren;
    }

    @Override
    public boolean isLeaf() {
        return isLeaf;
    }

    @Override
    public Enumeration children() {
        return Iterators.asEnumeration(childNodes.iterator());
    }

    static <S extends SchemaNode> Builder<S> builder(S schema) {
        return new Builder<>(schema);
    }

    static final class Builder<S extends SchemaNode> {
        private final S schema;
        private SchemaTreeNode parentNode;
        private Formatter<S> formatter;
        private Formatter<S> infoFormatter = SchemaNode::toJson;
        private boolean allowsChildren = true;
        private boolean isLeaf = false;
        private final ImmutableList.Builder<SchemaTreeNode> childNodes = ImmutableList.builder();

        private Builder(S schema) {
            this.schema = schema;
        }

        Builder<S> setParent(SchemaTreeNode parentNode) {
            this.parentNode = parentNode;
            return this;
        }

        Builder<S> setAllowsChildren(boolean allowsChildren) {
            this.allowsChildren = allowsChildren;
            return this;
        }

        Builder<S> setLeaf(boolean leaf) {
            this.isLeaf = leaf;
            return this;
        }

        Builder<S> setFormatter(Formatter<S> formatter) {
            this.formatter = formatter;
            return this;
        }

        Builder<S> setInfoFormatter(Formatter<S> formatter) {
            this.infoFormatter = formatter;
            return this;
        }

        Builder<S> addChild(SchemaTreeNode node) {
            if (node != null) {
                childNodes.add(node);
            }
            return this;
        }

        Builder<S> addChildren(Iterable<SchemaTreeNode> nodes) {
            if (nodes != null) {
                for (SchemaTreeNode node : nodes) {
                    if (node != null) {
                        childNodes.add(node);
                    }
                }
            }
            return this;
        }

        SchemaTreeNode<S> build() {
            SchemaTreeNode<S> node = new SchemaTreeNode<S>(schema, childNodes.build()) {
                @Override
                public String getInfoText() {
                    return infoFormatter.format(schema);
                }

                @Override
                public String toString() {
                    if (formatter == null) {
                        return schema.toJson();
                    }
                    return formatter.format(schema);
                }
            };
            for (SchemaTreeNode childNode : node.childNodes) {
                childNode.setParentNode(node);
            }
            node.isLeaf = isLeaf;
            node.allowsChildren = allowsChildren;
            return node;
        }
    }

    static SchemaTreeNode forAppSchema(AppSchema schema) {
        return builder(schema)
                .setAllowsChildren(true)
                .setLeaf(false)
                .setFormatter(Formatters::formatAppSchema)
                .addChild(forChildModules(schema))
                .build();
    }

    static SchemaTreeNode forChildModules(ParentSchemaNode schema) {
        if (schema.getChildModules().isEmpty()) {
            return null;
        }
        return builder(schema)
                .setAllowsChildren(true)
                .setLeaf(false)
                .setFormatter(s -> MoreObjects.toStringHelper("(modules)")
                        .add("(count)", s.getChildModules().size())
                        .toString())
                .setInfoFormatter(s -> "")
                .addChildren(((ImmutableList<ModuleSchema>) schema.getChildModules())
                        .stream()
                        .map(SchemaTreeNode::forModule)
                        .collect(Collectors.toList()))
                .build();
    }

    static SchemaTreeNode forParameters(ModuleSchema schema) {
        if (schema.getParams().isEmpty()) {
            return null;
        }
        return builder(schema)
                .setAllowsChildren(true)
                .setLeaf(false)
                .setFormatter(s -> MoreObjects.toStringHelper("(params)")
                        .add("(count)", s.getParams().size())
                        .toString())
                .setInfoFormatter(s -> "")
                .addChildren(schema.getParams()
                        .stream()
                        .map(SchemaTreeNode::forParameter)
                        .collect(Collectors.toList()))
                .build();
    }

    static SchemaTreeNode forParameter(ParamSchema schema) {
        Builder builder = builder(schema)
                .setAllowsChildren(schema.isVector())
                .setLeaf(schema.isSingle())
                .setFormatter(s -> MoreObjects.toStringHelper("Param")
                        .add("key", s.getKey())
                        .add("label", s.getLabel())
                        .toString());
        if (schema.isVector()) {
            ImmutableList<? extends NumericParamPartSchema<?>> parts = ((VectorParamSchema<?>) schema).getParts();
            for (int i = 0; i < parts.size(); i++) {
                builder.addChild(forParamPart(parts.get(i), i));
            }
        }
        return builder.build();
    }

    static SchemaTreeNode forParamPart(NumericParamPartSchema schema, int i) {
        return builder(schema)
                .setAllowsChildren(false)
                .setLeaf(true)
                .setFormatter(s -> MoreObjects.toStringHelper("ParamPart")
                        .add("index", i)
                        .add("key", schema.getKey())
                        .add("label", schema.getLabel())
                        .toString())
                .build();
    }

    static SchemaTreeNode forModule(ModuleSchema schema) {
        return builder(schema)
                .setAllowsChildren(true)
                .setLeaf(false)
                .setFormatter(s -> MoreObjects.toStringHelper("Module")
                        .add("key", s.getKey())
                        .add("label", s.getLabel())
                        .toString())
                .addChild(forChildModules(schema))
                .addChild(forParameters(schema))
                .build();
    }
}
