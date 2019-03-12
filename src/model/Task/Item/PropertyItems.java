package model.Task.Item;

import java.util.ArrayList;

public class PropertyItems {

    private String name;
    private ArrayList<PropertyItem> items;
    private String description;

    public PropertyItems(String name, ArrayList<PropertyItem> items) {
        this.name = name;
        this.items = items;
    }

    public ArrayList<PropertyItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<PropertyItem> items) {
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
