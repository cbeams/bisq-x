package bisq.app.desktop;

import bisq.core.domain.trade.Offer;
import bisq.core.node.BisqNode;
import bisq.core.node.BisqNodeApplication;
import bisq.core.node.Options;

import bisq.core.util.logging.Logging;
import org.slf4j.event.Level;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import joptsimple.OptionParser;

public class BisqDesktop extends Application implements BisqNodeApplication {

    private static final String APP_NAME_AND_VERSION = "Bisq FX v2.1.0";

    /**
     * Run the application via this helper main class. Doing so avoids the
     * 'JavaFX runtime components are missing, and are required to run this application'
     * error described at https://stackoverflow.com/a/67854230
     * and https://edencoding.com/runtime-components-error/
     */
    public static class Launcher {
        public static void main(String[] args) {
            BisqDesktop.main(args);
        }
    }

    /**
     * Do not run the application via this main method.
     * Run {@link BisqDesktop.Launcher#main(String[])} instead.
     */
    public static void main(String[] args) {
        int status;
        try {
            launch(args);
            status = EXIT_SUCCESS;
        } catch (Throwable t) {
            log.error("Error: {}", unwrap(t).getMessage());
            status = EXIT_FAILURE;
        }
        log.info("Exiting with status {}", status);
        Platform.exit();
        System.exit(status);
    }

    private Options options;

    @Override
    public void init() throws Exception {

        // ------------------------------------------------------------------
        // Initialize console output
        // ------------------------------------------------------------------

        // Get command line arguments
        String[] args = getParameters().getRaw().toArray(new String[]{});

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
        this.options = Options.withDefaultValues();

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

                    Usage:  bisq-fx [options]                         Start Bisq with GUI

                    """);
            parser.printHelpOn(System.out);
            return;
        }

        options.handleParsedCliOptions(cliOptions);
    }

    @Override
    public void start(Stage primaryStage) {

        StackPane root = new StackPane();

        ObservableList<Offer> offers = FXCollections.observableArrayList();
        ListView<Offer> offerListView = new ListView<>(offers);
        root.getChildren().add(offerListView);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Bisq");
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(event -> {
            System.exit(EXIT_SUCCESS);
        });

        primaryStage.show();

        var bisqNode = BisqNode.withOptions(options);

        // bind underlying offer repository to our observable list
        // of offers shown in the list view in a completely naive way
        var offerRepository = bisqNode.getOfferRepository();
        offerRepository.addChangeCallback(() -> {
            Platform.runLater(() -> {
                offers.clear();
                offers.addAll(offerRepository.findAll());
            });
        });

        bisqNode.run();
    }

    // Unwrap excessive exception nesting for better log output
    private static Throwable unwrap(Throwable t) {
        while (t.getCause() != null && t.getMessage().contains("Exception")) {
            t = t.getCause();
        }
        return t;
    }
}
