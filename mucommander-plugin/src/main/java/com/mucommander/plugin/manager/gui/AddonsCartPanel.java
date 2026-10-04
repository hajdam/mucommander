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

import java.awt.Component;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import com.mucommander.plugin.manager.LanguageUtils;
import com.mucommander.plugin.operation.AddonCartOperation;
import com.mucommander.plugin.operation.CartOperation;
import org.jspecify.annotations.NullMarked;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import org.jspecify.annotations.Nullable;

/**
 * Panel for addons cart operations panel.
 */
@NullMarked
public class AddonsCartPanel extends javax.swing.JPanel {

    protected final ResourceBundle resourceBundle = ResourceBundle.getBundle(LanguageUtils.getResourceBaseNameBundleByClass(AddonsCartPanel.class));
    protected @Nullable Controller controller;

    private javax.swing.JPanel controlPanel;
    private javax.swing.JList<AddonCartOperation> itemsList;
    private javax.swing.JButton removeButton;
    private javax.swing.JButton runButton;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JButton selectAllButton;
    private javax.swing.JPanel wrapperPanel;

    public AddonsCartPanel() {
        initComponents();
        init();
    }

    private void init() {
        itemsList.setModel(new DefaultListModel<>());
        itemsList.setCellRenderer(new DefaultListCellRenderer() {

            private final AddonCartComponent component = new AddonCartComponent();

            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                AddonCartOperation record = (AddonCartOperation) value;
                component.setCartRecord(list, record, isSelected, cellHasFocus);
                return component;
            }
        });
        itemsList.addListSelectionListener((ListSelectionEvent lse) -> {
            updateState();
        });
    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setCartItems(List<CartOperation> cartOperations) {
        DefaultListModel<AddonCartOperation> model = (DefaultListModel<AddonCartOperation>) itemsList.getModel();
        model.removeAllElements();
        for (CartOperation cartOperation : cartOperations) {
            model.addElement((AddonCartOperation) cartOperation);
        }
        updateState();
    }

    public List<AddonCartOperation> getCartItems() {
        List<AddonCartOperation> items = new ArrayList<>();
        DefaultListModel<AddonCartOperation> model = (DefaultListModel<AddonCartOperation>) itemsList.getModel();
        for (int i = 0; i < model.getSize(); i++) {
            items.add(model.getElementAt(i));
        }
        return items;
    }

    protected void updateState() {
        boolean hasItems = itemsList.getModel().getSize() > 0;
        selectAllButton.setEnabled(hasItems);
        removeButton.setEnabled(itemsList.getSelectedIndex() >= 0);
    }

    private void initComponents() {
        wrapperPanel = new javax.swing.JPanel();
        scrollPane = new javax.swing.JScrollPane();
        itemsList = new javax.swing.JList<>();
        controlPanel = new javax.swing.JPanel();
        selectAllButton = new javax.swing.JButton();
        removeButton = new javax.swing.JButton();
        runButton = new javax.swing.JButton();

        wrapperPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceBundle.getString("border.title"))); // NOI18N

        scrollPane.setViewportView(itemsList);

        selectAllButton.setText(resourceBundle.getString("selectAllButton.text")); // NOI18N
        selectAllButton.setEnabled(false);
        selectAllButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectAllButtonActionPerformed(evt);
            }
        });

        removeButton.setText(resourceBundle.getString("removeButton.text")); // NOI18N
        removeButton.setEnabled(false);
        removeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeButtonActionPerformed(evt);
            }
        });

        runButton.setIcon(new javax.swing.ImageIcon(getClass().getResource(resourceBundle.getString("runButton.icon"))));
        runButton.setText(resourceBundle.getString("runButton.text")); // NOI18N
        runButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout controlPanelLayout = new javax.swing.GroupLayout(controlPanel);
        controlPanel.setLayout(controlPanelLayout);
        controlPanelLayout.setHorizontalGroup(
            controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(controlPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(removeButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(selectAllButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(runButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        controlPanelLayout.setVerticalGroup(
            controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(controlPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(selectAllButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(removeButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(runButton)
                .addContainerGap())
        );

        javax.swing.GroupLayout wrapperPanelLayout = new javax.swing.GroupLayout(wrapperPanel);
        wrapperPanel.setLayout(wrapperPanelLayout);
        wrapperPanelLayout.setHorizontalGroup(
            wrapperPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 370, Short.MAX_VALUE)
            .addGroup(wrapperPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(wrapperPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(scrollPane)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(controlPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );
        wrapperPanelLayout.setVerticalGroup(
            wrapperPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 142, Short.MAX_VALUE)
            .addGroup(wrapperPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(wrapperPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(wrapperPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(controlPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(scrollPane))
                    .addContainerGap()))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(wrapperPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(wrapperPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
    }

    private void selectAllButtonActionPerformed(java.awt.event.ActionEvent evt) {
        if (itemsList.getSelectedIndices().length < itemsList.getModel().getSize()) {
            itemsList.setSelectionInterval(0, itemsList.getModel().getSize() - 1);
        } else {
            itemsList.clearSelection();
        }
    }

    private void removeButtonActionPerformed(java.awt.event.ActionEvent evt) {
        controller.performRemove(itemsList.getSelectedIndices());
    }

    private void runButtonActionPerformed(java.awt.event.ActionEvent evt) {
        controller.runOperations();
    }

    public void removeIndices(int[] indices) {
        if (indices.length == 0) {
            return;
        }

        DefaultListModel<AddonCartOperation> model = (DefaultListModel<AddonCartOperation>) itemsList.getModel();
        Arrays.sort(indices);
        for (int i = indices.length - 1; i >= 0; i--) {
            model.remove(indices[i]);
        }
        itemsList.clearSelection();
    }

    public interface Controller {

        /**
         * Removes items of specified indices from the list.
         *
         * @param indices item indices to remove
         */
        void performRemove(int[] indices);

        /**
         * Runs cart operations.
         */
        void runOperations();
    }
}
