package app.common;

import app.styles.LabelStyles;

import javax.swing.*;
import java.awt.*;

public class ControlsHelper {
    public final static JPanel addControlWithLabel(Component componentToAdd,
                                                   String label,
                                                   int rowIndex,
                                                   Insets padding,
                                                   GridBagConstraints formGridConfig,
                                                   JPanel controlsContainer) {
        return addControlWithLabel(componentToAdd, label, rowIndex, 0,
                padding, formGridConfig, controlsContainer);
    }

    public final static JPanel addControlWithLabel(Component componentToAdd,
                                                   String label,
                                                   int rowIndex,
                                                   int columnIndex,
                                                   Insets padding,
                                                   GridBagConstraints formGridConfig,
                                                   JPanel controlsContainer) {

        JLabel componentLabel = new JLabel(label);
        componentLabel.setLabelFor(componentToAdd);
        componentLabel.setFont(LabelStyles.getFontForFieldLabel());
        componentToAdd.setFont(LabelStyles.getFontForFieldLabel());

        int columnMultiplier = 2;

        formGridConfig.gridy = rowIndex;
        formGridConfig.gridx = columnIndex * columnMultiplier;
        formGridConfig.weightx = 0.2;
        formGridConfig.weighty = 0.1;

        formGridConfig.fill = GridBagConstraints.NONE;
        formGridConfig.anchor = GridBagConstraints.LINE_END;
        formGridConfig.insets = padding;
        controlsContainer.add(componentLabel, formGridConfig);

        formGridConfig.gridx = columnIndex * columnMultiplier + 1;
        formGridConfig.weightx = 0.4;
        formGridConfig.insets = padding;
        formGridConfig.anchor = GridBagConstraints.LINE_START;
        formGridConfig.gridheight = 1;
        controlsContainer.add(componentToAdd, formGridConfig);

        return controlsContainer;
    }
}
