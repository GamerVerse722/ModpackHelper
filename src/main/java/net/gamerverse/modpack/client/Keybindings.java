package net.gamerverse.modpack.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.gamerverse.modpack.ModpackHelper;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;

public final class Keybindings {
    public static final Keybindings INSTANCE = new Keybindings();

    private Keybindings() {}

    private static final String CATEGORY = "key.categories." + ModpackHelper.MOD_ID;

    public final KeyMapping copyBlockId = new KeyMapping(
            "key." + ModpackHelper.MOD_ID + ".copy_block_id",
            KeyConflictContext.IN_GAME,
            InputConstants.UNKNOWN,
            CATEGORY
    );
}
