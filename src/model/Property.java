package model;

import java.io.Serializable;

public class Property implements Serializable {

    private String title;
    private boolean optional;
    private Group group;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isOptional() {
        return optional;
    }

    public void setOptional(boolean optional) {
        this.optional = optional;
    }
}
