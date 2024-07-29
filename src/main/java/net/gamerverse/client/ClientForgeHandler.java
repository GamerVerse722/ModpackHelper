package net.gamerverse.client;

import com.mojang.logging.LogUtils;
import net.gamerverse.client.blocktypes.BlockTypes;
import net.gamerverse.client.blocktypes.FormatBlockId;
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
import org.slf4j.Logger;

import java.text.MessageFormat;
import java.util.Objects;

@Mod.EventBusSubscriber(modid = ModpackHelper.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientForgeHandler {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Minecraft minecraft = Minecraft.getInstance();

    @SubscribeEvent
    public static void clientTick(TickEvent.ClientTickEvent event) {
        if (Keybindings.INSTANCE.copyBlockId.consumeClick()) {
            handleBlockOrFluidIdCopy();
        }
    }

    private static void handleBlockOrFluidIdCopy() {
        if (minecraft.player == null || minecraft.level == null) {
            return;
        }

        HitResult hitResult = minecraft.player.pick(Config.PICK_DISTANCE.get(), 0, Config.PICK_FLUIDS.get());
        if (hitResult.getType() == HitResult.Type.BLOCK) {
            BlockHitResult blockHitResult = (BlockHitResult) hitResult;
            var blockPos = blockHitResult.getBlockPos().immutable();
            var blockState = minecraft.level.getBlockState(blockPos);

            if (blockState.getFluidState().isSource()) {
                var fluid = blockState.getFluidState().getType();
                String fluidId = Objects.requireNonNull(ForgeRegistries.FLUIDS.getKey(fluid)).toString();
                multiActions(BlockTypes.FLUID, fluidId);
            } else {
                var block = blockState.getBlock();
                String blockId = Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).toString();
                multiActions(BlockTypes.BLOCK, blockId);
            }
        }
    }

    private static void multiActions(BlockTypes blockTypes, String blockId) {
        String formated_id = FormatBlockId.formatId(blockTypes, blockId);
        setClipboard(formated_id);
        sendMessage(blockTypes, formated_id);
    }

    private static void setClipboard(String message) {
        minecraft.keyboardHandler.setClipboard(message);
    }

    private static void sendMessage(BlockTypes blockTypes, String message) {
        String formated_message;
        if (Config.NOTIFICATION_FORMATING.get()) {
            formated_message = MessageFormat.format("Copied {0} ( {1} ) to clipboard.", blockTypes.name, message);
        } else {
            formated_message = message;
        }

        if (minecraft.player == null) {
            return;
        }

        switch (Config.NOTIFICATION_LOCATION.get()) {
            case CHAT:
                minecraft.player.sendSystemMessage(Component.literal(formated_message));
                break;

            case ACTION_BAR:
                minecraft.player.displayClientMessage(Component.literal(formated_message), true);
                break;
        }
    }
}
