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
package com.mucommander.plugin.catalog.operation;

import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mucommander.plugin.AddonsManagementLocalState;
import com.mucommander.plugin.CancellableOperation;
import com.mucommander.plugin.ProgressOperation;
import com.mucommander.plugin.RepositoryAddonRecord;
import com.mucommander.plugin.TitledOperation;
import com.mucommander.plugin.UpdateAvailabilityManagement;
import com.mucommander.plugin.manager.LanguageUtils;
import org.jspecify.annotations.NullMarked;
import com.mucommander.plugin.catalog.service.AddonCatalogService;
import com.mucommander.plugin.catalog.service.AddonCatalogServiceException;

/**
 * Operation to search in catalog.
 */
@NullMarked
public class CatalogSearchOperation implements Runnable, CancellableOperation, ProgressOperation, TitledOperation {

    protected final ResourceBundle resourceBundle = ResourceBundle.getBundle(LanguageUtils.getResourceBaseNameBundleByClass(CatalogSearchOperation.class));
    protected final AddonsManagementLocalState localState;
    protected final UpdateAvailabilityManagement updateAvailabilityModules;
    protected final AddonCatalogService addonCatalogService;
    protected final Output output;
    protected boolean cancelled = false;
    protected final String searchCondition;

    public CatalogSearchOperation(AddonCatalogService addonCatalogService, AddonsManagementLocalState localState, UpdateAvailabilityManagement updateAvailabilityModules, String searchCondition, Output output) {
        this.addonCatalogService = addonCatalogService;
        this.localState = localState;
        this.updateAvailabilityModules = updateAvailabilityModules;
        this.searchCondition = searchCondition;
        this.output = output;
    }

    @Override
    public void run() {
        try {
            List<RepositoryAddonRecord> searchResult = addonCatalogService.searchForAddons(searchCondition);
            for (int i = searchResult.size() - 1; i >= 0; i--) {
                RepositoryAddonRecord record = searchResult.get(i);
                // TODO
                /*ModuleProvider moduleProvider = App.getModuleProvider();
                if (((BasicModuleProvider) moduleProvider).hasModule(record.getId()) && !localState.isModuleRemoved(record.getId())) {
                    searchResult.remove(i);
                } else {
                    if (updateAvailabilityModules.getRevision() != -1) {
                        record.setUpdateAvailable(updateAvailabilityModules.isUpdateAvailable(record.getId(), record.getVersion()));
                    }
                } */
            }
            output.outputItems(searchResult);
        } catch (AddonCatalogServiceException ex) {
            Logger.getLogger(CatalogSearchOperation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void cancelOperation() {
        cancelled = true;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public String getTitle() {
        return resourceBundle.getString("operation.name");
    }

    @Override
    public int getOperationProgress() {
        return -1;
    }

    public interface Output {

        void outputItems(List<RepositoryAddonRecord> addonItems);
    }
}
