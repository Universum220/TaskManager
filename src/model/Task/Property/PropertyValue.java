package model.Task.Property;

import model.Task.Item.PropertyItem;
import model.Task.Item.PropertyItems;

public class PropertyValue extends Property {

    private PropertyItem item;
    private PropertyItems items;


    public PropertyValue(String name, boolean optional, PropertyCommon parentProperty) {
        super(name, optional, parentProperty);
    }

    public PropertyValue(Property property) {
        super(property);
    }


    @Override
    public Double getValue() {
        return coefficient * item.getValue();
    }

    public PropertyItem getItem() {
        return item;
    }

    public void setItem(PropertyItem item) {
        this.item = item;
    }

    public PropertyItems getItems() {
        return items;
    }

    public void setItems(PropertyItems items) {
        this.items = items;
    }

    @Override
    public String getInfo() {
        return name + ": " + getItem().getName()
                + (getDescription() != null && !getDescription().isEmpty() ? (" - " + getItem().getDescription()) : "")
                + " [" + getValue() + "](" + coefficient + ")"
                + (getDescription() != null && !getDescription().isEmpty() ? (" - " + getDescription()) : "");
    }
}
