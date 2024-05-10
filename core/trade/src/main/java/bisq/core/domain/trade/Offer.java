package bisq.core.domain.trade;

import io.micronaut.serde.annotation.Serdeable;

import jakarta.validation.constraints.NotBlank;

@Serdeable
public record Offer(String id, @NotBlank String details) {

}
