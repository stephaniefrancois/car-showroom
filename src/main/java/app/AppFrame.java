package app;

import javax.swing.*;
import java.awt.*;

public final class AppFrame extends JFrame {

    private final CarEditorPanel panel;

    public AppFrame() throws HeadlessException {
        super("Car Showroom");

        configureSelf();

        panel = new CarEditorPanel();

//        JButton button = new JButton("Log some messages.");
        this.add(panel, BorderLayout.CENTER);

    }

    private void configureSelf() {
        setMinimumSize(new Dimension(600, 500));
        setSize(600, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setVisible(true);
    }
}
