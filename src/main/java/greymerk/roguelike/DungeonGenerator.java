package greymerk.roguelike;

import greymerk.roguelike.dungeon.Catacomb;

import java.util.Random;

import net.minecraftforge.fml.common.IWorldGenerator;

public class DungeonGenerator implements IWorldGenerator { 

	@Override
	public void generate(Random random, int chunkX, int chunkZ, net.minecraft.world.World world, net.minecraft.world.chunk.IChunkProvider chunkGenerator, net.minecraft.world.chunk.IChunkProvider chunkProvider) {
		Catacomb.spawnInChunk(world, random, chunkX, chunkZ);
	}

}