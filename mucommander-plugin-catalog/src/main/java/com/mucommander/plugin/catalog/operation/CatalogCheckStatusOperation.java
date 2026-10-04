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

import com.mucommander.plugin.CancellableOperation;
import com.mucommander.plugin.TitledOperation;
import com.mucommander.plugin.manager.LanguageUtils;
import org.jspecify.annotations.NullMarked;
import com.mucommander.plugin.catalog.service.AddonCatalogService;
import com.mucommander.plugin.catalog.service.AddonCatalogServiceException;

/**
 * Operation to check status of catalog.
 */
@NullMarked
public class CatalogCheckStatusOperation implements Runnable, CancellableOperation, TitledOperation {

    protected final ResourceBundle resourceBundle = ResourceBundle.getBundle(LanguageUtils.getResourceBaseNameBundleByClass(CatalogCheckStatusOperation.class));
    protected final AddonCatalogService addonCatalogService;
    protected final Output output;
    protected boolean cancelled = false;

    public CatalogCheckStatusOperation(AddonCatalogService addonCatalogService, Output output) {
        this.addonCatalogService = addonCatalogService;
        this.output = output;
    }

    @Override
    public void run() {
        try {
            String releaseString = "0.0"; // TODO get app version
            int catalogRevision = addonCatalogService.checkStatus(releaseString);
            output.outputStatus(catalogRevision);
        } catch (AddonCatalogServiceException ex) {
            Logger.getLogger(CatalogCheckStatusOperation.class.getName()).log(Level.SEVERE, "Status check failed", ex);
            output.outputStatus(-1);
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

    public interface Output {

        void outputStatus(int status);
    }
}
