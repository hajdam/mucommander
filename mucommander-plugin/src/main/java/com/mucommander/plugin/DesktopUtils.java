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

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jspecify.annotations.NullMarked;
import javax.swing.SwingUtilities;

/**
 * Utility static methods usable for desktop tasks.
 */
@NullMarked
public class DesktopUtils {

    private static final String ERROR_MESSAGE = "Error attempting to launch web browser";
    private static final String OS_NAME = "os.name";

    private DesktopUtils() {
    }

    /**
     * Invokes URL link opening via Desktop utility.
     *
     * @param url link URL
     */
    public static void openDesktopURL(final String url) {
        SwingUtilities.invokeLater(() -> {
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.BROWSE)) {
                    try {
                        URI uri = new URI(url);
                        desktop.browse(uri);
                        return;
                    } catch (IOException | URISyntaxException ex) {
                        Logger.getLogger(DesktopUtils.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }

    /**
     * Invokes URL link opening via Desktop utility.
     *
     * @param uri link URI
     */
    public static void openDesktopURL(final URI uri) {
        SwingUtilities.invokeLater(() -> {
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.BROWSE)) {
                    try {
                        desktop.browse(uri);
                        return;
                    } catch (IOException ex) {
                        Logger.getLogger(DesktopUtils.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }

    /**
     * Invokes URL link opening via Desktop utility.
     *
     * @param url link URL
     */
    public static void openDesktopURL(final URL url) {
        SwingUtilities.invokeLater(() -> {
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.BROWSE)) {
                    try {
                        URI uri = url.toURI();
                        desktop.browse(uri);
                        return;
                    } catch (IOException | URISyntaxException ex) {
                        Logger.getLogger(DesktopUtils.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }

    /**
     * Detects basic operating system.
     * <p>
     * See sun.awt.OSInfo
     * @return basic operating system type
     */
    public static OsType detectBasicOs() {
        String osName = System.getProperty(OS_NAME).toLowerCase();
        if (osName.contains("os x")) {
            return OsType.MACOSX;
        }

        if (osName.contains("windows")) {
            return OsType.WINDOWS;
        }

        if (osName.contains("linux")) {
            return OsType.LINUX;
        }

        if (osName.contains("aix")) {
            return OsType.AIX;
        }

        return OsType.UNKNOWN;
    }

    public enum OsType {
        WINDOWS,
        LINUX,
        MACOSX,
        AIX,
        UNKNOWN
    }
}
