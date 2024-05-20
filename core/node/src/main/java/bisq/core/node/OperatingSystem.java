package bisq.core.node;

import java.io.File;
import java.nio.file.Paths;
import java.util.Locale;

class OperatingSystem {

    public static File getUserDataDir() {
        if (isWindows())
            return Paths.get(System.getenv("APPDATA")).toFile();

        if (isMac())
            return Paths.get(System.getProperty("user.home"), "Library", "Application Support").toFile();

        // is *nix
        return Paths.get(System.getProperty("user.home"), ".local", "share").toFile();
    }

    public static boolean isWindows() {
        return getOSName().contains("win");
    }

    public static boolean isMac() {
        return getOSName().toLowerCase().contains("mac") || getOSName().contains("darwin");
    }

    public static String getOSName() {
        return System.getProperty("os.name").toLowerCase(Locale.US);
    }
}
