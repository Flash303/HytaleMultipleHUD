package fr.flash303.multiplehud;

import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.entity.entities.player.hud.CustomUIHud;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import fr.flash303.multiplehud.hud.HudWrapper;
import fr.flash303.multiplehud.identifier.HudClassIdentifier;
import fr.flash303.multiplehud.identifier.HudIdIdentifier;
import fr.flash303.multiplehud.identifier.HudIdentifier;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * @author Flash303
 * @project HytaleMultipleHud
 * @date 25/01/2026 10:34
 */
public class MultipleHud {
    public static void displayCustomHud(@NotNull Player player,
                                        @NotNull PlayerRef playerRef,
                                        @NotNull CustomUIHud customHud) {
        displayCustomHud(player, playerRef, HudClassIdentifier.fromCustomHud(customHud), customHud);
    }

    public static void displayCustomHud(@NotNull Player player,
                                        @NotNull PlayerRef playerRef,
                                        @NotNull String identifier,
                                        @NotNull CustomUIHud customHud) {
        displayCustomHud(player, playerRef, new HudIdIdentifier(identifier), customHud);
    }

    public static void displayCustomHud(@NotNull Player player,
                                        @NotNull PlayerRef playerRef,
                                        @NotNull HudIdentifier identifier,
                                        @NotNull CustomUIHud customHud) {
        Objects.requireNonNull(player, "player cannot be null");
        Objects.requireNonNull(playerRef, "playerRef cannot be null");
        Objects.requireNonNull(identifier, "identifier cannot be null");
        Objects.requireNonNull(customHud, "customHud cannot be null");

        final CustomUIHud currentHud = player.getHudManager().getCustomHud();

        HudWrapper wrapper;
        boolean isWrapper = false;

        if (currentHud instanceof HudWrapper) {
            isWrapper = true;
            wrapper = (HudWrapper) currentHud;
        } else {
            wrapper = new HudWrapper(playerRef);

            if (currentHud != null) {
                wrapper.addCustomHud(HudClassIdentifier.fromCustomHud(currentHud), currentHud);
            }
        }

        wrapper.addCustomHud(identifier, customHud);

        if (!isWrapper) {
            player.getHudManager().setCustomHud(playerRef, wrapper);
        }
    }

    public static void removeCustomHud(@NotNull Player player,
                                       @NotNull HudIdentifier identifier) {
        Objects.requireNonNull(player, "player cannot be null");
        Objects.requireNonNull(identifier, "identifier cannot be null");

        final CustomUIHud currentHud = player.getHudManager().getCustomHud();
        if (currentHud instanceof HudWrapper wrapper) {
            wrapper.removeCustomHud(identifier);
        }
    }
}
