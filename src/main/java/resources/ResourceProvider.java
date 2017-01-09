package resources;

import javax.swing.*;
import java.net.URL;

public final class ResourceProvider {

    public static ImageIcon getCarIcon() {
        return createIcon(getIconPath("IconCar.jpg"));
    }

    public static ImageIcon getSalesIcon() {
        return createIcon(getIconPath("IconSales.jpg"));
    }

    public static ImageIcon getReportsIcon() {
        return createIcon(getIconPath("IconReport.jpg"));
    }

    public static ImageIcon getSettingsIcon() {
        return createIcon(getIconPath("IconDBSetting.jpg"));
    }

    private static String getIconPath(String fileName) {
        return String.format("/resources/icons/%s", fileName);
    }

    private static ImageIcon createIcon(String path) {
        URL url = System.class.getResource(path);

        if (url == null) {
            System.err.println("Unable to load image: " + path);
        }

        ImageIcon icon = new ImageIcon(url);

        return icon;
    }
}
