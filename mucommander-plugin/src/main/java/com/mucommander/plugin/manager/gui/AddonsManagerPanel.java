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

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.mucommander.plugin.AddonManagerPage;
import com.mucommander.plugin.TabPages;
import com.mucommander.plugin.TabPagesChangeListener;
import com.mucommander.plugin.TabPagesComponent;
import com.mucommander.plugin.gui.TabbedPagesPanel;
import com.mucommander.plugin.manager.LanguageUtils;
import org.jspecify.annotations.NullMarked;
import javax.swing.JComponent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import org.jspecify.annotations.Nullable;

/**
 * Addons manager panel.
 */
@NullMarked
public class AddonsManagerPanel extends javax.swing.JPanel {

    protected final ResourceBundle resourceBundle = ResourceBundle.getBundle(LanguageUtils.getResourceBaseNameBundleByClass(AddonsManagerPanel.class));
    protected final List<AddonManagerPage> managerTabs = new ArrayList<>();
    protected final TabPages tabPages;
    protected @Nullable Controller controller;
    protected @Nullable Component cartComponent;

    private javax.swing.JToggleButton cartButton;
    private javax.swing.JLabel filterLabel;
    private javax.swing.JLabel filterTextLabel;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JLabel searchLabel;
    private javax.swing.JTextField searchTextField;

    public AddonsManagerPanel() {
        tabPages = new TabbedPagesPanel();
        initComponents();
        init();
    }

    private void init() {
        tabPages.addPageChangeListener((int index) -> {
            if (controller == null) {
                return;
            }
            controller.notifyTabSwitched();
        });
        add(tabPages.getComponent(), BorderLayout.CENTER);
        filterTextLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (controller != null && e.getButton() == MouseEvent.BUTTON1 && !e.isPopupTrigger()) {
                    controller.changeFilter();
                }
            }
        });
        Document document = searchTextField.getDocument();
        document.addDocumentListener(new DocumentListener() {

            private String lastSearch = "";

            @Override
            public void insertUpdate(DocumentEvent de) {
                searchValueChanged();
            }

            @Override
            public void removeUpdate(DocumentEvent de) {
                searchValueChanged();
            }

            @Override
            public void changedUpdate(DocumentEvent de) {
                searchValueChanged();
            }

            public void searchValueChanged() {
                if (controller != null) {
                    String newSearch = searchTextField.getText();
                    if (!lastSearch.equals(newSearch)) {
                        lastSearch = newSearch;
                        controller.performSearch(newSearch);
                    }
                }
            }
        });
    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setCartComponent(Component cartComponent) {
        this.cartComponent = cartComponent;
    }

    public void setCartItemsCount(int itemsCount) {
        ((CartButton) cartButton).setChangesCount(itemsCount);
    }

    public void setFilter(String filterText) {
        filterTextLabel.setText(filterText);
    }

    public List<AddonManagerPage> getManagerTabs() {
        return managerTabs;
    }

    public TabPages createTabPagesWrapper() {
        return new TabPages() {
            @Override
            public JComponent getComponent() {
                return tabPages.getComponent();
            }

            @Override
            public void addPage(TabPagesComponent page) {
                managerTabs.add((AddonManagerPage) page);
                tabPages.addPage(page);
            }

            @Override
            public void changeActivePageIndex(int index) {
                tabPages.changeActivePageIndex(index);
            }

            @Override
            public int getActivePageIndex() {
                return tabPages.getActivePageIndex();
            }

            @Override
            public int getPagesCount() {
                return tabPages.getPagesCount();
            }

            @Override
            public void addPageChangeListener(TabPagesChangeListener listener) {
                tabPages.addPageChangeListener(listener);
            }

            @Override
            public void removePageChangeListener(TabPagesChangeListener listener) {
                tabPages.removePageChangeListener(listener);
            }
        };
    }

    public boolean isCartOpened() {
        return cartButton.isSelected();
    }

    public AddonManagerPage getActiveTab() {
        int activeIndex = tabPages.getActivePageIndex();
        return managerTabs.get(activeIndex);
    }

    private void initComponents() {
        headerPanel = new javax.swing.JPanel();
        filterLabel = new javax.swing.JLabel();
        filterTextLabel = new javax.swing.JLabel();
        searchLabel = new javax.swing.JLabel();
        searchTextField = new javax.swing.JTextField();
        cartButton = new CartButton();

        setLayout(new java.awt.BorderLayout());

        filterLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource(resourceBundle.getString("filterLabel.icon"))));
        filterLabel.setToolTipText(resourceBundle.getString("filterLabel.toolTipText")); // NOI18N

        filterTextLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        filterTextLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        searchLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource(resourceBundle.getString("searchLabel.icon"))));
        searchLabel.setToolTipText(resourceBundle.getString("searchLabel.toolTipText")); // NOI18N

        cartButton.setIcon(new javax.swing.ImageIcon(getClass().getResource(resourceBundle.getString("cartButton.icon"))));
        cartButton.setText(resourceBundle.getString("cartButton.text")); // NOI18N
        cartButton.setToolTipText(resourceBundle.getString("cartButton.toolTipText")); // NOI18N
        cartButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cartButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout headerPanelLayout = new javax.swing.GroupLayout(headerPanel);
        headerPanel.setLayout(headerPanelLayout);
        headerPanelLayout.setHorizontalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(searchLabel)
                    .addComponent(filterLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(filterTextLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(searchTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 766, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(cartButton)
                .addContainerGap())
        );
        headerPanelLayout.setVerticalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(headerPanelLayout.createSequentialGroup()
                        .addGroup(headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(filterTextLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(filterLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(searchLabel)
                            .addComponent(searchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(cartButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        add(headerPanel, java.awt.BorderLayout.PAGE_START);
    }

    private void cartButtonActionPerformed(java.awt.event.ActionEvent evt) {
        if (cartButton.isSelected()) {
            remove(tabPages.getComponent());
            add(cartComponent, BorderLayout.CENTER);
        } else {
            remove(cartComponent);
            add(tabPages.getComponent(), BorderLayout.CENTER);
        }
        controller.notifyTabSwitched();
        revalidate();
        repaint();
    }

    @NullMarked
    public interface Controller {

        /**
         * Notifies manager page was switched.
         */
        void notifyTabSwitched();

        /**
         * Changes filter.
         */
        void changeFilter();

        /**
         * Performs search.
         *
         * @param search search condition
         */
        void performSearch(String search);
    }
}
