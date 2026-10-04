/*
 * This file is part of muCommander, http://www.mucommander.com
 *
 * muCommander is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * muCommander is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.mucommander.plugin.manager.filter.gui;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import com.mucommander.plugin.manager.LanguageUtils;
import org.jspecify.annotations.NullMarked;
import com.mucommander.plugin.manager.gui.CategoryRecord;
import org.jspecify.annotations.Nullable;

/**
 * Category filter panel.
 */
@NullMarked
public class CategoryFilterPanel extends javax.swing.JPanel {

    protected final ResourceBundle resourceBundle = ResourceBundle.getBundle(LanguageUtils.getResourceBaseNameBundleByClass(CategoryFilterPanel.class));

    private javax.swing.JList<CategoryRecord> categoriesList;
    private javax.swing.JScrollPane categoriesScrollPane;
    private javax.swing.JCheckBox modeCheckBox;

    public CategoryFilterPanel() {
        initComponents();
        init();
    }

    private void init() {
        categoriesList.setModel(new DefaultListModel<>());
        categoriesList.setCellRenderer(new CheckboxListCellRenderer());
        categoriesList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                if (event.getX() < 20) {
                    int index = categoriesList.locationToIndex(event.getPoint());
                    if (index >= 0) {
                        CategoryRecord item = categoriesList.getModel().getElementAt(index);
                        item.setSelected(!item.isSelected());
                        categoriesList.repaint(categoriesList.getCellBounds(index, index));
                    }
                }
            }
        });
    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    public void setCategories(List<CategoryRecord> records) {
        DefaultListModel<CategoryRecord> model = (DefaultListModel<CategoryRecord>) categoriesList.getModel();
        model.removeAllElements();
        for (CategoryRecord record : records) {
            model.addElement(record);
        }
    }

    private void initComponents() {
        modeCheckBox = new javax.swing.JCheckBox();
        categoriesScrollPane = new javax.swing.JScrollPane();
        categoriesList = new javax.swing.JList<>();

        setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 4));

        modeCheckBox.setText("Exclude mode");

        categoriesScrollPane.setViewportView(categoriesList);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(modeCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(categoriesScrollPane))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(modeCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(categoriesScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }

    @NullMarked
    public static class CheckboxListCellRenderer extends JCheckBox implements ListCellRenderer<CategoryRecord> {

        private static final Border DEFAULT_NO_FOCUS_BORDER = new EmptyBorder(1, 1, 1, 1);

        @Override
        public Component getListCellRendererComponent(JList<? extends CategoryRecord> list, @Nullable CategoryRecord value, int index, boolean isSelected, boolean cellHasFocus) {
            setEnabled(list.isEnabled());
            setComponentOrientation(list.getComponentOrientation());
            setFont(list.getFont());
            setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
            setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());
            setFocusPainted(cellHasFocus);
            setBorderPainted(true);
            Border border;
            if (cellHasFocus) {
                border = UIManager.getBorder(isSelected ? "List.focusSelectedCellHighlightBorder" : "List.focusCellHighlightBorder");
            } else {
                border = UIManager.getBorder("List.cellNoFocusBorder");
            }
            if (border == null) {
                border = DEFAULT_NO_FOCUS_BORDER;
            }
            setBorder(border);
            setText(value == null ? "" : value.getName());
            setSelected(value != null && value.isSelected());

            return this;
        }
    }
}
