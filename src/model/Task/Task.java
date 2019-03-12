package model.Task;

import model.Task.Property.Property;
import model.Task.Property.PropertyCommon;
import model.Utils;

import java.io.Serializable;

public class Task implements Serializable {

    private String name = "Task";
    private Property benefitProperty = new PropertyCommon("Benefit", false, null, PropertyCommon.Operation.MULTIPLICATION);
    private Property resourceProperty = new PropertyCommon("Resource", false, null, PropertyCommon.Operation.MULTIPLICATION);


    public Task() {
    }

    public Task(String name) {
        this.name = name;
    }

    public Task(Task task) {
        name = task.name;
        benefitProperty = task.benefitProperty;
        resourceProperty = task.resourceProperty;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getValue() {
        return benefitProperty.getValue() / resourceProperty.getValue();
    }

    public String getVersion() {
        return Utils.getConfig().getVersion();
    }

    public Property getBenefitProperty() {
        return benefitProperty;
    }

    public Property getResourceProperty() {
        return resourceProperty;
    }

    public String getInfo() {
        return name + ": " + getValue() + ";" + "\n" + benefitProperty.getInfo() + "\n" + resourceProperty.getInfo();
    }
}