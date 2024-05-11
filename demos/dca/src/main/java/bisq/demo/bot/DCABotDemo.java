package bisq.demo.bot;

import bisq.sdk.java.BisqNodeClient;

import java.time.Duration;

public class DCABotDemo {

    public static void main(String... args) {
        System.out.println("Bisq DCA bot demo");
        var nodeVersion = new BisqNodeClient().fetchVersion();
        System.out.println("Connected to node version " + nodeVersion);
        System.out.println("Placing offers to buy every 10 seconds until killed");
        while (true) {
            try {
                Thread.sleep(Duration.ofSeconds(10));
                System.out.println("Placing buy offer");
            } catch (InterruptedException ex) {
                System.err.println("Error: " + ex.getMessage());
                System.exit(1);
            }
        }
    }
}
