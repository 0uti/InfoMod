package de.outinetworks.InfoMod;

import org.lwjgl.input.Keyboard;

import de.outinetworks.InfoMod.events.GUIHandlers;
import de.outinetworks.InfoMod.events.KeyPressHandler;


import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = InfoMod.MODID, version = InfoMod.VERSION, clientSideOnly = true)
public class InfoMod
{
    public static final String MODID = "InfoMod";
    public static final String VERSION = "1.0";
    
    // The Keys used in the game.
	public static KeyBinding SpawnOverlayKey;
	public static KeyBinding ChunkOverlayKey;
    
	@EventHandler
	public void preload(FMLPreInitializationEvent event){
		
		// Load Config File
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		
		// loading the configuration from its file
        config.load();

        // saving the configuration to its file
        config.save();
		
		
		// Only Client Side
		if(event.getSide() == Side.CLIENT){
			MinecraftForge.EVENT_BUS.register(new GUIHandlers());
			
			FMLCommonHandler.instance().bus().register(new KeyPressHandler());
			//MinecraftForge.EVENT_BUS.register(new KeyPressHandler());
			
			// SpawnOverlay
			SpawnOverlayKey = new KeyBinding("key.spawn_overlay", Keyboard.KEY_F7, "key.categories.infomod");
			ClientRegistry.registerKeyBinding(SpawnOverlayKey);
			
			// ChunkOverlay
	        ChunkOverlayKey = new KeyBinding("key.chunk_overlay", Keyboard.KEY_F9, "key.categories.infomod");
	        ClientRegistry.registerKeyBinding(ChunkOverlayKey);
		}
	}	
}
