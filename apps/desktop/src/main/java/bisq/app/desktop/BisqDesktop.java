package bisq.app.desktop;

import bisq.core.domain.trade.Offer;
import bisq.core.node.BisqNode;
import bisq.core.node.BisqNodeApplication;
import bisq.core.node.Options;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class BisqDesktop extends Application implements BisqNodeApplication {

    @Override
    public void start(Stage primaryStage) {
        ObservableList<Offer> offers = FXCollections.observableArrayList();
        ListView<Offer> listView = new ListView<>(offers);

        StackPane root = new StackPane();
        root.getChildren().add(listView);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Bisq");
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> {
            System.exit(0);
        });

        var options = Options.withDefaultValues();
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

    // See Launcher below
    public static void main(String[] args) {
        launch(args);
    }

    // -----------------------------------------------------------------------
    // Run this main method when launching from within an IDE
    // See https://edencoding.com/runtime-components-error/
    // and https://stackoverflow.com/a/67854230 for details
    // -----------------------------------------------------------------------
    public static class Launcher {
        public static void main(String[] args) {
            BisqDesktop.main(args);
        }
    }
}
