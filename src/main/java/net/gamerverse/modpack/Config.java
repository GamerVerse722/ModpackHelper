package net.gamerverse.modpack;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

import java.util.Arrays;


@Mod.EventBusSubscriber(modid = ModpackHelper.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
    public static final String[] VALID_OUTPUT_MODES = new String[]{"Chat", "Title", "ActionBar", "None"};

    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec.ConfigValue<String> ID_OUTPUT_MODE = BUILDER
            .comment("Message Output valid option are ['Chat', 'Title', 'ActionBar', 'None']")
            .define("idOutputMode", "ActionBar");


    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static String idOutputMode;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        idOutputMode = ID_OUTPUT_MODE.get();

        if (!Arrays.asList(VALID_OUTPUT_MODES).contains(idOutputMode)) {
            idOutputMode = "ActionBar";
        }
    }
}
