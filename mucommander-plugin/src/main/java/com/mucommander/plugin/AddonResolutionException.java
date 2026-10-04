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

import org.jspecify.annotations.Nullable;
import org.jspecify.annotations.NullMarked;

/**
 * Addon catalog service exception.
 */
@NullMarked
public class AddonResolutionException extends Exception {

    public AddonResolutionException() {
    }

    public AddonResolutionException(String string) {
        super(string);
    }

    public AddonResolutionException(String string, @Nullable Throwable thrwbl) {
        super(string, thrwbl);
    }

    public AddonResolutionException(@Nullable Throwable thrwbl) {
        super(thrwbl);
    }

    public AddonResolutionException(String string, @Nullable Throwable thrwbl, boolean enableSuppression, boolean writableStackTrace) {
        super(string, thrwbl, enableSuppression, writableStackTrace);
    }
}
