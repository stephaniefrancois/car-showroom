package app.styles;

import java.awt.*;

public final class ComponentSizes {
    private static final int APP_WIDTH = 1024;
    private static final int APP_HEIGHT = 768;
    private static final int TOOLBAR_PANEL_HEIGHT = 150;

    public static final Dimension APP_SIZE = new Dimension(APP_WIDTH,
            APP_HEIGHT);

    public static final Dimension MINIMUM_TOOLBAR_SIZE = new Dimension(APP_WIDTH,
            TOOLBAR_PANEL_HEIGHT);

    public static final Dimension MINIMUM_CONTENT_PANEL_SIZE = new Dimension(APP_WIDTH,
            APP_HEIGHT - MINIMUM_TOOLBAR_SIZE.height);

    private static final int SEARCH_PANEL_HEIGHT = 60;
    private static final int CAR_DETAILS_WIDTH = 600;
    public static final Dimension MINIMUM_CAR_SEARCH_PANEL_SIZE = new Dimension(
            APP_WIDTH - CAR_DETAILS_WIDTH,
            SEARCH_PANEL_HEIGHT);
    public static final Dimension MINIMUM_CAR_LIST_PANEL_SIZE = new Dimension(
            APP_WIDTH - CAR_DETAILS_WIDTH,
            APP_HEIGHT - (TOOLBAR_PANEL_HEIGHT + SEARCH_PANEL_HEIGHT));

    public static final Dimension MINIMUM_CAR_DETAILS_PANEL_SIZE = new Dimension(
            CAR_DETAILS_WIDTH,
            APP_HEIGHT - TOOLBAR_PANEL_HEIGHT);
}
