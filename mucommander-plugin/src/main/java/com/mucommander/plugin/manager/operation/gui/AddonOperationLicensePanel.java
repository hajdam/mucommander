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

import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mucommander.plugin.DesktopUtils;
import com.mucommander.plugin.manager.LanguageUtils;
import org.jspecify.annotations.NullMarked;
import javax.swing.DefaultListModel;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.ListSelectionEvent;
import com.mucommander.plugin.manager.LicenseItemRecord;
import org.jspecify.annotations.Nullable;

/**
 * Addons operation license approval panel.
 */
@NullMarked
public class AddonOperationLicensePanel extends javax.swing.JPanel {

    protected final ResourceBundle resourceBundle = ResourceBundle.getBundle(LanguageUtils.getResourceBaseNameBundleByClass(AddonOperationLicensePanel.class));
    protected @Nullable Controller controller;
    protected @Nullable List<LicenseItemRecord> licenseRecords;
    protected int toApprove;

    private javax.swing.JCheckBox licenseApprovalCheckBox;
    private javax.swing.JEditorPane licenseContentEditorPane;
    private javax.swing.JScrollPane licenseContentScrollPane;
    private javax.swing.JList<String> licenseList;
    private javax.swing.JPanel licensePanel;
    private javax.swing.JLabel licensesToApproveLabel;
    private javax.swing.JPanel listPanel;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JSplitPane splitPane;

    public AddonOperationLicensePanel() {
        initComponents();
        init();
    }

    private void init() {
        licenseList.setModel(new DefaultListModel<>());
        licenseList.addListSelectionListener((ListSelectionEvent e) -> {
            int selectedIndex = licenseList.getSelectedIndex();
            if (selectedIndex < 0) {
                licenseContentEditorPane.setText("");
                licenseApprovalCheckBox.setSelected(false);
                licenseApprovalCheckBox.setEnabled(false);
            } else {
                LicenseItemRecord record = licenseRecords.get(selectedIndex);
                try {
                    licenseContentEditorPane.setPage(record.getUrl());
                } catch (IOException ex) {
                    Logger.getLogger(AddonOperationLicensePanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                licenseApprovalCheckBox.setSelected(record.isApproved());
                licenseApprovalCheckBox.setEnabled(true);
            }
        });
        licenseContentEditorPane.addHyperlinkListener((HyperlinkEvent event) -> {
            if (event.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                DesktopUtils.openDesktopURL(event.getURL().toExternalForm());
            }
        });
    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setLicenseRecords(List<LicenseItemRecord> licenseRecords) {
        this.licenseRecords = licenseRecords;
        DefaultListModel<String> model = (DefaultListModel<String>) licenseList.getModel();
        model.removeAllElements();
        for (LicenseItemRecord licenseRecord : licenseRecords) {
            model.addElement(licenseRecord.getName());
        }
        toApprove = licenseRecords.size();
    }

    private void initComponents() {
        licensesToApproveLabel = new javax.swing.JLabel();
        splitPane = new javax.swing.JSplitPane();
        listPanel = new javax.swing.JPanel();
        scrollPane = new javax.swing.JScrollPane();
        licenseList = new javax.swing.JList<>();
        licensePanel = new javax.swing.JPanel();
        licenseContentScrollPane = new javax.swing.JScrollPane();
        licenseContentEditorPane = new javax.swing.JEditorPane();
        licenseApprovalCheckBox = new javax.swing.JCheckBox();

        licensesToApproveLabel.setText(resourceBundle.getString("licensesToApproveLabel.text")); // NOI18N

        splitPane.setDividerLocation(250);

        listPanel.setLayout(new javax.swing.BoxLayout(listPanel, javax.swing.BoxLayout.LINE_AXIS));

        scrollPane.setViewportView(licenseList);

        listPanel.add(scrollPane);

        splitPane.setLeftComponent(listPanel);

        licenseContentEditorPane.setEditable(false);
        licenseContentScrollPane.setViewportView(licenseContentEditorPane);

        licenseApprovalCheckBox.setText(resourceBundle.getString("licenseApprovalCheckBox.text")); // NOI18N
        licenseApprovalCheckBox.setEnabled(false);
        licenseApprovalCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                licenseApprovalCheckBoxItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout licensePanelLayout = new javax.swing.GroupLayout(licensePanel);
        licensePanel.setLayout(licensePanelLayout);
        licensePanelLayout.setHorizontalGroup(
            licensePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(licensePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(licensePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(licenseContentScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 442, Short.MAX_VALUE)
                    .addGroup(licensePanelLayout.createSequentialGroup()
                        .addComponent(licenseApprovalCheckBox)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        licensePanelLayout.setVerticalGroup(
            licensePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, licensePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(licenseContentScrollPane)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(licenseApprovalCheckBox)
                .addContainerGap())
        );

        splitPane.setRightComponent(licensePanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(licensesToApproveLabel)
                        .addContainerGap(518, Short.MAX_VALUE))
                    .addComponent(splitPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(licensesToApproveLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(splitPane))
        );
    }

    private void licenseApprovalCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {
        int selectedIndex = licenseList.getSelectedIndex();
        if (selectedIndex >= 0) {
            LicenseItemRecord record = licenseRecords.get(selectedIndex);
            boolean isApproved = record.isApproved();
            boolean changeTo = licenseApprovalCheckBox.isSelected();
            if (isApproved != changeTo) {
                record.setApproved(changeTo);
                toApprove += changeTo ? -1 : 1;
                if (controller != null) {
                    controller.approvalStateChanged(toApprove);
                }
            }
        }
    }

    public interface Controller {

        void approvalStateChanged(int toApprove);
    }
}
