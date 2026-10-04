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
package com.mucommander.plugin.manager;

import com.mucommander.plugin.AddonManagerPage;
import com.mucommander.plugin.AddonPageRefreshFilter;
import com.mucommander.plugin.AddonRecord;
import com.mucommander.plugin.AddonResolutionManagement;
import com.mucommander.plugin.AddonsManagementCartController;
import com.mucommander.plugin.AddonsManagementCatalogState;
import com.mucommander.plugin.AddonsManagementLocalState;
import com.mucommander.plugin.DefaultControlController;
import com.mucommander.plugin.UiUtils;
import com.mucommander.plugin.gui.DefaultControlPanel;
import com.mucommander.plugin.MultiStepControlController;
import com.mucommander.plugin.gui.MultiStepControlPanel;
import com.mucommander.plugin.ProgressOperation;
import com.mucommander.plugin.TabPages;
import com.mucommander.plugin.TabPagesComponent;
import com.mucommander.plugin.TitledOperation;
import com.mucommander.plugin.manager.filter.CategoryManager;
import com.mucommander.plugin.manager.page.InstalledAddonsPage;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Window;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JDialog;

import com.mucommander.plugin.operation.AddonCartOperation;
import com.mucommander.plugin.operation.AddonCartOperationType;
import com.mucommander.plugin.operation.CartOperation;
import com.mucommander.plugin.operation.CartOperationType;
import com.mucommander.plugin.update.AddonUpdateChangesManagement;
import org.jspecify.annotations.NullMarked;
import javax.swing.JOptionPane;

import com.mucommander.plugin.manager.gui.AddonsCartPanel;
import com.mucommander.plugin.manager.gui.AddonsManagerPanel;
import com.mucommander.plugin.manager.operation.AddonModificationStep;
import com.mucommander.plugin.manager.operation.AddonModificationsOperation;
import com.mucommander.plugin.manager.operation.DownloadOperation;
import com.mucommander.plugin.manager.operation.gui.AddonOperationDownloadPanel;
import com.mucommander.plugin.manager.operation.gui.AddonOperationLicensePanel;
import com.mucommander.plugin.manager.operation.gui.AddonOperationOverviewPanel;
import com.mucommander.plugin.manager.operation.gui.AddonOperationPanel;
import com.mucommander.plugin.manager.operation.service.AddonOperationService;
import org.jspecify.annotations.Nullable;
import com.mucommander.plugin.manager.gui.AddonsFilterPanel;
import com.mucommander.plugin.manager.filter.gui.CategoryFilterPanel;
import com.mucommander.plugin.manager.gui.CategoryRecord;

/**
 * Addon manager.
 */
@NullMarked
public class AddonManager implements AddonsManagementCartController, AddonsManagementLocalState, AddonsManagementCatalogState {

    protected java.util.ResourceBundle resourceBundle = ResourceBundle.getBundle(LanguageUtils.getResourceBaseNameBundleByClass(AddonManager.class));

    protected @Nullable AddonsManagerPanel managerPanel;
    protected final List<CartOperation> cartOperations = new ArrayList<>();

    protected @Nullable AddonResolutionManagement resolutionManagement;
    protected String catalogWebsiteUrl = "";
    protected final AddonsState addonsState = new AddonsState();
    protected CategoryManager categoryManager = new CategoryManager();
    protected @Nullable AddonManagerStatusListener statusListener;

    protected final ExecutorService operationsExecutor = Executors.newFixedThreadPool(1);
    protected List<TabPagesComponent> pages = new ArrayList<>();
    protected Window parentWindow;

    public AddonManager() {
    }

    @Override
    public void runOperation(Runnable operation) {
        operationsExecutor.submit(() -> {
            if (operation instanceof TitledOperation) {
                String title = ((TitledOperation) operation).getTitle();
                if (operation instanceof ProgressOperation) {
                    statusListener.setProgressStatus(title);
                } else {
                    statusListener.setStatusLabel(title);
                }
            }

            operation.run();
            statusListener.clear();
        });
    }

    public void notifyChanged() {
        AddonManagerPage managerTab = managerPanel.getActiveTab();
        managerTab.refreshContent();
        managerPanel.setCartItemsCount(cartOperations.size());
    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    @Override
    public String getCatalogBaseUrl() {
        return catalogWebsiteUrl;
    }

    public AddonsManagerPanel getManagerPanel() {
        if (managerPanel != null) {
            return managerPanel;
        }

        addonsState.init();

        managerPanel = new AddonsManagerPanel();
        AddonsCartPanel cartPanel = new AddonsCartPanel();
        managerPanel.setPreferredSize(new Dimension(800, 500));
        managerPanel.setCartComponent(cartPanel);
        managerPanel.setController(new AddonsManagerPanel.Controller() {
            @Override
            public void notifyTabSwitched() {
                if (managerPanel.isCartOpened()) {
                    cartPanel.setCartItems(getCartOperations());
                    return;
                }

                notifyChanged();
            }

            @Override
            public void changeFilter() {
                AddonManagerPage activePage = managerPanel.getActiveTab();
                AddonsFilterPanel filtersPanel = new AddonsFilterPanel();
                CategoryFilterPanel categoryFilterPanel = new CategoryFilterPanel();
                List<CategoryRecord> categoryRecords = new ArrayList<>();
                categoryRecords.add(new CategoryRecord("test", "Test"));
                categoryFilterPanel.setCategories(categoryRecords);
                filtersPanel.addFilter("Category", categoryFilterPanel);

                DefaultControlPanel controlPanel = new DefaultControlPanel();
                JDialog dialog = UiUtils.createDialog(parentWindow,"Filter", filtersPanel, controlPanel);
                controlPanel.setController((actionType) -> {
                    if (actionType == DefaultControlController.ControlActionType.OK) {
                        // TODO AddonPageFilter filter1 = managerPage.getFilter();
                        managerPanel.setFilter("category:" + "test");
                        // activePage.setFilter(filter);
                        activePage.refreshContent();
                    }

                    dialog.setVisible(false);
                    dialog.dispose();
                });
                dialog.setLocationRelativeTo(parentWindow);
                dialog.setVisible(true);
            }

            @Override
            public void performSearch(String search) {
                List<AddonManagerPage> managerPages = managerPanel.getManagerTabs();
                for (AddonManagerPage managerPage : managerPages) {
                    AddonPageRefreshFilter filter = managerPage.getFilter();
                    filter.setSearchCondition(search);
                    managerPage.setFilter(filter);
                    managerPage.refreshContent();
                }
            }
        });

        cartPanel.setController(new AddonsCartPanel.Controller() {
            @Override
            public void runOperations() {
                runCartModifications();
            }

            @Override
            public void performRemove(int[] indices) {
                cartPanel.removeIndices(indices);
                removeIndices(indices);
            }
        });

        TabPages tabPages = managerPanel.createTabPagesWrapper();
        for (TabPagesComponent page : pages) {
            if (page instanceof AddonManagerPage) {
                ((AddonManagerPage) page).setContext(this);
            }
            tabPages.addPage(page);
        }
        return managerPanel;
    }

    public void setParentWindow(Window parentWindow) {
        this.parentWindow = parentWindow;
    }

    public void registerBasicAddonManager() {
        pages.add(new InstalledAddonsPage());
    }

    public void addPage(TabPagesComponent page) {
        pages.add(page);
    }

    public void setResolutionManagement(AddonResolutionManagement resolutionManagement) {
        this.resolutionManagement = resolutionManagement;
    }

    public void setCatalogWebsiteUrl(String catalogWebsiteUrl) {
        this.catalogWebsiteUrl = catalogWebsiteUrl;
    }

    public void setStatusListener(AddonManagerStatusListener statusListener) {
        this.statusListener = statusListener;
    }

    private void removeIndices(int[] indices) {
        if (indices.length == 0) {
            return;
        }

        Arrays.sort(indices);
        for (int i = indices.length - 1; i >= 0; i--) {
            cartOperations.remove(i);
        }
        managerPanel.setCartItemsCount(cartOperations.size());
    }

    public void refreshContent() {
        // TODO Replace with on switch update
        List<AddonManagerPage> managerPages = managerPanel.getManagerTabs();
        for (AddonManagerPage managerPage : managerPages) {
            managerPage.refreshContent();
        }
    }

    public void updateAll() {
        List<CartOperation> updateOperations = new ArrayList<>();
        List<AddonRecord> installedAddons = addonsState.getInstalledAddons();
        UpdateAvailabilityManager availableModuleUpdates = addonsState.getAvailableModuleUpdates();
        for (AddonRecord installedAddon : installedAddons) {
            if (availableModuleUpdates.isUpdateAvailable(installedAddon.getId(), installedAddon.getVersion())) {
                updateOperations.add(new AddonCartOperation(AddonCartOperationType.UPDATE, installedAddon));
            }
        }

        AddonOperationService addonOperationService = new AddonOperationService(AddonManager.this);
        addonOperationService.setResolutionManagement(resolutionManagement);
        AddonModificationsOperation modificationsOperations = addonOperationService.performAddonOperations(updateOperations);
        if (performAddonsOperation(modificationsOperations, managerPanel)) {
            notifyChanged();
        }
    }

    public UpdateAvailabilityManager getAvailableModuleUpdates() {
        return addonsState.getAvailableModuleUpdates();
    }

    public AddonUpdateChangesManagement getAddonUpdateChanges() {
        return addonsState.getAddonUpdateChanges();
    }

    public ApplicationModulesUsage getApplicationModulesUsage() {
        return addonsState.getApplicationModulesUsage();
    }

    @Override
    public boolean isModuleInstalled(String moduleId) {
        return addonsState.isModuleInstalled(moduleId);
    }

    @Override
    public boolean isModuleRemoved(String moduleId) {
        return addonsState.isModuleRemoved(moduleId);
    }

    @Override
    public void addCartOperation(CartOperation operation) {
        cartOperations.add(operation);
        managerPanel.setCartItemsCount(cartOperations.size());

        if (cartOperations.size() == 1) {
            int result = JOptionPane.showOptionDialog(managerPanel,
                    resourceBundle.getString("runModificationsQuestion.message"),
                    resourceBundle.getString("runModificationsQuestion.title"),
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    new String[]{
                        resourceBundle.getString("runModificationsQuestion.continue"),
                        resourceBundle.getString("runModificationsQuestion.run")
                    },
                    resourceBundle.getString("runModificationsQuestion.run"));
            if (result == 1) {
                runCartModifications();
            }
        }
    }

    public void runCartModifications() {
        AddonOperationService addonOperationService = new AddonOperationService(AddonManager.this);
        addonOperationService.setResolutionManagement(resolutionManagement);
        try {
            AddonModificationsOperation modificationsOperations = addonOperationService.performAddonOperations(cartOperations);
            if (performAddonsOperation(modificationsOperations, managerPanel)) {
                cartOperations.clear();
                notifyChanged();
            }
        } catch (Throwable tw) {
            JOptionPane.showMessageDialog(managerPanel,
                    String.format(resourceBundle.getString("operationFailedError.message"), tw.getLocalizedMessage()),
                    resourceBundle.getString("operationFailedError.title"),
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public boolean performAddonsOperation(AddonModificationsOperation modificationsOperation, Component parentComponent) {
        boolean[] success = new boolean[]{false};
        MultiStepControlPanel controlPanel = new MultiStepControlPanel();
        AddonOperationPanel operationPanel = new AddonOperationPanel();
        operationPanel.setPreferredSize(new Dimension(600, 300));

        AddonOperationOverviewPanel panel = (AddonOperationOverviewPanel) operationPanel.getActiveComponent().get();
        for (String operation : modificationsOperation.getOperations()) {
            panel.addOperation(operation);
        }

        JDialog dialog = UiUtils.createDialog(parentWindow, operationPanel.getResourceBundle().getString("dialog.title"), operationPanel, controlPanel);
        controlPanel.setController(new MultiStepControlController() {

            private AddonModificationStep step = AddonModificationStep.OVERVIEW;
            private DownloadOperation downloadOperation = null;

            @Override
            public void controlActionPerformed(MultiStepControlController.ControlActionType actionType) {
                switch (actionType) {
                    case CANCEL:
                        if (downloadOperation != null) {
                            downloadOperation.cancelOperation();
                        }
                        dialog.setVisible(false);
                        break;
                    case NEXT:
                        switch (step) {
                            case OVERVIEW:
                                List<LicenseItemRecord> licenseRecords = modificationsOperation.getLicenseRecords();
                                if (!licenseRecords.isEmpty()) {
                                    step = AddonModificationStep.LICENSE;
                                    operationPanel.goToStep(step);
                                    AddonOperationLicensePanel panel = (AddonOperationLicensePanel) operationPanel.getActiveComponent().get();
                                    panel.setController(new AddonOperationLicensePanel.Controller() {
                                        @Override
                                        public void approvalStateChanged(int toApprove) {
                                            controlPanel.setActionEnabled(MultiStepControlController.ControlActionType.NEXT, toApprove == 0);
                                        }
                                    });
                                    panel.setLicenseRecords(licenseRecords);
                                    controlPanel.setActionEnabled(MultiStepControlController.ControlActionType.NEXT, false);
                                    break;
                                } // no break
                            case LICENSE:
                                controlPanel.setActionEnabled(MultiStepControlController.ControlActionType.PREVIOUS, true);
                                List<DownloadItemRecord> downloadRecords = modificationsOperation.getDownloadRecords();
                                if (!downloadRecords.isEmpty()) {
                                    goToDownload(downloadRecords);
                                    break;
                                } // no break
                            case DOWNLOAD:
                                step = AddonModificationStep.SUCCESS;
                                operationPanel.goToStep(step);
                                controlPanel.setActionEnabled(MultiStepControlController.ControlActionType.NEXT, false);
                                controlPanel.setActionEnabled(MultiStepControlController.ControlActionType.PREVIOUS, false);
                                controlPanel.setActionEnabled(MultiStepControlController.ControlActionType.CANCEL, false);
                                controlPanel.setActionEnabled(MultiStepControlController.ControlActionType.FINISH, true);
                                break;
                            default:
                                throw new AssertionError();
                        }
                        break;
                    case PREVIOUS:
                        // TODO
                        switch (step) {
                            case LICENSE:
                                step = AddonModificationStep.OVERVIEW;
                                controlPanel.setActionEnabled(MultiStepControlController.ControlActionType.PREVIOUS, false);
                                operationPanel.goToStep(step);
                                break;
                            default:
                                throw new AssertionError();
                        }
                        break;
                    case FINISH:
                        modificationsOperation.finished();
                        success[0] = true;
                        dialog.setVisible(false);
                        break;
                    default:
                        throw new AssertionError();
                }
            }

            private void goToDownload(List<DownloadItemRecord> downloadRecords) {
                step = AddonModificationStep.DOWNLOAD;
                operationPanel.goToStep(step);
                AddonOperationDownloadPanel panel = (AddonOperationDownloadPanel) operationPanel.getActiveComponent().get();
                panel.setDownloadedItemRecords(downloadRecords);
                downloadOperation = new DownloadOperation(downloadRecords);
                downloadOperation.setItemChangeListener(new DownloadOperation.ItemChangeListener() {
                    @Override
                    public void itemChanged(int itemIndex) {
                        panel.notifyDownloadedItemChanged(itemIndex);
                    }

                    @Override
                    public void progressChanged(int itemIndex) {
                        DownloadItemRecord record = downloadRecords.get(itemIndex);
                        panel.setProgress(record.getFileName(), downloadOperation.getOperationProgress(), false);
                    }

                });
                controlPanel.setActionEnabled(MultiStepControlController.ControlActionType.NEXT, false);
                Thread thread = new Thread(() -> {
                    try {
                        downloadOperation.run();
                        controlPanel.setActionEnabled(MultiStepControlController.ControlActionType.NEXT, true);
                    } catch (Throwable tw) {
                        JOptionPane.showMessageDialog(parentComponent,
                                String.format(resourceBundle.getString("downloadFailedError.message"), tw.getLocalizedMessage()),
                                resourceBundle.getString("downloadFailedError.title"),
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                });
                thread.start();
            }
        });
        controlPanel.setActionEnabled(MultiStepControlController.ControlActionType.NEXT, true);
        controlPanel.setActionEnabled(MultiStepControlController.ControlActionType.FINISH, false);
        dialog.setLocationRelativeTo(parentWindow);
        dialog.setVisible(true);

        return success[0];
    }

    @Override
    public boolean isInCart(String moduleId, CartOperationType variant) {
        for (CartOperation cartOperation : cartOperations) {
            if (cartOperation instanceof AddonCartOperation) {
                if (moduleId.equals(((AddonCartOperation) cartOperation).getRecord().getId()) && variant == cartOperation.getVariant()) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public List<AddonRecord> getInstalledAddons() {
        return addonsState.getInstalledAddons();
    }

    public void addUpdateAvailabilityListener(UpdateAvailabilityManager.AvailableModulesChangeListener listener) {
        addonsState.addUpdateAvailabilityListener(listener);
    }

    @Override
    public List<CartOperation> getCartOperations() {
        return cartOperations;
    }

    private void updateLatestVersions() {
        UpdateAvailabilityManager availableModuleUpdates = getAvailableModuleUpdates();
        List<AddonRecord> installedAddons = addonsState.getInstalledAddons();
        int availableUpdates = 0;
        for (AddonRecord installedAddon : installedAddons) {
            if (availableModuleUpdates.isUpdateAvailable(installedAddon.getId(), installedAddon.getVersion())) {
                availableUpdates++;
            }
        }
        statusListener.setAvailableUpdates(availableUpdates);
    }

    @NullMarked
    public interface AddonManagerStatusListener {

        void setProgressStatus(String status);

        void setStatusLabel(String text);

        void clear();

        void setAvailableUpdates(int updatesCount);

        void setManualOnlyMode();

        void setCatalogNotAvailable();
    }
}
