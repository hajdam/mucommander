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

import java.util.Optional;
import java.util.ResourceBundle;

import com.mucommander.plugin.DefaultControlController;
import com.mucommander.plugin.UiUtils;
import org.jspecify.annotations.NullMarked;
import javax.swing.JButton;
import org.jspecify.annotations.Nullable;

/**
 * Basic default control panel.
 */
@NullMarked
public class DefaultControlPanel extends FooterControlPanel implements DefaultControlController.DefaultControlComponent {

    protected @Nullable DefaultControlController controller;
    protected JButton okButton;
    protected JButton cancelButton;

    public DefaultControlPanel() {
        super();
        init();
    }

    public DefaultControlPanel(ResourceBundle resourceBundle) {
        super(resourceBundle);
        init();
    }

    private void init() {
        okButton = new JButton();

        okButton.setText(resourceBundle.getString("okButton.text"));
        okButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            if (controller != null) {
                controller.controlActionPerformed(DefaultControlController.ControlActionType.OK);
            }
        });
        addButton(okButton, ButtonPosition.LAST_RIGHT);

        cancelButton = new JButton();
        cancelButton.setText(resourceBundle.getString("cancelButton.text"));
        cancelButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            if (controller != null) {
                controller.controlActionPerformed(DefaultControlController.ControlActionType.CANCEL);
            }
        });
        addButton(cancelButton, ButtonPosition.LAST_RIGHT);
    }

    public void setController(DefaultControlController controller) {
        this.controller = controller;
    }

    @Override
    public void performClick(DefaultControlController.ControlActionType actionType) {
        UiUtils.doButtonClick(actionType == DefaultControlController.ControlActionType.OK ? okButton : cancelButton);
    }

    @Override
    public void invokeOkEvent() {
        performClick(DefaultControlController.ControlActionType.OK);
    }

    @Override
    public void invokeCancelEvent() {
        performClick(DefaultControlController.ControlActionType.CANCEL);
    }

    @Override
    public Optional<JButton> getDefaultButton() {
        return Optional.of(okButton);
    }

    @Override
    public void setActionEnabled(DefaultControlController.ControlActionType actionType, boolean enablement) {
        switch (actionType) {
            case OK: {
                okButton.setEnabled(enablement);
                break;
            }
            case CANCEL: {
                cancelButton.setEnabled(enablement);
                break;
            }
            default:
                throw new IllegalStateException("Illegal action type " + actionType.name());
        }
    }
}
