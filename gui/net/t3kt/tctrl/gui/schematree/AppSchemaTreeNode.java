package net.t3kt.tctrl.gui.schematree;

import java.util.Enumeration;
import javax.swing.tree.TreeNode;
import net.t3kt.tctrl.schema.AppSchema;

final class AppSchemaTreeNode extends SchemaTreeNode<AppSchema> {
    AppSchemaTreeNode(AppSchema schema) {
        super(schema);
        throw new RuntimeException("NOT IMPLEMENTED");
    }

    @Override
    public TreeNode getChildAt(int childIndex) {
        return null;
    }

    @Override
    public int getChildCount() {
        return 0;
    }

    @Override
    public TreeNode getParent() {
        return null;
    }

    @Override
    public int getIndex(TreeNode node) {
        return 0;
    }

    @Override
    public boolean getAllowsChildren() {
        return false;
    }

    @Override
    public boolean isLeaf() {
        return false;
    }

    @Override
    public Enumeration children() {
        return null;
    }
}
