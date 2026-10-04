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

import org.jspecify.annotations.NullMarked;
import javax.swing.JComponent;

/**
 * Interface for tab pages.
 */
@NullMarked
public interface TabPages {

    /**
     * Returns tab pages component.
     *
     * @return component
     */
    JComponent getComponent();

    /**
     * Adds tab page component.
     *
     * @param page tab page component
     */
    void addPage(TabPagesComponent page);

    /**
     * Changes active page.
     *
     * @param index active page index
     */
    void changeActivePageIndex(int index);

    /**
     * Returns active page index.
     *
     * @return page index or -1
     */
    int getActivePageIndex();

    /**
     * Returns count of registered pages.
     *
     * @return number of pages
     */
    int getPagesCount();

    /**
     * Adds page change listener.
     *
     * @param listener page change listener
     */
    void addPageChangeListener(TabPagesChangeListener listener);

    /**
     * Removes page change listener.
     *
     * @param listener page change listener
     */
    void removePageChangeListener(TabPagesChangeListener listener);
}
