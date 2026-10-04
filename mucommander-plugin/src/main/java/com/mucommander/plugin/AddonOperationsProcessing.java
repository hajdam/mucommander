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

import com.mucommander.plugin.update.AddonModification;
import com.mucommander.plugin.update.AddonModificationType;
import org.jspecify.annotations.NullMarked;

/**
 * Addon operations processing.
 */
@NullMarked
public interface AddonOperationsProcessing {

    /**
     * Adds new modification to processing queue.
     *
     * @param type addon modification operation type
     * @param moduleId module identifier
     */
    void addModification(AddonModificationType type, String moduleId);

    /**
     * Adds new modification to processing queue.
     *
     * @param modification addon modification operation
     */
    void addModification(AddonModification modification);

    /**
     * Returns true if module is included in local modules set.
     *
     * @param moduleId module identifier
     * @return true if module is local
     */
    boolean isLocalModule(String moduleId);
}
