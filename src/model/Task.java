package model;

import java.io.Serializable;

public class Task implements Serializable {

    public static String VERSION_GENERATOR = "0.1";

    private String name = "";
    private int value = 0;
    private Resource resource = null;
    private Config config;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }
}