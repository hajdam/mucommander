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
import com.mucommander.plugin.operation.AddonCartOperation;
import org.jspecify.annotations.NullMarked;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

/**
 * Component for list of cart operations.
 */
@NullMarked
public class AddonCartComponent extends javax.swing.JPanel {

    private static final Border DEFAULT_NO_FOCUS_BORDER = new EmptyBorder(1, 1, 1, 1);
    protected final ResourceBundle resourceBundle = ResourceBundle.getBundle(LanguageUtils.getResourceBaseNameBundleByClass(AddonCartComponent.class));
    protected final ImageIcon defaultItemIcon;
    protected final ImageIcon installVariantIcon;
    protected final ImageIcon updateVariantIcon;
    protected final ImageIcon removeVariantIcon;

    private javax.swing.JLabel iconLabel;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JLabel providerLabel;
    private javax.swing.JLabel variantLabel;

    public AddonCartComponent() {
        defaultItemIcon = new ImageIcon(getClass().getResource(resourceBundle.getString("defaultItem.icon")));
        installVariantIcon = new ImageIcon(getClass().getResource(resourceBundle.getString("installVariant.icon")));
        updateVariantIcon = new ImageIcon(getClass().getResource(resourceBundle.getString("updateVariant.icon")));
        removeVariantIcon = new ImageIcon(getClass().getResource(resourceBundle.getString("removeVariant.icon")));
        initComponents();
    }

    public void setCartRecord(JList<?> list, AddonCartOperation addonOperation, boolean isSelected, boolean cellHasFocus) {
        AddonRecord addonRecord = addonOperation.getRecord();
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
        switch (addonOperation.getVariant()) {
            case INSTALL:
                variantLabel.setIcon(installVariantIcon);
                setToolTipText(resourceBundle.getString("installVariant.toolTip"));
                break;
            case UPDATE:
                variantLabel.setIcon(updateVariantIcon);
                setToolTipText(resourceBundle.getString("updateVariant.toolTip"));
                break;
            case REMOVE:
                variantLabel.setIcon(removeVariantIcon);
                setToolTipText(resourceBundle.getString("removeVariant.toolTip"));
                break;
            default:
                throw new AssertionError();
        }
    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    private void initComponents() {
        variantLabel = new javax.swing.JLabel();
        iconLabel = new javax.swing.JLabel();
        nameLabel = new javax.swing.JLabel();
        providerLabel = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 4));

        variantLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        variantLabel.setToolTipText("");

        iconLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/48px/puzzle.png"))); // NOI18N

        nameLabel.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        nameLabel.setText("Addon Name");

        providerLabel.setFont(getFont().deriveFont(Font.PLAIN));
        providerLabel.setText("Provider");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(variantLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(iconLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(providerLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(iconLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(nameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(providerLabel))
            .addComponent(variantLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }
}
