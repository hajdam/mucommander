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

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import org.jspecify.annotations.Nullable;
import org.jspecify.annotations.NullMarked;

/**
 * Repository addon record.
 */
@NullMarked
public class RepositoryAddonRecord extends AddonRecord {

    protected RepositoryRecord repository;
    protected List<DependencyRecord> dependencies = new ArrayList<>();
    protected String license = "";
    protected @Nullable String licenseSpdx;
    protected String licenseRemoteFile = "";

    public RepositoryAddonRecord(String id, String name) {
        super(id, name);
    }

    public RepositoryRecord getRepository() {
        return repository;
    }

    public void setRepository(RepositoryRecord repository) {
        this.repository = repository;
    }

    public List<DependencyRecord> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<DependencyRecord> dependencies) {
        this.dependencies = dependencies;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public Optional<String> getLicenseSpdx() {
        return Optional.ofNullable(licenseSpdx);
    }

    public void setLicenseSpdx(@Nullable String licenseSpdx) {
        this.licenseSpdx = licenseSpdx;
    }

    public String getLicenseRemoteFile() {
        return licenseRemoteFile;
    }

    public void setLicenseRemoteFile(String licenseRemoteFile) {
        this.licenseRemoteFile = licenseRemoteFile;
    }
}
