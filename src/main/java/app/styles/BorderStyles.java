package app.styles;

import javax.swing.*;
import javax.swing.border.Border;

public final class BorderStyles {
    private static int ContentMargin = 20;
    private static int OutsidePadding = 10;
    private static int InsidePadding = 10;

    public final static Border getTitleBorder(String title) {
        return BorderFactory.createCompoundBorder(
                getPaddedBorder(OutsidePadding),
                BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder(title),
                        getPaddedBorder(InsidePadding)
                )
        );
    }

    public final static Border getTitleBorderNarrow(String title) {
        return BorderFactory.createCompoundBorder(
                getPaddedBorder(OutsidePadding / 2),
                BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder(title),
                        getPaddedBorder(InsidePadding)
                )
        );
    }

    public final static Border getContentMargin() {
        return BorderFactory.createEmptyBorder(ContentMargin,
                ContentMargin,
                ContentMargin,
                ContentMargin);
    }

    private final static Border getPaddedBorder(int padding) {
        return BorderFactory.createEmptyBorder(padding, padding, padding, padding);
    }
}
