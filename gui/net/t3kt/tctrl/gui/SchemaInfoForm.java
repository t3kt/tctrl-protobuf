package net.t3kt.tctrl.gui;

import java.awt.*;
import javax.swing.*;
import net.t3kt.tctrl.gui.schematree.SchemaTree;
import net.t3kt.tctrl.schema.AppSchema;

public class SchemaInfoForm {
    private JPanel root;
    private JButton button1;
    private JTree schemaTree;
    private final AppSchema appSchema;

    public SchemaInfoForm(AppSchema appSchema) {
        this.appSchema = appSchema;
    }

    public static void showForSchema(AppSchema appSchema) {
        SchemaInfoForm form = new SchemaInfoForm(appSchema);
        JFrame frame = new JFrame("tctrl schema info: [" + appSchema.getLabel() + "]");
        frame.setContentPane(form.root);
        frame.setPreferredSize(new Dimension(500, 600));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void createUIComponents() {
        schemaTree = new SchemaTree(appSchema);
        // TODO: place custom component creation code here
    }
}
