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

/**
 * Controller for default control panel.
 */
@NullMarked
public interface DefaultControlController {

    /**
     * Invokes control action.
     *
     * @param actionType action type
     */
    void controlActionPerformed(ControlActionType actionType);

    /**
     * Interface for control component.
     */
    @NullMarked
    public interface DefaultControlComponent extends OkCancelControlComponent {

        /**
         * Invokes action button click.
         *
         * @param actionType action type
         */
        void performClick(ControlActionType actionType);

        /**
         * Sets action button enablement.
         *
         * @param actionType action type
         * @param enablement button enablement
         */
        void setActionEnabled(ControlActionType actionType, boolean enablement);
    }

    public static enum ControlActionType {
        OK, CANCEL
    }
}
