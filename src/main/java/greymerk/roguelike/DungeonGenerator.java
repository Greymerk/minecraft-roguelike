package greymerk.roguelike;

import java.util.Random;

import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.IDungeon;
import greymerk.roguelike.worldgen.WorldEditor;
import net.minecraftforge.fml.common.IWorldGenerator;

public class DungeonGenerator implements IWorldGenerator { 

	@Override
	public void generate(Random random, int chunkX, int chunkZ, net.minecraft.world.World world, net.minecraft.world.chunk.IChunkProvider chunkGenerator, net.minecraft.world.chunk.IChunkProvider chunkProvider) {
		IDungeon dungeon = new Dungeon();
		WorldEditor editor = new WorldEditor(world);
		dungeon.spawnInChunk(editor, random, chunkX, chunkZ);
	}

}