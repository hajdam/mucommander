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

import java.util.List;
import org.jspecify.annotations.NullMarked;

/**
 * Addons management local state interface.
 */
@NullMarked
public interface AddonsManagementLocalState extends AddonsManagementContext {

    /**
     * Returns list of installed addons.
     *
     * @return installed addons
     */
    List<AddonRecord> getInstalledAddons();

    /**
     * Returns true if module is installed.
     *
     * @param moduleId module identifier
     * @return true if installed
     */
    boolean isModuleInstalled(String moduleId);

    /**
     * Returns true if module is removed.
     *
     * @param moduleId module identifier
     * @return true if removed
     */
    boolean isModuleRemoved(String moduleId);
}
