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
package com.mucommander.plugin.manager.operation.gui;

import java.awt.Component;
import java.util.List;
import java.util.ResourceBundle;

import com.mucommander.plugin.manager.LanguageUtils;
import org.jspecify.annotations.Nullable;
import org.jspecify.annotations.NullMarked;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JList;
import com.mucommander.plugin.manager.DownloadItemRecord;

/**
 * Addons operation file downloads panel.
 */
@NullMarked
public class AddonOperationDownloadPanel extends javax.swing.JPanel {

    protected final ResourceBundle resourceBundle = ResourceBundle.getBundle(LanguageUtils.getResourceBaseNameBundleByClass(AddonOperationDownloadPanel.class));
    protected final ListModel listModel = new ListModel();

    private javax.swing.JLabel downloadItemsLabel;
    private javax.swing.JList<DownloadItemRecord> downloadItemsList;
    private javax.swing.JScrollPane downloadItemsScrollPane;
    private javax.swing.JProgressBar downloadProgressBar;
    private javax.swing.JPanel overallStatusPanel;

    public AddonOperationDownloadPanel() {
        initComponents();
        init();
    }

    private void init() {
        downloadItemsList.setCellRenderer(new DefaultListCellRenderer() {

            private final ImageIcon uncheckedIcon = new ImageIcon(getClass().getResource(resourceBundle.getString("downloadItem.unchecked.icon")));
            private final ImageIcon checkedIcon = new ImageIcon(getClass().getResource(resourceBundle.getString("downloadItem.checked.icon")));
            private final ImageIcon inprogressIcon = new ImageIcon(getClass().getResource(resourceBundle.getString("downloadItem.inprogress.icon")));
            private final ImageIcon doneIcon = new ImageIcon(getClass().getResource(resourceBundle.getString("downloadItem.done.icon")));

            @Override
            public Component getListCellRendererComponent(JList<?> list, @Nullable Object value, int index, boolean isSelected, boolean cellHasFocus) {
                if (value instanceof DownloadItemRecord) {
                    DownloadItemRecord record = (DownloadItemRecord) value;
                    Component component = super.getListCellRendererComponent(list, record.getDescription(), index, isSelected, cellHasFocus);
                    switch (record.getStatus()) {
                        case UNCHECKED:
                            setIcon(uncheckedIcon);
                            break;
                        case CHECKED:
                            setIcon(checkedIcon);
                            break;
                        case INPROGRESS:
                            setIcon(inprogressIcon);
                            break;
                        case DONE:
                            setIcon(doneIcon);
                            break;
                        default:
                            throw new AssertionError();
                    }
                    return component;
                }
                return super.getListCellRendererComponent(list, null, index, isSelected, cellHasFocus);
            }
        });
        downloadItemsList.setModel(listModel);
    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    public void setDownloadedItemRecords(List<DownloadItemRecord> records) {
        listModel.removeAllElements();
        for (DownloadItemRecord record : records) {
            listModel.addElement(record);
        }
    }

    public void notifyDownloadedItemChanged(int recordIndex) {
        listModel.rowChanged(recordIndex);
        if (recordIndex == listModel.getSize() - 1) {
            DownloadItemRecord record = listModel.get(recordIndex);
            if (record.getStatus() == DownloadItemRecord.Status.DONE) {
                downloadProgressBar.setString(resourceBundle.getString("downloadProgressBar.finished"));
            }
        }
    }

    public void setProgress(String fileName, int progress, boolean indeterminate) {
        downloadProgressBar.setString(String.format(resourceBundle.getString("downloadProgressBar.progress"), fileName, (progress / 10f)));
        downloadProgressBar.setValue(progress);
        downloadProgressBar.setIndeterminate(indeterminate);
        downloadProgressBar.repaint();
    }

    private void initComponents() {
        downloadItemsLabel = new javax.swing.JLabel();
        downloadItemsScrollPane = new javax.swing.JScrollPane();
        downloadItemsList = new javax.swing.JList<>();
        overallStatusPanel = new javax.swing.JPanel();
        downloadProgressBar = new javax.swing.JProgressBar();

        downloadItemsLabel.setText(resourceBundle.getString("downloadItemsLabel.text")); // NOI18N

        downloadItemsScrollPane.setViewportView(downloadItemsList);

        downloadProgressBar.setMaximum(1000);
        downloadProgressBar.setStringPainted(true);

        javax.swing.GroupLayout overallStatusPanelLayout = new javax.swing.GroupLayout(overallStatusPanel);
        overallStatusPanel.setLayout(overallStatusPanelLayout);
        overallStatusPanelLayout.setHorizontalGroup(
            overallStatusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(overallStatusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(downloadProgressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        overallStatusPanelLayout.setVerticalGroup(
            overallStatusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, overallStatusPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(downloadProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(overallStatusPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(downloadItemsScrollPane)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(downloadItemsLabel)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(downloadItemsLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(downloadItemsScrollPane)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(overallStatusPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }

    private static class ListModel extends DefaultListModel<DownloadItemRecord> {

        void rowChanged(int rowIndex) {
            fireContentsChanged(this, rowIndex, rowIndex);
        }
    }
}
