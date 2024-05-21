package bisq.core.node;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.lang.reflect.Field;
import java.util.Properties;

import static bisq.core.node.ConfLog.log;
import static java.lang.String.format;
import static java.lang.reflect.Modifier.isFinal;
import static java.lang.reflect.Modifier.isStatic;

public final class Options {

    public static final String DEFAULT_CONF_FILENAME = "bisq.conf";

    public static final String DEBUG_OPT = "debug";
    public static final String BASE_DATA_DIR_OPT = "base-data-dir";
    public static final String APP_NAME_OPT = "app-name";
    public static final String APP_DATA_DIR_OPT = "app-data-dir";
    public static final String P2P_PORT_OPT = "p2p-port";
    public static final String HTTP_PORT_OPT = "http-port";

    private Boolean debug;
    private File baseDataDir;
    private String appName;
    private File appDataDir;
    private Integer p2pPort;
    private Integer httpPort;

    private String[] cliArgs = new String[0];

    private Options() {
    }

    public static Options withDefaultValues() {
        var options = new Options();
        options.baseDataDir(OperatingSystem.getUserDataDir());
        options.loadFromClassPath(DEFAULT_CONF_FILENAME);
        options.appDataDir(new File(options.baseDataDir(), options.appName()));
        options.checkValueAssignments();
        return options;
    }

    public void loadFromClassPath(String resource) {
        log.debug("Loading options from bundled defaults at classpath:{}", resource);
        var propStream = Options.class.getClassLoader().getResourceAsStream(resource);
        if (propStream == null)
            throw new RuntimeException(format("Could not find config file '%s' as a classpath resource", resource));
        loadFromStream(propStream);
    }

    public void loadFromDataDir() {
        var confFile = new File(appDataDir, DEFAULT_CONF_FILENAME);
        var confFileExists = confFile.exists();
        log.info("Default config file {}{}", confFile, confFileExists ? "" : " (skipping, not found)");
        if (confFileExists)
            loadFromFile(confFile);
    }

    public void loadFromPath(String confFilePath) {
        log.debug("Resolving specified config file path {}", confFilePath);
        var confFile = new File(confFilePath);
        if (confFile.isAbsolute()) {
            log.debug("Proceeding to load config file because its path is absolute");
            loadFromFile(confFile);
        } else if (confFile.exists()) {
            log.debug("Proceeding to load relative config file path because it exists");
            loadFromFile(confFile);
        } else {
            log.debug("Prefixing relative config file path with data directory because it does not otherwise exist");
            loadFromFile(new File(appDataDir, confFilePath));
        }
    }

    public void loadFromFile(File confFile) {
        log.info("Using config file {}", confFile);

        if (!confFile.exists())
            throw new RuntimeException(format("Config file does not exist: %s", confFile));

        log.debug("Loading options from config file {}", confFile);
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
                case HTTP_PORT_OPT -> httpPort = Integer.valueOf(props.getProperty(HTTP_PORT_OPT));
                case P2P_PORT_OPT -> p2pPort = Integer.valueOf(props.getProperty(P2P_PORT_OPT));
                default -> log.warn("WARNING: Ignoring unsupported option '{}'", key);
            }
        }
    }

    private void checkValueAssignments() {
        for (Field field : this.getClass().getDeclaredFields()) {
            var fieldName = field.getName();
            var fieldType = field.getType();
            var modifiers = field.getModifiers();
            try {
                if (isStatic(modifiers) || isFinal(modifiers)) {
                    continue; // skip static and/or final fields; check only mutable option value instance fields
                }
                if (fieldType.isPrimitive()) {
                    throw new IllegalStateException(format("Option value fields may not be primitive. " +
                                                           "Please box the field named '%s' appropriately", fieldName));
                }
                if (field.get(this) == null) {
                    throw new IllegalStateException(format("Option fields may not be null after initialization time. " +
                                                           "Please ensure the field named '%s' is assigned a default " +
                                                           "value. This may be because its value was not assigned " +
                                                           "in Options.assignValues() or because it is missing an " +
                                                           "entry in the default %s configuration file.", fieldName, DEFAULT_CONF_FILENAME));
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // -----------------------------------------------------------------------
    // option accessors
    // -----------------------------------------------------------------------

    public boolean debug() {
        return this.debug;
    }

    public void debug(boolean debug) {
        this.debug = debug;
    }

    public String appName() {
        return this.appName;
    }

    public void appName(String appName) {
        this.appName = appName;
    }

    public File appDataDir() {
        return this.appDataDir;
    }

    public File baseDataDir() {
        return baseDataDir;
    }

    public void baseDataDir(File baseDataDir) {

        if (this.baseDataDir == null)
            log.debug("Default base data dir {}", baseDataDir);
        else
            log.debug("Using base data dir {}", baseDataDir);

        this.baseDataDir = baseDataDir;
    }

    public void appDataDir(File appDataDir) {
        var note = appDataDir.exists() ? "" : " (does not yet exist)";

        if (this.appDataDir == null)
            log.info("Default data directory {}{}", appDataDir, note);
        else
            log.info("Using data directory {}{}", appDataDir, note);

        this.appDataDir = appDataDir;
    }

    public int p2pPort() {
        return p2pPort;
    }

    public void p2pPort(int p2pPort) {
        this.p2pPort = p2pPort;
    }

    public int httpPort() {
        return httpPort;
    }

    public void httpPort(int httpPort) {
        this.httpPort = httpPort;
    }

    public void cliArgs(String[] cliArgs) {
        this.cliArgs = cliArgs;
    }

    public String[] cliArgs() {
        return cliArgs;
    }
}
