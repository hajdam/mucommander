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
package com.mucommander.plugin.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.mucommander.plugin.manager.LanguageUtils;
import org.jspecify.annotations.NullMarked;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 * Footer control panel.
 */
@NullMarked
public class FooterControlPanel extends JPanel {

    protected final ResourceBundle resourceBundle;

    protected GroupLayout.ParallelGroup verticalGroup;
    protected final List<JComponent> leftComponents = new ArrayList<>();
    protected final List<JComponent> rightComponents = new ArrayList<>();

    public FooterControlPanel() {
        this(ResourceBundle.getBundle(LanguageUtils.getResourceBaseNameBundleByClass(FooterControlPanel.class)));
    }

    public FooterControlPanel(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
        initComponents();
    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    private void initComponents() {
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        rebuildHorizontalGroup();

        verticalGroup = layout.createParallelGroup(GroupLayout.Alignment.BASELINE);
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(verticalGroup)
                                .addContainerGap())
        );
    }

    public void addButton(JButton button, ButtonPosition buttonPosition) {
        switch (buttonPosition) {
            case FIRST_LEFT:
                leftComponents.add(0, button);
                break;
            case LAST_LEFT:
                leftComponents.add(button);
                break;
            case FIRST_RIGHT:
                rightComponents.add(0, button);
                break;
            case LAST_RIGHT:
                rightComponents.add(button);
                break;
        }

        rebuildHorizontalGroup();
        verticalGroup.addComponent(button);
    }

    protected void rebuildHorizontalGroup() {
        GroupLayout layout = (GroupLayout) getLayout();
        GroupLayout.SequentialGroup horizontalGroup = layout.createSequentialGroup();
        if (!leftComponents.isEmpty()) {
            boolean first = true;
            for (JComponent component : leftComponents) {
                if (first) {
                    horizontalGroup.addContainerGap();
                    first = false;
                } else {
                    horizontalGroup.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
                }
                horizontalGroup.addComponent(component);
            }
        }
        horizontalGroup.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);
        if (!rightComponents.isEmpty()) {
            boolean first = true;
            for (JComponent component : rightComponents) {
                if (first) {
                    first = false;
                } else {
                    horizontalGroup.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
                }
                horizontalGroup.addComponent(component);
            }
            horizontalGroup.addContainerGap();
        }
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, horizontalGroup)
        );
    }

    public enum ButtonPosition {
        FIRST_LEFT,
        LAST_LEFT,
        FIRST_RIGHT,
        LAST_RIGHT
    }
}
