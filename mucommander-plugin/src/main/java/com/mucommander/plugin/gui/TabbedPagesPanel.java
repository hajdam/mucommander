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
package com.mucommander.plugin.gui;

import java.util.ArrayList;
import java.util.List;

import com.mucommander.plugin.TabPages;
import com.mucommander.plugin.TabPagesChangeListener;
import com.mucommander.plugin.TabPagesComponent;
import org.jspecify.annotations.NullMarked;
import javax.swing.JComponent;
import javax.swing.JTabbedPane;

/**
 * Panel for multiple pages organized by tabs.
 */
@NullMarked
public class TabbedPagesPanel extends JTabbedPane implements TabPages {

    protected final List<TabPagesChangeListener> pageChangeListeners = new ArrayList<>();
    protected int activeIndex = -1;

    public TabbedPagesPanel() {
        addChangeListener((e) -> {
            int selectedIndex = getSelectedIndex();
            changeActivePageIndex(selectedIndex);
        });
    }

    @Override
    public JComponent getComponent() {
        return this;
    }

    @Override
    public void addPage(TabPagesComponent page) {
        String tabTitle = page.getName();
        addTab(tabTitle, page.getComponent());
    }

    @Override
    public int getActivePageIndex() {
        return activeIndex;
    }

    @Override
    public void changeActivePageIndex(int index) {
        if (activeIndex != index) {
            activeIndex = index;
            notifyActiveIndexChanged();
        }
    }

    @Override
    public int getPagesCount() {
        return getComponentCount();
    }

    @Override
    public void addPageChangeListener(TabPagesChangeListener listener) {
        pageChangeListeners.add(listener);
    }

    @Override
    public void removePageChangeListener(TabPagesChangeListener listener) {
        pageChangeListeners.remove(listener);
    }

    private void notifyActiveIndexChanged() {
        for (TabPagesChangeListener listener : pageChangeListeners) {
            listener.activeIndexChanged(activeIndex);
        }
    }
}
