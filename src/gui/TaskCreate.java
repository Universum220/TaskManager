package gui;

import model.Task.Task;

public class TaskCreate {

    public static void main(String[] args) {
        TaskWindowFactory windowFactory = new TaskWindowFactory();
        windowFactory.createFrame(new Task("Default"));
    }

}
