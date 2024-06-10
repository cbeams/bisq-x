package bisq.core.domain.trade;

import io.micronaut.serde.annotation.Serdeable;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

@Serdeable
public record Offer(String id, @NotBlank String details) {

    public static Offer withDetails(String details) {
        var uuid = UUID.randomUUID().toString();
        var id = uuid.substring(0, uuid.indexOf('-'));
        return new Offer(id, details);
    }
}
