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

import java.util.ResourceBundle;
import javax.swing.JComponent;

import com.mucommander.plugin.manager.LanguageUtils;
import org.jspecify.annotations.NullMarked;

/**
 * Panel for addons filter panel.
 */
@NullMarked
public class AddonsFilterPanel extends javax.swing.JPanel {

    protected final ResourceBundle resourceBundle = ResourceBundle.getBundle(LanguageUtils.getResourceBaseNameBundleByClass(AddonsFilterPanel.class));

    private javax.swing.JTabbedPane tabbedPane;

    public AddonsFilterPanel() {
        initComponents();
        init();
    }

    private void init() {
    }
    
    public void addFilter(String name, JComponent component) {
        tabbedPane.addTab(name, component);
    }

    private void initComponents() {

        tabbedPane = new javax.swing.JTabbedPane();

        setPreferredSize(new java.awt.Dimension(473, 373));
        setLayout(new java.awt.BorderLayout());
        add(tabbedPane, java.awt.BorderLayout.CENTER);
    }
}
