package net.gamerverse.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.gamerverse.modpack.ModpackHelper;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;

public final class Keybindings {
    public static final Keybindings INSTANCE = new Keybindings();

    private Keybindings() {}

    private static final String CATEGORY = "Modpack Helper";

    public final KeyMapping copying = new KeyMapping(
            "Copy Block Id",
            KeyConflictContext.IN_GAME,
            InputConstants.getKey("key.mouse.middle"),
            CATEGORY
    );
}
