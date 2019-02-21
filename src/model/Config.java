package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Config implements Serializable {

    private String version;
    private ArrayList<Group> groups = new ArrayList<>();
    private ArrayList<Property> properties = new ArrayList<>();


}
