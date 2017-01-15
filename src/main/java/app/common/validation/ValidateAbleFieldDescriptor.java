package app.common.validation;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public final class ValidateAbleFieldDescriptor {
    private final JLabel label;
    private final Component field;
    private final Color labelDefaultForeground;
    private final Color labelDefaultBackground;
    private final Color fieldDefaultForeground;
    private final Color fieldDefaultBackground;

    public ValidateAbleFieldDescriptor(JLabel label, Component field) {
        Objects.requireNonNull(label);
        Objects.requireNonNull(field);

        this.label = label;
        this.field = field;
        this.labelDefaultForeground = label.getForeground();
        this.labelDefaultBackground = label.getBackground();
        this.fieldDefaultForeground = field.getForeground();
        this.fieldDefaultBackground = field.getBackground();
    }

    public void markFieldAsInvalid(Color labelForeground, Color fieldBackground) {
        Objects.requireNonNull(labelForeground);
        Objects.requireNonNull(fieldBackground);
        this.label.setForeground(labelForeground);
        this.field.setBackground(fieldBackground);
    }

    public void markFieldAsValid() {
        this.label.setForeground(this.labelDefaultForeground);
        this.label.setBackground(this.labelDefaultBackground);
        this.field.setForeground(this.fieldDefaultForeground);
        this.field.setBackground(this.fieldDefaultBackground);
    }
}
