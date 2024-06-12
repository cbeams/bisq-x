/*
 * This file is part of Bisq.
 *
 * Bisq is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version.
 *
 * Bisq is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with Bisq. If not, see <http://www.gnu.org/licenses/>.
 */

package bisq.app.desktop;

import bisq.core.domain.trading.offerbook.Offer;
import bisq.core.logging.LoggingService;
import bisq.core.node.BisqNode;
import bisq.core.node.Options;
import bisq.core.node.app.BisqNodeApp;
import bisq.core.node.app.CommandLine;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static bisq.core.node.app.BisqNodeApp.unwrap;
import static ch.qos.logback.classic.Level.*;

public class BisqDesktop extends Application implements BisqNodeApp {

    private static final String APP_NAME_AND_VERSION = "Bisq FX version 2.1.0";

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

        LoggingService.setLevel(INFO);

        var cli = new CommandLine(args);
        var helpRequested = cli.helpRequested();
        var debugRequested = cli.debugRequested();

        // Suppress normal log output if we know help screen is coming
        if (helpRequested && !debugRequested)
            LoggingService.setLevel(WARN);

        // Identify what is running
        log.info(APP_NAME_AND_VERSION);

        // Enable debug logging as early as possible if requested
        if (debugRequested)
            LoggingService.setLevel(DEBUG);

        // ------------------------------------------------------------------
        // Configure node
        // ------------------------------------------------------------------

        log.debug("Configuring node options");
        var options = Options.withDefaultValues();
        try {
            cli.parseAndLoad(options);
        } catch (CommandLine.HelpRequest request) {
            var helpText = request.getHelpText(
                    "Bisq FX", "bisq-fx", "2.1.0", "Start Bisq with GUI");
            System.out.print(helpText);
            System.exit(EXIT_SUCCESS);
        }
        log.debug("Finished configuring node options");

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

        var root = new BorderPane();
        var offerPane = new StackPane();
        var offerCreator = new VBox();

        root.setCenter(offerPane);
        root.setLeft(offerCreator);

        offerCreator.setMinWidth(150);

        var detailsInput = new TextField();
        var createOfferButton = new Button("Create Offer");

        offerCreator.getChildren().addAll(
                new Label("Details:"), detailsInput,
                createOfferButton);

        ObservableList<Offer> offers = FXCollections.observableArrayList();
        var offerListView = new ListView<>(offers);
        offerPane.getChildren().add(offerListView);

        var scene = new Scene(root, 450, 250);

        primaryStage.setTitle(bisqNode.getName());
        primaryStage.setScene(scene);

        primaryStage.show();

        // bind underlying offer repository to our observable list
        // of offers shown in the list view in a completely naive way
        var offerbook = bisqNode.getOfferbook();

        createOfferButton.setOnMouseClicked(e ->
                offerbook.save(Offer.withDetails(detailsInput.getText()))
        );

        offerbook.addChangeCallback(() ->
                Platform.runLater(() -> {
                    offers.clear();
                    offers.addAll(offerbook.findAll());
                }));
    }
}
