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
package com.mucommander.plugin.manager;

import java.net.URL;

import com.mucommander.plugin.AddonOperationsProcessing;
import com.mucommander.plugin.RepositoryAddonRecord;
import com.mucommander.plugin.AddonResolutionManagement;
import com.mucommander.plugin.AddonResolutionException;
import com.mucommander.plugin.manager.operation.LocalAddonModificationType;
import org.jspecify.annotations.NullMarked;

/**
 * Local addon resolution service.
 */
@NullMarked
public class LocalAddonResolutionManagement implements AddonResolutionManagement {

    @Override
    public void installAddon(AddonOperationsProcessing processing, String moduleId) throws AddonResolutionException {
        if (processing.isLocalModule(moduleId)) {
            processing.addModification(LocalAddonModificationType.INSTALL_ADDON, moduleId);
            return;
        }

        throw new AddonResolutionException("Missing module " + moduleId);
    }

    @Override
    public void updateAddon(AddonOperationsProcessing processing, String moduleId) throws AddonResolutionException {
        if (processing.isLocalModule(moduleId)) {
            return;
        }

        throw new AddonResolutionException("Missing module " + moduleId);
    }

    @Override
    public void installDependency(AddonOperationsProcessing processing, String moduleId) throws AddonResolutionException {
        if (processing.isLocalModule(moduleId)) {
            processing.addModification(LocalAddonModificationType.DEPENDENCY_ADDON, moduleId);
            return;
        }

        throw new AddonResolutionException("Missing module " + moduleId);
    }

    @Override
    public RepositoryAddonRecord getAddonDependency(String moduleId) throws AddonResolutionException {
        throw new AddonResolutionException("Dependency resolution not available");
    }

    @Override
    public URL getFileDownloadUrl(String remoteFilePath) throws AddonResolutionException {
        throw new AddonResolutionException("Download not supported");
    }

    @Override
    public URL getLicenseDownloadUrl(String remoteFilePath) throws AddonResolutionException {
        throw new AddonResolutionException("Download not supported");
    }
}
