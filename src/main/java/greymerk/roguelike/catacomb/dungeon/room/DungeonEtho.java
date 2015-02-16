package greymerk.roguelike.catacomb.dungeon.room;

import greymerk.roguelike.catacomb.dungeon.DungeonBase;
import greymerk.roguelike.catacomb.settings.CatacombLevelSettings;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Log;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class DungeonEtho extends DungeonBase {

	@Override
	public boolean generate(World world, Random rand, CatacombLevelSettings settings, Cardinal[] entrances, Coord origin) {
		
		int x = origin.getX();
		int y = origin.getY();
		int z = origin.getZ();
		
		MetaBlock air = new MetaBlock(Blocks.air);
		
		MetaBlock grass = new MetaBlock(Blocks.grass);
		MetaBlock planks = new MetaBlock(Blocks.planks);
		MetaBlock log = Log.getLog(Log.OAK);
		MetaBlock water = new MetaBlock(Blocks.flowing_water);
		
		WorldGenPrimitive.fillRectSolid(world, rand, x - 5, y, z - 5, x + 5, y + 3, z + 5, air);
		
		BlockWeightedRandom leafJumble = new BlockWeightedRandom();
		leafJumble.addBlock(air, 10);
		leafJumble.addBlock(new MetaBlock(Blocks.leaves, 4), 50);
		leafJumble.addBlock(new MetaBlock(Blocks.log), 10);
		
		
		WorldGenPrimitive.fillRectSolid(world, rand, x - 4, y + 6, z - 4, x + 4, y + 6, z + 4, log);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 4, y + 4, z - 4, x + 4, y + 5, z + 4, leafJumble);
		

		// corner pools
		WorldGenPrimitive.fillRectSolid(world, rand, x - 5, y - 1, z - 5, x - 2, y - 1, z - 2, planks);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 4, y - 1, z - 4, x - 3, y - 1, z - 3, water);
		WorldGenPrimitive.setBlock(world, x - 5, y, z - 5, water);

		WorldGenPrimitive.fillRectSolid(world, rand, x + 2, y - 1, z + 2, x + 5, y - 1, z + 5, planks);
		WorldGenPrimitive.fillRectSolid(world, rand, x + 3, y - 1, z + 3, x + 4, y - 1, z + 4, water);
		WorldGenPrimitive.setBlock(world, x + 5, y, z + 5, water);
		
		WorldGenPrimitive.fillRectSolid(world, rand, x + 2, y - 1, z - 5, x + 5, y - 1, z - 2, planks);
		WorldGenPrimitive.fillRectSolid(world, rand, x + 3, y - 1, z - 4, x + 4, y - 1, z - 3, water);
		WorldGenPrimitive.setBlock(world, x + 5, y, z - 5, water);
		
		WorldGenPrimitive.fillRectSolid(world, rand, x - 5, y - 1, z + 2, x - 2, y - 1, z + 5, planks);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 4, y - 1, z + 3, x - 3, y - 1, z + 4, water);
		WorldGenPrimitive.setBlock(world, x - 5, y, z + 5, water);
		
		// alcove walls with diamonds
		BlockWeightedRandom ethoStone = new BlockWeightedRandom();
		ethoStone.addBlock(new MetaBlock(Blocks.stone), 100);
		ethoStone.addBlock(new MetaBlock(Blocks.diamond_ore), 15);
		
		WorldGenPrimitive.fillRectSolid(world, rand, x - 6, y, z - 5, x - 6, y + 4, z - 4, ethoStone);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 6, y, z + 4, x - 6, y + 4, z + 5, ethoStone);
		
		WorldGenPrimitive.fillRectSolid(world, rand, x + 6, y, z - 5, x + 6, y + 4, z - 4, ethoStone);
		WorldGenPrimitive.fillRectSolid(world, rand, x + 6, y, z + 4, x + 6, y + 4, z + 5, ethoStone);
		
		WorldGenPrimitive.fillRectSolid(world, rand, x - 5, y, z - 6, x - 4, y + 4, z - 6, ethoStone);
		WorldGenPrimitive.fillRectSolid(world, rand, x + 4, y, z - 6, x + 5, y + 4, z - 6, ethoStone);
		
		WorldGenPrimitive.fillRectSolid(world, rand, x - 5, y, z + 6, x - 4, y + 4, z + 6, ethoStone);
		WorldGenPrimitive.fillRectSolid(world, rand, x + 4, y, z + 6, x + 5, y + 4, z + 6, ethoStone);
		
		WorldGenPrimitive.fillRectSolid(world, rand, x - 1, y - 1, z - 5, x + 1, y - 1, z + 5, grass);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 5, y - 1, z - 1, x + 5, y - 1, z + 1, grass);
		
		
		MetaBlock jungleLeaf = new MetaBlock(Blocks.leaves, 3);
		
		// north

		WorldGenPrimitive.fillRectSolid(world, rand, x - 1, y, z - 6, x + 1, y + 2, z - 6, air);
		
		WorldGenPrimitive.fillRectSolid(world, rand, x - 2, y - 1, z - 6, x - 2, y + 2, z - 6, log);
		WorldGenPrimitive.fillRectSolid(world, rand, x + 2, y - 1, z - 6, x + 2, y + 2, z - 6, log);
		
		WorldGenPrimitive.fillRectSolid(world, rand, x - 3, y - 1, z - 5, x - 3, y + 2, z - 5, log);
		WorldGenPrimitive.fillRectSolid(world, rand, x + 3, y - 1, z - 5, x + 3, y + 2, z - 5, log);
		
		WorldGenPrimitive.fillRectSolid(world, rand, x - 2, y + 3, z - 6, x + 2, y + 3, z - 6, planks);
		WorldGenPrimitive.setBlock(world, x - 2, y + 2, z - 6, Blocks.planks);
		WorldGenPrimitive.setBlock(world, x - 1, y + 2, z - 6, new MetaBlock(Blocks.oak_stairs, WorldGenPrimitive.blockOrientation(Cardinal.EAST, true)));
		WorldGenPrimitive.setBlock(world, x + 1, y + 2, z - 6, new MetaBlock(Blocks.oak_stairs, WorldGenPrimitive.blockOrientation(Cardinal.WEST, true)));
		WorldGenPrimitive.setBlock(world, x + 2, y + 2, z - 6, Blocks.planks);
		
		WorldGenPrimitive.fillRectSolid(world, rand, x - 2, y + 4, z - 5, x + 2, y + 4, z - 5, planks);
		WorldGenPrimitive.setBlock(world, x - 2, y + 3, z - 5, new MetaBlock(Blocks.oak_stairs, WorldGenPrimitive.blockOrientation(Cardinal.EAST, true)));
		WorldGenPrimitive.setBlock(world, x + 2, y + 3, z - 5, new MetaBlock(Blocks.oak_stairs, WorldGenPrimitive.blockOrientation(Cardinal.WEST, true)));

		WorldGenPrimitive.fillRectSolid(world, rand, x - 2, y + 5, z - 4, x + 2, y + 5, z - 4, planks);
		WorldGenPrimitive.setBlock(world, x - 2, y + 4, z - 4, new MetaBlock(Blocks.oak_stairs, WorldGenPrimitive.blockOrientation(Cardinal.EAST, true)));
		WorldGenPrimitive.setBlock(world, x + 2, y + 4, z - 4, new MetaBlock(Blocks.oak_stairs, WorldGenPrimitive.blockOrientation(Cardinal.WEST, true)));
		
		WorldGenPrimitive.setBlock(world, x - 3, y + 3, z - 4, new MetaBlock(Blocks.oak_stairs, WorldGenPrimitive.blockOrientation(Cardinal.SOUTH, true)));
		WorldGenPrimitive.setBlock(world, x + 3, y + 3, z - 4, new MetaBlock(Blocks.oak_stairs, WorldGenPrimitive.blockOrientation(Cardinal.SOUTH, true)));
		
		WorldGenPrimitive.setBlock(world, x - 2, y - 2, z - 5, Blocks.glowstone);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 2, y - 1, z - 5, x - 2, y, z - 5, jungleLeaf, true, true);
		
		WorldGenPrimitive.setBlock(world, x + 2, y - 2, z - 5, Blocks.glowstone);
		WorldGenPrimitive.fillRectSolid(world, rand, x + 2, y - 1, z - 5, x + 2, y, z - 5, jungleLeaf, true, true);
		
		// south
		
		WorldGenPrimitive.fillRectSolid(world, rand, x - 1, y, z + 6, x + 1, y + 2, z + 6, air);
		
		WorldGenPrimitive.fillRectSolid(world, rand, x - 2, y - 1, z + 6, x - 2, y + 2, z + 6, log);
		WorldGenPrimitive.fillRectSolid(world, rand, x + 2, y - 1, z + 6, x + 2, y + 2, z + 6, log);
		
		WorldGenPrimitive.fillRectSolid(world, rand, x - 3, y - 1, z + 5, x - 3, y + 2, z + 5, log);
		WorldGenPrimitive.fillRectSolid(world, rand, x + 3, y - 1, z + 5, x + 3, y + 2, z + 5, log);

		WorldGenPrimitive.fillRectSolid(world, rand, x - 2, y + 3, z + 6, x + 2, y + 3, z + 6, planks);
		WorldGenPrimitive.setBlock(world, x - 2, y + 2, z + 6, Blocks.planks);
		WorldGenPrimitive.setBlock(world, x - 1, y + 2, z + 6, new MetaBlock(Blocks.oak_stairs, WorldGenPrimitive.blockOrientation(Cardinal.EAST, true)));
		WorldGenPrimitive.setBlock(world, x + 1, y + 2, z + 6, new MetaBlock(Blocks.oak_stairs, WorldGenPrimitive.blockOrientation(Cardinal.WEST, true)));
		WorldGenPrimitive.setBlock(world, x + 2, y + 2, z + 6, Blocks.planks);
		
		WorldGenPrimitive.fillRectSolid(world, rand, x - 2, y + 4, z + 5, x + 2, y + 4, z + 5, planks);
		WorldGenPrimitive.setBlock(world, x - 2, y + 3, z + 5, new MetaBlock(Blocks.oak_stairs, WorldGenPrimitive.blockOrientation(Cardinal.EAST, true)));
		WorldGenPrimitive.setBlock(world, x + 2, y + 3, z + 5, new MetaBlock(Blocks.oak_stairs, WorldGenPrimitive.blockOrientation(Cardinal.WEST, true)));

		WorldGenPrimitive.fillRectSolid(world, rand, x - 2, y + 5, z + 4, x + 2, y + 5, z + 4, planks);
		WorldGenPrimitive.setBlock(world, x - 2, y + 4, z + 4, new MetaBlock(Blocks.oak_stairs, WorldGenPrimitive.blockOrientation(Cardinal.EAST, true)));
		WorldGenPrimitive.setBlock(world, x + 2, y + 4, z + 4, new MetaBlock(Blocks.oak_stairs, WorldGenPrimitive.blockOrientation(Cardinal.WEST, true)));

		WorldGenPrimitive.setBlock(world, x - 3, y + 3, z + 4, new MetaBlock(Blocks.oak_stairs, WorldGenPrimitive.blockOrientation(Cardinal.NORTH, true)));
		WorldGenPrimitive.setBlock(world, x + 3, y + 3, z + 4, new MetaBlock(Blocks.oak_stairs, WorldGenPrimitive.blockOrientation(Cardinal.NORTH, true)));
		
		WorldGenPrimitive.setBlock(world, x - 2, y - 2, z + 5, Blocks.glowstone);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 2, y - 1, z + 5, x - 2, y, z + 5, jungleLeaf, true, true);
		
		WorldGenPrimitive.setBlock(world, x + 2, y - 2, z + 5, Blocks.glowstone);
		WorldGenPrimitive.fillRectSolid(world, rand, x + 2, y - 1, z + 5, x + 2, y, z + 5, jungleLeaf, true, true);
		
		
		// west
		
		WorldGenPrimitive.fillRectSolid(world, rand, x - 6, y, z - 1, x - 6, y + 2, z + 1, air);
		
		WorldGenPrimitive.fillRectSolid(world, rand, x - 6, y - 1, z - 2, x - 6, y + 2, z - 2, log);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 6, y - 1, z + 2, x - 6, y + 2, z + 2, log);
		
		WorldGenPrimitive.fillRectSolid(world, rand, x - 5, y - 1, z - 3, x - 5, y + 3, z - 3, log);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 5, y - 1, z + 3, x - 5, y + 3, z + 3, log);
		
		WorldGenPrimitive.fillRectSolid(world, rand, x - 6, y + 3, z - 2, x - 6, y + 3, z + 2, planks);
		WorldGenPrimitive.setBlock(world, x - 6, y + 2, z - 2, Blocks.planks);
		WorldGenPrimitive.setBlock(world, x - 6, y + 2, z - 1, new MetaBlock(Blocks.oak_stairs, WorldGenPrimitive.blockOrientation(Cardinal.SOUTH, true)));
		WorldGenPrimitive.setBlock(world, x - 6, y + 2, z + 1, new MetaBlock(Blocks.oak_stairs, WorldGenPrimitive.blockOrientation(Cardinal.NORTH, true)));
		WorldGenPrimitive.setBlock(world, x - 6, y + 2, z + 2, Blocks.planks);
		
		WorldGenPrimitive.fillRectSolid(world, rand, x - 5, y + 4, z - 2, x - 5, y + 4, z + 2, planks);
		WorldGenPrimitive.setBlock(world, x - 5, y + 3, z - 2, new MetaBlock(Blocks.oak_stairs, WorldGenPrimitive.blockOrientation(Cardinal.SOUTH, true)));
		WorldGenPrimitive.setBlock(world, x - 5, y + 3, z + 2, new MetaBlock(Blocks.oak_stairs, WorldGenPrimitive.blockOrientation(Cardinal.NORTH, true)));

		WorldGenPrimitive.fillRectSolid(world, rand, x - 4, y + 5, z - 2, x - 4, y + 5, z + 2, planks);
		WorldGenPrimitive.setBlock(world, x - 4, y + 4, z - 2, new MetaBlock(Blocks.oak_stairs, WorldGenPrimitive.blockOrientation(Cardinal.SOUTH, true)));
		WorldGenPrimitive.setBlock(world, x - 4, y + 4, z + 2, new MetaBlock(Blocks.oak_stairs, WorldGenPrimitive.blockOrientation(Cardinal.NORTH, true)));

		WorldGenPrimitive.setBlock(world, x - 4, y + 3, z - 3, new MetaBlock(Blocks.oak_stairs, WorldGenPrimitive.blockOrientation(Cardinal.EAST, true)));
		WorldGenPrimitive.setBlock(world, x - 4, y + 3, z + 3, new MetaBlock(Blocks.oak_stairs, WorldGenPrimitive.blockOrientation(Cardinal.EAST, true)));

		WorldGenPrimitive.setBlock(world, x - 5, y - 2, z - 2, Blocks.glowstone);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 5, y - 1, z - 2, x - 5, y, z - 2, jungleLeaf, true, true);
		
		WorldGenPrimitive.setBlock(world, x - 6, y - 2, z + 2, Blocks.glowstone);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 5, y - 1, z + 2, x - 5, y, z + 2, jungleLeaf, true, true);
		
		
		// east
		
		WorldGenPrimitive.fillRectSolid(world, rand, x + 6, y, z - 1, x + 6, y + 2, z + 1, air);
		
		WorldGenPrimitive.fillRectSolid(world, rand, x + 6, y - 1, z - 2, x + 6, y + 2, z - 2, log);
		WorldGenPrimitive.fillRectSolid(world, rand, x + 6, y - 1, z + 2, x + 6, y + 2, z + 2, log);
		
		WorldGenPrimitive.fillRectSolid(world, rand, x + 5, y - 1, z - 3, x + 5, y + 3, z - 3, log);
		WorldGenPrimitive.fillRectSolid(world, rand, x + 5, y - 1, z + 3, x + 5, y + 3, z + 3, log);
		
		WorldGenPrimitive.fillRectSolid(world, rand, x + 6, y + 3, z - 2, x + 6, y + 3, z + 2, planks);
		WorldGenPrimitive.setBlock(world, x + 6, y + 2, z - 2, Blocks.planks);
		WorldGenPrimitive.setBlock(world, x + 6, y + 2, z - 1, new MetaBlock(Blocks.oak_stairs, WorldGenPrimitive.blockOrientation(Cardinal.SOUTH, true)));
		WorldGenPrimitive.setBlock(world, x + 6, y + 2, z + 1, new MetaBlock(Blocks.oak_stairs, WorldGenPrimitive.blockOrientation(Cardinal.NORTH, true)));
		WorldGenPrimitive.setBlock(world, x + 6, y + 2, z + 2, Blocks.planks);
		
		WorldGenPrimitive.fillRectSolid(world, rand, x + 5, y + 4, z - 2, x + 5, y + 4, z + 2, planks);
		WorldGenPrimitive.setBlock(world, x + 5, y + 3, z - 2, new MetaBlock(Blocks.oak_stairs, WorldGenPrimitive.blockOrientation(Cardinal.SOUTH, true)));
		WorldGenPrimitive.setBlock(world, x + 5, y + 3, z + 2, new MetaBlock(Blocks.oak_stairs, WorldGenPrimitive.blockOrientation(Cardinal.NORTH, true)));

		WorldGenPrimitive.fillRectSolid(world, rand, x + 4, y + 5, z - 2, x + 4, y + 5, z + 2, planks);
		WorldGenPrimitive.setBlock(world, x + 4, y + 4, z - 2, new MetaBlock(Blocks.oak_stairs, WorldGenPrimitive.blockOrientation(Cardinal.SOUTH, true)));
		WorldGenPrimitive.setBlock(world, x + 4, y + 4, z + 2, new MetaBlock(Blocks.oak_stairs, WorldGenPrimitive.blockOrientation(Cardinal.NORTH, true)));

		WorldGenPrimitive.setBlock(world, x + 4, y + 3, z - 3, new MetaBlock(Blocks.oak_stairs, WorldGenPrimitive.blockOrientation(Cardinal.WEST, true)));
		WorldGenPrimitive.setBlock(world, x + 4, y + 3, z + 3, new MetaBlock(Blocks.oak_stairs, WorldGenPrimitive.blockOrientation(Cardinal.WEST, true)));

		WorldGenPrimitive.fillRectSolid(world, rand, x - 3, y + 4, z - 3, x - 3, y + 5, z - 3, planks);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 3, y + 4, z + 3, x - 3, y + 5, z + 3, planks);
		WorldGenPrimitive.fillRectSolid(world, rand, x + 3, y + 4, z - 3, x + 3, y + 5, z - 3, planks);
		WorldGenPrimitive.fillRectSolid(world, rand, x + 3, y + 4, z + 3, x + 3, y + 5, z + 3, planks);
		
		WorldGenPrimitive.setBlock(world, x + 5, y - 2, z - 2, Blocks.glowstone);
		WorldGenPrimitive.fillRectSolid(world, rand, x + 5, y - 1, z - 2, x + 5, y, z - 2, jungleLeaf, true, true);
		
		WorldGenPrimitive.setBlock(world, x + 5, y - 2, z + 2, Blocks.glowstone);
		WorldGenPrimitive.fillRectSolid(world, rand, x + 5, y - 1, z + 2, x + 5, y, z + 2, jungleLeaf, true, true);
		
		/*
		ITreasureChest chest = new TreasureChestEmpty();
		chest.generate(world, rand, settings.getLoot(), new Coord(x - 4, y - 2, z - 4), 0, false);
		
		ItemStack yourMum = new ItemStack(Items.wooden_hoe);
		Loot.setItemName(yourMum, "Your Mum", null);
		int middle = chest.getInventorySize() / 2;
		int mumSlot = rand.nextInt(chest.getInventorySize());
		if (mumSlot == middle){
			mumSlot += rand.nextInt(5);
		}
		chest.setInventorySlot(yourMum, mumSlot);
		
		chest.setInventorySlot(ItemNovelty.getItem(ItemNovelty.ETHO), middle);
		*/
		
		return true;
	}
	
	public int getSize(){
		return 8;
	}

}
