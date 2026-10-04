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

import com.mucommander.plugin.operation.AddonCartOperationType;
import org.jspecify.annotations.NullMarked;

/**
 * Addons list component controller.
 */
@NullMarked
public interface AddonsListComponentController {

    /**
     * Returns number of items.
     *
     * @return number of items
     */
    int getItemsCount();

    /**
     * Returns specific item.
     *
     * @param index order index
     * @return item record
     */
    AddonRecord getItem(int index);

    /**
     * Adds item to the cart.
     *
     * @param itemRecord item record
     * @param variant operation variant
     */
    void addToCart(AddonRecord itemRecord, AddonCartOperationType variant);

    /**
     * Checks whether item for specific addon is in cart.
     *
     * @param moduleId module id
     * @param variant operation variant
     * @return true if present in the cart
     */
    boolean isInCart(String moduleId, AddonCartOperationType variant);

    /**
     * Requests item record to be amended with additional details.
     *
     * @param itemRecord item record to update
     */
    void requestModuleDetail(AddonRecord itemRecord);
}
