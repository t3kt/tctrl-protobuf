package net.t3kt.tctrl.gui;

import java.awt.*;
import javax.swing.*;
import net.t3kt.tctrl.gui.schematree.SchemaTree;
import net.t3kt.tctrl.gui.schematree.SchemaTreeNode;
import net.t3kt.tctrl.schema.AppSchema;

public class SchemaInfoDialog extends JDialog {
    private final AppSchema appSchema;
    private JPanel contentPane;
    private JTree schemaTree;
    private JTextPane nodeInfoPane;
    private JButton buttonOK;

    private SchemaInfoDialog(AppSchema appSchema) {
        this.appSchema = appSchema;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        schemaTree.addTreeSelectionListener(e -> {
            Object treeNode = e.getNewLeadSelectionPath().getLastPathComponent();
            nodeInfoPane.setText(((SchemaTreeNode) treeNode).getInfoText());
        });
    }

    public static void showForSchema(AppSchema appSchema) {
        SchemaInfoDialog dialog = new SchemaInfoDialog(appSchema);
        dialog.setTitle("tctrl schema info: [" + appSchema.getLabel() + "]");
        dialog.setPreferredSize(new Dimension(800, 600));
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        dialog.pack();
        dialog.setVisible(true);
    }

    private void createUIComponents() {
        schemaTree = new SchemaTree(appSchema);
    }
}
