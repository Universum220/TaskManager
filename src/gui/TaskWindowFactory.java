package gui;

import model.Task.Config;
import model.Task.Item.PropertyItem;
import model.Task.Property.Property;
import model.Task.Property.PropertyCommon;
import model.Task.Property.PropertyValue;
import model.Task.Task;
import model.Utils;
import net.miginfocom.swing.MigLayout;
import resource.Icons;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;


public class TaskWindowFactory extends WindowFactory {

    private Task task;
    private JTextArea resultArea;
    private JTextArea additionalCommentArea;
    private boolean editMode = true;
    private JPanel propertysPanel;
    private int level;
    private JPanel panel;
    private Config config;
    private JPanel templatePanel;

    public void createFrame(Task task) {
        this.task = task;
        super.createFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    protected JPanel createEditorPanel() {
        panel = new JPanel();
        panel.setLayout(new MigLayout("debug"));

        // templateTask
        templatePanel = new JPanel(new MigLayout("debug"));
        panel.add(templatePanel, "wrap");

        // propertysPanel
        propertysPanel = new JPanel();
        propertysPanel.setLayout(new MigLayout("fill,debug"));
        panel.add(propertysPanel, "push, grow, wrap");

        // add comment
        additionalCommentArea = new JTextArea();
        panel.add(additionalCommentArea, "push, grow, span 2, wrap");

        resultArea = new JTextArea();
        resultArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                updateReport();
            }

            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                updateReport();
            }

            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                updateReport();
            }
        });
        panel.add(resultArea, "push, grow, span 2");

        config = Utils.getConfig();

        update();
        return panel;
    }

    @Override
    protected JPanel createToolbarPanel() {
        JPanel toolbarPanel = super.createToolbarPanel();

        addButton(toolbarPanel,
                new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ConfigWindowFactory configWindowFactory = new ConfigWindowFactory();
                        configWindowFactory.createFrame(new Task());
                        update();
                    }
                },
                Icons.SETTING);

        addButton(toolbarPanel,
                new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        editMode = !editMode;
                        update();
                    }
                },
                Icons.DEBUG);

        return toolbarPanel;
    }

    private void addEditLine(Property property) {
        level++;

        JPanel propertyPanel = new JPanel(new MigLayout("fill, debug"));
        propertysPanel.add(propertyPanel, "growx, pushx, x " + (level - 1) * 20 + ", wrap");

        JCheckBox enableCheckBox = null;
        if (property.isOptional()) {
            enableCheckBox = new JCheckBox();
            enableCheckBox.setSelected(property.isEnabled());
            final JCheckBox finalEnableCheckBox = enableCheckBox;
            enableCheckBox.addActionListener(e -> {
                property.setEnabled(finalEnableCheckBox.isSelected());
                update();
            });
            propertyPanel.add(enableCheckBox);
        }

        propertyPanel.add(new JLabel(property.getName() + " [" + property.getValue() + "](" + property.getCoefficient() + ")"));

        // PropertyValue
        if (property instanceof PropertyValue) {
            PropertyValue propertyValue = (PropertyValue) property;
            DefaultComboBoxModel<PropertyItem> model = new DefaultComboBoxModel(propertyValue.getItems().getItems().toArray());
            JComboBox<PropertyItem> comboBox = new JComboBox<>(model);
            comboBox.setSelectedItem(((PropertyValue) property).getItem());
            comboBox.addActionListener(e -> {
                propertyValue.setItem((PropertyItem) comboBox.getSelectedItem());
                update();
            });
            propertyPanel.add(comboBox, "pushx, growx");
        }

        // edit button
        if (editMode) {
            if (property instanceof PropertyCommon) {
                addEditLineButton(Icons.ADD, propertyPanel, e -> {
                    new PropertyWindowFactory().createFrame(new PropertyCommon("", false, ((PropertyCommon) property), PropertyCommon.Operation.SUM));
                    update();
                });

                addEditLineButton(Icons.DELETE, propertyPanel, e -> {
                    property.remove();
                    update();
                });
            }

            addEditLineButton(Icons.EDIT, propertyPanel, e -> {
                new PropertyWindowFactory().createFrame(property);
                update();
            });
        }

        // enabled
        Arrays.stream(propertyPanel.getComponents()).forEach(component -> component.setEnabled(property.isEnabled()));
        if (enableCheckBox != null) {
            enableCheckBox.setEnabled(property.getParentProperty() == null || property.getParentProperty().isEnabled());
        }

        // children
        if (property instanceof PropertyCommon) {
            PropertyCommon propertyCommon = (PropertyCommon) property;
            propertyCommon.getChildrenProperty().forEach(this::addEditLine);
        }

        level--;
    }

    @Override
    public void updateGui() {
        // templateTask
        templatePanel.removeAll();
        templatePanel.repaint();

        templatePanel.add(new JLabel("Task template:"));

        JComboBox<Task> templateCompoBox = new JComboBox(new DefaultComboBoxModel<>(Utils.getConfig().getTaskTemplate().toArray()));
        templateCompoBox.setSelectedItem(task);
        templateCompoBox.addActionListener(e -> {
            task = (Task) templateCompoBox.getModel().getElementAt(templateCompoBox.getSelectedIndex());
            update();
        });
        templatePanel.add(templateCompoBox, "pushx, growx");

        addEditLineButton(Icons.ADD, templatePanel, e -> {
            config.getTaskTemplate().add(new Task(task));
            update();
        });
        addEditLineButton(Icons.DELETE, templatePanel, e -> {
            config.getTaskTemplate().remove(templateCompoBox.getSelectedItem());
            update();
        });
        addEditLineButton(Icons.SAVE, templatePanel, e -> {
            config.getTaskTemplate().set(templateCompoBox.getSelectedIndex(), task);
            update();
        });

        // name
        templatePanel.add(new JLabel("Name:"), "newline");
        JTextField nameTextField = new JTextField();
        nameTextField.setText(task.getName());
        nameTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                task.setName(nameTextField.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                task.setName(nameTextField.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                task.setName(nameTextField.getText());
            }
        });
        templatePanel.add(nameTextField, "pushx, growx, span");

        // propertysPanel
        propertysPanel.removeAll();
        propertysPanel.repaint();

        addEditLine(task.getBenefitProperty());
        addEditLine(task.getResourceProperty());
        updateReport();
    }

    private void updateReport() {
        String version = "Version generator task: " + task.getVersion() + "\n";
        String additionalComment = "Comment: " + additionalCommentArea.getText() + "\n";
        String info = task.getInfo();
        resultArea.setText(version + "\n" + additionalComment + "\n" + info);
    }

}
