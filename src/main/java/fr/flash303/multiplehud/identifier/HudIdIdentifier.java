package fr.flash303.multiplehud.identifier;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * @author Flash303
 * @project HytaleMultipleHud
 * @date 25/01/2026 10:45
 */
public record HudIdIdentifier(@NotNull String identifier) implements HudIdentifier {
    public HudIdIdentifier {
        Objects.requireNonNull(identifier, "identifier cannot be null");
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        HudIdIdentifier that = (HudIdIdentifier) o;
        return Objects.equals(identifier, that.identifier);
    }
}
