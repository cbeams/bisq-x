package bisq.app.client;

import bisq.sdk.java.BisqNodeClient;

public class BisqClient {

    private static final String APP_NAME_AND_VERSION = "Bisq RPC client v2.1.0";

    public static void main(String... args) {

        System.out.println(APP_NAME_AND_VERSION);
        System.out.print("""

                Usage:  bisq-cli [options] <command> [params]        Send command to Bisq

                """);

        var remoteVersion = new BisqNodeClient().fetchVersion();

        System.out.println("Remote Bisq node version: " + remoteVersion);
    }
}
