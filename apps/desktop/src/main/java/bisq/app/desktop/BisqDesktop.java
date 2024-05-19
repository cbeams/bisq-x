package bisq.app.desktop;

import bisq.core.domain.trade.Offer;
import bisq.core.node.BisqNode;
import bisq.core.node.BisqNodeApplication;
import bisq.core.node.CommandLine;
import bisq.core.node.Options;
import bisq.core.logging.Logging;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import org.slf4j.event.Level;

import static bisq.core.node.BisqNodeApplication.*;

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
            run(args);
            status = EXIT_SUCCESS;
        } catch (Throwable t) {
            log.error("Error: {}", unwrap(t).getMessage());
            status = EXIT_FAILURE;
        }
        log.info("Exiting with status {}", status);
        System.exit(status);
    }

    private static BisqNode bisqNode;

    private static void run(String... args) {

        // ------------------------------------------------------------------
        // Init logging
        // ------------------------------------------------------------------

        Logging.setLevel(Level.INFO);

        var cli = new CommandLine(args);
        var helpRequested = cli.helpRequested();
        var debugRequested = cli.debugRequested();

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
        // Configure node
        // ------------------------------------------------------------------

        log.info("Configuring node options");
        var options = Options.withDefaultValues();
        try {
            cli.parse(options);
        } catch (CommandLine.HelpRequest request) {
            System.out.println(APP_NAME_AND_VERSION);
            System.out.print("""

                    Usage:  bisq-fx [options]                         Start Bisq with GUI

                    """);
            System.out.println(request.getHelpText());
            System.exit(EXIT_SUCCESS);
        }

        // ------------------------------------------------------------------
        // Start node
        // ------------------------------------------------------------------

        bisqNode = BisqNode.withOptions(options);
        bisqNode.start();

        // ------------------------------------------------------------------
        // Launch gui
        // ------------------------------------------------------------------

        launch(args);
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

        primaryStage.show();

        // bind underlying offer repository to our observable list
        // of offers shown in the list view in a completely naive way
        var offerRepository = bisqNode.getOfferRepository();
        offerRepository.addChangeCallback(() ->
                Platform.runLater(() -> {
                    offers.clear();
                    offers.addAll(offerRepository.findAll());
                }));
    }
}
