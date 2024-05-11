package bisq.core.node;

import bisq.core.util.logging.Logging;
import org.slf4j.event.Level;

import joptsimple.ArgumentAcceptingOptionSpec;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import java.nio.file.Paths;

import java.util.Properties;

import static java.lang.String.format;
import static bisq.core.node.OptionsLog.log;

public final class Options {

    static final String DEFAULT_CONF_FILENAME = "bisq.conf";

    public static final String DEBUG_OPT = "debug";
    static final String APP_NAME_OPT = "app-name";
    static final String P2P_PORT_OPT = "p2p-port";
    static final String API_PORT_OPT = "api-port";
    static final String DATA_DIR_OPT = "data-dir";
    static final String CONF_FILE_OPT = "conf";

    public Boolean debug;
    public String appName;
    public Integer p2pPort;
    public Integer apiPort;
    public File userDataDir;
    public File dataDir;

    private ArgumentAcceptingOptionSpec<Boolean> debugOpt;
    private ArgumentAcceptingOptionSpec<String> appNameOpt;
    private ArgumentAcceptingOptionSpec<File> dataDirOpt;
    private ArgumentAcceptingOptionSpec<String> confFileOpt;
    private ArgumentAcceptingOptionSpec<Integer> apiPortOpt;

    private Options() {
    }

    public static Options withDefaultValues() {
        Options options = new Options();

        OptionsLog.log.debug("Loading system-specific option defaults");
        options.userDataDir = determineUserDataDir();

        log.debug("Loading bundled option defaults");
        options.loadFromClassPath(DEFAULT_CONF_FILENAME);

        log.debug("Loading computed option defaults");
        options.setDataDir(new File(options.userDataDir, options.appName));

        log.debug("Checking all option defaults");
        options.checkValueAssignments();

        return options;
    }

    private static File determineUserDataDir() {
        log.debug("Determining location of user data directory");
        return Paths.get(System.getProperty("user.home"), "Library", "Application Support").toFile();
    }

    public void loadFromClassPath(String resource) {
        log.debug("Loading options from config file at classpath:{}", resource);
        var propStream = Options.class.getClassLoader().getResourceAsStream(resource);
        if (propStream == null)
            throw new RuntimeException(format("Could not find config file '%s' as a classpath resource", resource));
        loadFromStream(propStream);
    }

    public void loadFromDataDir() {
        var confFile = new File(dataDir, DEFAULT_CONF_FILENAME);
        var confFileExists = confFile.exists();
        log.info("Using default config file {}{}",
                confFile,
                confFileExists ? "" : " (skipping, not found)");
        if (confFileExists) {
            loadFromFile(confFile);
        }
    }

    public void loadFromPath(String confFilePath) {
        log.debug("Evaluating specified config file path: {}", confFilePath);
        var confFile = new File(confFilePath);
        if (confFile.isAbsolute()) {
            log.debug("Proceeding to load config file because its path is absolute");
            loadFromFile(confFile);
        } else if (confFile.exists()) {
            log.debug("Proceeding to load relative config file path because it exists");
            loadFromFile(confFile);
        } else {
            log.debug("Prefixing relative config file path with data directory because it does not otherwise exist");
            loadFromFile(new File(dataDir, confFilePath));
        }
    }

    public void loadFromFile(File confFile) {
        log.debug("Loading options from config file: {}", confFile);

        if (!confFile.exists()) {
            throw new RuntimeException(format("Config file does not exist: %s", confFile));
        }

        try {
            loadFromStream(new FileInputStream(confFile));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadFromStream(InputStream inputStream) {
        Properties props = new Properties();
        try {
            props.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assignValues(props);
    }

    private void assignValues(Properties props) {
        for (Object key : props.keySet()) {
            switch ((String) key) {
                case DEBUG_OPT -> debug = Boolean.valueOf(props.getProperty(DEBUG_OPT));
                case APP_NAME_OPT -> appName = String.valueOf(props.getProperty(APP_NAME_OPT));
                case API_PORT_OPT -> apiPort = Integer.valueOf(props.getProperty(API_PORT_OPT));
                case P2P_PORT_OPT -> p2pPort = Integer.valueOf(props.getProperty(P2P_PORT_OPT));
                default -> log.warn("Ignoring unsupported option '{}'", key);
            }
        }
    }

    public void configureCliOptionParsing(OptionParser parser) {

        debugOpt = parser.accepts(DEBUG_OPT, "Enable debug logging")
                .withOptionalArg()
                .ofType(Boolean.class)
                .defaultsTo(this.debug);

        appNameOpt = parser.accepts(APP_NAME_OPT, "Specify application name")
                .withRequiredArg()
                .ofType(String.class)
                .defaultsTo(this.appName);

        dataDirOpt = parser.accepts(DATA_DIR_OPT, "Specify data directory")
                .withRequiredArg()
                .ofType(File.class)
                .defaultsTo(this.dataDir);

        confFileOpt = parser.accepts(CONF_FILE_OPT, format("Specify path to read-only configuration file. " +
                        "Relative paths will be prefixed by <%s> location " +
                        "(only usable from command line, not configuration file)", DATA_DIR_OPT))
                .withRequiredArg()
                .ofType(String.class)
                .defaultsTo(DEFAULT_CONF_FILENAME);

        apiPortOpt = parser.accepts(API_PORT_OPT, "Listen for rest api client requests on <port>")
                .withRequiredArg()
                .ofType(Integer.class)
                .defaultsTo(this.apiPort);

    }

    public void handleParsedCliOptions(OptionSet cliOptions) {
        if (cliOptions.has(debugOpt)) {
            Logging.setLevel(Level.DEBUG);
            this.debug = cliOptions.valueOf(debugOpt);
        }

        if (cliOptions.has(appNameOpt)) {
            this.appName = cliOptions.valueOf(appNameOpt);
        }

        if (cliOptions.has(dataDirOpt)) {
            this.setDataDir(cliOptions.valueOf(dataDirOpt));
        }

        if (cliOptions.has(confFileOpt)) {
            String confFilePath = cliOptions.valueOf(confFileOpt);
            log.info("Using custom config file path {}", confFilePath);
            this.loadFromPath(confFilePath);
        } else {
            this.loadFromDataDir();
        }

        if (cliOptions.has(apiPortOpt)) {
            this.apiPort = cliOptions.valueOf(apiPortOpt);
        }

        var nonOptionArgs = cliOptions.nonOptionArguments();
        if (!nonOptionArgs.isEmpty()) {
            throw new IllegalArgumentException(format(
                    "Command line contains unsupported argument(s) %s", nonOptionArgs));
        }
    }

    private void checkValueAssignments() {
        for (Field field : this.getClass().getDeclaredFields()) {
            var fieldName = field.getName();
            var fieldType = field.getType();
            try {
                if (Modifier.isStatic(field.getModifiers())) {
                    continue; // skip static fields; only option value instance fields matter
                }
                if (ArgumentAcceptingOptionSpec.class.isAssignableFrom(fieldType)) {
                    continue; // skip command line parsing instance fields
                }
                if (fieldType.isPrimitive()) {
                    throw new IllegalStateException(format("Option value fields may not be primitive. " +
                            "Please box the field named '%s' appropriately", fieldName));
                }
                if (field.get(this) == null) {
                    throw new IllegalStateException(format("Options fields may not be null after initialization time. " +
                                    "Please ensure the field named '%s' is assigned a default value. " +
                                    "This may be because its value was not assigned in Options.assignValues() " +
                                    "or because it is missing an entry in the default %s configuration file.", fieldName,
                            DEFAULT_CONF_FILENAME));
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static boolean argsContainOption(String[] args, String... opts) {
        for (String arg : args) {
            if (arg.startsWith("-")) {
                arg = arg.replaceFirst("^-?-", "");
                arg = arg.split("=")[0];
                for (String opt : opts) {
                    if (arg.equals(opt)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // -----------------------------------------------------------------------
    // option accessors
    // -----------------------------------------------------------------------

    public void setDataDir(File dataDir) {
        var dataDirExists = dataDir.exists();
        log.info("Using {} data directory {}{}",
                this.dataDir == null ? "default" : "custom",
                dataDir,
                dataDirExists ? "" : " (does not yet exist)");
        this.dataDir = dataDir;
    }
}
