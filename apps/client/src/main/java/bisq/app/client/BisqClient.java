package bisq.app.client;

import bisq.sdk.java.BisqNodeClient;

public class BisqClient {

    private static final String APP_NAME_AND_VERSION = "Bisq RPC client v2.1.0";

    public static void main(String... args) {
        try {
            execute(args);
            System.exit(0);
        } catch (Throwable t) {
            System.err.println("Error: " + t.getMessage());
            t.printStackTrace(System.err);
            System.exit(1);
        }
    }

    private static void execute(String... args) throws Exception {

        System.out.println(APP_NAME_AND_VERSION);
        System.out.print("""

                Usage:  bisq-cli [options] <command> [<args>]        Send command to Bisq

                """);

        var remoteVersion = new BisqNodeClient().fetchVersion();

        System.out.println("Remote Bisq node version: " + remoteVersion);
    }
}