package app.cars;

import javax.swing.*;
import java.awt.*;

public final class CarsPanel extends JPanel {
    public CarsPanel() {
        setLayout(new BorderLayout());
        add(new Label("I am a cars panel :)"));
    }
}
