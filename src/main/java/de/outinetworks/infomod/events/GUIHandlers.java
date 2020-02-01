package de.outinetworks.infomod.events;

import com.mojang.blaze3d.systems.RenderSystem;
import de.outinetworks.infomod.config.InfoModConfig;
import de.outinetworks.infomod.mods.AnimalInfo;
import de.outinetworks.infomod.mods.ChunkOverlay;
import de.outinetworks.infomod.mods.DurabilityViewer;
import de.outinetworks.infomod.mods.SpawnOverlay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class GUIHandlers
{
    @SubscribeEvent
    public void tickEvent(RenderWorldLastEvent event)
    {
        RenderSystem.pushMatrix();
        RenderSystem.loadIdentity();
        RenderSystem.multMatrix(event.getMatrixStack().getLast().getPositionMatrix());
        RenderSystem.disableTexture();
        RenderSystem.disableBlend();

        Minecraft client = Minecraft.getInstance();
        ClientPlayerEntity playerEntity = client.player;

        Vec3d renderView = TileEntityRendererDispatcher.instance.renderInfo.getProjectedView();
        RenderSystem.translated(-renderView.getX(), -renderView.getY(), -renderView.getZ());

        ChunkOverlay.renderBounds(playerEntity);
        SpawnOverlay.renderLighting(playerEntity);
        RenderSystem.popMatrix();
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onRenderGui(RenderGameOverlayEvent.Post event)
    {
		if (event.isCancelable() || event.getType() != ElementType.ALL) return;
        new DurabilityViewer(Minecraft.getInstance());
        if (InfoModConfig.GENERAL.showAnimalInfo.get()) AnimalInfo.renderInfoBox();
    }
}
