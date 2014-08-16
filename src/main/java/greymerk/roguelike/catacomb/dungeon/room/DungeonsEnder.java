package greymerk.roguelike.catacomb.dungeon.room;

import greymerk.roguelike.catacomb.Catacomb;
import greymerk.roguelike.catacomb.dungeon.DungeonBase;
import greymerk.roguelike.catacomb.settings.CatacombLevelSettings;
import greymerk.roguelike.worldgen.BlockFactoryCheckers;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.Spawner;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class DungeonsEnder extends DungeonBase {
	World world;
	Random rand;

	byte dungeonHeight;
	int dungeonLength;
	int dungeonWidth;
	
	public DungeonsEnder() {
		dungeonHeight = 10;
		dungeonLength = 4;
		dungeonWidth = 4;
	}

	public boolean generate(World inWorld, Random inRandom, CatacombLevelSettings settings, Cardinal[] entrances, int x, int y, int z) {
		world = inWorld;
		rand = inRandom;

		MetaBlock black = new MetaBlock(Blocks.obsidian);
		MetaBlock white = new MetaBlock(Blocks.quartz_block);
		MetaBlock air = new MetaBlock(Blocks.air);

		for (int blockX = x - dungeonLength - 1; blockX <= x + dungeonLength + 1; blockX++) {
			for (int blockZ = z - dungeonWidth - 1; blockZ <= z + dungeonWidth + 1; blockZ++){
				for (int blockY = y - 1; blockY <= y + dungeonHeight; blockY++) {

					int height = y + rand.nextInt(8) + 2;

					if(blockY > height){
						continue;
					}
					
					if (blockX == x - dungeonLength - 1 || blockZ == z - dungeonWidth - 1 || blockX == x + dungeonLength + 1 || blockZ == z + dungeonWidth + 1) {

						if (blockY >= 0 && !world.getBlock(blockX, blockY - 1, blockZ).getMaterial().isSolid()) {
							WorldGenPrimitive.setBlock(world, blockX, blockY, blockZ, air);
							continue;
						}
						if (!world.getBlock(blockX, blockY, blockZ).getMaterial().isSolid()) {
							continue;
						}

						WorldGenPrimitive.setBlock(world, blockX, blockY, blockZ, black);
					} else {
						WorldGenPrimitive.setBlock(world, blockX, blockY, blockZ, air);
					}
				}
			}
		}
		
		BlockFactoryCheckers checkers = new BlockFactoryCheckers(black, white);
		WorldGenPrimitive.fillRectSolid(inWorld, inRandom, x - 4, y - 1, z - 4, x + 4, y - 1, z + 4, checkers, true, true);
		// TODO: add ender chest
		Spawner.generate(world, rand, settings.getSpawners(), x, y, z, Catacomb.getLevel(y), Spawner.ENDERMAN);

		return true;
	}
	
	
	public int getSize(){
		return 7;
	}
}
