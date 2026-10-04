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

import java.util.ArrayList;
import java.util.List;
import org.jspecify.annotations.NullMarked;

/**
 * Addon manager page category filter.
 */
@NullMarked
public class CategoryFilter implements AddonPageFilter {

    protected final List<String> categoryIds = new ArrayList<>();
    protected Mode mode = Mode.INCLUDE;

    public List<String> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(List<String> ids) {
        categoryIds.clear();
        categoryIds.addAll(ids);
    }

    public boolean isEmpty() {
        return categoryIds.isEmpty();
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public enum Mode {
        INCLUDE,
        EXCLUDE
    }
}
