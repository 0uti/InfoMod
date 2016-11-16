package de.outinetworks.InfoMod.mods;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.passive.EntityDonkey;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.entity.passive.EntityMule;
import net.minecraft.entity.passive.EntitySkeletonHorse;
import net.minecraft.entity.passive.EntityZombieHorse;

public class AnimalInfo {

	static int width;
	static int height;
	static Minecraft MC;
	
	public static void renderInfoBox(Entity entity)
	{
		MC = Minecraft.getMinecraft();
		ScaledResolution scaled = new ScaledResolution(MC);
		width = scaled.getScaledWidth();
		height = scaled.getScaledHeight();
		Entity target = null;
		
		try {
			target = Minecraft.getMinecraft().objectMouseOver.entityHit;
		}
		catch(Exception e) {
			
		}
		if (target == null) return;
		// no players
		if (!(target instanceof EntityLiving)) return ;
		
		// is Horse
		if (target instanceof EntityHorse)  drawInfo((EntityHorse) target);
		
		// is Zombie Horse
		if (target instanceof EntityZombieHorse)  drawInfo((EntityZombieHorse) target);
		
		// is Skeleton Horse
		if (target instanceof EntitySkeletonHorse)  drawInfo((EntitySkeletonHorse) target);
		
		// is Donkey
		if (target instanceof EntityDonkey)  drawInfo((EntityDonkey) target);
		
		// is Mule
		if (target instanceof EntityMule)  drawInfo((EntityMule) target);
		
		// is Llama
		if (target instanceof EntityLlama)  drawInfo((EntityLlama) target);
	}
	
	private static void drawInfo(AbstractHorse animal)
	{
		double jumpHeight = calcJumpHeight(animal.getHorseJumpStrength());
		
		GL11.glPushMatrix();
        GlStateManager.disableDepth();
        GL11.glScalef(1F, 1F, 1F);
      
        MC.fontRendererObj.drawString("Jump: " + String.format("%.1f", round(jumpHeight,1)) + " blocks", (width / 2) + 5, (height/2) + 5, 0xffffff);
        MC.fontRendererObj.drawString("Health: " + String.format("%.0f", round(animal.getMaxHealth(),0)), (width / 2) + 5, (height/2) + 15, 0xffffff);
        MC.fontRendererObj.drawString("Speed: " + String.format("%.1f", round((float)animal.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getBaseValue() * 43 ,1)) + " blocks/sec", (width / 2) + 5, (height/2) + 25, 0xffffff);
        
        GlStateManager.enableDepth();
        GL11.glPopMatrix();
	}
	
	
	private static double calcJumpHeight(double jumpStrength)
	{
		// JumpHeight = -0.1817584952 * x^3 + 3.689713992 * x^2 + 2.128599134 * x - 0.343930367
		return (-0.1817584952 * Math.pow(jumpStrength, 3)) + (3.689713992 * Math.pow(jumpStrength, 2)) + (2.128599134 * jumpStrength) - 0.343930367;
	}
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.DOWN);
	    return bd.doubleValue();
	}
}