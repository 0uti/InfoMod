package de.outinetworks.InfoMod.config;

import de.outinetworks.InfoMod.InfoMod;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

import java.util.List;


public class InfoModConfig extends GuiConfig {

    public InfoModConfig(GuiScreen parent) {
        super(parent, InfoModConfig.getConfigElements(), InfoMod.MODID, false, false, GuiConfig.getAbridgedConfigPath(ConfigurationHandler.config.toString()));
    }

    private static List<IConfigElement> getConfigElements() {
        return new ConfigElement(ConfigurationHandler.config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements();
    }

}
