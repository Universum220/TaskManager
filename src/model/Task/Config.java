package model.Task;

import model.Task.Item.PropertyItem;
import model.Task.Item.PropertyItems;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Config implements Serializable {

    private String version = "0.1";
    private List<Task> taskTemplate;
    private List<PropertyItems> propertyItemsTemplate;

    public Config() {
        // task template
        taskTemplate = new ArrayList<>();
        Task task = new Task("Default");
        taskTemplate.add(task);

        // items template
        ArrayList<PropertyItem> items = new ArrayList<>();
        items.add(new PropertyItem("false", 0));
        items.add(new PropertyItem("true", 1));
        PropertyItems propertyItems = new PropertyItems("Default", items);
        propertyItemsTemplate = new ArrayList<>();
        propertyItemsTemplate.add(propertyItems);
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<Task> getTaskTemplate() {
        return taskTemplate;
    }

    public void setTaskTemplate(List<Task> taskTemplate) {
        this.taskTemplate = taskTemplate;
    }

    public List<PropertyItems> getPropertyItemsTemplate() {
        return propertyItemsTemplate;
    }

    public void setPropertyItemsTemplate(List<PropertyItems> propertyItemsTemplate) {
        this.propertyItemsTemplate = propertyItemsTemplate;
    }
}
