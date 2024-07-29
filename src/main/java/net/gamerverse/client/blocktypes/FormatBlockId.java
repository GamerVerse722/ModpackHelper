package net.gamerverse.client.blocktypes;

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
        return switch (blockTypes) {
            case BLOCK -> "'" + blockId + "'";
            case FLUID -> "Fluid.of('" + blockId + "')";
        };
    }

    private static String formatCraftTweaker(BlockTypes blockTypes, String blockId) {
        return switch (blockTypes) {
            case BLOCK -> "<block:" + blockId + ">";
            case FLUID -> "<fluid:" + blockId + ">";
        };
    }
}
