package bisq.core.network.p2p;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class P2PService implements Runnable {

    public static class Seed {
        public static void main(String[] args) {
            new P2PService(2140).start();
        }
    }

    public static class FirstPeer {
        public static void main(String[] args) {
            new P2PService(2240).start();
        }
    }

    public static class SecondPeer {
        public static void main(String[] args) {
            new P2PService(2340).start();
        }
    }

    private final int port;

    public P2PService(int port) {
        this.port = port;
    }

    /*
    flow

    service starts up
    - [x] binds server to port, is accepting requests
    - [ ] uses client to connect to seed node and find out about other peers
    - [ ] uses client to connect to other peers
    - [ ] and that's it. network is established

    commands involved are: connect and get_peers; neither are propagable

    then. when a command ... no. not yet. Just do the above.
     */

    @Override
    public void run() {
        ExecutorService executor = Executors.newCachedThreadPool();
        P2PServer server = new P2PServer("localhost", port);
        var localAddr = server.getAddress();
        executor.submit(server);

        if (port != 2140) {
            var seedAddr = "localhost:2140";
            try {
                var client = new P2PClient(seedAddr, localAddr);
                var ret = client.getPeers();
                System.out.println("ret = " + ret);
            } catch (IOException ex) {
                throw new UncheckedIOException(ex);
            }
        }
    }

    public void start() {
        run();
    }

    public void stop() {
        System.out.println("P2PService.stop");
    }
}

