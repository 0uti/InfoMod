package de.outinetworks.InfoMod;


import de.outinetworks.InfoMod.config.ConfigurationHandler;
import de.outinetworks.InfoMod.events.GUIHandlers;
import de.outinetworks.InfoMod.events.KeyPressHandler;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.lwjgl.input.Keyboard;

@Mod(modid = InfoMod.MODID, name = InfoMod.NAME, version = InfoMod.VERSION, clientSideOnly = true, acceptedMinecraftVersions = InfoMod.MCVERSIONS, guiFactory = InfoMod.GUI_FACTORY_CLASS)
public class InfoMod {
    public static final String MODID = "infomod";
    static final String NAME = "InfoMod";
    static final String VERSION = "${mod_version}";
    static final String MCVERSIONS = "[1.10.2]";
    static final String GUI_FACTORY_CLASS = "de.outinetworks.InfoMod.config.GuiFactory";

    // The Keys used in the game.
    public static KeyBinding SpawnOverlayKey;
    public static KeyBinding ChunkOverlayKey;

    @EventHandler
    public void preload(FMLPreInitializationEvent event) {

        // Only Client Side
        if (event.getSide() == Side.CLIENT) {

            ConfigurationHandler.init(event.getSuggestedConfigurationFile());
            MinecraftForge.EVENT_BUS.register(new ConfigurationHandler());

            MinecraftForge.EVENT_BUS.register(new GUIHandlers());

            MinecraftForge.EVENT_BUS.register(new KeyPressHandler());

            // SpawnOverlay
            SpawnOverlayKey = new KeyBinding("infomod.key.spawn_overlay", Keyboard.KEY_F7, "infomod.key.category");
            ClientRegistry.registerKeyBinding(SpawnOverlayKey);

            // ChunkOverlay
            ChunkOverlayKey = new KeyBinding("infomod.key.chunk_overlay", Keyboard.KEY_F9, "infomod.key.category");
            ClientRegistry.registerKeyBinding(ChunkOverlayKey);
        }
    }
}
