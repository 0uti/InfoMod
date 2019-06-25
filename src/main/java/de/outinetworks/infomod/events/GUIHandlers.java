package de.outinetworks.infomod.events;

import com.mojang.blaze3d.platform.GlStateManager;
import de.outinetworks.infomod.config.InfoModConfig;
import de.outinetworks.infomod.mods.AnimalInfo;
import de.outinetworks.infomod.mods.ChunkOverlay;
import de.outinetworks.infomod.mods.DurabilityViewer;
import de.outinetworks.infomod.mods.SpawnOverlay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class GUIHandlers
{


	@SubscribeEvent
	public void tickEvent(RenderWorldLastEvent event)
	{
		Entity entity = Minecraft.getInstance().getRenderViewEntity();
		if(entity != null)
		{
			GL11.glPushMatrix();
			GlStateManager.translated(-TileEntityRendererDispatcher.staticPlayerX,-TileEntityRendererDispatcher.staticPlayerY,-TileEntityRendererDispatcher.staticPlayerZ);

			// Map GL Output to Player Position
			//WCoordMatch(entity, event.getPartialTicks());

			ChunkOverlay.renderBounds(entity);
			SpawnOverlay.renderLighting(entity);
			GL11.glPopMatrix();
		}
	}
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onRenderGui(RenderGameOverlayEvent.Post event)
	{
		if(event.isCancelable() || event.getType() != ElementType.ALL)     
			return;
	
		new DurabilityViewer(Minecraft.getInstance());

		if(InfoModConfig.GENERAL.showAnimalInfo.get()) AnimalInfo.renderInfoBox();
	}
	
	private static void WCoordMatch(Entity entity, float frame)
	{



		//double X = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * frame;
		//double Y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * frame;
		//double Z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * frame;

		//GL11.glTranslated(-X, -Y - entity.getEyeHeight(), -Z);
	}
}
