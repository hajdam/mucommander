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
import java.awt.event.MouseEvent;
import java.util.Optional;
import java.util.ResourceBundle;

import com.mucommander.plugin.CloseControlController;
import com.mucommander.plugin.UiUtils;
import com.mucommander.plugin.manager.LanguageUtils;
import org.jspecify.annotations.NullMarked;
import javax.swing.JButton;
import org.jspecify.annotations.Nullable;

/**
 * Control panel for addons manager.
 */
@NullMarked
public class AddonsManagerControlPanel extends javax.swing.JPanel implements CloseControlController.CloseControlComponent {

    public static final String MANUAL_CATALOG_URL = "https://www.mucommander.com/#download";
    protected final java.util.ResourceBundle resourceBundle;
    protected @Nullable Controller controller = null;
    protected @Nullable Component activeStatusComponent = null;
    protected @Nullable Component defaultStatusComponent = null;

    private javax.swing.JPanel buttonsPanel;
    private javax.swing.JButton closeButton;
    private javax.swing.JLabel connectionFailedLabel;
    private javax.swing.JPanel connectionFailedPanel;
    private javax.swing.JLabel legacyModeLabel;
    private javax.swing.JPanel legacyModePanel;
    private javax.swing.JLabel manualOnlyModeLabel;
    private javax.swing.JPanel manualOnlyModePanel;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JButton refreshButton;
    private javax.swing.JLabel statusLabel;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JButton updateAllButton;
    private javax.swing.JLabel updatesAvailableLabel;
    private javax.swing.JPanel updatesAvailablePanel;

    public AddonsManagerControlPanel() {
        this(ResourceBundle.getBundle(LanguageUtils.getResourceBaseNameBundleByClass(AddonsManagerControlPanel.class)));
    }

    public AddonsManagerControlPanel(java.util.ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
        initComponents();
    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    public void showLegacyWarning() {
        add(legacyModePanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public void showManualOnlyWarning() {
        String link = MANUAL_CATALOG_URL;
        manualOnlyModeLabel.setText(String.format(resourceBundle.getString("manualOnlyModeLabel.text"), link));
        manualOnlyModeLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getButton() == MouseEvent.BUTTON1 && !evt.isPopupTrigger() && link != null) {
                    // TODO DesktopUtils.openDesktopURL(link);
                }
            }
        });
        add(manualOnlyModePanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public void setController(Controller controller) {
        this.controller = controller;
        refreshButton.setEnabled(true);
        updateAllButton.setEnabled(true);
    }

    private void initComponents() {
        legacyModePanel = new javax.swing.JPanel();
        legacyModeLabel = new javax.swing.JLabel();
        manualOnlyModePanel = new javax.swing.JPanel();
        manualOnlyModeLabel = new javax.swing.JLabel();
        updatesAvailablePanel = new javax.swing.JPanel();
        updatesAvailableLabel = new javax.swing.JLabel();
        updateAllButton = new javax.swing.JButton();
        statusLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();
        connectionFailedPanel = new javax.swing.JPanel();
        connectionFailedLabel = new javax.swing.JLabel();
        buttonsPanel = new javax.swing.JPanel();
        statusPanel = new javax.swing.JPanel();
        refreshButton = new javax.swing.JButton();
        closeButton = new javax.swing.JButton();

        legacyModePanel.setBackground(new java.awt.Color(255, 153, 0));

        legacyModeLabel.setFont(new java.awt.Font("sansserif", 1, 13)); // NOI18N
        legacyModeLabel.setForeground(new java.awt.Color(0, 0, 0));
        legacyModeLabel.setText(resourceBundle.getString("legacyModeLabel.text")); // NOI18N

        javax.swing.GroupLayout legacyModePanelLayout = new javax.swing.GroupLayout(legacyModePanel);
        legacyModePanel.setLayout(legacyModePanelLayout);
        legacyModePanelLayout.setHorizontalGroup(
            legacyModePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(legacyModePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(legacyModeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 814, Short.MAX_VALUE)
                .addContainerGap())
        );
        legacyModePanelLayout.setVerticalGroup(
            legacyModePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(legacyModePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(legacyModeLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        manualOnlyModePanel.setBackground(new java.awt.Color(255, 102, 102));

        manualOnlyModeLabel.setFont(new java.awt.Font("sansserif", 1, 13)); // NOI18N
        manualOnlyModeLabel.setForeground(new java.awt.Color(255, 255, 255));
        manualOnlyModeLabel.setText(resourceBundle.getString("manualOnlyModeLabel.text")); // NOI18N

        javax.swing.GroupLayout manualOnlyModePanelLayout = new javax.swing.GroupLayout(manualOnlyModePanel);
        manualOnlyModePanel.setLayout(manualOnlyModePanelLayout);
        manualOnlyModePanelLayout.setHorizontalGroup(
            manualOnlyModePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(manualOnlyModePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(manualOnlyModeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 814, Short.MAX_VALUE)
                .addContainerGap())
        );
        manualOnlyModePanelLayout.setVerticalGroup(
            manualOnlyModePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(manualOnlyModePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(manualOnlyModeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        updatesAvailableLabel.setFont(new java.awt.Font("sansserif", 1, 13)); // NOI18N
        updatesAvailableLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource(resourceBundle.getString("updatesAvailableLabel.icon"))));
        updatesAvailableLabel.setText(resourceBundle.getString("updatesAvailableLabel.text")); // NOI18N

        updateAllButton.setText(resourceBundle.getString("updateAllButton.text")); // NOI18N
        updateAllButton.setToolTipText(resourceBundle.getString("updateAllButton.toolTip")); // NOI18N
        updateAllButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateAllButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout updatesAvailablePanelLayout = new javax.swing.GroupLayout(updatesAvailablePanel);
        updatesAvailablePanel.setLayout(updatesAvailablePanelLayout);
        updatesAvailablePanelLayout.setHorizontalGroup(
            updatesAvailablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, updatesAvailablePanelLayout.createSequentialGroup()
                .addComponent(updateAllButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(updatesAvailableLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        updatesAvailablePanelLayout.setVerticalGroup(
            updatesAvailablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(updatesAvailablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(updateAllButton)
                .addComponent(updatesAvailableLabel))
        );

        progressBar.setIndeterminate(true);

        connectionFailedLabel.setFont(new java.awt.Font("sansserif", 1, 13)); // NOI18N
        connectionFailedLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource(resourceBundle.getString("connectionFailedLabel.icon"))));
        connectionFailedLabel.setText(resourceBundle.getString("connectionFailedLabel.text")); // NOI18N

        javax.swing.GroupLayout connectionFailedPanelLayout = new javax.swing.GroupLayout(connectionFailedPanel);
        connectionFailedPanel.setLayout(connectionFailedPanelLayout);
        connectionFailedPanelLayout.setHorizontalGroup(
            connectionFailedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(connectionFailedLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        connectionFailedPanelLayout.setVerticalGroup(
            connectionFailedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(connectionFailedLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setLayout(new java.awt.BorderLayout());

        statusPanel.setLayout(new java.awt.BorderLayout());

        refreshButton.setIcon(new javax.swing.ImageIcon(getClass().getResource(resourceBundle.getString("refreshButton.icon"))));
        refreshButton.setText(resourceBundle.getString("refreshButton.text")); // NOI18N
        refreshButton.setEnabled(false);
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });

        closeButton.setText(resourceBundle.getString("closeButton.text")); // NOI18N
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout buttonsPanelLayout = new javax.swing.GroupLayout(buttonsPanel);
        buttonsPanel.setLayout(buttonsPanelLayout);
        buttonsPanelLayout.setHorizontalGroup(
            buttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(refreshButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(closeButton)
                .addContainerGap())
        );
        buttonsPanelLayout.setVerticalGroup(
            buttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, buttonsPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(buttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(statusPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(buttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(closeButton)
                        .addComponent(refreshButton)))
                .addContainerGap())
        );

        add(buttonsPanel, java.awt.BorderLayout.SOUTH);
    }

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {
        controller.controlActionPerformed();
    }

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {
        controller.performRefresh();
    }

    private void updateAllButtonActionPerformed(java.awt.event.ActionEvent evt) {
        controller.performUpdateAll();
    }

    @Override
    public void performCloseClick() {
        UiUtils.doButtonClick(closeButton);
    }

    @Override
    public Optional<JButton> getDefaultButton() {
        return Optional.of(refreshButton);
    }

    @Override
    public void invokeOkEvent() {
        performCloseClick();
    }

    @Override
    public void invokeCancelEvent() {
        performCloseClick();
    }

    @Override
    public void setCloseActionEnabled(boolean enablement) {
        closeButton.setEnabled(enablement);
    }

    public void setProgressStatus(String status) {
        if (activeStatusComponent != null) {
            statusPanel.remove(activeStatusComponent);
        }

        if (status.isEmpty()) {
            activeStatusComponent = defaultStatusComponent;
            if (activeStatusComponent != null) {
                statusPanel.add(activeStatusComponent, BorderLayout.CENTER);
            }
        } else {
            progressBar.setString(status);
            progressBar.setStringPainted(!status.isEmpty());
            activeStatusComponent = progressBar;
            statusPanel.add(activeStatusComponent, BorderLayout.CENTER);
        }
        statusPanel.revalidate();
        statusPanel.repaint();
    }

    public void setStatusLabel(String text) {
        if (activeStatusComponent != null) {
            statusPanel.remove(activeStatusComponent);
        }

        if (text.isEmpty()) {
            activeStatusComponent = defaultStatusComponent;
            if (activeStatusComponent != null) {
                statusPanel.add(activeStatusComponent, BorderLayout.CENTER);
            }
        } else {
            statusLabel.setText(text);
            activeStatusComponent = statusLabel;
            statusPanel.add(activeStatusComponent, BorderLayout.CENTER);
        }
        statusPanel.revalidate();
        statusPanel.repaint();
    }

    public void setAvailableUpdates(int updatesAvailableCount) {
        updatesAvailableLabel.setText(String.format(resourceBundle.getString("updatesAvailableLabel.text"), updatesAvailableCount));
        if (updatesAvailableCount > 0) {
            if (defaultStatusComponent == null) {
                defaultStatusComponent = updatesAvailablePanel;
                if (activeStatusComponent == null) {
                    activeStatusComponent = defaultStatusComponent;
                    statusPanel.add(activeStatusComponent, BorderLayout.CENTER);
                }
            }
        } else {
            if (defaultStatusComponent != null) {
                if (activeStatusComponent == defaultStatusComponent) {
                    statusPanel.remove(activeStatusComponent);
                    activeStatusComponent = null;
                }
                defaultStatusComponent = null;
            }
        }
        statusPanel.revalidate();
        statusPanel.repaint();
    }

    public void setConnectionFailed() {
        if (defaultStatusComponent == null) {
            defaultStatusComponent = connectionFailedPanel;
            if (activeStatusComponent == null) {
                activeStatusComponent = defaultStatusComponent;
                statusPanel.add(activeStatusComponent, BorderLayout.CENTER);
            }
            statusPanel.revalidate();
            statusPanel.repaint();
        }
    }

    public void clearStatus() {
        if (activeStatusComponent != null) {
            statusPanel.remove(activeStatusComponent);
            activeStatusComponent = null;
            statusPanel.revalidate();
            statusPanel.repaint();
        }

        if (defaultStatusComponent != null) {
            defaultStatusComponent = null;
        }
    }

    public interface Controller extends CloseControlController {

        void performUpdateAll();

        void performRefresh();
    }
}
