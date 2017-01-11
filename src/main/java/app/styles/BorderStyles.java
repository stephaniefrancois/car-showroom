package app.styles;

import javax.swing.*;
import javax.swing.border.Border;

public final class BorderStyles {
    private static int OutsidePadding = 10;
    public final static Border OUTSIDE_BORDER = BorderFactory.createEmptyBorder(OutsidePadding, OutsidePadding, OutsidePadding, OutsidePadding);
    private static int InsidePadding = 10;
    public final static Border INSIDE_BORDER = BorderFactory.createEmptyBorder(InsidePadding, InsidePadding, InsidePadding, InsidePadding);

    public final static Border getTitleBorder(String title) {
        return BorderFactory.createCompoundBorder(
                OUTSIDE_BORDER,
                BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder(title),
                        INSIDE_BORDER
                )
        );
    }
}