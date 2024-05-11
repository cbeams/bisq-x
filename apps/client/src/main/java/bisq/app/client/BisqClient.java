package bisq.app.client;

import bisq.sdk.java.BisqNodeClient;

public class BisqClient {

    public static void main(String... args) {

        System.out.println("Bisq X client version v2.1.0");

        var remoteVersion = new BisqNodeClient().fetchVersion();

        System.out.println("Remote Bisq node version: " + remoteVersion);
    }
}
