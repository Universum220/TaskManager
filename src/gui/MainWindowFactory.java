package gui;

import model.Day;
import model.Task;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Vector;

public class MainWindowFactory extends WindowFactory {

    private Day day = new Day();
    private DefaultTableModel tableModel = new DefaultTableModel();
    private JTable table;
    private String filePath = "save.out";
    private JFrame frame;

    @Override
    public void createFrame() {
        load();
        super.createFrame();
    }

    @Override
    protected JScrollPane createEditorPanel() {
        table = new JTable();
        JScrollPane panel = new JScrollPane(table);

        table.setModel(tableModel);
        tableModel.addColumn("Name");
        tableModel.addColumn("Value");
        tableModel.addColumn("model.Resource");

        table.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                updateGui();
                save();
            }
        });

        table.setDefaultRenderer(day.getClass(), new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                return cell;
            }
        });

        updateModel();
        return panel;
    }

    @Override
    protected JPanel createToolbarPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        // button add
        Action actionAdd = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                day.addEvent(new Task());
                update();
            }
        };
        addButton(panel, actionAdd, "+");

        // button remove
        Action actonRemove = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                day.removeEvent(table.getSelectedRow());
                update();
            }
        };
        addButton(panel, actonRemove, "-");

        // button remove
        Action actionChooseFile = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final JFileChooser fc = new JFileChooser();
                if (fc.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                    filePath = fc.getSelectedFile().getPath();
                    load();
                    update();
                }
            }
        };
        addButton(panel, actionChooseFile, "F");

        return panel;
    }

    private void addButton(JPanel panel, Action actionAdd, String text) {
        JButton buttonAdd = new JButton(actionAdd);
        buttonAdd.setSize(new Dimension(24, 24));
        buttonAdd.setPreferredSize(new Dimension(24, 24));
        buttonAdd.setText(text);
        panel.add(buttonAdd);
    }

    @Override
    public void update() {
        super.update();
        save();
    }

    @Override
    public void updateGui() {

    }

    @Override
    public void updateModel() {
        tableModel.setRowCount(0);

        ArrayList<Task> events = day.getEvents();
        for (Task event : events) {
            Vector<Object> rowData = new Vector<>();
            rowData.add(event.getName());
            rowData.add(event.getValue());
            rowData.add(event.getResource());
            tableModel.addRow(rowData);
        }
    }

    private void save() {
        try {
            FileOutputStream fos = new FileOutputStream(filePath);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            for (Task event : day.getEvents()) {
                Vector vector = tableModel.getDataVector().get(0);

                String name = (String) vector.get(0);
                event.setName(name);

                Integer value = vector.get(1) instanceof String
                        ? Integer.valueOf(((String) vector.get(1)))
                        : (Integer) vector.get(1);
                event.setValue(value);
            }

            oos.writeObject(day);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            System.err.println("Error save file");
            e.printStackTrace();
        }
    }

    private void load() {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }

            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream oin = new ObjectInputStream(fis);
            day = (Day) oin.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error load file");
            e.printStackTrace();
        }
    }

}
