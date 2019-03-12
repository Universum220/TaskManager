package gui;

import model.Task.Item.PropertyItem;
import model.Task.Item.PropertyItems;
import model.Task.Property.Property;
import model.Task.Property.PropertyCommon;
import model.Task.Property.PropertyValue;
import model.Utils;
import net.miginfocom.swing.MigLayout;
import resource.Icons;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


public class PropertyWindowFactory extends WindowFactory {

    private Property property;
    private JPanel panel;
    private JTextField nameTextField;
    private JComboBox<Class> classComboBox;
    private JPanel classPanel;
    private JComboBox<Object> operationComboBox;
    private JComboBox itemsComboBox;
    private DocumentListener saveDocumentAction = new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
            save();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            save();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            save();
        }
    };
    private JTextField coefficientTextField;
    private JCheckBox optionalCheckBox;
    private JTextArea descriptionPropertyTextField;
    private AbstractAction saveAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            property.setName(nameTextField.getText());
            property.setCoefficient(Double.valueOf(coefficientTextField.getText()));
            property.setOptional(optionalCheckBox.isSelected());
            property.setEnabled(enableComboBox.isEnabled());
            property.setDescription(descriptionPropertyTextField.getText());

            if (property instanceof PropertyCommon) {
                PropertyCommon propertyCommon = (PropertyCommon) property;
                propertyCommon.setOperationForChildren((PropertyCommon.Operation) operationComboBox.getSelectedItem());
            }
            if (property instanceof PropertyValue) {
                PropertyValue propertyValue = (PropertyValue) property;
                PropertyItems selectedItems = (PropertyItems) itemsComboBox.getSelectedItem();
                propertyValue.setItems(selectedItems);
                PropertyItem selectedItem = (PropertyItem) itemComboBox.getSelectedItem();
                propertyValue.setItem(selectedItem);
                selectedItem.setName(nameItemTextField.getText());
                selectedItem.setValue(Double.valueOf(valueTextField.getText()));
            }

            enableComboBox.setEnabled(property.isOptional());
        }
    };
    private JPanel commonPanel;
    private JCheckBox enableComboBox;
    private JComboBox itemComboBox;
    private JTextField valueTextField;
    private JTextField nameItemTextField;

    public void createFrame(Property property) {
        this.property = property;

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                save();
            }
        });

        AbstractAction closeListener = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save();
                frame.setVisible(false);
            }
        };
        frame.getRootPane().registerKeyboardAction(closeListener,
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW);

        super.createFrame();
    }

    @Override
    protected JPanel createEditorPanel() {
        panel = new JPanel(new MigLayout("debug"));
        commonPanel = new JPanel(new MigLayout("debug"));
        panel.add(commonPanel, "grow,push,wrap");

        // class
        commonPanel.add(new Label("Class: "));
        classComboBox = new JComboBox<>(new DefaultComboBoxModel<>(new Class[]{PropertyCommon.class, PropertyValue.class}));
        classComboBox.setSelectedItem(property.getClass());
        classComboBox.addActionListener(e -> {
            Class selectClass = (Class) classComboBox.getSelectedItem();
            save();
            if (Objects.equals(selectClass, PropertyCommon.class)) {
                property = new PropertyCommon(property);
            } else if (Objects.equals(selectClass, PropertyValue.class)) {
                property = new PropertyValue(property);
            }
            update();
            save();
        });
        commonPanel.add(classComboBox, "growx, pushx, wrap");

        // name
        commonPanel.add(new Label("Name: "));
        nameTextField = new JTextField(property.getName());
        nameTextField.getDocument().addDocumentListener(saveDocumentAction);
        commonPanel.add(nameTextField, "growx, pushx, wrap");

        // coefficient
        commonPanel.add(new Label("Coefficient: "));
        coefficientTextField = new JTextField(String.valueOf(property.getCoefficient()));
        coefficientTextField.getDocument().addDocumentListener(saveDocumentAction);
        commonPanel.add(coefficientTextField, "growx, pushx, wrap");

        // optional
        optionalCheckBox = new JCheckBox("Optional: ");
        optionalCheckBox.setSelected(property.isOptional());
        optionalCheckBox.addActionListener(saveAction);
        commonPanel.add(optionalCheckBox);

        // enabled
        enableComboBox = new JCheckBox("Enable: ");
        enableComboBox.setSelected(property.isEnabled());
        enableComboBox.addActionListener(saveAction);
        commonPanel.add(enableComboBox, "wrap");
        enableComboBox.setEnabled(property.isOptional());

        // description
        commonPanel.add(new Label("Description: "));
        descriptionPropertyTextField = new JTextArea(property.getDescription());
        descriptionPropertyTextField.setText(property.getDescription());
        descriptionPropertyTextField.getDocument().addDocumentListener(saveDocumentAction);
        JScrollPane scrollPane = new JScrollPane(descriptionPropertyTextField);
        commonPanel.add(scrollPane, "push, grow, wrap");

        // classPanel
        classPanel = new JPanel(new MigLayout("debug"));
        panel.add(classPanel, "push, grow, span");

        update();
        return panel;
    }

    private void save() {
        saveAction.actionPerformed(null);
    }


    @Override
    public void updateGui() {
        classPanel.removeAll();
        classPanel.revalidate();

        // propertyValue
        if (property instanceof PropertyValue) {
            PropertyValue propertyValue = (PropertyValue) property;

            // items
            JPanel itemsPanel = new JPanel(new MigLayout(""));
            classPanel.add(itemsPanel, "push,grow,wrap");
            itemsPanel.add(new Label("Items: "));
            List<PropertyItems> propertyItemsTemplate = Utils.getConfig().getPropertyItemsTemplate();
            itemsComboBox = new JComboBox(new DefaultComboBoxModel(propertyItemsTemplate.toArray()));
            if (propertyValue.getItems() != null) {
                itemsComboBox.setSelectedItem(propertyValue.getItems());
            } else {
                itemsComboBox.setSelectedItem(0);
            }
            itemsComboBox.addActionListener(saveAction);
            itemsPanel.add(itemsComboBox, "pushx, growx");

            addEditLineButton(Icons.ADD, itemsPanel, e -> {
                ArrayList<PropertyItem> newItem = new ArrayList<>(Collections.singletonList(new PropertyItem("New", 0)));
                propertyItemsTemplate.add(new PropertyItems("New", newItem));
                save();
            });
            addEditLineButton(Icons.DELETE, itemsPanel, e -> {
                propertyItemsTemplate.remove(itemsComboBox.getSelectedItem());
                save();
            });

            // item
            JPanel itemPanel = new JPanel(new MigLayout("fill"));
            classPanel.add(itemPanel, "push,grow,wrap");
            itemPanel.add(new Label("Item: "));
            ArrayList<PropertyItem> propertyItems = propertyValue.getItems().getItems();
            itemComboBox = new JComboBox(new DefaultComboBoxModel(propertyItems.toArray()));
            if (propertyValue.getItem() != null) {
                itemComboBox.setSelectedItem(propertyValue.getItem());
            } else {
                itemComboBox.setSelectedItem(0);
            }
            itemComboBox.addActionListener(saveAction);
            itemPanel.add(itemComboBox, "pushx, growx");

            addEditLineButton(Icons.ADD, itemPanel, e -> {
                propertyItems.add(new PropertyItem("New", 0));
                save();
            });
            addEditLineButton(Icons.DELETE, itemPanel, e -> {
                propertyItems.remove(itemsComboBox.getSelectedItem());
                save();
            });

            // name
            classPanel.add(new Label("Name: "));
            nameItemTextField = new JTextField();
            nameItemTextField.setText(propertyValue.getName());
            nameItemTextField.getDocument().addDocumentListener(saveDocumentAction);
            classPanel.add(nameItemTextField, "pushx, growx, wrap");

            // value
            classPanel.add(new Label("Value: "));
            valueTextField = new JTextField();
            valueTextField.setText(propertyValue.getValue().toString());
            valueTextField.getDocument().addDocumentListener(saveDocumentAction);
            classPanel.add(valueTextField, "pushx, growx, wrap");

        }

        // propertyCommon
        if (property instanceof PropertyCommon) {
            PropertyCommon propertyCommon = (PropertyCommon) property;

            classPanel.add(new Label("Operation: "));
            operationComboBox = new JComboBox<>(new DefaultComboBoxModel<>(PropertyCommon.Operation.values()));
            operationComboBox.setSelectedItem(propertyCommon.getOperationForChildren());
            operationComboBox.addActionListener(saveAction);
            classPanel.add(operationComboBox, "pushx, growx, wrap");
        }
    }
}
