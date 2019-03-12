package gui;

import model.Task.Config;
import model.Task.Task;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;


public class ConfigWindowFactory extends WindowFactory {

    private Config config;
    private JPanel panel;

    public void createFrame(Task task) {
        config = new Config();
        super.createFrame();
    }

    @Override
    protected JPanel createEditorPanel() {
        panel = new JPanel();
        panel.setLayout(new MigLayout("debug"));
        update();
        return panel;
    }

}
