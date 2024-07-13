package net.gamerverse.client;


import net.gamerverse.modpack.Config;
import net.gamerverse.modpack.ModpackHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import java.util.Objects;


@Mod.EventBusSubscriber(modid = ModpackHelper.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientForgeHandler {
    @SubscribeEvent
    public static void clientTick(TickEvent.ClientTickEvent event) {
        Minecraft minecraft = Minecraft.getInstance();
        if (Keybindings.INSTANCE.copying.consumeClick()) {
            if (minecraft.player == null || minecraft.level == null) {
                return;
            }
            HitResult hitResult = minecraft.player.pick(5, 0, true);
            switch (hitResult.getType()) {
                case BLOCK:
                    BlockHitResult blockHitResult = (BlockHitResult) hitResult;
                    var blockPos = blockHitResult.getBlockPos().immutable();
                    var blockState = minecraft.level.getBlockState(blockPos);
                    var block = blockState.getBlock();
                    var blockId = Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).toString();

                    setClipboard(blockId);
                    sendSystemMessage("Copied ( " + blockId + " ) to clipboard");
                    displayClientMessage("Copied ( " + blockId + " ) to clipboard", true);
                    sendMessage("Copied ( " + blockId + " ) to clipboard");
            }
        }
    }
    private static void setClipboard(String message) {
        Minecraft.getInstance().keyboardHandler.setClipboard(message);
    }

    private static void sendSystemMessage(String message) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null) {
            return;
        }
        minecraft.player.sendSystemMessage(Component.literal(message));
    }

    private static void displayClientMessage(String message, boolean actionBar) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null) {
            return;
        }
        minecraft.player.displayClientMessage(Component.literal(message), actionBar);
    }

    private static void sendMessage(String message) {
        System.out.println(Config.idOutputMode);
        switch (Config.idOutputMode) {
            case "Chat":
                sendSystemMessage(message);
            case "Title":
                displayClientMessage(message, false);
            case "ActionBar":
                displayClientMessage(message, true);
        }
    }
}
