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

import java.awt.BorderLayout;
import java.util.Optional;
import java.util.ResourceBundle;

import com.mucommander.plugin.manager.LanguageUtils;
import org.jspecify.annotations.NullMarked;
import javax.swing.JComponent;
import com.mucommander.plugin.manager.operation.AddonModificationStep;

/**
 * Panel for addon installation / update operation.
 */
@NullMarked
public class AddonOperationPanel extends javax.swing.JPanel {

    protected final ResourceBundle resourceBundle = ResourceBundle.getBundle(LanguageUtils.getResourceBaseNameBundleByClass(AddonOperationPanel.class));

    protected final AddonOperationOverviewPanel overviewPanel = new AddonOperationOverviewPanel();
    protected final AddonOperationLicensePanel licensePanel = new AddonOperationLicensePanel();
    protected final AddonOperationDownloadPanel downloadPanel = new AddonOperationDownloadPanel();
    protected final AddonOperationSuccessPanel successPanel = new AddonOperationSuccessPanel();

    protected JComponent activePanel = null;

    public AddonOperationPanel() {
        initComponents();
        init();
    }

    private void init() {
        goToStep(AddonModificationStep.OVERVIEW);
    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    public void goToStep(AddonModificationStep step) {
        if (activePanel != null) {
            remove(activePanel);
        }

        switch (step) {
            case OVERVIEW:
                activePanel = overviewPanel;
                break;
            case LICENSE:
                activePanel = licensePanel;
                break;
            case DOWNLOAD:
                activePanel = downloadPanel;
                break;
            case SUCCESS:
                activePanel = successPanel;
                break;
            default:
                throw new AssertionError();
        }

        add(activePanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public Optional<JComponent> getActiveComponent() {
        return Optional.ofNullable(activePanel);
    }

    private void initComponents() {
        setLayout(new java.awt.BorderLayout());
    }
}
