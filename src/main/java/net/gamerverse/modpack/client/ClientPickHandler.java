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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

import java.util.Objects;

@Mod.EventBusSubscriber(modid = ModpackHelper.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientPickHandler {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Minecraft minecraft = Minecraft.getInstance();

    private static boolean isClientNotReady() {
        return minecraft.player == null || minecraft.level == null;
    }

    @SubscribeEvent
    public static void clientTick(TickEvent.ClientTickEvent event) {
        if (Keybindings.INSTANCE.copyBlockId.consumeClick()) {
            copyTargetIdToClipboard();
        }
    }

    private static void copyTargetIdToClipboard() {
        if (isClientNotReady()) return;

        HitResult hit = minecraft.player.pick(
                Config.PICK_DISTANCE.get(),
                0,
                Config.PICK_FLUIDS.get()
        );

        switch (hit.getType()) {
            case BLOCK -> handleBlockTarget((BlockHitResult) hit);
            case MISS  -> handleAirTarget();
        }
    }

    private static void handleBlockTarget(BlockHitResult hit) {
        if (isClientNotReady()) return;

        BlockPos pos = hit.getBlockPos();
        BlockState state = minecraft.level.getBlockState(pos);

        if (state.getFluidState().isSource()) {
            var fluid = state.getFluidState().getType();
            String fluidId = Objects.requireNonNull(ForgeRegistries.FLUIDS.getKey(fluid)).toString();
            copyAndNotify(BlockTypes.FLUID, fluidId);

        } else {
            var block = state.getBlock();
            String blockId = Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).toString();
            copyAndNotify(BlockTypes.BLOCK, blockId);
        }
    }

    private static void handleAirTarget() {
        if (isClientNotReady()) return;
        Item playerMainHandItem = minecraft.player.getMainHandItem().getItem();
        String itemId = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(playerMainHandItem)).toString();

        if (Config.PICK_AIR_FILTER.get() && itemId.equals("minecraft:air")) {
            return;
        }

        copyAndNotify(BlockTypes.ITEM, itemId);
    }

    private static void copyAndNotify(BlockTypes type, String rawId) {
        String formattedId = FormatBlockId.formatId(type, rawId);
        setClipboard(formattedId);
        sendNotification(type, formattedId);
    }

    private static void setClipboard(String message) {
        minecraft.keyboardHandler.setClipboard(message);
    }

    private static void sendNotification(BlockTypes type, String message) {
        if (isClientNotReady()) return;

        MutableComponent component = buildMessage(type, message);

        switch (Config.NOTIFICATION_LOCATION.get()) {
            case CHAT ->
                    minecraft.player.sendSystemMessage(component);
            case ACTION_BAR ->
                    minecraft.player.displayClientMessage(component, true);
        }
    }

    private static MutableComponent buildMessage(BlockTypes type, String message) {
        if (Config.NOTIFICATION_FORMATING.get()) {
            return Component.translatable(
                    "modpack_helper.component.copied_to_clipboard",
                    type.name,
                    message
            );
        }
        return Component.literal(message);
    }
}
