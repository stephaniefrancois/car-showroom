package app;

import app.toolbar.ToolbarPanel;

import javax.swing.*;
import java.awt.*;

public final class AppFrame extends JFrame {

    private final ToolbarPanel toolbar;
    private final ContentPanel content;
    private final String toolbarItemToSelectKey = "ViewCars";

    public AppFrame() throws HeadlessException {
        super("Car Showroom");

        configureSelf();
        toolbar = new ToolbarPanel();
        content = new ContentPanel();
        this.add(toolbar, BorderLayout.NORTH);
        this.add(content, BorderLayout.CENTER);

        toolbar.addListener(e -> {
            switch (e.getMenuItemKey()) {
                case "ViewCars": {
                    content.navigateToCars();
                    break;
                }
                case "ViewSales": {
                    content.navigateToSales();
                    break;
                }
                case "ViewReports": {
                    content.navigateToReports();
                    break;
                }
                case "ViewSettings": {
                    content.navigateToSettings();
                    break;
                }
                default: {
                    System.out.println("Don't know how to navigate to '" + e.getMenuItemKey() + "'");
                }
            }
        });

        toolbar.setActiveToolbarItem(toolbarItemToSelectKey);
    }

    private void configureSelf() {
        setMinimumSize(new Dimension(1024, 768));
        setSize(getMinimumSize());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setVisible(true);
    }
}