package net.gamerverse.modpack.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.gamerverse.modpack.ModpackHelper;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;

public final class Keybindings {
    public static final Keybindings INSTANCE = new Keybindings();

    private Keybindings() {}

    private static final String CATEGORY = "key.categories." + ModpackHelper.MOD_ID;

    public final KeyMapping default_copy_id = new KeyMapping(
            "key." + ModpackHelper.MOD_ID + ".default_copy_id",
            KeyConflictContext.IN_GAME,
            InputConstants.UNKNOWN,
            CATEGORY
    );

    public final KeyMapping none_copy_id = new KeyMapping(
            "key." + ModpackHelper.MOD_ID + ".none_copy_id",
            KeyConflictContext.IN_GAME,
            InputConstants.UNKNOWN,
            CATEGORY
    );

    public final KeyMapping kubejs_copy_id = new KeyMapping(
            "key." + ModpackHelper.MOD_ID + ".kubejs_copy_id",
            KeyConflictContext.IN_GAME,
            InputConstants.UNKNOWN,
            CATEGORY
    );

    public final KeyMapping crafttweaker_copy_id = new KeyMapping(
            "key." + ModpackHelper.MOD_ID + ".crafttweaker_copy_id",
            KeyConflictContext.IN_GAME,
            InputConstants.UNKNOWN,
            CATEGORY
    );
}
