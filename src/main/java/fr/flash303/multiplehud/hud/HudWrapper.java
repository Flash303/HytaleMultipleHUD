package fr.flash303.multiplehud.hud;

import com.hypixel.hytale.server.core.entity.entities.player.hud.CustomUIHud;
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import fr.flash303.multiplehud.identifier.HudIdentifier;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Flash303
 * @project HytaleMultipleHud
 * @date 25/01/2026 10:35
 */
public class HudWrapper extends CustomUIHud {
    private static Method BUILD_METHOD;

    static {
        try {
            BUILD_METHOD = CustomUIHud.class.getDeclaredMethod("build", UICommandBuilder.class);
            BUILD_METHOD.setAccessible(true);
        } catch (NoSuchMethodException var1) {
            BUILD_METHOD = null;
        }
    }

    private final Map<HudIdentifier, CustomUIHud> customHuds = new ConcurrentHashMap<>();
    private HudIdentifier lastIdentifier = null;

    public HudWrapper(@NotNull PlayerRef playerRef) {
        super(playerRef);
    }

    public void addCustomHud(HudIdentifier identifier, CustomUIHud customUIHud) {
        this.customHuds.put(identifier, customUIHud);
        this.lastIdentifier = identifier;
        show();
    }

    public void removeLastCustomHud() {
        if (this.lastIdentifier != null) {
            removeCustomHud(this.lastIdentifier);
            this.lastIdentifier = null;
        }
    }

    public void removeCustomHud(HudIdentifier identifier) {
        this.customHuds.remove(identifier);
        show();
    }

    @Override
    protected void build(@NotNull UICommandBuilder uiCommandBuilder) {
        for (Map.Entry<HudIdentifier, CustomUIHud> entry : customHuds.entrySet()) {
            if (BUILD_METHOD != null) {
                try {
                    BUILD_METHOD.invoke(entry.getValue(), uiCommandBuilder);
                } catch (Exception e) {
                    Logger.getGlobal().log(Level.SEVERE, "Failed to build custom HUD: " + entry.getValue().getClass().getName(), e);
                }
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
