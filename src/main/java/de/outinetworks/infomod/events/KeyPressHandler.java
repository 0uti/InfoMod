package de.outinetworks.infomod.events;

import de.outinetworks.infomod.InfoMod;
import de.outinetworks.infomod.mods.ChunkOverlay;
import de.outinetworks.infomod.mods.SpawnOverlay;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class KeyPressHandler
{
	@SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event)
	{
		if(InfoMod.ChunkOverlayKey.isPressed()) ChunkOverlay.ToggleMode();
		if(InfoMod.SpawnOverlayKey.isPressed()) SpawnOverlay.ToggleEnabled();
	}
}
