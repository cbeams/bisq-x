/*
 * This file is part of Bisq.
 *
 * Bisq is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version.
 *
 * Bisq is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with Bisq. If not, see <http://www.gnu.org/licenses/>.
 */

package bisq.core.node;

import java.io.File;
import java.nio.file.Paths;
import java.util.Locale;

import static bisq.core.node.ConfCategory.log;
import static java.lang.String.format;

class OperatingSystem {

    public static File getUserDataDir() {
        log.trace("Determining user data directory");
        var osName = getOSName();

        if (isWindows(osName)) {
            log.trace("OS is Windows");
            var prop = "APPDATA";
            var path = System.getenv(prop);
            if (path == null)
                throw new IllegalStateException(
                        format("Cannot determine user data directory because the '%s' " +
                               "environment variable is not set as expected. Is this " +
                               "actually a Windows OS?", prop));

            return validUserDataDir(path);
        }

        var homeProp = "user.home";
        var homePath = System.getProperty(homeProp);
        if (homePath == null || homePath.isBlank()) {
            throw new IllegalStateException(
                    format("Cannot determine user data directory because the '%s' " +
                           "system property is not set as expected.", homeProp));
        }

        if (isMac(osName)) {
            log.trace("OS is Mac");
            return validUserDataDir(homePath, "Library", "Application Support");
        }

        // is *nix
        log.trace("OS appears to be *nix");
        return validUserDataDir(homePath, ".local", "share");
    }

    private static boolean isWindows(String osName) {
        return osName.contains("win");
    }

    private static boolean isMac(String osName) {
        return osName.contains("mac") || osName.contains("darwin");
    }

    private static String getOSName() {
        var osProp = "os.name";
        var osName = System.getProperty(osProp);
        if (osName == null)
            throw new IllegalStateException(
                    format("The '%s' system property is not set as expected.", osProp));
        log.trace("OS name is '{}'", osName);
        return osName.toLowerCase(Locale.US);
    }

    private static File validUserDataDir(String dir, String... subdirs) {
        var path = Paths.get(dir, subdirs);
        var target = path.toFile();
        if (!target.isDirectory() || !target.canWrite())
            throw new IllegalStateException(
                    format("Cannot determine user data directory because " +
                    "'%s' is not a writeable directory as expected. Create " +
                           "this directory or modify its permissions as appropriate.", target));
        log.trace("Found user data directory {}", target);
        return target;
    }
}
