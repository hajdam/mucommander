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

import org.jspecify.annotations.Nullable;
import org.jspecify.annotations.NullMarked;

/**
 * Simple structure for application version.
 */
@NullMarked
public class VersionNumbers {

    private @Nullable VersionNumbersFormat format;
    private int major;
    private int minor;
    private int release;
    private int patch;

    public VersionNumbers() {
    }

    public void versionFromString(String version) {
        int minorPos = version.indexOf(".");
        major = Integer.parseInt(version.substring(0, minorPos));
        int releasePos = version.indexOf(".", minorPos + 1);
        minor = Integer.parseInt(version.substring(minorPos + 1, releasePos));
        int patchPos = version.indexOf(".", releasePos + 1);
        if (patchPos > 0) {
            format = VersionNumbersFormat.MAJOR_MINOR_RELEASE_PATCH;
            release = Integer.parseInt(version.substring(releasePos + 1, patchPos));
            patch = Integer.parseInt(version.substring(patchPos + 1));
        } else {
            format = VersionNumbersFormat.MAJOR_MINOR_PATCH;
            patch = Integer.parseInt(version.substring(releasePos + 1));
        }
    }

    @Nullable
    public String versionAsString() {
        if (format == null) {
            return null;
        }

        switch (format) {
            case MAJOR_MINOR_PATCH: {
                return major + "." + minor + "." + patch;
            }
            case MAJOR_MINOR_RELEASE_PATCH: {
                return major + "." + minor + "." + release + "." + patch;
            }
            default:
                throw new IllegalStateException("Unexpected format " + format.name());
        }
    }

    public int getMajor() {
        return major;
    }

    public void setMajor(int major) {
        this.major = major;
    }

    public int getMinor() {
        return minor;
    }

    public void setMinor(int minor) {
        this.minor = minor;
    }

    public int getRelease() {
        return release;
    }

    public void setRelease(int release) {
        this.release = release;
    }

    public int getPatch() {
        return patch;
    }

    public void setPatch(int patch) {
        this.patch = patch;
    }

    public boolean isGreaterThan(VersionNumbers updateVersion) {
        if (major > updateVersion.major) {
            return true;
        }
        if (major == updateVersion.major && minor > updateVersion.minor) {
            return true;
        }
        if (minor == updateVersion.minor) {
            switch (format) {
                case MAJOR_MINOR_PATCH: {
                    switch (updateVersion.format) {
                        case MAJOR_MINOR_PATCH: {
                            if (patch > updateVersion.patch) {
                                return true;
                            }
                            break;
                        }

                        case MAJOR_MINOR_RELEASE_PATCH: {
                            if (updateVersion.release == 0 && patch > updateVersion.patch) {
                                return true;
                            }
                            break;
                        }
                        default:
                            throw new IllegalStateException("Unexpected format type " + updateVersion.format.name());
                    }

                    break;
                }
                case MAJOR_MINOR_RELEASE_PATCH: {
                    switch (updateVersion.format) {
                        case MAJOR_MINOR_PATCH: {
                            if (release > 0) {
                                return true;
                            }
                            if (release == 0 && patch > updateVersion.patch) {
                                return true;
                            }
                            break;
                        }

                        case MAJOR_MINOR_RELEASE_PATCH: {
                            if (release > updateVersion.release) {
                                return true;
                            }
                            if (release == updateVersion.release && patch > updateVersion.patch) {
                                return true;
                            }
                            break;
                        }
                        default:
                            throw new IllegalStateException("Unexpected format type " + updateVersion.format.name());
                    }

                    break;
                }
                default:
                    throw new IllegalStateException("Unexpected format type " + format.name());
            }
        }

        return false;
    }

    public static enum VersionNumbersFormat {
        MAJOR_MINOR_PATCH,
        MAJOR_MINOR_RELEASE_PATCH
    }
}
