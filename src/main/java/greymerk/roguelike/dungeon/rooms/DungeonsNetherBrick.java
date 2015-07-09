package greymerk.roguelike.dungeon.rooms;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.Spawner;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class DungeonsNetherBrick extends DungeonBase {
	

	public DungeonsNetherBrick() {
	}

	public boolean generate(World world, Random rand, LevelSettings settings, Cardinal[] entrances, Coord origin) {

		int x = origin.getX();
		int y = origin.getY();
		int z = origin.getZ();
		ITheme theme = settings.getTheme();
		
		int height = 3;
		int length = 2 + rand.nextInt(3);
		int width = 2 + rand.nextInt(3);
		
		IBlockFactory walls = theme.getPrimaryWall();
		WorldGenPrimitive.fillRectHollow(world, rand, x - length - 1, y - 1, z - width - 1, x + length + 1, y + height + 1, z + width + 1, walls, false, true);
		
		
		BlockWeightedRandom floor = new BlockWeightedRandom();
		floor.addBlock(walls, 1000);
		floor.addBlock(new MetaBlock(Blocks.redstone_block), 50);
		if (RogueConfig.getBoolean(RogueConfig.PRECIOUSBLOCKS)) floor.addBlock(new MetaBlock(Blocks.gold_block), 2);
		if (RogueConfig.getBoolean(RogueConfig.PRECIOUSBLOCKS)) floor.addBlock(new MetaBlock(Blocks.diamond_block), 1);
		WorldGenPrimitive.fillRectSolid(world, rand, x - length - 1, y - 1, z - width - 1, x + length + 1, y - 1, z + width + 1, floor);

		// lava crap under the floor
		BlockWeightedRandom subFloor = new BlockWeightedRandom();
		subFloor.addBlock(new MetaBlock(Blocks.lava), 8);
		subFloor.addBlock(new MetaBlock(Blocks.obsidian), 3);
		WorldGenPrimitive.fillRectSolid(world, rand, x - length, y - 5, z - width, x + length, y - 2, z + width, subFloor);
		
		BlockWeightedRandom ceiling = new BlockWeightedRandom();
		ceiling.addBlock(new MetaBlock(Blocks.nether_brick_fence), 10);
		ceiling.addBlock(new MetaBlock(Blocks.air), 5);
		WorldGenPrimitive.fillRectSolid(world, rand, x - length, y + height, z - width, x + length, y + height, z + width, ceiling);
		
		TreasureChest.createChests(world, rand, settings, 1, WorldGenPrimitive.getRectSolid(x - length, y, z - width, x + length, y, z + width));

		Spawner.generate(world, rand, settings, new Coord(x - length - 1, y + rand.nextInt(2), z - width - 1));
		Spawner.generate(world, rand, settings, new Coord(x - length - 1, y + rand.nextInt(2), z + width + 1));
		Spawner.generate(world, rand, settings, new Coord(x + length + 1, y + rand.nextInt(2), z - width - 1));
		Spawner.generate(world, rand, settings, new Coord(x + length + 1, y + rand.nextInt(2), z + width + 1));

		return true;
	}
	
	public int getSize(){
		return 6;
	}
}
