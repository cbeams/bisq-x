package bisq.core.domain.identity;

import io.micronaut.serde.annotation.Serdeable;

import jakarta.validation.constraints.NotBlank;

@Serdeable
public record Identity(@NotBlank String nym) {
}
