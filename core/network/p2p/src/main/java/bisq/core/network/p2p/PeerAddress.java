package bisq.core.network.p2p;

public record PeerAddress(String host, int port) {

    public static PeerAddress fromString(String address) {
        var authority = address.replace("bisq://", "");
        var tokens = authority.split(":");
        var host = tokens[0];
        var port = Integer.parseInt(tokens[1]);
        return new PeerAddress(host, port);
    }

    @Override
    public String toString() {
        return String.format("bisq://%s:%d", host, port);
    }
}
