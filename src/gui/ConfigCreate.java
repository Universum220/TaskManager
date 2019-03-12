package gui;

import model.Task.Task;

public class ConfigCreate {

    public static void main(String[] args) {
        ConfigWindowFactory windowFactory = new ConfigWindowFactory();
        windowFactory.createFrame(new Task("Default"));
    }

}
