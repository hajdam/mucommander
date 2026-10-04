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

import org.jspecify.annotations.NullMarked;

/**
 * Addon modification.
 */
@NullMarked
public class AddonModification {

    private final AddonModificationType modificationType;
    private final String identifier;

    public AddonModification(AddonModificationType modificationType, String identifier) {
        this.modificationType = modificationType;
        this.identifier = identifier;
    }

    public AddonModificationType getModificationType() {
        return modificationType;
    }

    public String getIdentifier() {
        return identifier;
    }
}
