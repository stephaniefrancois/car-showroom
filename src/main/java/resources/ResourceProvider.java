package resources;

import javax.swing.*;
import java.net.URL;

public final class ResourceProvider {

    public static ImageIcon getCarIcon() {
        return createIcon(getIconPath("icons/IconCar.jpg"));
    }

    public static ImageIcon getSalesIcon() {
        return createIcon(getIconPath("icons/IconSales.jpg"));
    }

    public static ImageIcon getReportsIcon() {
        return createIcon(getIconPath("icons/IconReport.jpg"));
    }

    public static ImageIcon getSettingsIcon() {
        return createIcon(getIconPath("icons/IconDBSetting.jpg"));
    }

    public static ImageIcon getCustomersIcon() {
        return createIcon(getIconPath("icons/customers.jpg"));
    }

    public static ImageIcon getLoginBackgroundIcon() {
        return createIcon(getIconPath("images/LoginBackground.jpg"));
    }

    private static String getIconPath(String fileName) {
        return String.format("/resources/%s", fileName);
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
