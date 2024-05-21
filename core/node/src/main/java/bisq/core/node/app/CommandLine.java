package bisq.core.node.app;

import bisq.core.node.Options;
import joptsimple.OptionParser;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;

import static bisq.core.node.Options.*;
import static bisq.core.node.ConfLog.log;
import static java.lang.String.format;

public class CommandLine {

    private static final String CONF_FILE_OPT = "conf";

    private static final List<String> HELP_OPTS = List.of("help", "h", "?");
    private static final List<String> DEBUG_OPTS = List.of(DEBUG_OPT, "d");
    private static final List<String> BASE_DATA_DIR_OPTS = List.of(BASE_DATA_DIR_OPT, "base-dir");
    private static final List<String> APP_NAME_OPTS = List.of(APP_NAME_OPT, "name");
    private static final List<String> APP_DATA_DIR_OPTS = List.of(APP_DATA_DIR_OPT, "app-dir", "dir");

    private final boolean helpRequested;
    private final boolean debugRequested;

    private final String[] args;

    public CommandLine(String[] args) {
        var preParser = new OptionParser();

        preParser.allowsUnrecognizedOptions();

        var helpOpt = preParser.acceptsAll(HELP_OPTS).forHelp();
        var debugOpt = preParser.acceptsAll(DEBUG_OPTS)
                .withOptionalArg()
                .ofType(Boolean.class)
                .defaultsTo(true);

        var preOpts = preParser.parse(args);

        this.helpRequested = preOpts.has(helpOpt);
        this.debugRequested = preOpts.has(debugOpt) ? preOpts.valueOf(debugOpt) : false;
        this.args = args;
    }

    public boolean helpRequested() {
        return helpRequested;
    }

    public boolean debugRequested() {
        return debugRequested;
    }

    public void parseAndLoad(Options nodeOpts) throws HelpRequest {
        log.debug("Parsing command line arguments (count: {})", args.length);
        var parser = new OptionParser();

        // ------------------------------------------------------------------
        // Configure parser using provided options for default values
        // ------------------------------------------------------------------

        var helpOpt = parser.acceptsAll(HELP_OPTS, "Print this help message and exit").forHelp();

        var debugOpt = parser.acceptsAll(DEBUG_OPTS, "Enable/disable debug logging")
                .withOptionalArg()
                .ofType(Boolean.class)
                .describedAs("true|false")
                .defaultsTo(nodeOpts.debug());

        var baseDataDirOpt = parser.acceptsAll(BASE_DATA_DIR_OPTS, "Specify base data directory")
                .withRequiredArg()
                .ofType(File.class)
                .describedAs("dir")
                .defaultsTo(nodeOpts.baseDataDir());

        var appNameOpt = parser.acceptsAll(APP_NAME_OPTS, "Specify application name")
                .withRequiredArg()
                .ofType(String.class)
                .describedAs("name")
                .defaultsTo(nodeOpts.appName());

        var appDataDirOpt = parser.acceptsAll(APP_DATA_DIR_OPTS, "Specify application data directory")
                .availableUnless(baseDataDirOpt)
                .withRequiredArg()
                .ofType(File.class)
                .describedAs("dir")
                .defaultsTo(nodeOpts.appDataDir());

        var confFileOpt = parser.accepts(CONF_FILE_OPT,
                        format("Specify path to read-only configuration file. " +
                               "Relative paths will be prefixed by <%s> location " +
                               "(only usable from command line, not configuration file)", APP_DATA_DIR_OPT))
                .availableUnless(appNameOpt) // to avoid handling a complex edge case
                .withRequiredArg()
                .ofType(String.class)
                .describedAs("file")
                .defaultsTo(DEFAULT_CONF_FILENAME);

        var httpPortOpt = parser.accepts(HTTP_PORT_OPT, "Listen for http api requests on <port>")
                .withRequiredArg()
                .ofType(Integer.class)
                .describedAs("port")
                .defaultsTo(nodeOpts.httpPort());

        var p2pPortOpt = parser.accepts(P2P_PORT_OPT, "Listen for peer connections on <port>")
                .withRequiredArg()
                .ofType(Integer.class)
                .describedAs("port")
                .defaultsTo(nodeOpts.p2pPort());

        // ------------------------------------------------------------------
        // Do parsing
        // ------------------------------------------------------------------

        var cliOpts = parser.parse(args);

        // ------------------------------------------------------------------
        // Override provided options with values parsed from command line
        // ------------------------------------------------------------------

        log.debug("Loading options from command line arguments");

        // Print help and exit if requested
        if (cliOpts.has(helpOpt))
            throw new HelpRequest(parser);

        // -----------------------------------------------------------------------
        // Determine app data dir
        if (cliOpts.has(baseDataDirOpt))
            nodeOpts.baseDataDir(cliOpts.valueOf(baseDataDirOpt));

        if (cliOpts.has(appNameOpt))
            nodeOpts.appName(cliOpts.valueOf(appNameOpt));

        if (cliOpts.has(appDataDirOpt))
            nodeOpts.appDataDir(cliOpts.valueOf(appDataDirOpt));
        else if (cliOpts.has(baseDataDirOpt))
            nodeOpts.appDataDir(new File(nodeOpts.baseDataDir(), nodeOpts.appName()));
        else
            nodeOpts.appDataDir(nodeOpts.appDataDir()); // make using default dir explicit
        // -----------------------------------------------------------------------

        // Load options from specified conf file, only *after* determining app data dir
        if (cliOpts.has(confFileOpt))
            nodeOpts.loadFromPath(cliOpts.valueOf(confFileOpt));
        else
            nodeOpts.loadFromDataDir();

        // -----------------------------------------------------------------------
        // Resume loading options from cli, possibly overriding conf file values
        if (cliOpts.has(debugOpt))
            nodeOpts.debug(cliOpts.hasArgument(debugOpt) ? cliOpts.valueOf(debugOpt) : true);

        if (cliOpts.has(httpPortOpt))
            nodeOpts.httpPort(cliOpts.valueOf(httpPortOpt));

        if (cliOpts.has(p2pPortOpt))
            nodeOpts.p2pPort(cliOpts.valueOf(p2pPortOpt));

        log.debug("Finished loading options from command line arguments");
        // -----------------------------------------------------------------------

        nodeOpts.cliArgs(args);

        var nonOptionArgs = cliOpts.nonOptionArguments();
        if (!nonOptionArgs.isEmpty())
            throw new IllegalArgumentException(
                    format("Command line contains unsupported argument(s) %s", nonOptionArgs));
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
