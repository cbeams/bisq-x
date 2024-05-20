package bisq.core.node.app;

import bisq.core.node.Options;
import joptsimple.AbstractOptionSpec;
import joptsimple.ArgumentAcceptingOptionSpec;
import joptsimple.OptionParser;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Arrays;

import static bisq.core.node.Options.*;
import static bisq.core.node.ConfLog.log;
import static java.lang.String.format;

public class CommandLine {

    private static final String CONF_FILE_OPT = "conf";

    private static final String[] HELP_OPTS = new String[]{"help", "h", "?"};
    private static final String[] DEBUG_OPTS = new String[]{DEBUG_OPT, "d"};

    private final boolean helpRequested;
    private final boolean debugRequested;

    private final String[] args;

    public CommandLine(String[] args) {
        var preParser = new OptionParser();

        preParser.allowsUnrecognizedOptions();

        var helpOpt = helpOpt(preParser);
        var debugOpt = debugOpt(preParser, false);

        var preOpts = preParser.parse(args);

        this.helpRequested = preOpts.has(helpOpt);
        this.debugRequested = preOpts.has(debugOpt);

        this.args = args;
    }

    public boolean helpRequested() {
        return helpRequested;
    }

    public boolean debugRequested() {
        return debugRequested;
    }

    public void parse(Options options) throws HelpRequest {
        log.debug("Parsing command line arguments");
        var parser = new OptionParser();

        // ------------------------------------------------------------------
        // Configure parser using provided options for default values
        // ------------------------------------------------------------------

        var helpOpt = helpOpt(parser);

        var debugOpt = debugOpt(parser, options.debug());

        var appNameOpt = parser.accepts(APP_NAME_OPT, "Specify application name")
                .withRequiredArg()
                .ofType(String.class)
                .defaultsTo(options.appName());

        var dataDirOpt = parser.accepts(DATA_DIR_OPT, "Specify data directory")
                .withRequiredArg()
                .ofType(File.class)
                .defaultsTo(options.dataDir());

        var confFileOpt = parser.accepts(CONF_FILE_OPT,
                        format("Specify path to read-only configuration file. " +
                               "Relative paths will be prefixed by <%s> location " +
                               "(only usable from command line, not configuration file)", DATA_DIR_OPT))
                .withRequiredArg()
                .ofType(String.class)
                .defaultsTo(DEFAULT_CONF_FILENAME);

        var httpPortOpt = parser.accepts(HTTP_PORT_OPT, "Listen for http api requests on <port>")
                .withRequiredArg()
                .ofType(Integer.class)
                .defaultsTo(options.httpPort());

        var p2pPortOpt = parser.accepts(P2P_PORT_OPT, "Listen for peer connections on <port>")
                .withRequiredArg()
                .ofType(Integer.class)
                .defaultsTo(options.p2pPort());

        // ------------------------------------------------------------------
        // Do parsing
        // ------------------------------------------------------------------

        var cliOpts = parser.parse(args);

        // ------------------------------------------------------------------
        // Override provided options with values parsed from command line
        // ------------------------------------------------------------------

        if (cliOpts.has(helpOpt))
            throw new HelpRequest(parser);

        if (cliOpts.has(debugOpt))
            options.debug(cliOpts.valueOf(debugOpt));

        if (cliOpts.has(appNameOpt))
            options.appName(cliOpts.valueOf(appNameOpt));

        if (cliOpts.has(dataDirOpt))
            options.dataDir(cliOpts.valueOf(dataDirOpt));

        if (cliOpts.has(confFileOpt)) {
            String confFilePath = cliOpts.valueOf(confFileOpt);
            log.info("Using custom config file path {}", confFilePath);
            options.loadFromPath(confFilePath);
        } else {
            options.loadFromDataDir();
        }

        if (cliOpts.has(httpPortOpt))
            options.httpPort(cliOpts.valueOf(httpPortOpt));

        if (cliOpts.has(p2pPortOpt))
            options.p2pPort(cliOpts.valueOf(p2pPortOpt));

        options.cliArgs(args);

        var nonOptionArgs = cliOpts.nonOptionArguments();
        if (!nonOptionArgs.isEmpty())
            throw new IllegalArgumentException(
                    format("Command line contains unsupported argument(s) %s", nonOptionArgs));
    }

    private AbstractOptionSpec<Void> helpOpt(OptionParser parser) {
        return parser.acceptsAll(Arrays.asList(HELP_OPTS), "Show this help").forHelp();
    }

    private ArgumentAcceptingOptionSpec<Boolean> debugOpt(OptionParser parser, boolean defaultValue) {
        return parser.acceptsAll(Arrays.asList(DEBUG_OPTS), "Enable debug logging")
                .withOptionalArg()
                .ofType(Boolean.class)
                .defaultsTo(defaultValue);
    }


    public static class HelpRequest extends Exception {

        private final OptionParser parser;

        public HelpRequest(OptionParser parser) {
            this.parser = parser;
        }

        public String getHelpText(String fullName, String scriptName, String version, String description) {
            try {
                var output = new ByteArrayOutputStream();
                parser.formatHelpWith(new HelpFormatter(fullName, scriptName, version, description));
                parser.printHelpOn(output);
                return output.toString();
            } catch (IOException ex) {
                throw new UncheckedIOException(ex);
            }
        }
    }
}
