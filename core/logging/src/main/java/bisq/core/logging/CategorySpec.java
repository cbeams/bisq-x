package bisq.core.logging;

import io.micronaut.serde.annotation.Serdeable;

import jakarta.validation.constraints.NotBlank;

@Serdeable
public record CategorySpec(@NotBlank String name, @NotBlank String level) implements Category {
}
