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
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mucommander.plugin.AddonModuleFileLocation;
import com.mucommander.plugin.AddonRecord;
import com.mucommander.plugin.CancellableOperation;
import com.mucommander.plugin.ProgressOperation;
import com.mucommander.plugin.TitledOperation;
import com.mucommander.plugin.manager.LanguageUtils;
import org.jspecify.annotations.NullMarked;
import com.mucommander.plugin.catalog.service.AddonCatalogService;
import com.mucommander.plugin.catalog.service.AddonCatalogServiceException;

/**
 * Operation to receive details about module from catalog.
 */
@NullMarked
public class CatalogModuleDetailOperation implements Runnable, CancellableOperation, ProgressOperation, TitledOperation {

    protected final ResourceBundle resourceBundle = ResourceBundle.getBundle(LanguageUtils.getResourceBaseNameBundleByClass(CatalogModuleDetailOperation.class));
    protected final AddonCatalogService addonCatalogService;
    protected final Output output;
    protected boolean cancelled = false;
    protected final AddonRecord addonRecord;

    public CatalogModuleDetailOperation(AddonCatalogService addonCatalogService, AddonRecord addonRecord, Output output) {
        this.addonCatalogService = addonCatalogService;
        this.addonRecord = addonRecord;
        this.output = output;
    }

    @Override
    public void run() {
        if (addonRecord.getFileLocation() == AddonModuleFileLocation.ADDON) {
            try {
                String moduleDetail = addonCatalogService.getModuleDetails(addonRecord.getId());
                output.outputModuleDetail(moduleDetail);
            } catch (AddonCatalogServiceException ex) {
                Logger.getLogger(CatalogModuleDetailOperation.class.getName()).log(Level.SEVERE, null, ex);
            }
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
        return resourceBundle.getString("catalogModuleDetailOperation");
    }

    @Override
    public int getOperationProgress() {
        return -1;
    }

    public interface Output {

        void outputModuleDetail(String details);
    }
}
