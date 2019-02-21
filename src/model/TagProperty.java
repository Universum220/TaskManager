package model;

public class TagProperty extends Property {

    private Boolean isValue = false;

    public Boolean getValue() {
        return isValue;
    }

    public void setValue(Boolean value) {
        isValue = value;
    }
}
