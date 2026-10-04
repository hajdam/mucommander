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

import java.util.ResourceBundle;

import com.mucommander.plugin.CancellableOperation;
import com.mucommander.plugin.TitledOperation;
import com.mucommander.plugin.UpdateAvailabilityManagement;
import com.mucommander.plugin.manager.LanguageUtils;
import org.jspecify.annotations.NullMarked;
import com.mucommander.plugin.catalog.service.AddonCatalogService;

/**
 * Operation to get available updates from catalog.
 */
@NullMarked
public class CatalogAvailableUpdatesOperation implements Runnable, CancellableOperation, TitledOperation {

    protected final ResourceBundle resourceBundle = ResourceBundle.getBundle(LanguageUtils.getResourceBaseNameBundleByClass(CatalogAvailableUpdatesOperation.class));
    protected final UpdateAvailabilityManagement updateAvailabilityManager;
    protected final AddonCatalogService addonCatalogService;
    protected final int catalogRevision;
    protected final Output output;
    protected boolean cancelled = false;

    public CatalogAvailableUpdatesOperation(AddonCatalogService addonCatalogService, UpdateAvailabilityManagement updateAvailabilityManager, int catalogRevision, Output output) {
        this.addonCatalogService = addonCatalogService;
        this.updateAvailabilityManager = updateAvailabilityManager;
        this.catalogRevision = catalogRevision;
        this.output = output;
    }

    @Override
    public void run() {
        if (catalogRevision > updateAvailabilityManager.getRevision()) {
            UpdateAvailabilityOperation availabilityOperation = new UpdateAvailabilityOperation(addonCatalogService);
            availabilityOperation.run();
            // TODO
            // updateAvailabilityManager.setLatestVersion(catalogRevision, availabilityOperation.getLatestVersions());
            // updateAvailabilityManager.writeConfigFile();
            output.latestVersionsChanged();
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

    @NullMarked
    public interface Output {

        void latestVersionsChanged();
    }
}
