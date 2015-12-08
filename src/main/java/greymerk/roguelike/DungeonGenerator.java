package greymerk.roguelike;

import java.util.Random;

import cpw.mods.fml.common.IWorldGenerator;
import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.IDungeon;
import greymerk.roguelike.worldgen.WorldEditor;

public class DungeonGenerator implements IWorldGenerator { 

	@Override
	public void generate(Random random, int chunkX, int chunkZ, net.minecraft.world.World world, net.minecraft.world.chunk.IChunkProvider chunkGenerator, net.minecraft.world.chunk.IChunkProvider chunkProvider) {
		WorldEditor editor = new WorldEditor(world);
		IDungeon dungeon = new Dungeon(editor);
		dungeon.spawnInChunk(random, chunkX, chunkZ);
	}

}