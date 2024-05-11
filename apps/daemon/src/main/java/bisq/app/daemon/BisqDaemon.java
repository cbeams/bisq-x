package bisq.app.daemon;

import bisq.core.node.BisqNode;
import bisq.core.node.BisqNodeApplication;
import bisq.core.node.Options;
import bisq.core.util.logging.Logging;

import joptsimple.OptionParser;

import org.slf4j.event.Level;

public class BisqDaemon implements BisqNodeApplication {

    private static final int EXIT_SUCCESS = 0;
    private static final int EXIT_FAILURE = 1;

    private static final String APP_NAME_AND_VERSION = "Bisq X version v2.1.0";

    public static void main(String... args) {
        int status;
        try {
            execute(args);
            status = EXIT_SUCCESS;
        } catch (Throwable t) {
            log.error("Error: {}", unwrap(t).getMessage());
            status = EXIT_FAILURE;
        }
        log.info("Exiting with status {}", status);
        System.exit(status);
    }

    static void execute(String... args) throws Exception {

        // ------------------------------------------------------------------
        // Initialize console output
        // ------------------------------------------------------------------

        // Default to high-level, useful and easily understood console output
        Logging.setLevel(Level.INFO);

        // Preprocess cli args for early access to help and debug opts
        var helpRequested = Options.helpRequested(args);
        var debugRequested = Options.debugRequested(args);

        // Suppress normal log output if we know help screen is coming
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
        // Configure node options
        // ------------------------------------------------------------------

        log.info("Configuring node options");

        log.debug("Loading default option values");
        var options = Options.withDefaultValues();

        log.debug("Handling command line options");
        log.trace("Configuring command line option parsing");
        var parser = new OptionParser();
        options.configureCliOptionParsing(parser);

        log.trace("Parsing command line options");
        var cliOptions = parser.parse(args);

        log.trace("Handling parsed command line options");
        if (cliOptions.has(options.helpOpt())) {
            log.trace("Printing help and exiting");
            System.out.println(APP_NAME_AND_VERSION);
            System.out.print("""

                    Usage:  bisqd [options]                           Start Bisq Node

                    """);
            parser.printHelpOn(System.out);
            return;
        }

        options.handleParsedCliOptions(cliOptions);

        // ------------------------------------------------------------------
        // Run node
        // ------------------------------------------------------------------

        BisqNode.withOptions(options).run();

        // Keep it running until killed
        try {
            Thread.currentThread().join();
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }

    // Unwrap excessive exception nesting for better log output
    private static Throwable unwrap(Throwable t) {
        while (t.getCause() != null && t.getMessage().contains("Exception:")) {
            t = t.getCause();
        }
        return t;
    }
}
