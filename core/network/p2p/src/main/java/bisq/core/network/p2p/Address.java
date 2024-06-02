package bisq.core.network.p2p;

public record Address(String host, int port) {

    public static Address fromString(String address) {
        var authority = address.replace("bisq://", "");
        var tokens = authority.split(":");
        var host = tokens[0];
        var port = Integer.parseInt(tokens[1]);
        return new Address(host, port);
    }

    @Override
    public String toString() {
        return String.format("bisq://%s:%d", host, port);
    }
}
