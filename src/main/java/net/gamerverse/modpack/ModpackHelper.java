package net.gamerverse.modpack;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ModpackHelper.MOD_ID)
public class ModpackHelper
{
    public static final String MOD_ID = "modpack_helper";

    public ModpackHelper(FMLJavaModLoadingContext context)
    {
        MinecraftForge.EVENT_BUS.register(this);
        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }
}
