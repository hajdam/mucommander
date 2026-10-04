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

import java.net.URL;

import com.mucommander.plugin.update.AddonModification;
import org.jspecify.annotations.NullMarked;

/**
 * Addon related resolution management.
 */
@NullMarked
public interface AddonResolutionManagement {

    /**
     * Resolves addon installation request.
     *
     * @param processing operations processing queue
     * @param moduleId module id
     * @throws AddonResolutionException when service fails
     */
    void installAddon(AddonOperationsProcessing processing, String moduleId) throws AddonResolutionException;

    /**
     * Resolves addon update request.
     *
     * @param processing operations processing queue
     * @param moduleId module id
     * @throws AddonResolutionException when service fails
     */
    void updateAddon(AddonOperationsProcessing processing, String moduleId) throws AddonResolutionException;

    /**
     * Resolves dependency installation request.
     *
     * @param processing operations processing queue
     * @param moduleId module id
     * @throws AddonResolutionException when service fails
     */
    void installDependency(AddonOperationsProcessing processing, String moduleId) throws AddonResolutionException;

    /**
     * Returns simplified record of specific addon with dependency / license
     * info only.
     *
     * @param moduleId module id
     * @return addon record
     * @throws AddonResolutionException when service fails
     */
    RepositoryAddonRecord getAddonDependency(String moduleId) throws AddonResolutionException;

    /**
     * Returns remote download URI for module file.
     *
     * @param remoteFilePath remote file path
     * @throws AddonResolutionException when service fails
     * @return download URL
     */
    URL getFileDownloadUrl(String remoteFilePath) throws AddonResolutionException;

    /**
     * Returns remote download URI for license file.
     *
     * @param remoteFilePath remote file path
     * @throws AddonResolutionException when service fails
     * @return download URL
     */
    URL getLicenseDownloadUrl(String remoteFilePath) throws AddonResolutionException;
}
