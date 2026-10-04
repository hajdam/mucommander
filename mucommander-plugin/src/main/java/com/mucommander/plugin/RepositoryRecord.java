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

import java.net.URL;
import java.util.Optional;
import org.jspecify.annotations.Nullable;
import org.jspecify.annotations.NullMarked;

/**
 * Addon repository record.
 */
@NullMarked
public class RepositoryRecord {

    private String id;
    private @Nullable String name;
    private @Nullable URL repositoryUrl = null;

    public RepositoryRecord(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public @Nullable String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Optional<URL> getRepositoryUrl() {
        return Optional.ofNullable(repositoryUrl);
    }

    public void setRepositoryUrl(@Nullable URL repositoryUrl) {
        this.repositoryUrl = repositoryUrl;
    }
}
