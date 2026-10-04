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
package com.mucommander.plugin.catalog.service;

import java.net.URL;
import java.util.List;

import com.mucommander.plugin.RepositoryAddonRecord;
import com.mucommander.plugin.UpdateRecord;
import org.jspecify.annotations.NullMarked;

/**
 * Addon catalog service.
 */
@NullMarked
public interface AddonCatalogService {

    /**
     * Checks whether service supports specific catalog version.
     *
     * @param version checked version
     * @return -1 for failure, greater number for revisions
     * @throws AddonCatalogServiceException when service fails
     */
    int checkStatus(String version) throws AddonCatalogServiceException;

    /**
     * Requests search for addons with option to specific search condition for
     * name.
     *
     * @param searchCondition search condition
     * @return list of found addons
     * @throws AddonCatalogServiceException when service fails
     */
    List<RepositoryAddonRecord> searchForAddons(String searchCondition) throws AddonCatalogServiceException;

    /**
     * Returns simplified record of specific addon with dependency / license info
     * only.
     *
     * @param moduleId module id
     * @return addon record
     * @throws AddonCatalogServiceException when service fails
     */
    RepositoryAddonRecord getAddonDependency(String moduleId) throws AddonCatalogServiceException;

    /**
     * Returns module filename for specific addon.
     *
     * @param moduleId module id
     * @return addon filename
     * @throws AddonCatalogServiceException when service fails
     */
    String getAddonFile(String moduleId) throws AddonCatalogServiceException;

    /**
     * Returns update records for all addons.
     *
     * @return update records
     * @throws AddonCatalogServiceException when service fails
     */
    List<UpdateRecord> getUpdateRecords() throws AddonCatalogServiceException;

    /**
     * Returns module details text.
     *
     * @param id module id
     * @return details text
     * @throws AddonCatalogServiceException when service fails
     */
    String getModuleDetails(String id) throws AddonCatalogServiceException;

    /**
     * Returns remote download URI for module file.
     *
     * @param remoteFilePath remote file path
     * @throws AddonCatalogServiceException when service fails
     * @return download URL
     */
    URL getFileDownloadUrl(String remoteFilePath) throws AddonCatalogServiceException;

    /**
     * Returns remote download URI for license file.
     *
     * @param remoteFilePath remote file path
     * @throws AddonCatalogServiceException when service fails
     * @return download URL
     */
    URL getLicenseDownloadUrl(String remoteFilePath) throws AddonCatalogServiceException;

    /**
     * Returns link to catalog main page URL.
     *
     * @return link URL
     */
    String getCatalogPageUrl();

//    CancellableOperation createIconsDownloadOperation(List<AddonRecord> records);
}
