package de.outinetworks.infomod.mods;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.passive.horse.*;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.passive.horse.SkeletonHorseEntity;
import net.minecraft.entity.passive.horse.ZombieHorseEntity;

public class AnimalInfo {
	private static int width;
	private static int height;
	private static Minecraft MC;
	
	public static void renderInfoBox()
	{
		MC = Minecraft.getInstance();
		width = MC.mainWindow.getScaledWidth();
		height = MC.mainWindow.getScaledHeight();
		Entity target;

		try {
			if (Minecraft.getInstance().objectMouseOver == null || Minecraft.getInstance().objectMouseOver.getType() != RayTraceResult.Type.ENTITY) return;
			target = ((EntityRayTraceResult) Minecraft.getInstance().objectMouseOver).getEntity();
		}
		catch (Exception ignored)
		{
			return;
		}


		if (!(target instanceof MobEntity)) return;
		
		// is Horse
		if (target instanceof HorseEntity)  drawInfo((HorseEntity) target);
		
		// is Zombie Horse
		if (target instanceof ZombieHorseEntity)  drawInfo((ZombieHorseEntity) target);
		
		// is Skeleton Horse
		if (target instanceof SkeletonHorseEntity)  drawInfo((SkeletonHorseEntity) target);
		
		// is Donkey
		if (target instanceof DonkeyEntity)  drawInfo((DonkeyEntity) target);
		
		// is Mule
		if (target instanceof MuleEntity)  drawInfo((MuleEntity) target);
		
		// is Llama
		if (target instanceof LlamaEntity)  drawInfo((LlamaEntity) target);
	}
	
	private static void drawInfo(AbstractHorseEntity animal)
	{
		double jumpHeight = calcJumpHeight(animal.getHorseJumpStrength());
		
		GL11.glPushMatrix();
        GlStateManager.disableDepthTest();
        GL11.glScalef(1F, 1F, 1F);
      
        MC.fontRenderer.drawString("Jump: " + String.format("%.1f", round(jumpHeight,1)) + " blocks", (width / 2) + 5, (height/2) + 5, 0xffffff);
        MC.fontRenderer.drawString("Health: " + String.format("%.0f", round(animal.getMaxHealth(),0)), (width / 2) + 5, (height/2) + 15, 0xffffff);
        MC.fontRenderer.drawString("Speed: " + String.format("%.1f", round((float)animal.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getBaseValue() * 43 ,1)) + " blocks/sec", (width / 2) + 5, (height/2) + 25, 0xffffff);
        
        GlStateManager.enableDepthTest();
        GL11.glPopMatrix();
	}
	
	
	private static double calcJumpHeight(double jumpStrength)
	{
		// JumpHeight = -0.1817584952 * x^3 + 3.689713992 * x^2 + 2.128599134 * x - 0.343930367
		return (-0.1817584952 * Math.pow(jumpStrength, 3)) + (3.689713992 * Math.pow(jumpStrength, 2)) + (2.128599134 * jumpStrength) - 0.343930367;
	}
	
	private static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.DOWN);
	    return bd.doubleValue();
	}
}