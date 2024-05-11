package bisq.core.network.p2p;

public record P2PMessage(String type, String id, String body) {

    public enum Operation { ADD, REMOVE }
}
