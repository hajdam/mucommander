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
package com.mucommander.plugin.manager.operation.service;

import java.awt.Component;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mucommander.plugin.AddonRecord;
import com.mucommander.plugin.AddonResolutionManagement;
import com.mucommander.plugin.AddonResolutionException;
import com.mucommander.plugin.RepositoryAddonRecord;
import com.mucommander.plugin.manager.LanguageUtils;
import com.mucommander.plugin.operation.AddonCartOperation;
import com.mucommander.plugin.operation.CartOperation;
import com.mucommander.plugin.update.AddonUpdateChangesManagement;
import org.jspecify.annotations.NullMarked;
import com.mucommander.plugin.manager.operation.AddonModificationsOperation;
import com.mucommander.plugin.manager.AddonManager;
import com.mucommander.plugin.manager.ApplicationModulesUsage;
import org.jspecify.annotations.Nullable;

/**
 * Addon operation service.
 */
@NullMarked
public class AddonOperationService {

    protected java.util.ResourceBundle resourceBundle = ResourceBundle.getBundle(LanguageUtils.getResourceBaseNameBundleByClass(AddonOperationService.class));

    protected final AddonManager addonManager;
    protected @Nullable AddonResolutionManagement resolutionManagement;

    public AddonOperationService(AddonManager addonManager) {
        this.addonManager = addonManager;
    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    public void setResolutionManagement(AddonResolutionManagement resolutionManagement) {
        this.resolutionManagement = resolutionManagement;
    }

    public AddonModificationsOperation performAddonOperations(List<CartOperation> operations) {
        AddonModificationsOperation modifications = createOperation();
        for (CartOperation operation : operations) {
            if (!(operation instanceof AddonCartOperation)) {
                throw new IllegalStateException();
            }
            
            AddonCartOperation addonOperation = (AddonCartOperation) operation;
            switch (addonOperation.getVariant()) {
                case INSTALL:
                    modifications.installItem(addonOperation.getRecord());
                    break;
                case UPDATE:
                    AddonRecord item = addonOperation.getRecord();
                    modifications.updateItem(item, item);
                    break;
                case REMOVE:
                    modifications.removeItem(addonOperation.getRecord());
                    break;
            }
        }
        return modifications;
    }

    public AddonModificationsOperation installItem(AddonRecord item, Component parentComponent) {
        AddonModificationsOperation operation = createOperation();
        operation.installItem(item);
        return operation;
    }

    public AddonModificationsOperation updateItem(AddonRecord item) {
        AddonModificationsOperation operation = createOperation();
        RepositoryAddonRecord addonRecord;
        try {
            addonRecord = resolutionManagement.getAddonDependency(item.getId());
            operation.updateItem(addonRecord, item);
        } catch (AddonResolutionException ex) {
            Logger.getLogger(AddonOperationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return operation;
    }

    public AddonModificationsOperation removeItem(AddonRecord item) {
        AddonModificationsOperation operation = createOperation();
        operation.removeItem(item);
        return operation;
    }

    public AddonModificationsOperation installAddons(Set<String> toInstall) {
        List<AddonRecord> installedAddons = addonManager.getInstalledAddons();
        AddonModificationsOperation operation = createOperation();
        if (toInstall.isEmpty()) {
            for (AddonRecord addon : installedAddons) {
                if (addon.isUpdateAvailable()) {
                    RepositoryAddonRecord addonRecord;
                    try {
                        addonRecord = resolutionManagement.getAddonDependency(addon.getId());
                        operation.updateItem(addonRecord, addon);
                    } catch (AddonResolutionException ex) {
                        Logger.getLogger(AddonOperationService.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        } else {
            for (String addonId : toInstall) {
                RepositoryAddonRecord addonRecord;
                try {
                    addonRecord = resolutionManagement.getAddonDependency(addonId);
                    operation.installItem(addonRecord);
                } catch (AddonResolutionException ex) {
                    Logger.getLogger(AddonOperationService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return operation;
    }

    public AddonModificationsOperation updateAddons(Set<String> toUpdate) {
        List<AddonRecord> installedAddons = addonManager.getInstalledAddons();
        AddonModificationsOperation operation = createOperation();
        if (toUpdate.isEmpty()) {
            for (AddonRecord addon : installedAddons) {
                if (addon.isUpdateAvailable()) {
                    RepositoryAddonRecord addonRecord;
                    try {
                        addonRecord = resolutionManagement.getAddonDependency(addon.getId());
                        operation.updateItem(addonRecord, addon);
                    } catch (AddonResolutionException ex) {
                        Logger.getLogger(AddonOperationService.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        } else {
            for (AddonRecord addon : installedAddons) {
                if (toUpdate.contains(addon.getId())) {
                    RepositoryAddonRecord addonRecord;
                    try {
                        addonRecord = resolutionManagement.getAddonDependency(addon.getId());
                        operation.updateItem(addonRecord, addon);
                    } catch (AddonResolutionException ex) {
                        Logger.getLogger(AddonOperationService.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return operation;
    }

    private AddonModificationsOperation createOperation() {
        AddonUpdateChangesManagement addonUpdateChanges = addonManager.getAddonUpdateChanges();
        ApplicationModulesUsage applicationModulesUsage = addonManager.getApplicationModulesUsage();
        return new AddonModificationsOperation(resolutionManagement, applicationModulesUsage, addonUpdateChanges);
    }
}
