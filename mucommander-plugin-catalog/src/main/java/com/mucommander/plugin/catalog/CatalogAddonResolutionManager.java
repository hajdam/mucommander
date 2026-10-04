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
package com.mucommander.plugin.catalog;

import java.net.URL;

import com.mucommander.plugin.AddonOperationsProcessing;
import com.mucommander.plugin.AddonResolutionException;
import com.mucommander.plugin.AddonResolutionManagement;
import com.mucommander.plugin.RepositoryAddonRecord;
import com.mucommander.plugin.catalog.service.AddonCatalogServiceException;
import com.mucommander.plugin.catalog.service.DefaultAddonCatalogService;
import com.mucommander.plugin.manager.operation.LocalAddonModificationType;
import org.jspecify.annotations.NullMarked;

/**
 * Catalog addon resolution manager.
 */
@NullMarked
public class CatalogAddonResolutionManager implements AddonResolutionManagement {
    
    protected DefaultAddonCatalogService addonCatalogService;

    public CatalogAddonResolutionManager(DefaultAddonCatalogService addonCatalogService) {
        this.addonCatalogService = addonCatalogService;
    }

    @Override
    public void installAddon(AddonOperationsProcessing processing, String moduleId) throws AddonResolutionException {
        if (processing.isLocalModule(moduleId)) {
            processing.addModification(LocalAddonModificationType.INSTALL_ADDON, moduleId);
            return;
        }

        try {
            processing.addModification(LocalAddonModificationType.DOWNLOAD_MODULE, addonCatalogService.getAddonFile(moduleId));
            processing.addModification(LocalAddonModificationType.INSTALL_ADDON, moduleId);
        } catch (AddonCatalogServiceException ex) {
            throw new AddonResolutionException(ex);
        }
    }

    @Override
    public void updateAddon(AddonOperationsProcessing processing, String moduleId) throws AddonResolutionException {
        if (processing.isLocalModule(moduleId)) {
            return;
        }

        try {
            processing.addModification(LocalAddonModificationType.DOWNLOAD_MODULE, addonCatalogService.getAddonFile(moduleId));
        } catch (AddonCatalogServiceException ex) {
            throw new AddonResolutionException(ex);
        }
    }

    @Override
    public void installDependency(AddonOperationsProcessing processing, String moduleId) throws AddonResolutionException {
        if (processing.isLocalModule(moduleId)) {
            processing.addModification(LocalAddonModificationType.DEPENDENCY_ADDON, moduleId);
            return;
        }

        try {
            processing.addModification(LocalAddonModificationType.DOWNLOAD_MODULE, addonCatalogService.getAddonFile(moduleId));
            processing.addModification(LocalAddonModificationType.DEPENDENCY_ADDON, moduleId);
        } catch (AddonCatalogServiceException ex) {
            throw new AddonResolutionException(ex);
        }
    }

    @Override
    public RepositoryAddonRecord getAddonDependency(String moduleId) throws AddonResolutionException {
        try {
            return addonCatalogService.getAddonDependency(moduleId);
        } catch (AddonCatalogServiceException ex) {
            throw new AddonResolutionException(ex);
        }
    }

    @Override
    public URL getFileDownloadUrl(String remoteFilePath) throws AddonResolutionException {
        try {
            return addonCatalogService.getFileDownloadUrl(remoteFilePath);
        } catch (AddonCatalogServiceException ex) {
            throw new AddonResolutionException(ex);
        }
    }

    @Override
    public URL getLicenseDownloadUrl(String remoteFilePath) throws AddonResolutionException {
        try {
            return addonCatalogService.getLicenseDownloadUrl(remoteFilePath);
        } catch (AddonCatalogServiceException ex) {
            throw new AddonResolutionException(ex);
        }
    }
}
