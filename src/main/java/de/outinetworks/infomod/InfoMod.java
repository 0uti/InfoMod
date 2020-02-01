package de.outinetworks.infomod;

import de.outinetworks.infomod.config.InfoModConfig;
import de.outinetworks.infomod.events.GUIHandlers;
import de.outinetworks.infomod.events.KeyPressHandler;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.lwjgl.glfw.GLFW;

@Mod(InfoMod.MOD_ID)
public class InfoMod
{
    public static final String MOD_ID = "infomod";

    // The Keys used in the game.
    public static KeyBinding SpawnOverlayKey;
    public static KeyBinding ChunkOverlayKey;

    public InfoMod()
    {
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        // Load Config
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, InfoModConfig.spec);
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void doClientStuff(final FMLClientSetupEvent event)
    {
        MinecraftForge.EVENT_BUS.register(new GUIHandlers());
        MinecraftForge.EVENT_BUS.register(new KeyPressHandler());

        // SpawnOverlay
        SpawnOverlayKey = new KeyBinding("infomod.key.spawn_overlay", GLFW.GLFW_KEY_F7, "infomod.key.category");
        ClientRegistry.registerKeyBinding(SpawnOverlayKey);

        // ChunkOverlay
        ChunkOverlayKey = new KeyBinding("infomod.key.chunk_overlay", GLFW.GLFW_KEY_F9, "infomod.key.category");
        ClientRegistry.registerKeyBinding(ChunkOverlayKey);
    }
}


