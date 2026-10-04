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

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.jspecify.annotations.Nullable;
import org.jspecify.annotations.NullMarked;
import javax.swing.ImageIcon;

/**
 * Basic module record.
 */
@NullMarked
public class BasicModuleRecord implements ModuleRecord {

    private String moduleId = "";
    private ModuleType type = ModuleType.MODULE;
    private ModuleFileLocation fileLocation = BasicModuleFileLocation.LIBRARY;
    private String name;
    private ClassLoader classLoader;
    private String version = "";
    private String description = null;
    private String provider = null;
    private String homepage = null;
    private ImageIcon icon = null;
    private final List<String> dependencyModuleIds = new ArrayList<>();
    private final List<String> optionalModuleIds = new ArrayList<>();
    private final List<String> dependencyLibraries = new ArrayList<>();
    private Module module;

    public BasicModuleRecord() {
    }

    public BasicModuleRecord(String moduleId, Module module, ClassLoader classLoader) {
        this.moduleId = moduleId;
        this.module = module;
        this.classLoader = classLoader;
    }

    @Override
    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    @Override
    public ModuleType getType() {
        return type;
    }

    public void setType(ModuleType type) {
        this.type = type;
    }

    @Override
    public ModuleFileLocation getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(ModuleFileLocation fileLocation) {
        this.fileLocation = fileLocation;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public ClassLoader getClassLoader() {
        if (classLoader == null) {
            throw new IllegalStateException("Attempt to use uninitialized module: " + moduleId);
        }
        return classLoader;
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public Optional<String> getProvider() {
        return Optional.ofNullable(provider);
    }

    public void setProvider(@Nullable String provider) {
        this.provider = provider;
    }

    @Override
    public Optional<String> getHomepage() {
        return Optional.ofNullable(homepage);
    }

    public void setHomepage(@Nullable String homepage) {
        this.homepage = homepage;
    }

    @Override
    public Optional<String> getDescription() {
        return Optional.ofNullable(description);
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    @Override
    public Optional<ImageIcon> getIcon() {
        return Optional.ofNullable(icon);
    }

    public void setIcon(@Nullable ImageIcon icon) {
        this.icon = icon;
    }

    @Override
    public List<String> getDependencyModuleIds() {
        return dependencyModuleIds;
    }

    public void setDependencyModuleIds(List<String> dependencyModuleIds) {
        this.dependencyModuleIds.clear();
        this.dependencyModuleIds.addAll(dependencyModuleIds);
    }

    @Override
    public List<String> getOptionalModuleIds() {
        return optionalModuleIds;
    }

    @Override
    public List<String> getDependencyLibraries() {
        return dependencyLibraries;
    }

    public void setDependencyLibraries(List<String> dependencyLibraries) {
        this.dependencyLibraries.clear();
        this.dependencyLibraries.addAll(dependencyLibraries);
    }

    @Override
    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    @NullMarked
    public static class ModuleLink implements Module {

        private final URI moduleLink;
        private final boolean preloaded;

        public ModuleLink(URI moduleLink, boolean preloaded) {
            this.moduleLink = moduleLink;
            this.preloaded = preloaded;
        }

        public URI getModuleLink() {
            return moduleLink;
        }

        public boolean isPreloaded() {
            return preloaded;
        }
    }
}
