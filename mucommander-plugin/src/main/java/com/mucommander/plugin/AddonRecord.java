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

import java.util.Optional;
import org.jspecify.annotations.Nullable;
import org.jspecify.annotations.NullMarked;
import javax.swing.ImageIcon;

/**
 * Addon record.
 */
@NullMarked
public class AddonRecord {

    protected String id;
    protected String name;
    protected String version = "";
    protected boolean enabled = true;
    protected boolean installed = false;
    protected boolean updateAvailable = false;
    protected AddonModuleFileLocation fileLocation = AddonModuleFileLocation.UNSPECIFIED;
    protected @Nullable ImageIcon icon = null;
    protected @Nullable String provider = null;
    protected @Nullable String homepage = null;
    protected @Nullable String description = null;

    public AddonRecord() {
        this("", "");
    }

    public AddonRecord(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isInstalled() {
        return installed;
    }

    public void setInstalled(boolean installed) {
        this.installed = installed;
    }

    public boolean isUpdateAvailable() {
        return updateAvailable;
    }

    public void setUpdateAvailable(boolean updateAvailable) {
        this.updateAvailable = updateAvailable;
    }

    public AddonModuleFileLocation getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(AddonModuleFileLocation fileLocation) {
        this.fileLocation = fileLocation;
    }

    public Optional<ImageIcon> getIcon() {
        return Optional.ofNullable(icon);
    }

    public void setIcon(@Nullable ImageIcon icon) {
        this.icon = icon;
    }

    public Optional<String> getProvider() {
        return Optional.ofNullable(provider);
    }

    public void setProvider(@Nullable String provider) {
        this.provider = provider;
    }

    public Optional<String> getHomepage() {
        return Optional.ofNullable(homepage);
    }

    public void setHomepage(@Nullable String homepage) {
        this.homepage = homepage;
    }

    public Optional<String> getDescription() {
        return Optional.ofNullable(description);
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }
}
