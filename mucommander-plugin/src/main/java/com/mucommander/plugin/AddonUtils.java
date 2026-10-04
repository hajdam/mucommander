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
package com.mucommander.plugin;

import com.mucommander.conf.PlatformManager;
import org.jspecify.annotations.NullMarked;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Addon utilities.
 */
@NullMarked
public class AddonUtils {

    public static final String ADDONS_DIRECTORY = "addons";
    public static final String ADDONS_UPDATE_DIRECTORY = "addons_update";
    public static final String ADDONS_CHANGES_FILE = "changes.cfg";
    public static final String FILE_UPDATE = "UPDATE_FILE";
    public static final String FILE_REMOVAL = "REMOVE_FILE";

    /**
     * Method to run before application loads addon modules.
     */
    public static void setupAddons() {
        String configDirectoryPath = PlatformManager.getPreferencesFolder().getAbsolutePath();
        File addonsDirectory = new File(configDirectoryPath, ADDONS_DIRECTORY);
        File updateDirectory = new File(configDirectoryPath, ADDONS_UPDATE_DIRECTORY);
        if (updateDirectory.exists()) {
            File changesConfig = new File(updateDirectory, ADDONS_CHANGES_FILE);

            if (changesConfig.exists()) {
                if (!addonsDirectory.exists()) {
                    addonsDirectory.mkdirs();
                }
                // Perform update
                Logger.getLogger(AddonUtils.class.getName()).log(Level.INFO, "Starting addons update");
                boolean success = false;
                String line = null;
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(changesConfig)))) {
                    do {
                        line = reader.readLine();
                        if (line != null) {
                            if (line.startsWith(FILE_UPDATE)) {
                                String fileName = line.substring(12);
                                File replacedFile = new File(addonsDirectory, fileName);
                                File sourceFile = new File(updateDirectory, fileName);
                                if (sourceFile.exists()) {
                                    if (replacedFile.exists()) {
                                        replacedFile.delete();
                                    }
                                }
                                sourceFile.renameTo(replacedFile);
                            } else if (line.startsWith(FILE_REMOVAL)) {
                                String fileName = line.substring(12);
                                File removedFile = new File(addonsDirectory, fileName);
                                if (removedFile.exists()) {
                                    removedFile.delete();
                                }
                            }
                        }
                    } while (line != null);
                    success = true;
                } catch (IOException ex) {
                    Logger.getLogger(AddonUtils.class.getName()).log(Level.SEVERE, "Failed to move file " + line, ex);
                }
                if (success) {
                    changesConfig.delete();
                }

                Logger.getLogger(AddonUtils.class.getName()).log(Level.INFO, "Finished addons update");
            }
        }
    }
}
