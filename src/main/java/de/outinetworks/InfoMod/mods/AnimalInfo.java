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
import net.minecraft.entity.passive.EntityHorse;

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
		if (!(target instanceof EntityLiving)) return ;
		if (target instanceof EntityHorse)
		{
			EntityHorse Horse = (EntityHorse) target;
			
			GL11.glPushMatrix();
	        GlStateManager.disableDepth();
	        GL11.glScalef(1F, 1F, 1F);
	       
	        double yVelocity = Horse.getHorseJumpStrength(); //horses's jump strength attribute
	        double jumpHeight = 0;
	        while (yVelocity > 0)
	        {
		        jumpHeight += yVelocity;
		        yVelocity -= 0.08;
		        yVelocity *= 0.98;
	        }
	        
	        MC.fontRendererObj.drawString("Jump: " + String.format("%.1f", round(jumpHeight,1)) + " blocks", (width / 2) + 5, (height/2) + 5, 0xffffff);
	        MC.fontRendererObj.drawString("Health: " + String.format("%.0f", round(Horse.getMaxHealth(),0)), (width / 2) + 5, (height/2) + 15, 0xffffff);
	        MC.fontRendererObj.drawString("Speed: " + String.format("%.1f", round((float)Horse.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getBaseValue() * 43 ,1)) + " blocks/sec", (width / 2) + 5, (height/2) + 25, 0xffffff);
	        
	        GlStateManager.enableDepth();
	        GL11.glPopMatrix();
		}
		
	}
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.DOWN);
	    return bd.doubleValue();
	}
}