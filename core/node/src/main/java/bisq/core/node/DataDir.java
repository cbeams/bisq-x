package bisq.core.node;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.channels.FileLock;

import static bisq.core.node.OptionsLog.log;
import static java.lang.String.format;

class DataDir implements Closeable {

    private final File dir;
    private final FileLock lock;

    public DataDir(Options options) {
        this.dir = options.dataDir();

        // Create data dir if necessary
        if (!dir.exists()) {
            log.info("Creating data directory {}", dir);
            //noinspection ResultOfMethodCallIgnored
            dir.mkdirs();
        }

        // Obtain exclusive lock so only one instance at a time may use this dir
        try {
            var lockFile = new File(dir, ".lock");
            if (!lockFile.exists())
                //noinspection ResultOfMethodCallIgnored
                lockFile.createNewFile();
            //noinspection resource
            lock = new FileOutputStream(lockFile).getChannel().tryLock();
            if (lock == null)
                throw new IllegalStateException(
                        format("Cannot obtain a lock on data directory '%s'. Bisq is probably already running.", dir));
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }

        // TODO: create log file
        // TODO: create pid file
        // TODO: create args file
    }

    @Override
    public void close() {
        try {
            lock.release();
        } catch (IOException ex) {
            log.error("Error: unable to release lock on data directory '{}'", dir);
        }
    }
}