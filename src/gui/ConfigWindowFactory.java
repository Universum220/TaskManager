package gui;

import model.Config;
import model.DefaultGradation;
import model.Task;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;


public class ConfigWindowFactory extends WindowFactory {

    private Config config;
    private JPanel panel;

    @Override
    public void createFrame() {
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
