package net.gamerverse.modpack.client.blocktypes;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public enum BlockTypes {
    ITEM(Component.translatable("modpack_helper.blocktypes.item")),
    BLOCK(Component.translatable("modpack_helper.blocktypes.block")),
    FLUID(Component.translatable("modpack_helper.blocktypes.fluid"));

    public final MutableComponent name;

    BlockTypes(MutableComponent name) {
        this.name = name;
    }

}
