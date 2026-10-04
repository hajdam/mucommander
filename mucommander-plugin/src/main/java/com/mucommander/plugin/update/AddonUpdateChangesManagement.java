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
package com.mucommander.plugin.update;

import java.util.List;
import org.jspecify.annotations.NullMarked;

/**
 * Addon update changes management.
 */
@NullMarked
public interface AddonUpdateChangesManagement {

    List<String> getInstallAddons();

    List<String> getRemoveAddons();

    List<String> getUpdateFiles();

    List<String> getRemoveFiles();

    void addInstallAddon(String addonId);

    void removeInstallAddon(String addonId);

    boolean hasInstallAddon(String addonId);

    void addRemoveAddon(String addonId);

    void removeRemoveAddon(String addonId);

    boolean hasRemoveAddon(String addonId);

    void addUpdateFile(String fileName);

    void removeUpdateFile(String fileName);

    boolean hasUpdateFile(String fileName);

    void addRemoveFile(String fileName);

    void removeRemoveFile(String fileName);

    boolean hasRemoveFile(String fileName);

    void readConfig();

    void writeConfig();
}
