package bisq.core.logging.api;

import io.micronaut.serde.annotation.Serdeable;

import jakarta.validation.constraints.NotBlank;

@Serdeable
public record LogConfig(@NotBlank String name, @NotBlank String level) {
}
