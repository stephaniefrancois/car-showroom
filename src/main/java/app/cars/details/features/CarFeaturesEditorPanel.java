package app.cars.details.features;

import core.domain.car.CarFeature;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public final class CarFeaturesEditorPanel extends JPanel {
    private final JList featuresListEditor;
    private List<CarFeature> availableFeatures;

    public CarFeaturesEditorPanel() {
        setLayout(new BorderLayout());
        this.featuresListEditor = new JList();
        this.featuresListEditor.setCellRenderer(new CheckListRenderer());
        this.featuresListEditor.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.featuresListEditor.setBorder(new EmptyBorder(0, 4, 0, 0));
        this.featuresListEditor.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int index = featuresListEditor.locationToIndex(e.getPoint());
                EditableCarFeatureItem item = (EditableCarFeatureItem) featuresListEditor.getModel()
                        .getElementAt(index);
                item.setSelected(!item.isSelected());
                Rectangle rect = featuresListEditor.getCellBounds(index, index);
                featuresListEditor.repaint(rect);
            }
        });

        add(new JScrollPane(featuresListEditor), BorderLayout.CENTER);
    }

    public void displayAvailableCarFeatures(List<CarFeature> features) {
        Objects.requireNonNull(features);
        availableFeatures = features;

        List<EditableCarFeatureItem> editableCarFeatures = features.stream()
                .map(EditableCarFeatureItem::new)
                .collect(Collectors.toList());

        this.featuresListEditor.setListData(editableCarFeatures.toArray());
    }

    public void displaySelectedCarFeatures(List<CarFeature> selectedCarFeatures) {
        Objects.requireNonNull(selectedCarFeatures);
        if (selectedCarFeatures.isEmpty() || this.availableFeatures.isEmpty()) {
            return;
        }

        List<EditableCarFeatureItem> featuresToDisplay =
                this.availableFeatures.stream()
                        .map(availableFeature -> {
                            EditableCarFeatureItem editableFeature = new EditableCarFeatureItem(availableFeature);
                            if (selectedCarFeatures.contains(availableFeature)) {
                                editableFeature.setSelected(true);
                            }
                            return editableFeature;
                        }).collect(Collectors.toList());

        this.featuresListEditor.setListData(featuresToDisplay.toArray());
    }

    public List<CarFeature> getSelectedCarFeatures() {
        List<CarFeature> selectedFeatures = new ArrayList<>();

        ListModel model = featuresListEditor.getModel();
        for (int index = 0; index < model.getSize(); index++) {
            EditableCarFeatureItem item = (EditableCarFeatureItem) model.getElementAt(index);
            if (item.isSelected()) {
                selectedFeatures.add(item.getFeature());
            }
        }

        return selectedFeatures;
    }

    private class EditableCarFeatureItem {
        private CarFeature feature;

        private boolean isSelected;

        public EditableCarFeatureItem(CarFeature feature) {
            Objects.requireNonNull(feature);
            this.feature = feature;
            this.isSelected = false;
        }

        public void setSelected(boolean b) {
            this.isSelected = b;
        }

        public boolean isSelected() {
            return this.isSelected;
        }

        public CarFeature getFeature() {
            return this.feature;
        }

        public String toString() {
            return this.feature.toString();
        }
    }

    private final class CheckListRenderer extends JCheckBox implements ListCellRenderer {

        public CheckListRenderer() {
            setBackground(UIManager.getColor("List.textBackground"));
            setForeground(UIManager.getColor("List.textForeground"));
        }

        public Component getListCellRendererComponent(JList list, Object value,
                                                      int index, boolean isSelected, boolean hasFocus) {
            setEnabled(list.isEnabled());
            setSelected(((EditableCarFeatureItem) value).isSelected());
            setFont(list.getFont());
            setText(value.toString());
            return this;
        }
    }
}
