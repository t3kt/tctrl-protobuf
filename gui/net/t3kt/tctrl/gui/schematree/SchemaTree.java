package net.t3kt.tctrl.gui.schematree;

import javax.swing.*;
import net.t3kt.tctrl.schema.AppSchema;

public class SchemaTree extends JTree {
    private final AppSchema appSchema;

    public SchemaTree(AppSchema appSchema) {
//        super(new AppSchemaTreeNode(appSchema));
        super(SchemaTreeNode.forAppSchema(appSchema));
        this.appSchema = appSchema;
    }

    @Override
    public String convertValueToText(Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        return super.convertValueToText(value, selected, expanded, leaf, row, hasFocus);
    }
}
