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
        float fontSize = defaultFont.getSize() * 1.2f;
        return defaultFont.deriveFont(fontSize);
    }
}
