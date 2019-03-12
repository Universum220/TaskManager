package gui;

import model.Task.Task;

public class Main {

    public static void main(String[] args) {
        MainWindowFactory windowFactory = new MainWindowFactory();
        windowFactory.createFrame();
    }

}
