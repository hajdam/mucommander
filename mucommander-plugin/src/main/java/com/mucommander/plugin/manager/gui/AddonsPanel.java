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

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.ResourceBundle;

import com.mucommander.plugin.AddonRecord;
import com.mucommander.plugin.AddonsListComponent;
import com.mucommander.plugin.AddonsListComponentController;
import com.mucommander.plugin.AddonsManagementContext;
import com.mucommander.plugin.manager.LanguageUtils;
import com.mucommander.plugin.operation.AddonCartOperationType;
import org.jspecify.annotations.NullMarked;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComponent;
import javax.swing.JList;
import org.jspecify.annotations.Nullable;

/**
 * Addons list with details panel.
 */
@NullMarked
public class AddonsPanel extends javax.swing.JPanel implements AddonsListComponent {

    protected final ResourceBundle resourceBundle = ResourceBundle.getBundle(LanguageUtils.getResourceBaseNameBundleByClass(AddonsPanel.class));
    protected final AddonsListModel addonsListModel = new AddonsListModel();
    protected final AddonDetailsPanel addonDetailsPanel = new AddonDetailsPanel();
    protected @Nullable AddonRecord activeRecord;
    protected @Nullable AddonsListComponentController controller;

    private javax.swing.JPanel infoPanel;
    private javax.swing.JList<AddonRecord> itemsList;
    private javax.swing.JScrollPane itemsListScrollPane;
    private javax.swing.JLabel noItemSelectedLabel;
    private javax.swing.JSplitPane splitPane;

    public AddonsPanel() {
        initComponents();
        init();
    }

    private void init() {
        itemsList.setCellRenderer(new DefaultListCellRenderer() {

            private final AddonItemComponent addonItemPanel = new AddonItemComponent();

            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                AddonRecord record = (AddonRecord) value;
                addonItemPanel.setItemRecord(list, record, isSelected, cellHasFocus);
                return addonItemPanel;
            }
        });
        itemsList.addListSelectionListener((event) -> {
            notifyItemSelected();
        });
    }

    @Override
    public JComponent getComponent() {
        return this;
    }

    @Override
    public void setController(AddonsListComponentController controller) {
        this.controller = controller;
        addonsListModel.setProvider(new AddonsListModel.RecordsProvider() {
            @Override
            public int getItemsCount() {
                return AddonsPanel.this.controller.getItemsCount();
            }

            @Override
            public AddonRecord getItem(int index) {
                return AddonsPanel.this.controller.getItem(index);
            }
        });
        addonsListModel.notifyItemsChanged();
        itemsList.setModel(addonsListModel);
        addonDetailsPanel.setController(new AddonDetailsPanel.Controller() {

            @Override
            public void addToCart(AddonCartOperationType variant) {
                AddonRecord record = activeRecord;
                controller.addToCart(record, variant);
                addonDetailsPanel.updateRecordControlState(record);
            }

            @Override
            public boolean isInCart(String addonId, AddonCartOperationType variant) {
                return controller.isInCart(addonId, variant);
            }

            @Override
            public void requestModuleDetail(AddonRecord itemRecord) {
                controller.requestModuleDetail(itemRecord);
            }
        });
    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    @Override
    public void setContext(AddonsManagementContext context) {
        addonDetailsPanel.setContext(context);
    }

    private void notifyItemSelected() {
        int index = itemsList.getSelectedIndex();
        AddonRecord itemRecord = index >= 0 ? itemsList.getModel().getElementAt(index) : null;

        if (activeRecord != itemRecord) {
            if (activeRecord == null) {
                infoPanel.remove(noItemSelectedLabel);
                addonDetailsPanel.setRecord(itemRecord);
                infoPanel.add(addonDetailsPanel, BorderLayout.CENTER);
                infoPanel.revalidate();
                infoPanel.repaint();
            } else if (itemRecord == null) {
                infoPanel.remove(addonDetailsPanel);
                infoPanel.add(noItemSelectedLabel, BorderLayout.CENTER);
                infoPanel.revalidate();
                infoPanel.repaint();
            } else {
                addonDetailsPanel.setRecord(itemRecord);
            }
            activeRecord = itemRecord;
        }
    }

    @Override
    public void notifyItemChanged() {
        if (activeRecord != null) {
            addonDetailsPanel.setRecord(activeRecord);
        }
    }

    @Override
    public void notifyItemsChanged() {
        addonsListModel.notifyItemsChanged();
    }

    public void setModuleDetail(AddonRecord itemRecord, String details) {
        addonDetailsPanel.setModuleDetail(itemRecord, details);
    }

    private void initComponents() {
        splitPane = new javax.swing.JSplitPane();
        infoPanel = new javax.swing.JPanel();
        noItemSelectedLabel = new javax.swing.JLabel();
        itemsListScrollPane = new javax.swing.JScrollPane();
        itemsList = new javax.swing.JList<>();

        setLayout(new java.awt.BorderLayout());

        splitPane.setDividerLocation(250);

        infoPanel.setLayout(new java.awt.BorderLayout());

        noItemSelectedLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        noItemSelectedLabel.setText(resourceBundle.getString("noItemSelectedLabel.text")); // NOI18N
        noItemSelectedLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        infoPanel.add(noItemSelectedLabel, java.awt.BorderLayout.CENTER);

        splitPane.setRightComponent(infoPanel);

        itemsList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        itemsListScrollPane.setViewportView(itemsList);

        splitPane.setLeftComponent(itemsListScrollPane);

        add(splitPane, java.awt.BorderLayout.CENTER);
    }
}
