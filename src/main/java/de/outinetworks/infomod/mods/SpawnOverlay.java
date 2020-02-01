package de.outinetworks.infomod.mods;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.spawner.WorldEntitySpawner;
import org.lwjgl.opengl.GL11;


public class SpawnOverlay
{
	private static boolean Enabled = false;
    private static Entity dummyEntity;
	public static void ToggleEnabled()
    {
    	Enabled = !Enabled;	
    }

    /**
     * @param entity DummyEntity
     */
	public static void renderLighting(Entity entity)
	{
		if (!Enabled) return;

        RenderSystem.lineWidth(5F);
        GL11.glBegin(GL11.GL_LINES);

        RenderSystem.color3f(1, 0, 0);

        World world = entity.getEntityWorld();
        int x1 = (int) entity.getPosX();
        int z1 = (int) entity.getPosZ();
        int y1 = (int) normalize(entity.getPosY(), 16, world.getHeight() - 16);

        dummyEntity = new PigEntity(EntityType.PIG,world);

        // 16 blocks in each direction
        // make it configable ?
        for (int x = x1 - 16; x <= x1 + 16; x++)
        {
            for (int z = z1 - 16; z <= z1 + 16; z++)
            {
                BlockPos pos = new BlockPos(x, y1, z);
                Chunk chunk = world.getChunkAt(pos);
                Biome biome = world.getBiome(pos);
                if (biome.getSpawns(EntityClassification.MONSTER).isEmpty() || biome.getSpawningChance() <= 0) continue;

                for (int y = y1 - 16; y < y1 + 16; y++)
                {
                    int spawnMode = getSpawnMode(chunk, x, y, z);
                    if (spawnMode == 0) continue;
                    if (spawnMode == 1) RenderSystem.color3f(1, 1, 0);
                    else RenderSystem.color3f(1, 0, 0);

                    double dMarkerOffset = 0.02;
                    GL11.glVertex3d(x, y + dMarkerOffset, z);
                    GL11.glVertex3d(x + 1, y + dMarkerOffset, z + 1);
                    GL11.glVertex3d(x + 1, y + dMarkerOffset, z);
                    GL11.glVertex3d(x, y + dMarkerOffset, z + 1);
                }
            }
        }
        GL11.glEnd();
	}

    /**
     * @param chunk Chunk
     * @param x X
     * @param y Y
     * @param z Z
     * @return SpawnMode
     */
	private static int getSpawnMode(Chunk chunk, int x, int y, int z) {
        World world = chunk.getWorld();
        BlockPos pos = new BlockPos(x, y, z);
        
        // can Spawn something on Block / World ?
        if (!WorldEntitySpawner.canCreatureTypeSpawnAtLocation(EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, world, pos, EntityType.PIG) || chunk.getWorldLightManager().getLightEngine(LightType.BLOCK).getLightFor(pos) >= 8) return 0;

        AxisAlignedBB aabb = new AxisAlignedBB(x+0.2, y+0.01, z+0.2, x+0.8, y+1.8, z+0.8);

        // no spawn in liquids
        if (world.containsAnyLiquid(aabb)) return 0;

        // entity collides with no block
        if (!world.checkNoEntityCollision(dummyEntity, world.getBlockState(pos).getCollisionShape(world,pos))) return 0;

        // Entity collides with no other Entity
        //ToDo: clarify that null ;) should be Set<Entity>
        //if(world.getCollisionBoxes(dummyEntity,world.getBlockState(pos).getCollisionShape(world,pos),null).count() != 0) return 0;


        // sky visible ?
        if (chunk.getWorldLightManager().getLightEngine(LightType.SKY).getLightFor(pos) >= 8) return 1;
        
        // no light from sun.
        return 2;
    }
	
	private static double normalize(double v, double n, double x)
    {
        return v>x ? x : (v<n ? n : v);
    }
}
