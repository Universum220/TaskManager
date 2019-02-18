package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Day implements Serializable {

    private ArrayList<Task> events = new ArrayList<>();

    public ArrayList<Task> getEvents() {
        return events;
    }

    public void addEvent(Task event) {
        events.add(event);
    }

    public void removeEvent(Task event){
        events.remove(event);
    }
    public void removeEvent(int index){
        events.remove(index);
    }
}



