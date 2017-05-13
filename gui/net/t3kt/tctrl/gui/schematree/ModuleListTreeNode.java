package net.t3kt.tctrl.gui.schematree;

import static com.google.common.collect.ImmutableList.toImmutableList;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterators;
import java.util.Enumeration;
import javax.swing.tree.TreeNode;
import net.t3kt.tctrl.schema.ModuleSchema;
import net.t3kt.tctrl.schema.ParentSchemaNode;

final class ModuleListTreeNode<S extends ParentSchemaNode> extends SchemaTreeNode<S> implements TreeNode {

    private final ImmutableList<ModuleTreeNode> moduleNodes;
    private SchemaTreeNode<S> parentNode;

    ModuleListTreeNode(S schema) {
        super(schema);
        ImmutableList<ModuleSchema> modules = schema.getChildModules();
        moduleNodes = modules.stream()
                .map(ModuleTreeNode::new)
                .collect(toImmutableList());
    }

    void setParentNode(SchemaTreeNode<S> parentNode) {
        this.parentNode = parentNode;
    }

    @Override
    public TreeNode getChildAt(int childIndex) {
        return moduleNodes.get(childIndex);
    }

    @Override
    public int getChildCount() {
        return moduleNodes.size();
    }

    @Override
    public TreeNode getParent() {
        return parentNode;
    }

    @Override
    public int getIndex(TreeNode node) {
        return moduleNodes.indexOf(node);
    }

    @Override
    public boolean getAllowsChildren() {
        return true;
    }

    @Override
    public boolean isLeaf() {
        return false;
    }

    @Override
    public Enumeration children() {
        return Iterators.asEnumeration(moduleNodes.iterator());
    }
}
