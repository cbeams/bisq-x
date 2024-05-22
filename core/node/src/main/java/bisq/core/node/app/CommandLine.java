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

        var debugOpt = parser.acceptsAll(DEBUG_OPTS,
                        "Enable or disable debug logging. When specified in the <" + CONF_FILE_OPT + "> file, debug " +
                        "logging is not enabled until node startup time. When specified at the command line, debug " +
                        "logging is enabled immediately, useful when debugging the configuration process.")
                .withOptionalArg()
                .ofType(Boolean.class)
                .describedAs("true|false")
                .defaultsTo(nodeOpts.debug());

        var baseDataDirOpt = parser.acceptsAll(BASE_DATA_DIR_OPTS,
                        "Specify the base directory to use when constructing the path to <" + APP_DATA_DIR_OPT + ">. " +
                        "Defaults to the OS-specific 'user data directory', e.g. ~/.local/share/ on *nix systems. " +
                        "Useful when running many short-lived dev and test instances whose data directories do not " +
                        "need to be kept around. Example: `bisqd --dir=/tmp`; Caveats: Use is supported only at the " +
                        "command line.")
                .withRequiredArg()
                .ofType(File.class)
                .describedAs("dir")
                .defaultsTo(nodeOpts.baseDataDir());

        var appNameOpt = parser.acceptsAll(APP_NAME_OPTS,
                        "Specify the name of this node. If specified at the command line and <" + APP_DATA_DIR_OPT + "> " +
                        "is not also specified, this value is used to construct the path to <" + APP_DATA_DIR_OPT + ">. " +
                        "If specified in <" + CONF_FILE_OPT + ">, this value is available only at runtime and does not " +
                        "affect the path to <" + APP_DATA_DIR_OPT + ">. Useful when working with more than one local " +
                        "node, e.g. Alice, Bob and Charlie instances whose data directories are long-lived. For " +
                        "example, running `bisqd --" + APP_NAME_OPT + "=Bisq-Alice` will use " +
                        "<" + BASE_DATA_DIR_OPT + ">/Bisq-Alice/ as its <" + APP_DATA_DIR_OPT + ">.")
                .withRequiredArg()
                .ofType(String.class)
                .describedAs("name")
                .defaultsTo(nodeOpts.appName());

        var appDataDirOpt = parser.acceptsAll(APP_DATA_DIR_OPTS,
                        "Specify the application data directory for this node. Defaults to " +
                        "<" + BASE_DATA_DIR_OPT + ">/<" + APP_NAME_OPT + ">/. Useful any time an instance other than " +
                        "one's own primary Bisq node is being run, e.g. in development and testing scenarios. " +
                        "Caveat: Use is supported only at the command line. ")
                .availableUnless(baseDataDirOpt)
                .withRequiredArg()
                .ofType(File.class)
                .describedAs("dir")
                .defaultsTo(nodeOpts.appDataDir());

        var confFileOpt = parser.accepts(CONF_FILE_OPT,
                        "Specify path to configuration file. Absolute paths are expected to exist and are " +
                        "loaded directly, relative paths are loaded if they exist relative to the current working " +
                        "directory and are otherwise expected to exist relative to <" + APP_DATA_DIR_OPT + ">. " +
                        "Caveat: Use is supported only at the command line.")
                .withRequiredArg()
                .ofType(String.class)
                .describedAs("file")
                .defaultsTo(DEFAULT_CONF_FILENAME);

        var p2pPortOpt = parser.accepts(P2P_PORT_OPT,
                        "Listen for p2p connections on <port>. Used to receive broadcast and direct " +
                        "messages from peers.")
                .withRequiredArg()
                .ofType(Integer.class)
                .describedAs("port")
                .defaultsTo(nodeOpts.p2pPort());

        var httpPortOpt = parser.accepts(HTTP_PORT_OPT,
                        "Listen for http connections on <port>. Used to receive API requests from client " +
                        "applications that query and/or control this node.")
                .withRequiredArg()
                .ofType(Integer.class)
                .describedAs("port")
                .defaultsTo(nodeOpts.httpPort());

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

        if (cliOpts.has(appNameOpt)) // 1st pass, for constructing path to app data dir
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

        if (cliOpts.has(appNameOpt)) // 2nd pass, so cli value overrides any conf value
            nodeOpts.appName(cliOpts.valueOf(appNameOpt));

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
