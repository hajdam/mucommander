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
package com.mucommander.plugin.manager.operation;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import com.mucommander.conf.PlatformManager;
import com.mucommander.plugin.AddonUtils;
import com.mucommander.plugin.CancellableOperation;
import com.mucommander.plugin.ProgressOperation;
import org.jspecify.annotations.NullMarked;
import com.mucommander.plugin.manager.DownloadItemRecord;

/**
 * Download operation.
 */
@NullMarked
public class DownloadOperation implements Runnable, CancellableOperation, ProgressOperation {

    protected final List<DownloadItemRecord> records;
    protected DownloadOperation.ItemChangeListener listener;
    protected boolean cancelled = false;
    protected long totalDownloadSize = 0;
    protected long downloadProgress = 0;
    protected int lastProgress = 0;

    public DownloadOperation(List<DownloadItemRecord> records) {
        this.records = records;
    }

    @Override
    public void run() {
        // Ask for download sizes
        try {
            for (int i = 0; i < records.size(); i++) {
                DownloadItemRecord record = records.get(i);

                if (cancelled) {
                    return;
                }
                long contentLength = getContentLength(record.getUrl());
                totalDownloadSize += contentLength;
                record.setSize(contentLength);
                record.setStatus(DownloadItemRecord.Status.CHECKED);
                listener.itemChanged(i);
            }
        } catch (Exception ex) {
            return;
        }

        // Download
        File targetDirectory = new File(PlatformManager.getPreferencesFolder().getAbsolutePath(), AddonUtils.ADDONS_UPDATE_DIRECTORY);
        if (!targetDirectory.isDirectory()) {
            targetDirectory.mkdirs();
        }
        for (int i = 0; i < records.size(); i++) {
            DownloadItemRecord record = records.get(i);
            if (cancelled) {
                return;
            }

            File targetFile = new File(targetDirectory, record.getFileName());
            record.setStatus(DownloadItemRecord.Status.INPROGRESS);
            listener.itemChanged(i);
            downloadToFile(record, i, targetFile);
            record.setStatus(DownloadItemRecord.Status.DONE);
            listener.itemChanged(i);
        }
    }

    public void downloadToFile(DownloadItemRecord record, int recordIndex, File targetFile) {
        URL downloadUrl = record.getUrl();
        HttpURLConnection connection = null;
        try (FileOutputStream outputStream = new FileOutputStream(targetFile)) {
            connection = (HttpURLConnection) downloadUrl.openConnection();
            connection.setConnectTimeout(30 * 1000);
            connection.setRequestMethod("GET");
            connection.connect();
            try (InputStream inputStream = connection.getInputStream()) {
                final byte[] buffer = new byte[2048];
                long remaining = record.getSize();
                while (remaining > 0) {
                    if (cancelled) {
                        return;
                    }
                    int read = inputStream.read(buffer);
                    if (read < 0) {
                        throw new RuntimeException("Could not receive download size for URL " + downloadUrl);
                    }
                    outputStream.write(buffer, 0, read);
                    remaining -= read;
                    downloadProgress += read;
                    int operationProgress = getOperationProgress();
                    if (operationProgress != lastProgress) {
                        lastProgress = operationProgress;
                        listener.progressChanged(recordIndex);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not receive download size for URL " + downloadUrl, e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    @Override
    public void cancelOperation() {
        cancelled = true;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public int getOperationProgress() {
        return (int) (downloadProgress * 1000 / totalDownloadSize);
    }

    public void setItemChangeListener(DownloadOperation.ItemChangeListener listener) {
        this.listener = listener;
    }

    public static long getContentLength(URL downloadURL) {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) downloadURL.openConnection();
            connection.setRequestMethod("HEAD");
            return connection.getContentLengthLong();
        } catch (IOException e) {
            throw new RuntimeException("Could not receive download size for URL " + downloadURL, e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public static interface ItemChangeListener {

        void itemChanged(int itemIndex);

        void progressChanged(int itemIndex);
    }
}
