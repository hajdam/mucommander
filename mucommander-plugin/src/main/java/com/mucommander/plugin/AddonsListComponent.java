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
import javax.swing.JComponent;

/**
 * Addons list component.
 */
@NullMarked
public interface AddonsListComponent {

    /**
     * Returns addons list component.
     *
     * @return addons list component
     */
    JComponent getComponent();

    /**
     * Sets component controller.
     *
     * @param controller component controller
     */
    void setController(AddonsListComponentController controller);

    /**
     * Sets addon management context.
     *
     * @param context addon management context
     */
    void setContext(AddonsManagementContext context);

    /**
     * Notifies item changed.
     */
    void notifyItemChanged();

    /**
     * Notifies items changed.
     */
    void notifyItemsChanged();
}
