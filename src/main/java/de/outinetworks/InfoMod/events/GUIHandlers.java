package de.outinetworks.InfoMod.events;


import org.lwjgl.opengl.GL11;

import de.outinetworks.InfoMod.InfoMod;
import de.outinetworks.InfoMod.mods.AnimalInfo;
import de.outinetworks.InfoMod.mods.ChunkOverlay;
import de.outinetworks.InfoMod.mods.DurabilityViewer;
import de.outinetworks.InfoMod.mods.SpawnOverlay;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GUIHandlers
{

	@SubscribeEvent
    public void tickEvent(RenderWorldLastEvent event)
	{
    	Entity entity = Minecraft.getMinecraft().getRenderViewEntity();
    	
    	// Map GL Output to Player Position
        WCoordMatch(entity, event.partialTicks);
        
    	ChunkOverlay.renderBounds(entity);
    	SpawnOverlay.renderLighting(entity);
    }
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
    public void onRenderGui(RenderGameOverlayEvent.Post event)
    {
		if(event.isCancelable() || event.type != ElementType.ALL)
	    {      
	      return;
	    }
		
		new DurabilityViewer(Minecraft.getMinecraft());
		
		Entity entity = Minecraft.getMinecraft().getRenderViewEntity();
		AnimalInfo.renderInfoBox(entity);
    }
	
	@SubscribeEvent
	public void onModelBakeEvent(ModelBakeEvent event)
	{
		System.out.print("fired");
		
	}
	
	
	
	public static void WCoordMatch(Entity entity, float frame)
    {       
        double X = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * frame;
        double Y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * frame;
        double Z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * frame;
        
        GL11.glTranslated(-X, -Y, -Z);
    }
}
