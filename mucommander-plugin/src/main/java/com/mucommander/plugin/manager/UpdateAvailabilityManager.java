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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mucommander.conf.PlatformManager;
import com.mucommander.plugin.AddonRecord;
import com.mucommander.plugin.UpdateAvailabilityManagement;
import com.mucommander.plugin.VersionUtils;
import org.jspecify.annotations.NullMarked;

/**
 * Manager for available module updates.
 */
@NullMarked
public class UpdateAvailabilityManager implements UpdateAvailabilityManagement {

    protected static final String MODULE_UPDATES_FILE = "available-updates.cfg";
    protected final Map<String, String> latestVersions = new HashMap<>();
    protected int revision = -1;
    protected final List<AvailableModulesChangeListener> changeListeners = new ArrayList<>();

    @Override
    public int getRevision() {
        return revision;
    }

    /**
     * Checks whether never version of module is available for update.
     *
     * @param moduleId module id
     * @param version current module version
     * @return true if never version is available
     */
    @Override
    public boolean isUpdateAvailable(String moduleId, String version) {
        String latestVersion = latestVersions.get(moduleId);
        if (latestVersion != null) {
            return VersionUtils.isGreaterThan(latestVersion, version);
        }
        return false;
    }

    /**
     * Applies availability state to records.
     *
     * @param addonRecord target record
     */
    @Override
    public void applyTo(AddonRecord addonRecord) {
        addonRecord.setUpdateAvailable(isUpdateAvailable(addonRecord.getId(), addonRecord.getVersion()));
    }

    public void setLatestVersion(int revision, Map<String, String> latestVersions) {
        this.revision = revision;
        this.latestVersions.clear();
        this.latestVersions.putAll(latestVersions);
        notifyChanged();
    }

    public void readConfigFile() {
        File changesConfigFile = new File(PlatformManager.getPreferencesFolder().getAbsolutePath(), MODULE_UPDATES_FILE);
        latestVersions.clear();
        revision = -1;
        if (changesConfigFile.exists()) {
            String line;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(changesConfigFile)))) {
                line = reader.readLine();
                revision = Integer.parseInt(line);
                do {
                    line = reader.readLine();
                    if (line != null && !line.isEmpty()) {
                        int versionPos = line.indexOf(":");
                        latestVersions.put(line.substring(0, versionPos), line.substring(versionPos + 1));
                    }
                } while (line != null);
            } catch (NumberFormatException | IOException ex) {
                Logger.getLogger(UpdateAvailabilityManager.class.getName()).log(Level.SEVERE, "Failed to read modules update cache", ex);
            }
        }
    }

    public void writeConfigFile() {
        File configDirectory = new File(PlatformManager.getPreferencesFolder().getAbsolutePath());
        if (!configDirectory.isDirectory()) {
            configDirectory.mkdirs();
        }
        File changesConfigFile = new File(configDirectory, MODULE_UPDATES_FILE);
        try (OutputStreamWriter writer = new FileWriter(changesConfigFile)) {
            writer.write(revision + "\r\n");
            for (Map.Entry<String, String> entry : latestVersions.entrySet()) {
                writer.write(entry.getKey() + ":" + entry.getValue() + "\r\n");
            }
        } catch (IOException ex) {
            Logger.getLogger(UpdateAvailabilityManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addChangeListener(AvailableModulesChangeListener listener) {
        changeListeners.add(listener);
    }

    public void removeChangeListener(AvailableModulesChangeListener listener) {
        changeListeners.remove(listener);
    }

    public void notifyChanged() {
        for (AvailableModulesChangeListener changeListener : changeListeners) {
            changeListener.changed(this);
        }
    }

    @NullMarked
    public interface AvailableModulesChangeListener {

        void changed(UpdateAvailabilityManager availableModuleUpdates);
    }
}
