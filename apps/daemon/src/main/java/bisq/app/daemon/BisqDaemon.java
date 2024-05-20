package bisq.app.daemon;

import bisq.core.node.BisqNode;
import bisq.core.node.BisqNodeApplication;
import bisq.core.node.CommandLine;
import bisq.core.node.Options;
import bisq.core.logging.Logging;

import org.slf4j.event.Level;

import static bisq.core.node.BisqNodeApplication.*;

public class BisqDaemon implements BisqNodeApplication {

    private static final String APP_NAME_AND_VERSION = "Bisq daemon version 2.1.0";

    public static void main(String... args) {
        int status;
        try {
            run(args);
            status = EXIT_SUCCESS;
        } catch (Throwable t) {
            log.error("Error: {}", unwrap(t).getMessage());
            status = EXIT_FAILURE;
        }
        log.info("Exiting with status {}", status);
        System.exit(status);
    }

    private static void run(String... args) {

        // ------------------------------------------------------------------
        // Init logging
        // ------------------------------------------------------------------

        Logging.setLevel(Level.INFO);

        var cli = new CommandLine(args);
        var helpRequested = cli.helpRequested();
        var debugRequested = cli.debugRequested();

        // Suppress normal log output if we know help output is coming
        if (helpRequested && !debugRequested) {
            Logging.setLevel(Level.WARN);
        }

        // Identify what is running
        log.info(APP_NAME_AND_VERSION);

        // Enable debug logging as early as possible if requested
        if (debugRequested) {
            Logging.setLevel(Level.DEBUG);
            log.debug("Enabling debug logging");
        }

        // ------------------------------------------------------------------
        // Configure node
        // ------------------------------------------------------------------

        log.debug("Configuring node options");
        var options = Options.withDefaultValues();
        try {
            cli.parse(options);
        } catch (CommandLine.HelpRequest request) {
            var helpText = request.getHelpText(
                    "Bisq daemon", "bisqd", "2.1.0", "Start Bisq");
            System.out.print(helpText);
            return;
        }

        // ------------------------------------------------------------------
        // Start node
        // ------------------------------------------------------------------

        BisqNode.withOptions(options).start();

        // ------------------------------------------------------------------
        // Keep alive
        // ------------------------------------------------------------------

        try {
            Thread.currentThread().join();
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }
}
