package fr.flash303.multiplehud.identifier;

import com.hypixel.hytale.server.core.entity.entities.player.hud.CustomUIHud;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * @author Flash303
 * @project HytaleMultipleHud
 * @date 25/01/2026 10:38
 */
public record HudClassIdentifier(@NotNull Class<? extends CustomUIHud> clazz) implements HudIdentifier {
    public HudClassIdentifier {
        Objects.requireNonNull(clazz, "clazz cannot be null");
    }

    public static HudClassIdentifier fromCustomHud(@NotNull CustomUIHud hud) {
        Objects.requireNonNull(hud, "hud cannot be null");
        return new HudClassIdentifier(hud.getClass());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        HudClassIdentifier that = (HudClassIdentifier) o;
        return Objects.equals(clazz, that.clazz);
    }
}
