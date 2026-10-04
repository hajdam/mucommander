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
package com.mucommander.plugin.update;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mucommander.conf.PlatformManager;
import com.mucommander.plugin.AddonUtils;
import org.jspecify.annotations.NullMarked;

/**
 * Addon update changes manager.
 */
@NullMarked
public class LocalAddonUpdateChangesManager implements AddonUpdateChangesManagement {

    protected final List<String> installAddons = new ArrayList<>();
    protected final List<String> removeAddons = new ArrayList<>();
    protected final List<String> updateFiles = new ArrayList<>();
    protected final List<String> removeFiles = new ArrayList<>();

    @Override
    public List<String> getInstallAddons() {
        return installAddons;
    }

    @Override
    public List<String> getRemoveAddons() {
        return removeAddons;
    }

    @Override
    public List<String> getUpdateFiles() {
        return updateFiles;
    }

    @Override
    public List<String> getRemoveFiles() {
        return removeFiles;
    }

    @Override
    public void addInstallAddon(String addonId) {
        installAddons.add(addonId);
    }

    @Override
    public void removeInstallAddon(String addonId) {
        installAddons.remove(addonId);
    }

    @Override
    public boolean hasInstallAddon(String addonId) {
        return installAddons.contains(addonId);
    }

    @Override
    public void addRemoveAddon(String addonId) {
        removeAddons.add(addonId);
    }

    @Override
    public void removeRemoveAddon(String addonId) {
        removeAddons.remove(addonId);
    }

    @Override
    public boolean hasRemoveAddon(String addonId) {
        return removeAddons.contains(addonId);
    }

    @Override
    public void addUpdateFile(String fileName) {
        updateFiles.add(fileName);
    }

    @Override
    public void removeUpdateFile(String fileName) {
        updateFiles.remove(fileName);
    }

    @Override
    public boolean hasUpdateFile(String fileName) {
        return updateFiles.contains(fileName);
    }

    @Override
    public void addRemoveFile(String fileName) {
        removeFiles.add(fileName);
    }

    @Override
    public void removeRemoveFile(String fileName) {
        removeFiles.remove(fileName);
    }

    @Override
    public boolean hasRemoveFile(String fileName) {
        return removeFiles.contains(fileName);
    }

    @Override
    public void readConfig() {
        File targetDirectory = new File(PlatformManager.getPreferencesFolder().getAbsolutePath(), AddonUtils.ADDONS_UPDATE_DIRECTORY);
        File changesConfigFile = new File(targetDirectory, AddonUtils.ADDONS_CHANGES_FILE);
        installAddons.clear();
        removeAddons.clear();
        updateFiles.clear();
        removeFiles.clear();
        if (changesConfigFile.exists()) {
            String line = null;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(changesConfigFile)))) {
                do {
                    line = reader.readLine();
                    if (line != null && !line.isEmpty()) {
                        int prefixEnd = line.indexOf(":");
                        String prefix = line.substring(0, prefixEnd);
                        String value = line.substring(prefixEnd + 1);
                        if (ChangeType.INSTALL_ADDON.name().equals(prefix)) {
                            installAddons.add(value);
                        } else if (ChangeType.REMOVE_ADDON.name().equals(prefix)) {
                            removeAddons.add(value);
                        } else if (ChangeType.UPDATE_FILE.name().equals(prefix)) {
                            updateFiles.add(value);
                        } else if (ChangeType.REMOVE_FILE.name().equals(prefix)) {
                            removeFiles.add(value);
                        }
                    }
                } while (line != null);
            } catch (IOException ex) {
                Logger.getLogger(LocalAddonUpdateChangesManager.class.getName()).log(Level.SEVERE, "Failed to read config file " + line, ex);
            }
        }
    }

    @Override
    public void writeConfig() {
        File targetDirectory = new File(PlatformManager.getPreferencesFolder().getAbsolutePath(), AddonUtils.ADDONS_UPDATE_DIRECTORY);
        File changesConfigFile = new File(targetDirectory, AddonUtils.ADDONS_CHANGES_FILE);
        try (OutputStreamWriter writer = new FileWriter(changesConfigFile)) {
            String prefix = ChangeType.INSTALL_ADDON.name() + ":";
            for (String line : installAddons) {
                writer.write(prefix + line + "\r\n");
            }
            prefix = ChangeType.REMOVE_ADDON.name() + ":";
            for (String line : removeAddons) {
                writer.write(prefix + line + "\r\n");
            }
            prefix = ChangeType.UPDATE_FILE.name() + ":";
            for (String line : updateFiles) {
                writer.write(prefix + line + "\r\n");
            }
            prefix = ChangeType.REMOVE_FILE.name() + ":";
            for (String line : removeFiles) {
                writer.write(prefix + line + "\r\n");
            }
        } catch (IOException ex) {
            Logger.getLogger(LocalAddonUpdateChangesManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public enum ChangeType {
        INSTALL_ADDON,
        REMOVE_ADDON,
        UPDATE_FILE,
        REMOVE_FILE
    }
}
