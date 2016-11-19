package de.outinetworks.InfoMod.mods;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class ChunkOverlay
{

	private static int OverlayMode;
	
	public static void ToggleMode()
    {
		OverlayMode = (OverlayMode + 1)%3;
    }

	
	public static void renderBounds(Entity entity)
	{
        if (OverlayMode == 0)	return;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.disableLighting();
        GL11.glLineWidth(3F);
        GL11.glBegin(GL11.GL_LINES);

        for (int ChunkX = -4; ChunkX <= 4; ChunkX++)
        {
            for (int ChunkZ = -4; ChunkZ <= 4; ChunkZ++)
            {
                double x1 = (entity.chunkCoordX + ChunkX) << 4;
                double z1 = (entity.chunkCoordZ + ChunkZ) << 4;
                double x2 = x1 + 16;
                double z2 = z1 + 16;

                double dy = 128;
                double y1 = Math.floor(entity.posY - dy / 2);
                double y2 = y1 + dy;
                if (y1 < 0)
                {
                    y1 = 0;
                    y2 = dy;
                }

                if (y1 > entity.world.getHeight())
                {
                    y2 = entity.world.getHeight();
                    y1 = y2 - dy;
                }

                double dist = Math.pow(1.5, -(ChunkX * ChunkX + ChunkZ * ChunkZ));

                GlStateManager.color(0.9F, 0, 0, (float) dist);
                if (ChunkX >= 0 && ChunkZ >= 0)
                {
                	GL11.glVertex3d(x2, y1, z2);
                	GL11.glVertex3d(x2, y2, z2);
                }
                if (ChunkX >= 0 && ChunkZ <= 0)
                {
                	GL11.glVertex3d(x2, y1, z1);
                	GL11.glVertex3d(x2, y2, z1);
                }
                if (ChunkX <= 0 && ChunkZ >= 0)
                {
                	GL11.glVertex3d(x1, y1, z2);
                	GL11.glVertex3d(x1, y2, z2);
                }
                if (ChunkX <= 0 && ChunkZ <= 0)
                {
                	GL11.glVertex3d(x1, y1, z1);
                	GL11.glVertex3d(x1, y2, z1);
                }

                if (OverlayMode == 2 && ChunkX == 0 && ChunkZ == 0)
                {
                    dy = 32;
                    y1 = Math.floor(entity.posY - dy / 2);
                    y2 = y1 + dy;
                    if (y1 < 0)
                    {
                        y1 = 0;
                        y2 = dy;
                    }

                    if (y1 > entity.world.getHeight())
                    {
                        y2 = entity.world.getHeight();
                        y1 = y2 - dy;
                    }
                    
                    GlStateManager.color(0, 0.9F, 0, 0.4F);
                    for (double y = (int) y1; y <= y2; y++)
                    {
                    	// z1 -> z2 (x1 side)
                    	GL11.glVertex3d(x1 + 0.004, y, z1);
                    	GL11.glVertex3d(x1 + 0.004, y, z2);
                    	// z1 -> z2 (x2 side)
                    	GL11.glVertex3d(x2 - 0.004, y, z1);
                    	GL11.glVertex3d(x2 - 0.004, y, z2);
                    	// x1 -> x2 (z1 side)
                    	GL11.glVertex3d(x1, y, z1 + 0.004);
                    	GL11.glVertex3d(x2, y, z1 + 0.004);
                    	// x1 -> x2 (z2 side)
                    	GL11.glVertex3d(x1, y, z2 - 0.004);
                    	GL11.glVertex3d(x2, y, z2 - 0.004);
                    }
                    for (double h = 1; h <= 15; h++)
                    {
                    	// Upper x1 side
                    	GL11.glVertex3d(x1 + h, y1, z1 + 0.004);
                    	GL11.glVertex3d(x1 + h, y2, z1 + 0.004);
                    	// upper x2 side
                    	GL11.glVertex3d(x1 + h, y1, z2 - 0.004);
                    	GL11.glVertex3d(x1 + h, y2, z2 - 0.004);
                    	// upper z1 side
                    	GL11.glVertex3d(x1 + 0.004, y1, z1 + h);
                    	GL11.glVertex3d(x1 + 0.004, y2, z1 + h);
                    	// upper z2 side
                    	GL11.glVertex3d(x2 - 0.004, y1, z1 + h);
                    	GL11.glVertex3d(x2 - 0.004, y2, z1 + h);
                    }
                }
            }
        }
        GL11.glEnd();
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.enableTexture2D();
    }
}
