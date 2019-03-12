package gui;

import model.Task.Task;
import resource.Icons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class WindowFactory {

    protected JDialog frame = new JDialog();

    public void createFrame() {
        frame.setModal(true);
        JPanel panel = createPanel();
        frame.setContentPane(panel);
        frame.setSize(600, 400);
        frame.setLocation(-700, 100);
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

    protected JPanel createToolbarPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        addButton(panel, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update();
            }
        }, Icons.RELOAD);
        return panel;
    }


    protected void addButton(JPanel panel, Action actionAdd, Icon icon) {
        JButton buttonAdd = new JButton(actionAdd);
        buttonAdd.setSize(new Dimension(24, 24));
        buttonAdd.setPreferredSize(new Dimension(24, 24));
        buttonAdd.setIcon(icon);
        buttonAdd.setFocusable(false);
        panel.add(buttonAdd);
    }

    public void update() {
        updateModel();
        updateGui();
    }

    protected void addEditLineButton(ImageIcon icon, JPanel parentPanel, ActionListener action) {
        JButton addChildrenPropButton = new JButton(icon);
        addChildrenPropButton.setMaximumSize(new Dimension(16, 16));
        addChildrenPropButton.addActionListener(action);
        parentPanel.add(addChildrenPropButton);
    }

    public void updateGui() {

    }

    public void updateModel() {

    }

}
