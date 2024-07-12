package net.gamerverse.client;


import com.mojang.logging.LogUtils;
import net.gamerverse.modpack.ModpackHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;


import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Clipboard;


@Mod.EventBusSubscriber(modid = ModpackHelper.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientForgeHandler {
    private static final Logger LOGGER = LogUtils.getLogger();

    @SubscribeEvent
    public static void clientTick(TickEvent.ClientTickEvent event) {
        Minecraft minecraft = Minecraft.getInstance();
        if (Keybindings.INSTANCE.copying.consumeClick()) {
            if (minecraft.player != null && minecraft.level != null) {
                HitResult hitResult = minecraft.player.pick(5, 0, false);
                if (hitResult.getType() == HitResult.Type.BLOCK) {
                    BlockHitResult blockHitResult = (BlockHitResult) hitResult;
                    var blockPos = blockHitResult.getBlockPos().immutable();
                    var blockState = minecraft.level.getBlockState(blockPos);
                    var block = blockState.getBlock();
                    var blockId = ForgeRegistries.BLOCKS.getKey(block).toString();


//                    copyToClipboard(blockId);
                    Minecraft.getInstance().keyboardHandler.setClipboard(blockId);

                    // Send chat message to player
                    minecraft.player.sendSystemMessage(Component.literal("Copied block ID to clipboard: " + blockId));

                }
            }
        }
    }

//    private static void copyToClipboard(String text) {
//        try {
//            StringSelection stringSelection = new StringSelection(text);
//            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
//            clipboard.setContents(stringSelection, null);
//        } catch (HeadlessException e) {
//            System.err.println("Failed to copy to clipboard (headless environment): " + e.getMessage());
//        }
//    }
}
