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
package com.mucommander.plugin.manager.page;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.mucommander.plugin.AddonManagerPage;
import com.mucommander.plugin.AddonPageRefreshFilter;
import com.mucommander.plugin.AddonRecord;
import com.mucommander.plugin.AddonsListComponent;
import com.mucommander.plugin.AddonsListComponentController;
import com.mucommander.plugin.AddonsManagementCartController;
import com.mucommander.plugin.AddonsManagementContext;
import com.mucommander.plugin.AddonsManagementLocalState;
import com.mucommander.plugin.TabPagesComponent;
import com.mucommander.plugin.UpdateAvailabilityContext;
import com.mucommander.plugin.UpdateAvailabilityManagement;
import com.mucommander.plugin.manager.LanguageUtils;
import com.mucommander.plugin.manager.gui.AddonsPanel;
import com.mucommander.plugin.operation.AddonCartOperation;
import com.mucommander.plugin.operation.AddonCartOperationType;
import org.jspecify.annotations.NullMarked;
import javax.swing.JComponent;
import org.jspecify.annotations.Nullable;

/**
 * Installed addons manager page.
 */
@NullMarked
public class InstalledAddonsPage implements TabPagesComponent, AddonManagerPage {

    public static final String PAGE_ID = "installedAddons";
    protected final ResourceBundle resourceBundle = ResourceBundle.getBundle(LanguageUtils.getResourceBaseNameBundleByClass(InstalledAddonsPage.class));
    protected @Nullable AddonsListComponent listComponent;
    protected final List<ItemChangedListener> itemChangedListeners = new ArrayList<>();

    protected AddonPageRefreshFilter filter = new AddonPageRefreshFilter();
    protected @Nullable AddonsManagementContext managementContext;
    protected @Nullable List<AddonRecord> addonItems = null;

    public InstalledAddonsPage() {
        init();
    }

    private void init() {
        listComponent = new AddonsPanel();
        listComponent.setController(new AddonsListComponentController() {

            @Override
            public int getItemsCount() {
                return InstalledAddonsPage.this.getItemsCount();
            }

            @Override
            public AddonRecord getItem(int index) {
                return InstalledAddonsPage.this.getItem(index);
            }

            @Override
            public void addToCart(AddonRecord addonRecord, AddonCartOperationType variant) {
                ((AddonsManagementCartController) managementContext).addCartOperation(new AddonCartOperation(variant, addonRecord));
            }

            @Override
            public boolean isInCart(String moduleId, AddonCartOperationType variant) {
                return ((AddonsManagementCartController) managementContext).isInCart(moduleId, variant);
            }

            @Override
            public void requestModuleDetail(AddonRecord addonRecord) {
                // TODO addonManager.requestModuleDetail(addonRecord, addonsPanel);
            }
        });
        itemChangedListeners.add(listComponent::notifyItemChanged);
    }

    @Override
    public String getName() {
        return resourceBundle.getString("page.name");
    }

    private int getItemsCount() {
        if (managementContext == null || addonItems == null) {
            return 0;
        }

        return addonItems.size();
    }

    private AddonRecord getItem(int index) {
        return addonItems.get(index);
    }

    @Override
    public void setAvailableModuleUpdates(UpdateAvailabilityManagement availableModuleUpdates) {
        int itemsCount = getItemsCount();
        for (int i = 0; i < itemsCount; i++) {
            availableModuleUpdates.applyTo(getItem(i));
        }
        notifyItemsChanged();
    }

    @Override
    public JComponent getComponent() {
        return listComponent.getComponent();
    }

    @Override
    public void setContext(AddonsManagementContext context) {
        this.managementContext = context;
        listComponent.setContext(context);
    }

    @Override
    public AddonPageRefreshFilter getFilter() {
        return filter;
    }

    @Override
    public void setFilter(AddonPageRefreshFilter filter) {
        this.filter = filter;
    }

    @Override
    public void refreshContent() {
        if (managementContext == null) {
            return;
        }

        managementContext.runOperation(() -> {
            // TODO Implement as background thread
            List<AddonRecord> installedAddons = ((AddonsManagementLocalState) managementContext).getInstalledAddons();
            String searchCondition = filter.getSearchCondition().trim().toLowerCase();
            if (searchCondition.isEmpty()) {
                addonItems = installedAddons;
                notifyItemsChanged();
                return;
            }

            List<AddonRecord> items = new ArrayList<>();
            for (int i = 0; i < installedAddons.size(); i++) {
                AddonRecord record = installedAddons.get(i);
                if (record.getName().toLowerCase().contains(searchCondition)) {
                    items.add(record);
                }
            }
            addonItems = items;
            notifyItemsChanged();
        });
    }

    private void notifyItemsChanged() {
        for (ItemChangedListener itemChangedListener : itemChangedListeners) {
            itemChangedListener.itemChanged();
        }
        listComponent.notifyItemsChanged();
    }

    public interface ItemChangedListener {

        void itemChanged();
    }
}
