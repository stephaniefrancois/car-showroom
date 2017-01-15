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
                                                   JPanel constrolsContainer) {
        return addControlWithLabel(componentToAdd, label, rowIndex, 0,
                padding, formGridConfig, constrolsContainer);
    }

    public final static JPanel addControlWithLabel(Component componentToAdd,
                                                   String label,
                                                   int rowIndex,
                                                   int columnIndex,
                                                   Insets padding,
                                                   GridBagConstraints formGridConfig,
                                                   JPanel constrolsContainer) {

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
        constrolsContainer.add(componentLabel, formGridConfig);

        formGridConfig.gridx = columnIndex * columnMultiplier + 1;
        formGridConfig.weightx = 0.4;
        formGridConfig.insets = padding;
        formGridConfig.anchor = GridBagConstraints.LINE_START;
        formGridConfig.gridheight = 1;
        constrolsContainer.add(componentToAdd, formGridConfig);

        return constrolsContainer;
    }
}
