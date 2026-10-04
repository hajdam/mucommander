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
package com.mucommander.plugin;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Window;

/**
 * Utility static methods usable for UI.
 */
@NullMarked
public class UiUtils {

    private static final int BUTTON_CLICK_TIME = 150;

    private UiUtils() {
    }

    /**
     * Performs visually visible click on the button component.
     *
     * @param button button component
     */
    public static void doButtonClick(JButton button) {
        button.doClick(BUTTON_CLICK_TIME);
    }

    public static JDialog createDialog(String title, JComponent component, JComponent controlPanel) {
        return createDialog(null, title, component, controlPanel);
    }

    public static JDialog createDialog(@Nullable Window window, String title, JComponent component, JComponent controlPanel) {
        JPanel dialogPanel = new JPanel(new BorderLayout());
        JDialog dialog = window != null ? new JDialog(window, Dialog.ModalityType.APPLICATION_MODAL) : new JDialog();
        dialog.setContentPane(dialogPanel);
        Dimension size = component.getPreferredSize();
        if (size.width < 10) {
            size.width = 400;
        }
        if (size.height < 10) {
            size.height = 200;
        }
        dialog.add(component);
        dialogPanel.setPreferredSize(new Dimension(size.width, size.height + controlPanel.getHeight()));

        dialogPanel.add(component, BorderLayout.CENTER);
        dialogPanel.add(controlPanel, BorderLayout.SOUTH);
        dialog.setTitle(title);
        dialog.pack();
        return dialog;
    }
}
