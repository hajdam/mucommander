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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mucommander.plugin.AddonModuleFileLocation;
import com.mucommander.plugin.AddonRecord;
import com.mucommander.plugin.AddonsManagementCatalogState;
import com.mucommander.plugin.AddonsManagementContext;
import com.mucommander.plugin.DesktopUtils;
import com.mucommander.plugin.RepositoryAddonRecord;
import com.mucommander.plugin.manager.LanguageUtils;
import com.mucommander.plugin.operation.AddonCartOperationType;
import org.jspecify.annotations.NullMarked;
import javax.swing.ImageIcon;
import javax.swing.JPopupMenu;
import javax.swing.event.HyperlinkEvent;
import javax.swing.text.html.HTMLDocument;
import org.jspecify.annotations.Nullable;

/**
 * Addon details panel.
 */
@NullMarked
public class AddonDetailsPanel extends javax.swing.JPanel {

    protected final ResourceBundle resourceBundle = ResourceBundle.getBundle(LanguageUtils.getResourceBaseNameBundleByClass(AddonDetailsPanel.class));
    protected @Nullable Controller controller;
    protected final MouseListener providerLinkListener;
    protected final DependenciesTableModel dependenciesTableModel = new DependenciesTableModel();
    protected @Nullable String providerLink = null;
    protected boolean enablementMode = true;

    private javax.swing.JLabel addonNameLabel;
    private javax.swing.JPanel controlPanel;
    private javax.swing.JScrollPane dependenciesScrollPane;
    private javax.swing.JTable dependenciesTable;
    private javax.swing.JButton enablementButton;
    private javax.swing.JPanel infoPanel;
    private javax.swing.JButton installButton;
    private javax.swing.JScrollPane overviewScrollPane;
    private javax.swing.JTextPane overviewTextPane;
    private javax.swing.JLabel providerLabel;
    private javax.swing.JButton removeButton;
    private javax.swing.JTabbedPane tabbedPane;
    private javax.swing.JButton updateButton;
    private javax.swing.JLabel versionLabel;

    public AddonDetailsPanel() {
        providerLinkListener = new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getButton() == MouseEvent.BUTTON1 && !evt.isPopupTrigger()) {
                    if (providerLink != null) {
                        DesktopUtils.openDesktopURL(providerLink);
                    }
                }
            }
        };
        initComponents();
        init();
    }

    private void init() {
        dependenciesTable.setModel(dependenciesTableModel);
        overviewTextPane.addHyperlinkListener((HyperlinkEvent event) -> {
            if (event.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                DesktopUtils.openDesktopURL(event.getURL().toExternalForm());
            }
        });
        providerLabel.addMouseListener(providerLinkListener);
    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setContext(AddonsManagementContext context) {
        if (context instanceof AddonsManagementCatalogState) {
            try {
                HTMLDocument htmlDocument = new HTMLDocument();
                String catalogBaseUrl = ((AddonsManagementCatalogState) context).getCatalogBaseUrl();
                if (!catalogBaseUrl.isEmpty()) {
                    htmlDocument.setBase(new URI(catalogBaseUrl).toURL());
                }
                overviewTextPane.setDocument(htmlDocument);
            } catch (MalformedURLException | URISyntaxException ex) {
                Logger.getLogger(AddonDetailsPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void setRecord(AddonRecord addonRecord) {
        addonNameLabel.setText(addonRecord.getName());
        versionLabel.setText(addonRecord.getVersion());
        String provider = addonRecord.getProvider().orElse("");
        String providerHomepage = addonRecord.getHomepage().orElse(null);

        if (providerHomepage != null) {
            provider = "<html><body><a href=\"" + providerHomepage + "\">" + (provider.isEmpty() ? resourceBundle.getString("record.provider") : provider) + "</a></body></html>";
            providerLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            providerLabel.setToolTipText(providerHomepage);
            providerLink = providerHomepage;
        } else {
            providerLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            providerLabel.setToolTipText(null);
            providerLink = null;
        }
        providerLabel.setText(provider);
        setModuleDetail(addonRecord, "");
        controller.requestModuleDetail(addonRecord);
        if (addonRecord instanceof RepositoryAddonRecord) {
            dependenciesTableModel.setDependencies(((RepositoryAddonRecord) addonRecord).getDependencies());
        } else {
            dependenciesTableModel.setDependencies(null);
        }
        updateRecordControlState(addonRecord);
    }

    public void setModuleDetail(AddonRecord addonRecord, String details) {
        String description = addonRecord.getDescription().orElse("");
        if (!details.isEmpty()) {
            details = "<hr/>" + details;
        }
        overviewTextPane.setText("<html><body><p>" + description + "<br/>id: " + addonRecord.getId() + "</p>" + details + "</body></html>");
    }

    public void updateRecordControlState(AddonRecord addonRecord) {
        controlPanel.removeAll();
        if (addonRecord.isInstalled()) {
            boolean alreadyRemoved = controller.isInCart(addonRecord.getId(), AddonCartOperationType.REMOVE);
            boolean alreadyInstalled = controller.isInCart(addonRecord.getId(), AddonCartOperationType.INSTALL);
            removeButton.setEnabled(addonRecord.getFileLocation() == AddonModuleFileLocation.ADDON && !alreadyRemoved);
            controlPanel.add(removeButton);
            enablementMode = addonRecord.isEnabled();
            enablementButton.setText(resourceBundle.getString(enablementMode ? "disableButton.text" : "enableButton.text"));
            controlPanel.add(enablementButton);
            updateButton.setEnabled(addonRecord.isUpdateAvailable() && !alreadyInstalled);
            controlPanel.add(updateButton);
        } else {
            boolean isInstalled = controller.isInCart(addonRecord.getId(), AddonCartOperationType.INSTALL);
            installButton.setEnabled(!isInstalled);
            controlPanel.add(installButton);
        }
        controlPanel.revalidate();
        controlPanel.repaint();
    }

    private void initComponents() {
        installButton = new javax.swing.JButton();
        updateButton = new javax.swing.JButton();
        enablementButton = new javax.swing.JButton();
        removeButton = new javax.swing.JButton();
        controlPanel = new javax.swing.JPanel();
        infoPanel = new javax.swing.JPanel();
        addonNameLabel = new javax.swing.JLabel();
        providerLabel = new javax.swing.JLabel();
        versionLabel = new javax.swing.JLabel();
        tabbedPane = new javax.swing.JTabbedPane();
        overviewScrollPane = new javax.swing.JScrollPane();
        overviewTextPane = new javax.swing.JTextPane();
        dependenciesScrollPane = new javax.swing.JScrollPane();
        dependenciesTable = new javax.swing.JTable();

        installButton.setIcon(new ImageIcon(getClass().getResource(resourceBundle.getString("installButton.icon"))));
        installButton.setText(resourceBundle.getString("installButton.text")); // NOI18N
        installButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                installButtonActionPerformed(evt);
            }
        });

        updateButton.setIcon(new ImageIcon(getClass().getResource(resourceBundle.getString("updateButton.icon"))));
        updateButton.setText(resourceBundle.getString("updateButton.text")); // NOI18N
        updateButton.setEnabled(false);
        updateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateButtonActionPerformed(evt);
            }
        });

        enablementButton.setText(resourceBundle.getString("enableButton.text")); // NOI18N
        enablementButton.setEnabled(false);
        enablementButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enablementButtonActionPerformed(evt);
            }
        });

        removeButton.setIcon(new ImageIcon(getClass().getResource(resourceBundle.getString("removeButton.icon"))));
        removeButton.setText(resourceBundle.getString("removeButton.text")); // NOI18N
        removeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeButtonActionPerformed(evt);
            }
        });

        setLayout(new java.awt.BorderLayout());

        controlPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));
        add(controlPanel, java.awt.BorderLayout.PAGE_END);

        addonNameLabel.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        addonNameLabel.setText("Addon Name");

        providerLabel.setFont(providerLabel.getFont().deriveFont((providerLabel.getFont().getStyle() & ~java.awt.Font.ITALIC) & ~java.awt.Font.BOLD));
        providerLabel.setText("Addon Provider");

        versionLabel.setText("Version");

        overviewTextPane.setEditable(false);
        overviewTextPane.setContentType("text/html"); // NOI18N
        overviewScrollPane.setViewportView(overviewTextPane);

        tabbedPane.addTab(resourceBundle.getString("overviewTab.title"), overviewScrollPane); // NOI18N

        dependenciesScrollPane.setViewportView(dependenciesTable);

        tabbedPane.addTab(resourceBundle.getString("dependenciesTab.title"), dependenciesScrollPane); // NOI18N

        javax.swing.GroupLayout infoPanelLayout = new javax.swing.GroupLayout(infoPanel);
        infoPanel.setLayout(infoPanelLayout);
        infoPanelLayout.setHorizontalGroup(
            infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(infoPanelLayout.createSequentialGroup()
                        .addComponent(addonNameLabel)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(infoPanelLayout.createSequentialGroup()
                        .addComponent(providerLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(versionLabel)))
                .addContainerGap())
        );
        infoPanelLayout.setVerticalGroup(
            infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(addonNameLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(versionLabel)
                    .addComponent(providerLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                .addContainerGap())
        );

        add(infoPanel, java.awt.BorderLayout.CENTER);
    }

    private void enablementButtonActionPerformed(java.awt.event.ActionEvent evt) {
        if (enablementMode) {
            controller.addToCart(AddonCartOperationType.DISABLE);
        } else {
            controller.addToCart(AddonCartOperationType.ENABLE);
        }
        enablementMode = !enablementMode;
        enablementButton.setText(resourceBundle.getString(enablementMode ? "disableButton.text" : "enableButton.text"));
    }

    private void installButtonActionPerformed(java.awt.event.ActionEvent evt) {
        controller.addToCart(AddonCartOperationType.INSTALL);
    }

    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {
        controller.addToCart(AddonCartOperationType.UPDATE);
    }

    private void removeButtonActionPerformed(java.awt.event.ActionEvent evt) {
        controller.addToCart(AddonCartOperationType.REMOVE);
    }

    @NullMarked
    public interface Controller {

        /**
         * Adds addon operation to the cart.
         *
         * @param variant operation variant
         */
        void addToCart(AddonCartOperationType variant);

        /**
         * Check whether addon operation is in cart.
         *
         * @param addonId addon identifier
         * @param variant operation variant
         * @return true if present in cart
         */
        boolean isInCart(String addonId, AddonCartOperationType variant);

        /**
         * Requests to receive addon module detail.
         * 
         * @param addonRecord addon record
         */
        void requestModuleDetail(AddonRecord addonRecord);
    }
}
