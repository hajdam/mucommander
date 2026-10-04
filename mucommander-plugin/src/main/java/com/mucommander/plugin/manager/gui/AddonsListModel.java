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
package com.mucommander.plugin.manager.gui;

import com.mucommander.plugin.AddonRecord;
import org.jspecify.annotations.NullMarked;
import javax.swing.AbstractListModel;

/**
 * Addons list model.
 */
@NullMarked
public class AddonsListModel extends AbstractListModel<AddonRecord> {

    protected RecordsProvider provider;
    protected int size = 0;

    public void setProvider(RecordsProvider provider) {
        this.provider = provider;
    }

    public void notifyItemsChanged() {
        if (size > 0) {
            fireIntervalRemoved(this, 0, size - 1);
        }
        size = provider.getItemsCount();
        if (size > 0) {
            fireIntervalAdded(this, 0, size - 1);
        }
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public AddonRecord getElementAt(int index) {
        try {
            return provider.getItem(index);
        } catch (IndexOutOfBoundsException ex) {
            return new AddonRecord();
        }
    }

    public interface RecordsProvider {

        int getItemsCount();

        AddonRecord getItem(int index);
    }
}
