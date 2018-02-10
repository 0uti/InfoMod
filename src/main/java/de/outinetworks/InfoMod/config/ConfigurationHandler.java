package de.outinetworks.InfoMod.config;

import de.outinetworks.InfoMod.InfoMod;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

import static net.minecraftforge.common.config.Configuration.CATEGORY_GENERAL;


public class ConfigurationHandler {

    public static Configuration config;

    public static boolean showArmorDurability = true;
    public static boolean showHotBarDurability = true;
    public static boolean showAnimalInfo = true;

    public static void init(File configFile) {
        if (config == null) {
            config = new Configuration(configFile);
            loadConfiguration();
        }
    }

    private static void loadConfiguration() {
        showArmorDurability = config.getBoolean("showArmorDurability",CATEGORY_GENERAL,true,"Show armor durability.", InfoMod.MODID + ".config.armordurability");
        showHotBarDurability = config.getBoolean("showHotBarDurability",CATEGORY_GENERAL,true,"Show hotbar items durability.", InfoMod.MODID + ".config.hotbardurability");
        showAnimalInfo = config.getBoolean("showAnimalInfo",CATEGORY_GENERAL,true,"Show animal info overlay.", InfoMod.MODID + ".config.animalinfo");

        if (config.hasChanged()) {
            config.save();
        }
    }


    @SubscribeEvent
    public void OnConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equalsIgnoreCase(InfoMod.MODID)) {
            loadConfiguration();
        }
    }

}
