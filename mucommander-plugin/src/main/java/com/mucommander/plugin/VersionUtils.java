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

/**
 * Some simple static methods usable for version.
 */
@NullMarked
public class VersionUtils {
    
    public static final String SNAPSHOT_VERSION_SUFFIX = "-SNAPSHOT";

    private VersionUtils() {
    }

    /**
     * Compares version string to another version string.
     * <p>
     * Compares segments of the version separated by dot character. Numerical
     * segments are compared for value, string segments by character order.
     *
     * @param version compared version
     * @param thanVersion base version
     * @return true when version is greater
     */
    public static boolean isGreaterThan(String version, String thanVersion) {
        int versionSegment = 0;
        int thanVersionSegment = 0;

        while (versionSegment < version.length()) {
            int versionSplit = version.indexOf(".", versionSegment);
            if (versionSplit == -1) {
                versionSplit = version.length();
            }
            int thanVersionSplit = thanVersion.indexOf(".", thanVersionSegment);
            if (thanVersionSplit == -1) {
                thanVersionSplit = thanVersion.length();
            }

            String segment = version.substring(versionSegment, versionSplit);
            String thanSegment = thanVersion.substring(thanVersionSegment, thanVersionSplit);

            if (thanSegment.endsWith(SNAPSHOT_VERSION_SUFFIX)) {
                thanSegment = thanSegment.substring(0, thanSegment.length() - 9);
                if (thanSegment.equals(segment)) {
                    return true;
                }
            }

            if (segment.endsWith(SNAPSHOT_VERSION_SUFFIX)) {
                segment = segment.substring(0, segment.length() - 9);
            }

            try {
                int num = Integer.parseInt(segment);
                int thanNum = Integer.parseInt(thanSegment);

                if (num != thanNum) {
                    return num > thanNum;
                }
            } catch (NumberFormatException ex) {
                int compareTo = segment.compareToIgnoreCase(thanSegment);
                if (compareTo == -1) {
                    return false;
                } else if (compareTo == 1) {
                    return true;
                }
            }

            versionSegment = versionSplit + 1;
            thanVersionSegment = thanVersionSplit + 1;
            
            if (thanVersionSegment >= thanVersion.length() && versionSegment < version.length()) {
                return true;
            }
        }

        return false;
    }
}
