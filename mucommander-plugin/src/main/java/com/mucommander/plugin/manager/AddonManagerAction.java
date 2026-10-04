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
package com.mucommander.plugin.manager;

import java.awt.Component;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import com.mucommander.plugin.AddonResolutionManagement;
import com.mucommander.plugin.TabPagesComponent;
import com.mucommander.plugin.UiUtils;
import org.jspecify.annotations.NullMarked;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;

import com.mucommander.plugin.manager.gui.AddonsManagerPanel;
import com.mucommander.plugin.manager.gui.AddonsManagerControlPanel;

/**
 * Addon manager action.
 */
@NullMarked
public class AddonManagerAction extends AbstractAction {

    protected List<TabPagesComponent> pages = new ArrayList<>();
    protected Component parentComponent;
    protected AddonResolutionManagement resolutionManagement = new LocalAddonResolutionManagement();

    public AddonManagerAction() {
    }

    public void addPage(TabPagesComponent page) {
        pages.add(page);
    }

    public void setParentComponent(Component parentComponent) {
        this.parentComponent = parentComponent;
    }

    public void setResolutionManagement(AddonResolutionManagement resolutionManagement) {
        this.resolutionManagement = resolutionManagement;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        AddonsManagerControlPanel controlPanel = new AddonsManagerControlPanel();

        AddonManager addonManager = new AddonManager();
        addonManager.setResolutionManagement(resolutionManagement);
        for (TabPagesComponent page : pages) {
            addonManager.addPage(page);
        }
        addonManager.registerBasicAddonManager();
        AddonsManagerPanel addonManagerPanel = addonManager.getManagerPanel();
        addonManager.setStatusListener(new AddonManager.AddonManagerStatusListener() {
            @Override
            public void setProgressStatus(String status) {
                controlPanel.setProgressStatus(status);
            }

            @Override
            public void setStatusLabel(String text) {
                controlPanel.setStatusLabel(text);
            }

            @Override
            public void clear() {
                controlPanel.setStatusLabel("");
            }

            @Override
            public void setAvailableUpdates(int updatesCount) {
                controlPanel.setAvailableUpdates(updatesCount);
            }

            @Override
            public void setManualOnlyMode() {
                controlPanel.showManualOnlyWarning();
            }

            @Override
            public void setCatalogNotAvailable() {
                controlPanel.setConnectionFailed();
            }
        });
        addonManager.refreshContent();

        JDialog dialog = UiUtils.createDialog(parentComponent instanceof Frame ? (Frame) parentComponent : null, addonManagerPanel.getResourceBundle().getString("dialog.title"), addonManagerPanel, controlPanel);
        controlPanel.setController(new AddonsManagerControlPanel.Controller() {
            @Override
            public void performRefresh() {
                controlPanel.clearStatus();
                addonManager.refreshContent();
            }

            @Override
            public void performUpdateAll() {
                addonManager.updateAll();
            }

            @Override
            public void controlActionPerformed() {
                dialog.setVisible(false);;
            }
        });
        addonManager.setParentWindow(dialog);
        dialog.setLocationRelativeTo(parentComponent);
        dialog.setVisible(true);
    }
}
