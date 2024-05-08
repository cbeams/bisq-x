package bisq.app.daemon;

import bisq.core.node.BisqNode;
import bisq.core.node.Options;
import bisq.core.util.Logging;

import joptsimple.OptionParser;

import org.slf4j.Logger;
import org.slf4j.event.Level;

import java.util.Arrays;

import static bisq.core.node.Options.*;

public class BisqDaemon {

    private static final String APP_NAME_AND_VERSION = "Bisq X version v2.1.0";

    private static final String[] HELP_OPTS = new String[]{"help", "h", "?"};
    private static final int EXIT_SUCCESS = 0;
    private static final int EXIT_FAILURE = 1;

    private static final Logger log = Logging.appLog;

    public static void main(String... args) {
        int status;
        try {
            status = execute(args);
        } catch (Exception ex) {
            log.error("Error: ", unwrapped(ex));
            status = EXIT_FAILURE;
        }
        // Exit
        log.info("Exiting with status {}", status);
        System.exit(status);
    }

    @SuppressWarnings("SameReturnValue")
    public static int execute(String... args) throws Exception {

        // ------------------------------------------------------------------
        // Initialize console output
        // ------------------------------------------------------------------

        // Default to high-level, useful and easily understood console output
        Logging.setLevel(Level.INFO);

        // Preprocess cli args for early access to help and debug opts
        var helpRequested = argsContainOption(args, HELP_OPTS);
        var debugRequested = argsContainOption(args, DEBUG_OPT);

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
        var helpOpt = parser.acceptsAll(Arrays.asList(HELP_OPTS), "Show this help").forHelp();
        options.configureCliOptionParsing(parser);

        log.trace("Parsing command line options");
        var cliOptions = parser.parse(args);

        log.trace("Handling parsed command line options");
        if (cliOptions.has(helpOpt)) {
            log.trace("Printing help and exiting");
            System.out.println(APP_NAME_AND_VERSION);
            System.out.print("""

                    Usage:  bisqd [options]                           Start Bisq Node

                    """);
            parser.printHelpOn(System.out);
            return EXIT_SUCCESS;
        }

        options.handleParsedCliOptions(cliOptions);

        // ------------------------------------------------------------------
        // Run node
        // ------------------------------------------------------------------

        new BisqNode(options).run();

        // Keep it running until killed
        try {
            Thread.currentThread().join();
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }

        return EXIT_SUCCESS;
    }

    // Unwrap excessive exception nesting for better log output
    private static Throwable unwrapped(Exception ex) {
        Throwable t = ex;
        while (t.getCause() != null && t.getMessage().contains("Exception:")) {
            t = t.getCause();
        }
        return t;
    }
}
