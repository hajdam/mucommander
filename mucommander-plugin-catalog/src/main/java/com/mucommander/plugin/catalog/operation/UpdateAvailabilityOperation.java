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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mucommander.plugin.CancellableOperation;
import com.mucommander.plugin.UpdateRecord;
import org.jspecify.annotations.NullMarked;
import com.mucommander.plugin.catalog.service.AddonCatalogService;
import com.mucommander.plugin.catalog.service.AddonCatalogServiceException;

/**
 * Update availability operation.
 */
@NullMarked
public class UpdateAvailabilityOperation implements Runnable, CancellableOperation {

    protected final AddonCatalogService addonCatalogService;
    protected boolean cancelled = false;
    protected List<UpdateRecord> updateRecords;

    public UpdateAvailabilityOperation(AddonCatalogService addonCatalogService) {
        this.addonCatalogService = addonCatalogService;
    }

    @Override
    public void run() {
        try {
            updateRecords = addonCatalogService.getUpdateRecords();
        } catch (AddonCatalogServiceException ex) {
            Logger.getLogger(UpdateAvailabilityOperation.class.getName()).log(Level.SEVERE, null, ex);
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

    public List<UpdateRecord> getUpdateRecords() {
        return updateRecords;
    }

    public Map<String, String> getLatestVersions() {
        Map<String, String> latestVersions = new HashMap<>();
        if (updateRecords != null) {
            for (UpdateRecord record : updateRecords) {
                latestVersions.put(record.getModuleId(), record.getVersion());
            }
        }
        return latestVersions;
    }
}
