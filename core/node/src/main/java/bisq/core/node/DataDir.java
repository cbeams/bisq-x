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

import bisq.core.logging.Logging;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.channels.FileLock;
import java.nio.file.Files;

import static bisq.core.node.NodeCategory.log;
import static java.lang.String.format;

class DataDir implements Closeable {

    private final File dir;
    private final FileLock lock;
    private final File pidFile;

    public static DataDir init(Options options) {
        return new DataDir(options.appDataDir(), options.cliArgs());
    }

    private DataDir(File dir, String[] cliArgs) {
        this.dir = dir;

        log.debug("Initializing data directory {}", dir);

        // Create data dir if necessary
        if (!dir.exists()) {
            log.info("Creating data directory {}", dir);
            //noinspection ResultOfMethodCallIgnored
            dir.mkdirs();
        }

        // Obtain exclusive lock so only one instance at a time may use this dir
        log.trace("Locking data directory {}", dir);
        try {
            var lockFile = new File(dir, ".lock");
            if (!lockFile.exists())
                //noinspection ResultOfMethodCallIgnored
                lockFile.createNewFile();
            //noinspection resource
            lock = new FileOutputStream(lockFile).getChannel().tryLock();
            if (lock == null)
                throw new IllegalStateException(
                        format("Could not obtain a lock on data directory '%s'. " +
                               "Bisq is probably already running.", dir));
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }

        // Begin writing to log file (in addition to console) now that it's safe to do so
        var logFile = new File(dir, "bisq.log");
        log.debug("Appending logging output to {}", logFile);
        Logging.addLogFileAppender(logFile);

        // Write current process id to pid file
        this.pidFile = new File(dir, "bisq.pid");
        long pid = ProcessHandle.current().pid();
        log.debug("Writing current process id ({}) to '{}'", pid, pidFile);
        try {
            Files.writeString(pidFile.toPath(), String.valueOf(pid) + '\n');
        } catch (IOException ex) {
            log.error("Error: unable to write process id ({}) to '{}'", pid, pidFile, ex);
        }

        // Write command line args to file
        var argsFile = new File(dir, "bisq.args");
        log.debug("Writing command line args (count: {}) to '{}'", cliArgs.length, argsFile);
        try {
            Files.writeString(argsFile.toPath(), String.join(" ", cliArgs));
        } catch (IOException ex) {
            log.error("Error: unable to write command line arg(s) to file '{}'", argsFile, ex);
        }
    }

    @Override
    public void close() {
        log.info("Cleaning up data directory");
        log.debug("Deleting pid file '{}'", pidFile);
        if (!pidFile.delete())
            log.error("Error: unable to delete pid file '{}'", pidFile);

        log.debug("Releasing lock on data directory '{}'", dir);
        try {
            lock.release();
        } catch (Exception ex) {
            log.error("Error: unable to release lock on data directory '{}'", dir, ex);
        }
    }
}