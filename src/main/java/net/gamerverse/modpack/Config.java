package net.gamerverse.modpack;

import net.gamerverse.modpack.config.FormatMode;
import net.gamerverse.modpack.config.NotificationMode;
import net.gamerverse.modpack.config.QuoteMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ModpackHelper.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {

    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final String CATEGORY_PICK = "copy_block_id";
    private static final String SUBCATEGORY_PICK_KUBEJS = "kubejs";
    private static final String CATEGORY_NOTIFICATION = "notification_setting";

    public static final ForgeConfigSpec.IntValue PICK_DISTANCE;
    public static final ForgeConfigSpec.BooleanValue PICK_FLUIDS;
    public static final ForgeConfigSpec.BooleanValue PICK_AIR_FILTER;
    public static final ForgeConfigSpec.EnumValue<FormatMode> PICK_FORMAT_MODE;

    public static final ForgeConfigSpec.EnumValue<QuoteMode> KUBEJS_QUOTES_MODE;

    public static final ForgeConfigSpec.BooleanValue NOTIFICATION_FORMATING;
    public static final ForgeConfigSpec.EnumValue<NotificationMode> NOTIFICATION_LOCATION;

    static {
        BUILDER.comment("Copy id settings").push(CATEGORY_PICK);

        PICK_DISTANCE = BUILDER
                .comment("Range of getting block id 0 <= 25")
                .defineInRange("pick_distance", 5, 0, 25);

        PICK_FLUIDS = BUILDER
                .comment("Option to copy the id of liquids")
                .define("pick_fluid", false);

        PICK_AIR_FILTER = BUILDER
                .comment("Option to get the id of air item in hand")
                .define("pick_air_filter", true);

        PICK_FORMAT_MODE = BUILDER
                .comment("The format that the copy block is")
                .defineEnum("pick_format_mode", FormatMode.NONE);

        BUILDER.comment("KubeJS Copy Setting").push(SUBCATEGORY_PICK_KUBEJS);

        KUBEJS_QUOTES_MODE = BUILDER
                .comment("If to have the output single or double quote")
                .defineEnum("kubejs_quotes_mode", QuoteMode.SINGLE_QUOTE);

        BUILDER.pop();

        BUILDER.pop();
        
        BUILDER.comment("Notification Setting").push(CATEGORY_NOTIFICATION);

        NOTIFICATION_FORMATING = BUILDER
                .comment("If to have formating to the notification")
                .define("notification_formating", true);

        NOTIFICATION_LOCATION = BUILDER
                .comment("Notification location of block id")
                .defineEnum("notification_mode", NotificationMode.ACTION_BAR);

        SPEC = BUILDER.build();
    }

    static final ForgeConfigSpec SPEC;
}
