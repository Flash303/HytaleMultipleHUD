package fr.flash303.multiplehud.hud;

import com.hypixel.hytale.server.core.entity.entities.player.hud.CustomUIHud;
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import fr.flash303.multiplehud.identifier.HudIdentifier;
import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Flash303
 * @project HytaleMultipleHud
 * @date 25/01/2026 10:35
 */
public class HudWrapper extends CustomUIHud {
    private static final MethodHandle BUILD_HANDLE;

    static {
        MethodHandle handle = null;
        try {
            MethodHandles.Lookup lookup =
                    MethodHandles.privateLookupIn(CustomUIHud.class, MethodHandles.lookup());

            handle = lookup.findVirtual(
                    CustomUIHud.class,
                    "build",
                    MethodType.methodType(void.class, UICommandBuilder.class)
            );
        } catch (NoSuchMethodException | IllegalAccessException ignored) {}
        BUILD_HANDLE = handle;
    }


    private final Map<HudIdentifier, CustomUIHud> customHuds = new ConcurrentHashMap<>();

    public HudWrapper(@NotNull PlayerRef playerRef) {
        super(playerRef);
    }

    public void addCustomHud(@NotNull HudIdentifier identifier, @NotNull CustomUIHud customUIHud) {
        Objects.requireNonNull(identifier, "identifier cannot be null");
        Objects.requireNonNull(customUIHud, "customUIHud cannot be null");
        this.customHuds.put(identifier, customUIHud);
        show();
    }

    public void removeLastCustomHud() {
        if (this.lastIdentifier != null) {
            removeCustomHud(this.lastIdentifier);
            this.lastIdentifier = null;
        }
    }

    public void removeCustomHud(@NotNull HudIdentifier identifier) {
        this.customHuds.remove(identifier);
        show();
    }

    public void update(@NotNull HudIdentifier identifier, boolean clear, @NotNull UICommandBuilder commandBuilder) {
        Objects.requireNonNull(identifier, "identifier");
        Objects.requireNonNull(commandBuilder, "commandBuilder");
        CustomUIHud customUIHud = this.customHuds.get(identifier);
        if (customUIHud != null) {
            customUIHud.update(clear, commandBuilder);
        }
    }

    @Override
    protected void build(@NotNull UICommandBuilder uiCommandBuilder) {
        if (BUILD_HANDLE == null) return;

        for (Map.Entry<HudIdentifier, CustomUIHud> entry : customHuds.entrySet()) {
            try {
                BUILD_HANDLE.invoke(entry.getValue(), uiCommandBuilder);
            } catch (Throwable t) {
                Logger.getGlobal().log(Level.SEVERE, "Failed to build custom HUD: " + entry.getValue().getClass().getName(), t);
            }
        }
    }


    @Override
    public void update(boolean clear, @NotNull UICommandBuilder commandBuilder) {
        for (Map.Entry<HudIdentifier, CustomUIHud> entry : customHuds.entrySet()) {
            entry.getValue().update(clear, commandBuilder);
        }
    }
}