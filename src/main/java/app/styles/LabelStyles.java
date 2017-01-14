package app.styles;

import javax.swing.*;
import java.awt.*;

public class LabelStyles {
    public static Font getFontForHeaderLevelOne() {
        Font defaultFont = new JLabel().getFont();
        float fontSize = defaultFont.getSize() * 2;
        return defaultFont.deriveFont(fontSize);
    }

    public static Font getFontForFieldLabel() {
        Font defaultFont = new JLabel().getFont();
        float fontSize = defaultFont.getSize();
        return defaultFont.deriveFont(fontSize);
    }

    public static Color getBackgroundColorForFieldLabel() {
        return new JLabel().getBackground();
    }

    public static Color getForegroundColorForInvalidFieldLabel() {
        return Color.red;
    }

    public static Color getForegroundColorForInvalidField() {
        return Color.red;
    }
}
