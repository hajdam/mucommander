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

import com.mucommander.plugin.operation.CartOperation;
import java.util.List;
import org.jspecify.annotations.NullMarked;
import com.mucommander.plugin.operation.CartOperationType;

/**
 * Addons management cart controller interface.
 */
@NullMarked
public interface AddonsManagementCartController extends AddonsManagementContext {

    /**
     * Adds operation to the cart.
     *
     * @param operation cart operation
     */
    void addCartOperation(CartOperation operation);

    /**
     * Checks whether specific module operation is present in cart.
     *
     * @param moduleId module identifier
     * @param variant cart operation variant
     * @return true if present
     */
    boolean isInCart(String moduleId, CartOperationType variant);

    /**
     * Returns list of cart operations.
     *
     * @return cart operations
     */
    List<CartOperation> getCartOperations();
}
