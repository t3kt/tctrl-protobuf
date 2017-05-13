package net.t3kt.tctrl.gui.schematree;

import javax.swing.tree.TreeNode;
import net.t3kt.tctrl.schema.SchemaNode;

abstract class SchemaTreeNode<S extends SchemaNode> implements TreeNode {
    final S schema;

    SchemaTreeNode(S schema) {
        this.schema = schema;
    }
}
