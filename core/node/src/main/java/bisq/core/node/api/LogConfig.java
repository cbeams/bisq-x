package bisq.core.node.api;

import io.micronaut.serde.annotation.Serdeable;

import jakarta.validation.constraints.NotBlank;

@Serdeable
record LogConfig(@NotBlank String name, @NotBlank String level) {
}
