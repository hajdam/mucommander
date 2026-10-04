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

import com.mucommander.plugin.MultiStepControlController;
import com.mucommander.plugin.UiUtils;
import org.jspecify.annotations.NullMarked;
import javax.swing.JButton;
import org.jspecify.annotations.Nullable;

/**
 * Multi-step control panel for options dialogs.
 */
@NullMarked
public class MultiStepControlPanel extends FooterControlPanel implements MultiStepControlController.MultiStepControlComponent {

    protected @Nullable MultiStepControlController controller;
    protected JButton finishButton;
    protected JButton cancelButton;
    protected JButton nextButton;
    protected JButton previousButton;

    public MultiStepControlPanel() {
        super();
        init();
    }

    public MultiStepControlPanel(ResourceBundle resourceBundle) {
        super(resourceBundle);
        init();
    }

    private void init() {
        finishButton = new JButton();
        finishButton.setEnabled(false);
        finishButton.setText(resourceBundle.getString("finishButton.text"));
        finishButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            if (controller != null) {
                controller.controlActionPerformed(MultiStepControlController.ControlActionType.FINISH);
            }
        });
        addButton(finishButton, ButtonPosition.LAST_RIGHT);

        cancelButton = new JButton();
        cancelButton.setText(resourceBundle.getString("cancelButton.text"));
        cancelButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            if (controller != null) {
                controller.controlActionPerformed(MultiStepControlController.ControlActionType.CANCEL);
            }
        });
        addButton(cancelButton, ButtonPosition.LAST_RIGHT);

        nextButton = new JButton();
        nextButton.setEnabled(false);
        nextButton.setText(resourceBundle.getString("nextButton.text"));
        nextButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            if (controller != null) {
                controller.controlActionPerformed(MultiStepControlController.ControlActionType.NEXT);
            }
        });
        addButton(nextButton, ButtonPosition.FIRST_RIGHT);

        previousButton = new JButton();
        previousButton.setEnabled(false);
        previousButton.setText(resourceBundle.getString("previousButton.text"));
        previousButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            if (controller != null) {
                controller.controlActionPerformed(MultiStepControlController.ControlActionType.PREVIOUS);
            }
        });
        addButton(previousButton, ButtonPosition.FIRST_RIGHT);
    }

    public void setController(MultiStepControlController controller) {
        this.controller = controller;
    }

    @Override
    public void performClick(MultiStepControlController.ControlActionType actionType) {
        switch (actionType) {
            case FINISH: {
                UiUtils.doButtonClick(finishButton);
                break;
            }
            case CANCEL: {
                UiUtils.doButtonClick(cancelButton);
                break;
            }
            case NEXT: {
                UiUtils.doButtonClick(nextButton);
                break;
            }
            case PREVIOUS: {
                UiUtils.doButtonClick(previousButton);
                break;
            }
            default:
                throw new IllegalStateException("Illegal action type " + actionType.name());
        }
    }

    @Override
    public void invokeOkEvent() {
        performClick(MultiStepControlController.ControlActionType.FINISH);
    }

    @Override
    public void invokeCancelEvent() {
        performClick(MultiStepControlController.ControlActionType.CANCEL);
    }

    @Override
    public Optional<JButton> getDefaultButton() {
        return Optional.of(finishButton);
    }

    @Override
    public void setActionEnabled(MultiStepControlController.ControlActionType actionType, boolean enablement) {
        switch (actionType) {
            case FINISH: {
                finishButton.setEnabled(enablement);
                break;
            }
            case CANCEL: {
                cancelButton.setEnabled(enablement);
                break;
            }
            case NEXT: {
                nextButton.setEnabled(enablement);
                break;
            }
            case PREVIOUS: {
                previousButton.setEnabled(enablement);
                break;
            }
            default:
                throw new IllegalStateException("Illegal action type " + actionType.name());
        }
    }
}
