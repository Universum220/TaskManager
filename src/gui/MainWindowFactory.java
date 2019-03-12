package gui;

import model.TaskList;
import model.Task.Task;
import resource.Icons;

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

    private TaskList taskList = new TaskList();
    private DefaultTableModel tableModel = new DefaultTableModel();
    private JTable table;
    private String filePath = "save.out";

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

        table.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                updateGui();
                save();
            }
        });

        table.setDefaultRenderer(taskList.getClass(), new DefaultTableCellRenderer() {
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
        JPanel panel = super.createToolbarPanel();

        // button add
        Action actionAdd = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Task task = new Task("Default");
                editTask(task);
            }
        };
        addButton(panel, actionAdd, Icons.ADD);

        // button remove
        Action actonRemove = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                taskList.getTasks().remove(table.getSelectedRow());
                update();
            }
        };
        addButton(panel, actonRemove, Icons.DELETE);

        // button edit
        Action actionEdit = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Task task = taskList.getTasks().get(table.getSelectedRow());
                editTask(task);
            }
        };
        addButton(panel, actionEdit, Icons.EDIT);

        // config save file
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
        addButton(panel, actionChooseFile, Icons.SETTING);

        return panel;
    }

    private void editTask(Task task) {
        TaskWindowFactory windowFactory = new TaskWindowFactory();
        windowFactory.createFrame(task);
        taskList.getTasks().add(task);
        update();
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

        ArrayList<Task> events = taskList.getTasks();
        for (Task event : events) {
            Vector<Object> rowData = new Vector<>();
            rowData.add(event.getName());
            rowData.add(event.getValue());
            tableModel.addRow(rowData);
        }
    }

    private void save() {
        try {
            FileOutputStream fos = new FileOutputStream(filePath);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(taskList);
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
            taskList = (TaskList) oin.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error load file");
            e.printStackTrace();
        }
    }

}
