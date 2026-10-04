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
package com.mucommander.plugin.manager.gui;

import java.awt.Font;
import java.util.ResourceBundle;

import com.mucommander.plugin.AddonRecord;
import com.mucommander.plugin.manager.LanguageUtils;
import org.jspecify.annotations.NullMarked;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

/**
 * Component for list of packs / addon suites.
 */
@NullMarked
public class AddonItemComponent extends javax.swing.JPanel {

    private static final Border DEFAULT_NO_FOCUS_BORDER = new EmptyBorder(1, 1, 1, 1);
    protected final ResourceBundle resourceBundle = ResourceBundle.getBundle(LanguageUtils.getResourceBaseNameBundleByClass(AddonItemComponent.class));
    protected final ImageIcon defaultItemIcon;
    protected final ImageIcon disabledStateIcon;
    protected final ImageIcon updateAvailableStateIcon;

    private javax.swing.JLabel iconLabel;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JLabel providerLabel;
    private javax.swing.JLabel stateLabel;

    public AddonItemComponent() {
        defaultItemIcon = new ImageIcon(getClass().getResource(resourceBundle.getString("defaultItem.icon")));
        disabledStateIcon = new ImageIcon(getClass().getResource(resourceBundle.getString("disabledState.icon")));
        updateAvailableStateIcon = new ImageIcon(getClass().getResource(resourceBundle.getString("updateAvailableState.icon")));
        initComponents();
    }

    public void setItemRecord(JList<?> list, AddonRecord addonRecord, boolean isSelected, boolean cellHasFocus) {
        nameLabel.setText(addonRecord.getName());
        iconLabel.setIcon(addonRecord.getIcon().orElse(defaultItemIcon));
        providerLabel.setText(addonRecord.getProvider().orElse(""));

        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
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
        if (!addonRecord.isEnabled()) {
            stateLabel.setIcon(disabledStateIcon);
            setToolTipText(resourceBundle.getString("disabledState.toolTip"));
        } else if (addonRecord.isUpdateAvailable()) {
            stateLabel.setIcon(updateAvailableStateIcon);
            setToolTipText(resourceBundle.getString("updateAvailableState.toolTip"));
        } else {
            stateLabel.setIcon(null);
            setToolTipText(null);
        }
    }

    private Border getNoFocusBorder() {
        Border border = UIManager.getBorder("List.cellNoFocusBorder");
        return border == null ? DEFAULT_NO_FOCUS_BORDER : border;
    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    private void initComponents() {
        iconLabel = new javax.swing.JLabel();
        nameLabel = new javax.swing.JLabel();
        providerLabel = new javax.swing.JLabel();
        stateLabel = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 4));

        iconLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/48px/puzzle.png"))); // NOI18N

        nameLabel.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        nameLabel.setText("Addon Name");

        providerLabel.setFont(getFont().deriveFont(Font.PLAIN));
        providerLabel.setText("Provider");

        stateLabel.setPreferredSize(new java.awt.Dimension(16, 16));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(iconLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(providerLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(stateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(nameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(iconLabel)
            .addGroup(layout.createSequentialGroup()
                .addComponent(nameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(stateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(providerLabel)))
        );
    }
}
