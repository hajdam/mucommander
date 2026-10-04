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
import javax.swing.DefaultListModel;

/**
 * Panel for addon installation / update operation.
 */
@NullMarked
public class AddonOperationOverviewPanel extends javax.swing.JPanel {

    protected final ResourceBundle resourceBundle = ResourceBundle.getBundle(LanguageUtils.getResourceBaseNameBundleByClass(AddonOperationOverviewPanel.class));

    private javax.swing.JLabel operationsLabel;
    private javax.swing.JList<String> operationsList;
    private javax.swing.JScrollPane scrollPane;

    public AddonOperationOverviewPanel() {
        initComponents();
        init();
    }

    private void init() {
        operationsList.setModel(new DefaultListModel<>());
    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    public void addOperation(String operation) {
        DefaultListModel<String> model = (DefaultListModel<String>) operationsList.getModel();
        model.addElement(operation);
    }

    private void initComponents() {
        operationsLabel = new javax.swing.JLabel();
        scrollPane = new javax.swing.JScrollPane();
        operationsList = new javax.swing.JList<>();

        operationsLabel.setText(resourceBundle.getString("operationsLabel.text")); // NOI18N

        scrollPane.setViewportView(operationsList);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(operationsLabel)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(scrollPane))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(operationsLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollPane)
                .addContainerGap())
        );
    }
}
