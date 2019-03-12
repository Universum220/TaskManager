package model.Task.Property;

import java.io.Serializable;

public class Property implements Serializable {

    protected String name;
    protected String description;
    private boolean optional;
    private boolean enabled = true;
    private PropertyCommon parentProperty;
    protected double coefficient = 1;


    protected Property(String name, boolean optional, PropertyCommon parentProperty) {
        this.name = name;
        this.optional = optional;
        if (parentProperty != null) {
            setParentProperty(parentProperty);
        }
    }

    public Property(Property property) {
        name = property.getName();
        optional = property.isOptional();
        if (property.getParentProperty() != null) {
            setParentProperty(property.getParentProperty());
        }
        property.getParentProperty().getChildrenProperty().remove(property);
    }


    public void remove() {
        parentProperty.getChildrenProperty().remove(this);
        parentProperty = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOptional() {
        return optional;
    }

    public void setOptional(boolean optional) {
        this.optional = optional;
    }

    public PropertyCommon getParentProperty() {
        return parentProperty;
    }

    public void setParentProperty(PropertyCommon parentProperty) {
        this.parentProperty = parentProperty;
        parentProperty.getChildrenProperty().add(this);
    }

    public String getPropertyName() {
        return getClass().getSimpleName();
    }

    public String getInfo() {
        return "";
    }

    public Double getValue() {
        return null;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }

    public boolean isEnabled() {
        if (parentProperty != null) {
            return parentProperty.isEnabled() && enabled;
        }
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
