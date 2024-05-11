package bisq.core.node;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;

import static bisq.core.node.Options.*;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;

public class OptionsTest {

    private static final Options DEFAULT_OPTIONS = Options.withDefaultValues();
    private static final int DEFAULT_P2P_PORT = DEFAULT_OPTIONS.p2pPort;
    private static final int DEFAULT_API_PORT = DEFAULT_OPTIONS.apiPort;

    private static final int CUSTOM_API_PORT = 9999;

    @Test
    public void loadFromConfFile_withOverride() throws IOException {
        var confFile = Files.createTempFile("bisq", "conf");

        Files.writeString(confFile, format("%s=%d", API_PORT_OPT, CUSTOM_API_PORT));

        var options = Options.withDefaultValues();
        options.loadFromFile(confFile.toFile());

        assertEquals(DEFAULT_P2P_PORT, options.p2pPort);
        assertEquals(CUSTOM_API_PORT, options.apiPort);
    }

    @Test
    public void loadFromDataDir_containingConfFile_withOverride() throws IOException {
        var tmpDataDir = Files.createTempDirectory("bisq");
        var confFile = Files.createFile(tmpDataDir.resolve(DEFAULT_CONF_FILENAME));

        Files.writeString(confFile, format("%s=%d", API_PORT_OPT, CUSTOM_API_PORT));

        var options = Options.withDefaultValues();
        options.dataDir = tmpDataDir.toFile();
        options.loadFromDataDir();

        assertEquals(DEFAULT_P2P_PORT, options.p2pPort);
        assertEquals(CUSTOM_API_PORT, options.apiPort);
    }

    @Test
    public void loadFromDataDir_containingNoConfFile() throws IOException {
        var tmpDataDir = Files.createTempDirectory("bisq");

        var options = Options.withDefaultValues();
        options.dataDir = tmpDataDir.toFile();
        options.loadFromDataDir();

        assertEquals(DEFAULT_P2P_PORT, options.p2pPort);
        assertEquals(DEFAULT_API_PORT, options.apiPort);
    }

    @Test
    public void loadFromDataDir_thatDoesNotExist() throws IOException {
        var dataDir = Files.createTempDirectory("bisq").toFile();

        //noinspection ResultOfMethodCallIgnored
        dataDir.delete();

        var options = Options.withDefaultValues();
        options.dataDir = dataDir;
        options.loadFromDataDir();

        assertEquals(dataDir, options.dataDir);
    }
}
