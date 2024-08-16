package net.gamerverse.modpack.client;

import com.mojang.logging.LogUtils;
import net.gamerverse.modpack.client.blocktypes.BlockTypes;
import net.gamerverse.modpack.client.blocktypes.FormatBlockId;
import net.gamerverse.modpack.Config;
import net.gamerverse.modpack.ModpackHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
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
        switch (hitResult.getType()) {
            case BLOCK:
                hitBlockType(hitResult);
                break;
            case MISS:
                hitMissType();
                break;
        }
    }

    private static void hitBlockType(HitResult hitResult) {
        if (minecraft.player == null || minecraft.level == null) {
            return;
        }
        BlockHitResult blockHitResult = (BlockHitResult) hitResult;
        BlockPos blockPos = blockHitResult.getBlockPos().immutable();
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

    private static void hitMissType() {
        if (minecraft.player == null) {
            return;
        }
        Item playerMainHandItem = minecraft.player.getMainHandItem().getItem();
        String itemId = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(playerMainHandItem)).toString();

        if (Config.PICK_AIR_FILTER.get() && itemId.equals("minecraft:air")) {
            return;
        }
        multiActions(BlockTypes.ITEM, itemId);
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
        if (minecraft.player == null) {return;}

        MutableComponent finished_message;
        if (Config.NOTIFICATION_FORMATING.get()) {
            finished_message = Component.translatable("component.modpack_helper.copied_to_clipboard", blockTypes.name, message);

        } else {
            finished_message = Component.literal(message);
        }
        System.out.println(finished_message);

        switch (Config.NOTIFICATION_LOCATION.get()) {
            case CHAT:
                minecraft.player.sendSystemMessage(finished_message);
                break;

            case ACTION_BAR:
                minecraft.player.displayClientMessage(finished_message, true);
                break;
        }
    }
}
