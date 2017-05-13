package net.t3kt.tctrl.gui;

import java.awt.*;
import javax.swing.*;

/**
 * Created by tekt on 5/13/17.
 */
public class SchemaInfoForm {
    private JPanel root;
    private JButton button1;
    private JTree schemaTree;

    public static void main(String[] args) {
        JFrame frame = new JFrame("SchemaInfoForm");
        frame.setContentPane(new SchemaInfoForm().root);
        frame.setPreferredSize(new Dimension(500, 600));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void createUIComponents() {
        schemaTree = new JTree();
        // TODO: place custom component creation code here
    }
}
