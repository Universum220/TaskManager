package resource;

import sun.awt.SunToolkit;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.Collections;

public class Icons {

    public static ImageIcon ADD = createImageIcon("png/add-plus-button.png");
    public static ImageIcon DELETE = createImageIcon("png/horizontal-line-remove-button.png");
    public static ImageIcon EDIT = createImageIcon("png/create-new-pencil-button.png");
    public static ImageIcon SAVE = createImageIcon("png/check-symbol.png");

    public static ImageIcon RELOAD = createImageIcon("png/refresh-button.png");
    public static ImageIcon SETTING = createImageIcon("png/settings-cogwheel-button.png");
    public static ImageIcon DEBUG = createImageIcon("png/bug-report-button.png");

    private static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = Icons.class.getResource(path);
        if (imgURL != null) {
            ImageIcon imageIcon = new ImageIcon(imgURL);
            return new ImageIcon(SunToolkit.getScaledIconImage(Collections.singletonList(imageIcon.getImage()), 12, 12));
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

}
