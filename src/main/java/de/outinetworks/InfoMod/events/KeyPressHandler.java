package de.outinetworks.InfoMod.events;

import de.outinetworks.InfoMod.InfoMod;
import de.outinetworks.InfoMod.mods.ChunkOverlay;
import de.outinetworks.InfoMod.mods.SpawnOverlay;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class KeyPressHandler
{

	@SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event)
	{
		if(InfoMod.ChunkOverlayKey.isPressed()) ChunkOverlay.ToggleMode();
		if(InfoMod.SpawnOverlayKey.isPressed()) SpawnOverlay.ToggleEnabled();
	}
}
