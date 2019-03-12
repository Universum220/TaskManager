package model.Task.Property;

import java.util.ArrayList;

public class PropertyCommon extends Property {

    private ArrayList<Property> childrenProperty = new ArrayList<>();
    private Operation operationForChildren;


    public PropertyCommon(String name, boolean optional, PropertyCommon parentProperty, Operation operationForChildren) {
        super(name, optional, parentProperty);
        this.operationForChildren = operationForChildren;
    }

    public PropertyCommon(Property property) {
        super(property);
    }

    public ArrayList<Property> getChildrenProperty() {
        return childrenProperty;
    }

    public Double getValue() {
        switch (operationForChildren) {
            case SUM:
                return coefficient * childrenProperty.stream().mapToDouble(Property::getValue).sum();
            case MULTIPLICATION:
                return coefficient * childrenProperty.stream().mapToDouble(Property::getValue).reduce(1, (a, b) -> a * b);
        }
        return null;
    }

    public String getInfo() {
        var ref = new Object() {
            String mess = name + ": [" + getValue() + "](" + coefficient + "){" + operationForChildren.name() + "}"
                    + ((getDescription() != null && !getDescription().isEmpty()) ? (" - " + getDescription()) : "");
        };
        childrenProperty.forEach(property -> ref.mess += "\n" + property.getInfo());
        return ref.mess;
    }

    public Operation getOperationForChildren() {
        return operationForChildren;
    }

    public void setOperationForChildren(Operation operationForChildren) {
        this.operationForChildren = operationForChildren;
    }

    public enum Operation {
        SUM,
        MULTIPLICATION
    }

}
