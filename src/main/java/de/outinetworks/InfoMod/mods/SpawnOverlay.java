package de.outinetworks.InfoMod.mods;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving.SpawnPlacementType;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.WorldEntitySpawner;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;

public class SpawnOverlay
{
	
	public static boolean Enabled = false;
	private static double dMarkerOffset = 0.02;
	private static Entity dummyEntity = new EntityPig(null);
	
	public static void ToggleEnabled()
    {
    	Enabled = !Enabled;	
    }
	
	public static void renderLighting(Entity entity)
	{
		if (Enabled == false) return;
		
		GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();
        GL11.glLineWidth(5F);
        GL11.glBegin(GL11.GL_LINES);

        GlStateManager.color(1, 0, 0);

        World world = entity.worldObj;
        int x1 = (int) entity.posX;
        int z1 = (int) entity.posZ;
        int y1 = (int) normalize(entity.posY, 16, world.getHeight() - 16);

        // 16 blocks in each direction
        // make it configable ?
        
        for (int x = x1 - 16; x <= x1 + 16; x++)
        {
            for (int z = z1 - 16; z <= z1 + 16; z++)
            {
                BlockPos pos = new BlockPos(x, y1, z);
                Chunk chunk = world.getChunkFromBlockCoords(pos);
                Biome biome = world.getBiomeGenForCoords(pos);
                if (biome.getSpawnableList(EnumCreatureType.MONSTER).isEmpty() || biome.getSpawningChance() <= 0) continue;

                for (int y = y1 - 16; y < y1 + 16; y++)
                {
                    int spawnMode = getSpawnMode(chunk, x, y, z);
                    if (spawnMode == 0) continue;
                    if (spawnMode == 1) GlStateManager.color(1, 1, 0);
                    else GlStateManager.color(1, 0, 0);

                    GL11.glVertex3d(x, y + dMarkerOffset, z);
                    GL11.glVertex3d(x + 1, y + dMarkerOffset, z + 1);
                    GL11.glVertex3d(x + 1, y + dMarkerOffset, z);
                    GL11.glVertex3d(x, y + dMarkerOffset, z + 1);
                }
            }
        }
        GL11.glEnd();
        GlStateManager.enableLighting();
        GlStateManager.enableTexture2D();
	}
	
	private static int getSpawnMode(Chunk chunk, int x, int y, int z) {
        World world = chunk.getWorld();
        BlockPos pos = new BlockPos(x, y, z);
        
        // can Spawn something on Block / World ?
        if (!WorldEntitySpawner.canCreatureTypeSpawnAtLocation(SpawnPlacementType.ON_GROUND, world, pos) || chunk.getLightFor(EnumSkyBlock.BLOCK, pos) >= 8) return 0;

        AxisAlignedBB aabb = new AxisAlignedBB(x+0.2, y+0.01, z+0.2, x+0.8, y+1.8, z+0.8);
        
        // enough space for Spawn ? is liquid ?
        if (!world.checkNoEntityCollision(aabb) ||
                !world.getCollisionBoxes(dummyEntity, aabb).isEmpty() ||
                world.containsAnyLiquid(aabb))
            return 0;
        
        // sky visible ?
        if (chunk.getLightFor(EnumSkyBlock.SKY, pos) >= 8) return 1;
        
        // no light from sun.
        return 2;
    }
	
	private static double normalize(double v, double n, double x)
    {
        return v>x ? x : (v<n ? n : v);
    }
	
}
