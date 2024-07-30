package net.gamerverse.modpack.client.blocktypes;

import net.gamerverse.modpack.Config;

public class FormatBlockId {
    public static String formatId(BlockTypes blockTypes, String blockId) {
        return switch (Config.PICK_FORMAT_MODE.get()) {
            case NONE -> blockId;
            case KUBEJS -> formatKubeJs(blockTypes, blockId);
            case CRAFTTWEAKER -> formatCraftTweaker(blockTypes, blockId);
        };
    }

    private static String formatKubeJs(BlockTypes blockTypes, String blockId) {
        String quoteMode = Config.KUBEJS_QUOTES_MODE.get().quote;
        return switch (blockTypes) {
            case ITEM, BLOCK -> quoteMode + blockId + quoteMode;
            case FLUID -> "Fluid.of(" + quoteMode + blockId + quoteMode + ")";
        };
    }

    private static String formatCraftTweaker(BlockTypes blockTypes, String blockId) {
        return switch (blockTypes) {
            case ITEM -> "<item:" + blockId + ">";
            case BLOCK -> "<block:" + blockId + ">";
            case FLUID -> "<fluid:" + blockId + ">";
        };
    }
}
