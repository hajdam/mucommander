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
package com.mucommander.plugin.catalog.page;

import com.mucommander.plugin.AddonManagerPage;
import com.mucommander.plugin.AddonPageRefreshFilter;
import com.mucommander.plugin.AddonRecord;
import com.mucommander.plugin.AddonsListComponent;
import com.mucommander.plugin.AddonsListComponentController;
import com.mucommander.plugin.AddonsManagementCartController;
import com.mucommander.plugin.AddonsManagementContext;
import com.mucommander.plugin.AddonsManagementLocalState;
import com.mucommander.plugin.RepositoryAddonRecord;
import com.mucommander.plugin.TabPagesComponent;
import com.mucommander.plugin.UpdateAvailabilityManagement;
import com.mucommander.plugin.catalog.operation.CatalogSearchOperation;
import com.mucommander.plugin.catalog.service.AddonCatalogService;
import com.mucommander.plugin.manager.LanguageUtils;
import com.mucommander.plugin.manager.gui.AddonsPanel;
import com.mucommander.plugin.operation.AddonCartOperation;
import com.mucommander.plugin.operation.AddonCartOperationType;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import javax.swing.JComponent;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Addons manager page for remote catalog.
 */
@NullMarked
public class AddonsCatalogPage implements TabPagesComponent, AddonManagerPage {

    public static final String PAGE_ID = "addonsCatalog";
    protected final ResourceBundle resourceBundle = ResourceBundle.getBundle(LanguageUtils.getResourceBaseNameBundleByClass(AddonsCatalogPage.class));
    protected AddonsListComponent listComponent;
    protected final List<ItemChangedListener> itemChangedListeners = new ArrayList<>();
    protected AddonCatalogService addonCatalogService;

    protected AddonPageRefreshFilter filter = new AddonPageRefreshFilter();
    protected @Nullable AddonsManagementContext managementContext;
    protected @Nullable UpdateAvailabilityManagement availableModuleUpdates;
    protected List<RepositoryAddonRecord> addonItems;

    public AddonsCatalogPage() {
        init();
    }

    private void init() {
        listComponent = new AddonsPanel();
        listComponent.setController(new AddonsListComponentController() {

            @Override
            public int getItemsCount() {
                return AddonsCatalogPage.this.getItemsCount();
            }

            @Override
            public AddonRecord getItem(int index) {
                return AddonsCatalogPage.this.getItem(index);
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
        itemChangedListeners.add((ItemChangedListener) listComponent::notifyItemChanged);
    }

    @Override
    public String getName() {
        return resourceBundle.getString("page.name");
    }

    @Override
    public JComponent getComponent() {
        return listComponent.getComponent();
    }

    @Override
    public void setContext(AddonsManagementContext context) {
        this.managementContext = context;
        listComponent.setContext(context);
        notifyItemsChanged();
    }

    public void setAddonCatalogService(AddonCatalogService addonCatalogService) {
        this.addonCatalogService = addonCatalogService;
        listComponent.notifyItemsChanged();
        notifyItemsChanged();
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

        /* AddonManagerModuleApi addonManagerModule = App.getModule(AddonManagerModuleApi.class);
        setAddonCatalogService(addonManagerModule.addonCatalogService);

        runOperation(new CatalogCheckStatusOperation(this, addonCatalogService, (status) -> {
            if (status > 0) {
                runOperation(new CatalogAvailableUpdatesOperation(addonCatalogService, this, status, this::updateLatestVersions));
                runOperation(new CatalogSearchOperation(addonCatalogService, this, "", ((AddonsCatalogPage) managerPage)::setAddonItems));
            } else if (status == 0) {
                statusListener.setManualOnlyMode();
                ((AddonsCatalogPage) managerPage).setAddonItems(new ArrayList<>());
            } else {
                statusListener.setCatalogNotAvailable();
                ((AddonsCatalogPage) managerPage).setAddonItems(new ArrayList<>());
            }
        })); */

        String search = filter.getSearchCondition();
        managementContext.runOperation(new CatalogSearchOperation(addonCatalogService, managementContext instanceof AddonsManagementLocalState ? (AddonsManagementLocalState) managementContext : null, availableModuleUpdates, search, this::setAddonItems));
//        addonsPanel.notifyItemsChanged();
//        ResourceBundle resourceBundle = addonManager.getResourceBundle();
//        JOptionPane.showMessageDialog(addonsPanel, resourceBundle.getString("addonServiceApiError.message"), resourceBundle.getString("addonServiceApiError.title"), JOptionPane.ERROR_MESSAGE);
    }

    public void setAddonItems(List<RepositoryAddonRecord> addonItems) {
        this.addonItems = addonItems;
        notifyItemsChanged();
    }

    private int getItemsCount() {
        if (addonItems == null) {
            return 0;
        }

        return addonItems.size();
    }

    private AddonRecord getItem(int index) {
        return addonItems.get(index);
    }

    @Override
    public void setAvailableModuleUpdates(UpdateAvailabilityManagement availableModuleUpdates) {
        this.availableModuleUpdates = availableModuleUpdates;
        int itemsCount = getItemsCount();
        for (int i = 0; i < itemsCount; i++) {
            availableModuleUpdates.applyTo(getItem(i));
        }
        notifyItemsChanged();
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
