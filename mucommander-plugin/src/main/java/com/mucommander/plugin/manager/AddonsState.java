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

import java.util.ArrayList;
import java.util.List;

import com.mucommander.plugin.AddonModuleFileLocation;
import com.mucommander.plugin.AddonRecord;
import com.mucommander.plugin.BasicModuleRecord;
import com.mucommander.plugin.DependencyRecord;
import com.mucommander.plugin.Module;
import com.mucommander.plugin.ModuleRecord;
import com.mucommander.plugin.RepositoryAddonRecord;
import com.mucommander.plugin.update.AddonUpdateChangesManagement;
import com.mucommander.plugin.update.LocalAddonUpdateChangesManager;
import org.jspecify.annotations.NullMarked;

/**
 * Addons target state including queued changes.
 */
@NullMarked
public class AddonsState {

    protected final List<AddonRecord> installedAddons = new ArrayList<>();
    protected final UpdateAvailabilityManager availableModuleUpdates = new UpdateAvailabilityManager();
    protected final AddonUpdateChangesManagement addonUpdateChanges;
    protected ApplicationModulesUsage applicationModulesUsage;

    public AddonsState() {
        addonUpdateChanges = new LocalAddonUpdateChangesManager();
    }

    public void init() {
        availableModuleUpdates.readConfigFile();
        addonUpdateChanges.readConfig();

        List<ModuleRecord> basicModulesList = new ArrayList<>();
        // TODO Read list of modules
        BasicModuleRecord record = new BasicModuleRecord("test.id", new Module() {}, getClass().getClassLoader());
        record.setProvider("muCommander");
        record.setName("Test Addon");
        record.setVersion("1.0");
        record.setHomepage("https://www.mucommander.com");
        record.setDescription("Example description");
        basicModulesList.add(record);

        for (ModuleRecord moduleRecord : basicModulesList) {
            RepositoryAddonRecord addonRecord = new RepositoryAddonRecord(moduleRecord.getModuleId(), moduleRecord.getName());
            addonRecord.setInstalled(true);
            addonRecord.setFileLocation(AddonModuleFileLocation.ADDON);
            addonRecord.setVersion(moduleRecord.getVersion());
            addonRecord.setProvider(moduleRecord.getProvider().orElse(null));
            addonRecord.setHomepage(moduleRecord.getHomepage().orElse(null));
            addonRecord.setDescription(moduleRecord.getDescription().orElse(null));
            addonRecord.setIcon(moduleRecord.getIcon().orElse(null));
            List<DependencyRecord> dependencyRecords = new ArrayList<>();
            for (String dependencyModuleId : moduleRecord.getDependencyModuleIds()) {
                dependencyRecords.add(new DependencyRecord(dependencyModuleId));
            }
            for (String dependencyLibraryId : moduleRecord.getDependencyLibraries()) {
                dependencyRecords.add(new DependencyRecord(DependencyRecord.Type.JAR_LIBRARY, dependencyLibraryId));
            }
            addonRecord.setDependencies(dependencyRecords);
            installedAddons.add(addonRecord);
            /*System.out.println(moduleRecord.getModuleId() + "," + moduleRecord.getName() + "," + moduleRecord.getDescription().orElse("") + "," + moduleRecord.getVersion() + "," + moduleRecord.getHomepage().orElse(""));
            for (DependencyRecord dependency : dependencyRecords) {
                System.out.println("- " + dependency.getType().name() + ", " + dependency.getId());
            } */
        }
        applicationModulesUsage = new ApplicationModulesUsage() {
            @Override
            public boolean hasModule(String moduleId) {
                return false; // TODO ((BasicModuleProvider) moduleProvider).hasModule(moduleId);
            }

            @Override
            public boolean hasLibrary(String libraryFileName) {
                return false; // TODO ((BasicModuleProvider) moduleProvider).hasLibrary(libraryFileName);
            }
        };
    }

    public List<AddonRecord> getInstalledAddons() {
        return installedAddons;
    }

    public UpdateAvailabilityManager getAvailableModuleUpdates() {
        return availableModuleUpdates;
    }

    public AddonUpdateChangesManagement getAddonUpdateChanges() {
        return addonUpdateChanges;
    }

    public ApplicationModulesUsage getApplicationModulesUsage() {
        return applicationModulesUsage;
    }

    public boolean isModuleInstalled(String moduleId) {
        return addonUpdateChanges.hasInstallAddon(moduleId) && !addonUpdateChanges.hasRemoveAddon(moduleId);
    }

    public boolean isModuleRemoved(String moduleId) {
        return addonUpdateChanges.hasRemoveAddon(moduleId) && !addonUpdateChanges.hasInstallAddon(moduleId);
    }

    public void addUpdateAvailabilityListener(UpdateAvailabilityManager.AvailableModulesChangeListener listener) {
        availableModuleUpdates.addChangeListener(listener);
    }
}
