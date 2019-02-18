package gui;

import model.DefaultGradation;
import model.Task;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;


public class TaskWindowFactory extends WindowFactory {

    private Task task;
    private JPanel panel;
    private JTextArea textArea;
    private JComboBox<DefaultGradation> comboBox;
    private JComboBox<DefaultGradation> comboBox1;

    @Override
    public void createFrame() {
        task = new Task();
        super.createFrame();
    }

    @Override
    protected JPanel createEditorPanel() {
        panel = new JPanel();
        panel.setLayout(new MigLayout("debug"));

        comboBox = addEditLine("Время");
        comboBox1 = addEditLine("Количество изменений");
        comboBox1 = addEditLine("Сложность изменений");

        textArea = new JTextArea();
        panel.add(textArea, "push, grow, span 2");

        update();
        return panel;
    }

    @Override
    protected JPanel createToolbarPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        return panel;
    }

    private JComboBox<DefaultGradation> addEditLine(String label) {
        // label
        panel.add(new JLabel(label)); // panel.add(new JLabel(label),"split 2");

        // comboBox
        DefaultComboBoxModel<DefaultGradation> boxModel = new DefaultComboBoxModel<>(DefaultGradation.values());
        JComboBox<DefaultGradation> comboBox = new JComboBox<>(boxModel);
        comboBox.addActionListener(e -> update());
        panel.add(comboBox, "pushx, growx, wrap");
        return comboBox;
    }

    @Override
    public void updateGui() {
        updateReport();
    }

    private void updateReport() {
        String version = "Version generator task:" + Task.VERSION_GENERATOR;
        String mess = "test = " + comboBox.getSelectedItem().toString() + " test2=" + comboBox1.getSelectedItem().toString()
                + System.lineSeparator()
                + version;
        textArea.setText(mess);
    }

}
