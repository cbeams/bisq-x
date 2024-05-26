package bisq.core.node;

import java.io.File;
import java.nio.file.Paths;
import java.util.Locale;

import static java.lang.String.format;
import static bisq.core.node.ConfCategory.log;

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
