package de.outinetworks.InfoMod.config;

import de.outinetworks.InfoMod.InfoMod;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = InfoMod.MODID)
@Config.LangKey("infomod.config.title")
@Mod.EventBusSubscriber(modid = InfoMod.MODID)
public class InfoModConfig {
    private final static String config = InfoMod.MODID + ".config.";


    @Config.LangKey(config + "armordurability")
    @Config.Comment("Show armor durability.")
    public static boolean showArmorDurability = true;

    @Config.LangKey(config + "hotbardurability")
    @Config.Comment("Show hotbar items durability.")
    public static boolean showHotBarDurability = true;

    @Config.LangKey(config + "animalinfo")
    @Config.Comment("Show animal info overlay.")
    public static boolean showAnimalInfo = true;


    /**
     * Inject the new values and save to the config file when the config has been changed from the GUI.
     *
     * @param event The event
     */
    @SubscribeEvent
    public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(InfoMod.MODID)) {
            ConfigManager.sync(InfoMod.MODID, Config.Type.INSTANCE);
        }
    }
}
