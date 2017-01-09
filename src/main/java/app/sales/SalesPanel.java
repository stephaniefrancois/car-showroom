package app.sales;

import javax.swing.*;
import java.awt.*;

public final class SalesPanel extends JPanel {
    public SalesPanel() {
        setLayout(new BorderLayout());
        add(new Label("I am a sales panel :)"));
    }
}
