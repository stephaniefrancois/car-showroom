package app.styles;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public final class ButtonStyles {
    public static Border getDefaultButtonBorder() {
        return new JButton().getBorder();
    }

    public static Border getSelectedButtonBorder() {
        Border outter = BorderFactory.createLineBorder(Color.BLACK, 2);
        Border inner = BorderFactory.createEtchedBorder();
        return BorderFactory.createCompoundBorder(outter, inner);
    }
}
