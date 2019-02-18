package gui;

import javax.swing.*;
import java.awt.*;

public abstract class WindowFactory {

    protected JFrame frame;

    public void createFrame() {
        frame = new JFrame();
        JPanel panel = createPanel();
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setVisible(true);
        frame.requestFocus();
    }

    protected JPanel createPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        panel.add(createToolbarPanel(), BorderLayout.NORTH);
        panel.add(createEditorPanel(), BorderLayout.CENTER);

        return panel;
    }

    abstract JComponent createEditorPanel();

    abstract JPanel createToolbarPanel();


    public void update() {
        updateModel();
        updateGui();
    }

    public void updateGui() {

    }

    public void updateModel() {

    }

}
