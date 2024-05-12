package bisq.core.api;

import io.micronaut.serde.annotation.Serdeable;

import jakarta.validation.constraints.NotBlank;

@Serdeable
public record Info(@NotBlank String version) {

}
