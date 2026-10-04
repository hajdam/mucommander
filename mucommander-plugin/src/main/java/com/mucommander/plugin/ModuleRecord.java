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
import java.util.Optional;
import javax.swing.ImageIcon;
import org.jspecify.annotations.NullMarked;

/**
 * Interface for record about single module.
 */
@NullMarked
public interface ModuleRecord {

    /**
     * Returns module ID.
     *
     * @return module ID
     */
    String getModuleId();

    /**
     * Returns module type.
     *
     * @return module type
     */
    ModuleType getType();

    /**
     * Returns instance of the module.
     *
     * @return module instance
     */
    Module getModule();

    /**
     * Returns location of the module file.
     *
     * @return file location
     */
    ModuleFileLocation getFileLocation();

    /**
     * Returns module name.
     *
     * @return module name
     */
    String getName();

    /**
     * Returns module version.
     *
     * @return module version
     */
    String getVersion();

    /**
     * Returns module description.
     *
     * @return description text
     */
    Optional<String> getDescription();

    /**
     * Returns module icon.
     *
     * @return icon if present
     */
    Optional<ImageIcon> getIcon();

    /**
     * Returns module provider.
     *
     * @return module provider
     */
    Optional<String> getProvider();

    /**
     * Returns module homepage.
     *
     * @return module homepage
     */
    Optional<String> getHomepage();

    /**
     * Returns list of required dependency modules.
     *
     * @return list of dependency module identifiers
     */
    List<String> getDependencyModuleIds();

    /**
     * Returns list of required dependency libraries.
     *
     * @return list of dependency libraries
     */
    List<String> getDependencyLibraries();

    /**
     * Returns list of optional modules.
     *
     * @return list of module identifiers
     */
    List<String> getOptionalModuleIds();
}
