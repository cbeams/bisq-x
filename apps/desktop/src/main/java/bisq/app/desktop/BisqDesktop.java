package bisq.app.desktop;

import bisq.core.node.BisqNode;
import bisq.core.node.Options;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class BisqDesktop extends Application {

    @Override
    public void start(Stage primaryStage) {
        Label label = new Label("Hello, Bisq User");
        StackPane root = new StackPane();
        root.getChildren().add(label);
        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Bisq X");
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> {
            System.exit(0);
        });

        new BisqNode(Options.withDefaultValues()).run();
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
