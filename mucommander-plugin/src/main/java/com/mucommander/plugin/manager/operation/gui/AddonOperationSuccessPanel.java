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
package com.mucommander.plugin.manager.operation.gui;

import java.util.ResourceBundle;

import com.mucommander.plugin.manager.LanguageUtils;
import org.jspecify.annotations.NullMarked;

/**
 * Panel for addon installation / update operation.
 */
@NullMarked
public class AddonOperationSuccessPanel extends javax.swing.JPanel {

    protected final ResourceBundle resourceBundle = ResourceBundle.getBundle(LanguageUtils.getResourceBaseNameBundleByClass(AddonOperationSuccessPanel.class));

    private javax.swing.JLabel messageLabel;
    private javax.swing.JLabel restartAppLabel;

    public AddonOperationSuccessPanel() {
        initComponents();
    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    private void initComponents() {
        messageLabel = new javax.swing.JLabel();
        restartAppLabel = new javax.swing.JLabel();

        messageLabel.setText(resourceBundle.getString("messageLabel.text")); // NOI18N

        restartAppLabel.setText(resourceBundle.getString("restartAppLabel.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(messageLabel)
                    .addComponent(restartAppLabel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(messageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(restartAppLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }
}
