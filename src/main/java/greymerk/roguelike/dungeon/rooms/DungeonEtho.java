package greymerk.roguelike.dungeon.rooms;

import java.util.Random;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.Leaves;
import greymerk.roguelike.worldgen.blocks.Log;
import greymerk.roguelike.worldgen.blocks.Stair;
import greymerk.roguelike.worldgen.blocks.StairType;
import greymerk.roguelike.worldgen.blocks.Wood;
import greymerk.roguelike.worldgen.blocks.WoodBlock;

public class DungeonEtho extends DungeonBase {

	@Override
	public boolean generate(WorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances, Coord origin) {
		
		int x = origin.getX();
		int y = origin.getY();
		int z = origin.getZ();
		
		MetaBlock air = BlockType.get(BlockType.AIR);
		
		MetaBlock grass = BlockType.get(BlockType.GRASS);
		MetaBlock planks = Wood.get(Wood.OAK, WoodBlock.PLANK);
		MetaBlock log = Wood.get(Wood.OAK, WoodBlock.LOG);
		MetaBlock water = BlockType.get(BlockType.WATER_FLOWING);
		MetaBlock glowstone = BlockType.get(BlockType.GLOWSTONE);
		
		
		editor.fillRectSolid(rand, x - 5, y, z - 5, x + 5, y + 3, z + 5, air);
		
		BlockWeightedRandom leafJumble = new BlockWeightedRandom();
		leafJumble.addBlock(air, 10);
		leafJumble.addBlock(Leaves.get(Leaves.OAK, false), 50);
		leafJumble.addBlock(Log.getLog(Wood.OAK), 10);
		
		
		editor.fillRectSolid(rand, x - 4, y + 6, z - 4, x + 4, y + 6, z + 4, log);
		editor.fillRectSolid(rand, x - 4, y + 4, z - 4, x + 4, y + 5, z + 4, leafJumble);
		

		// corner pools
		editor.fillRectSolid(rand, x - 5, y - 1, z - 5, x - 2, y - 1, z - 2, planks);
		editor.fillRectSolid(rand, x - 4, y - 1, z - 4, x - 3, y - 1, z - 3, water);
		editor.setBlock(x - 5, y, z - 5, water);

		editor.fillRectSolid(rand, x + 2, y - 1, z + 2, x + 5, y - 1, z + 5, planks);
		editor.fillRectSolid(rand, x + 3, y - 1, z + 3, x + 4, y - 1, z + 4, water);
		editor.setBlock(x + 5, y, z + 5, water);
		
		editor.fillRectSolid(rand, x + 2, y - 1, z - 5, x + 5, y - 1, z - 2, planks);
		editor.fillRectSolid(rand, x + 3, y - 1, z - 4, x + 4, y - 1, z - 3, water);
		editor.setBlock(x + 5, y, z - 5, water);
		
		editor.fillRectSolid(rand, x - 5, y - 1, z + 2, x - 2, y - 1, z + 5, planks);
		editor.fillRectSolid(rand, x - 4, y - 1, z + 3, x - 3, y - 1, z + 4, water);
		editor.setBlock(x - 5, y, z + 5, water);
		
		// alcove walls with diamonds
		BlockWeightedRandom ethoStone = new BlockWeightedRandom();
		ethoStone.addBlock(BlockType.get(BlockType.STONE_SMOOTH), 100);
		ethoStone.addBlock(BlockType.get(BlockType.ORE_DIAMOND), 15);
		
		editor.fillRectSolid(rand, x - 6, y, z - 5, x - 6, y + 4, z - 4, ethoStone);
		editor.fillRectSolid(rand, x - 6, y, z + 4, x - 6, y + 4, z + 5, ethoStone);
		
		editor.fillRectSolid(rand, x + 6, y, z - 5, x + 6, y + 4, z - 4, ethoStone);
		editor.fillRectSolid(rand, x + 6, y, z + 4, x + 6, y + 4, z + 5, ethoStone);
		
		editor.fillRectSolid(rand, x - 5, y, z - 6, x - 4, y + 4, z - 6, ethoStone);
		editor.fillRectSolid(rand, x + 4, y, z - 6, x + 5, y + 4, z - 6, ethoStone);
		
		editor.fillRectSolid(rand, x - 5, y, z + 6, x - 4, y + 4, z + 6, ethoStone);
		editor.fillRectSolid(rand, x + 4, y, z + 6, x + 5, y + 4, z + 6, ethoStone);
		
		editor.fillRectSolid(rand, x - 1, y - 1, z - 5, x + 1, y - 1, z + 5, grass);
		editor.fillRectSolid(rand, x - 5, y - 1, z - 1, x + 5, y - 1, z + 1, grass);
		
		
		MetaBlock jungleLeaf = Leaves.get(Leaves.JUNGLE, false);
		
		// north

		editor.fillRectSolid(rand, x - 1, y, z - 6, x + 1, y + 2, z - 6, air);
		
		editor.fillRectSolid(rand, x - 2, y - 1, z - 6, x - 2, y + 2, z - 6, log);
		editor.fillRectSolid(rand, x + 2, y - 1, z - 6, x + 2, y + 2, z - 6, log);
		
		editor.fillRectSolid(rand, x - 3, y - 1, z - 5, x - 3, y + 2, z - 5, log);
		editor.fillRectSolid(rand, x + 3, y - 1, z - 5, x + 3, y + 2, z - 5, log);
		
		editor.fillRectSolid(rand, x - 2, y + 3, z - 6, x + 2, y + 3, z - 6, planks);
		editor.setBlock(x - 2, y + 2, z - 6, planks);
		editor.setBlock(x - 1, y + 2, z - 6, Stair.get(StairType.OAK, Cardinal.EAST, true));
		editor.setBlock(x + 1, y + 2, z - 6, Stair.get(StairType.OAK, Cardinal.WEST, true));
		editor.setBlock(x + 2, y + 2, z - 6, planks);
		
		editor.fillRectSolid(rand, x - 2, y + 4, z - 5, x + 2, y + 4, z - 5, planks);
		editor.setBlock(x - 2, y + 3, z - 5, Stair.get(StairType.OAK, Cardinal.EAST, true));
		editor.setBlock(x + 2, y + 3, z - 5, Stair.get(StairType.OAK, Cardinal.WEST, true));

		editor.fillRectSolid(rand, x - 2, y + 5, z - 4, x + 2, y + 5, z - 4, planks);
		editor.setBlock(x - 2, y + 4, z - 4, Stair.get(StairType.OAK, Cardinal.EAST, true));
		editor.setBlock(x + 2, y + 4, z - 4, Stair.get(StairType.OAK, Cardinal.WEST, true));
		
		editor.setBlock(x - 3, y + 3, z - 4, Stair.get(StairType.OAK, Cardinal.SOUTH, true));
		editor.setBlock(x + 3, y + 3, z - 4, Stair.get(StairType.OAK, Cardinal.SOUTH, true));
		
		editor.setBlock(x - 2, y - 2, z - 5, glowstone);
		editor.fillRectSolid(rand, x - 2, y - 1, z - 5, x - 2, y, z - 5, jungleLeaf, true, true);
		
		editor.setBlock(x + 2, y - 2, z - 5, glowstone);
		editor.fillRectSolid(rand, x + 2, y - 1, z - 5, x + 2, y, z - 5, jungleLeaf, true, true);
		
		// south
		
		editor.fillRectSolid(rand, x - 1, y, z + 6, x + 1, y + 2, z + 6, air);
		
		editor.fillRectSolid(rand, x - 2, y - 1, z + 6, x - 2, y + 2, z + 6, log);
		editor.fillRectSolid(rand, x + 2, y - 1, z + 6, x + 2, y + 2, z + 6, log);
		
		editor.fillRectSolid(rand, x - 3, y - 1, z + 5, x - 3, y + 2, z + 5, log);
		editor.fillRectSolid(rand, x + 3, y - 1, z + 5, x + 3, y + 2, z + 5, log);

		editor.fillRectSolid(rand, x - 2, y + 3, z + 6, x + 2, y + 3, z + 6, planks);
		editor.setBlock(x - 2, y + 2, z + 6, planks);
		editor.setBlock(x - 1, y + 2, z + 6, Stair.get(StairType.OAK, Cardinal.EAST, true));
		editor.setBlock(x + 1, y + 2, z + 6, Stair.get(StairType.OAK, Cardinal.WEST, true));
		editor.setBlock(x + 2, y + 2, z + 6, planks);
		
		editor.fillRectSolid(rand, x - 2, y + 4, z + 5, x + 2, y + 4, z + 5, planks);
		editor.setBlock(x - 2, y + 3, z + 5, Stair.get(StairType.OAK, Cardinal.EAST, true));
		editor.setBlock(x + 2, y + 3, z + 5, Stair.get(StairType.OAK, Cardinal.WEST, true));

		editor.fillRectSolid(rand, x - 2, y + 5, z + 4, x + 2, y + 5, z + 4, planks);
		editor.setBlock(x - 2, y + 4, z + 4, Stair.get(StairType.OAK, Cardinal.EAST, true));
		editor.setBlock(x + 2, y + 4, z + 4, Stair.get(StairType.OAK, Cardinal.WEST, true));

		editor.setBlock(x - 3, y + 3, z + 4, Stair.get(StairType.OAK, Cardinal.NORTH, true));
		editor.setBlock(x + 3, y + 3, z + 4, Stair.get(StairType.OAK, Cardinal.NORTH, true));
		
		editor.setBlock(x - 2, y - 2, z + 5, glowstone);
		editor.fillRectSolid(rand, x - 2, y - 1, z + 5, x - 2, y, z + 5, jungleLeaf, true, true);
		
		editor.setBlock(x + 2, y - 2, z + 5, glowstone);
		editor.fillRectSolid(rand, x + 2, y - 1, z + 5, x + 2, y, z + 5, jungleLeaf, true, true);
		
		
		// west
		
		editor.fillRectSolid(rand, x - 6, y, z - 1, x - 6, y + 2, z + 1, air);
		
		editor.fillRectSolid(rand, x - 6, y - 1, z - 2, x - 6, y + 2, z - 2, log);
		editor.fillRectSolid(rand, x - 6, y - 1, z + 2, x - 6, y + 2, z + 2, log);
		
		editor.fillRectSolid(rand, x - 5, y - 1, z - 3, x - 5, y + 3, z - 3, log);
		editor.fillRectSolid(rand, x - 5, y - 1, z + 3, x - 5, y + 3, z + 3, log);
		
		editor.fillRectSolid(rand, x - 6, y + 3, z - 2, x - 6, y + 3, z + 2, planks);
		editor.setBlock(x - 6, y + 2, z - 2, planks);
		editor.setBlock(x - 6, y + 2, z - 1, Stair.get(StairType.OAK, Cardinal.SOUTH, true));
		editor.setBlock(x - 6, y + 2, z + 1, Stair.get(StairType.OAK, Cardinal.NORTH, true));
		editor.setBlock(x - 6, y + 2, z + 2, planks);
		
		editor.fillRectSolid(rand, x - 5, y + 4, z - 2, x - 5, y + 4, z + 2, planks);
		editor.setBlock(x - 5, y + 3, z - 2, Stair.get(StairType.OAK, Cardinal.SOUTH, true));
		editor.setBlock(x - 5, y + 3, z + 2, Stair.get(StairType.OAK, Cardinal.NORTH, true));

		editor.fillRectSolid(rand, x - 4, y + 5, z - 2, x - 4, y + 5, z + 2, planks);
		editor.setBlock(x - 4, y + 4, z - 2, Stair.get(StairType.OAK, Cardinal.SOUTH, true));
		editor.setBlock(x - 4, y + 4, z + 2, Stair.get(StairType.OAK, Cardinal.NORTH, true));

		editor.setBlock(x - 4, y + 3, z - 3, Stair.get(StairType.OAK, Cardinal.EAST, true));
		editor.setBlock(x - 4, y + 3, z + 3, Stair.get(StairType.OAK, Cardinal.EAST, true));

		editor.setBlock(x - 5, y - 2, z - 2, glowstone);
		editor.fillRectSolid(rand, x - 5, y - 1, z - 2, x - 5, y, z - 2, jungleLeaf, true, true);
		
		editor.setBlock(x - 6, y - 2, z + 2, glowstone);
		editor.fillRectSolid(rand, x - 5, y - 1, z + 2, x - 5, y, z + 2, jungleLeaf, true, true);
		
		
		// east
		
		editor.fillRectSolid(rand, x + 6, y, z - 1, x + 6, y + 2, z + 1, air);
		
		editor.fillRectSolid(rand, x + 6, y - 1, z - 2, x + 6, y + 2, z - 2, log);
		editor.fillRectSolid(rand, x + 6, y - 1, z + 2, x + 6, y + 2, z + 2, log);
		
		editor.fillRectSolid(rand, x + 5, y - 1, z - 3, x + 5, y + 3, z - 3, log);
		editor.fillRectSolid(rand, x + 5, y - 1, z + 3, x + 5, y + 3, z + 3, log);
		
		editor.fillRectSolid(rand, x + 6, y + 3, z - 2, x + 6, y + 3, z + 2, planks);
		editor.setBlock(x + 6, y + 2, z - 2, planks);
		editor.setBlock(x + 6, y + 2, z - 1, Stair.get(StairType.OAK, Cardinal.SOUTH, true));
		editor.setBlock(x + 6, y + 2, z + 1, Stair.get(StairType.OAK, Cardinal.NORTH, true));
		editor.setBlock(x + 6, y + 2, z + 2, planks);
		
		editor.fillRectSolid(rand, x + 5, y + 4, z - 2, x + 5, y + 4, z + 2, planks);
		editor.setBlock(x + 5, y + 3, z - 2, Stair.get(StairType.OAK, Cardinal.SOUTH, true));
		editor.setBlock(x + 5, y + 3, z + 2, Stair.get(StairType.OAK, Cardinal.NORTH, true));

		editor.fillRectSolid(rand, x + 4, y + 5, z - 2, x + 4, y + 5, z + 2, planks);
		editor.setBlock(x + 4, y + 4, z - 2, Stair.get(StairType.OAK, Cardinal.SOUTH, true));
		editor.setBlock(x + 4, y + 4, z + 2, Stair.get(StairType.OAK, Cardinal.NORTH, true));

		editor.setBlock(x + 4, y + 3, z - 3, Stair.get(StairType.OAK, Cardinal.WEST, true));
		editor.setBlock(x + 4, y + 3, z + 3, Stair.get(StairType.OAK, Cardinal.WEST, true));

		editor.fillRectSolid(rand, x - 3, y + 4, z - 3, x - 3, y + 5, z - 3, planks);
		editor.fillRectSolid(rand, x - 3, y + 4, z + 3, x - 3, y + 5, z + 3, planks);
		editor.fillRectSolid(rand, x + 3, y + 4, z - 3, x + 3, y + 5, z - 3, planks);
		editor.fillRectSolid(rand, x + 3, y + 4, z + 3, x + 3, y + 5, z + 3, planks);
		
		editor.setBlock(x + 5, y - 2, z - 2, glowstone);
		editor.fillRectSolid(rand, x + 5, y - 1, z - 2, x + 5, y, z - 2, jungleLeaf, true, true);
		
		editor.setBlock(x + 5, y - 2, z + 2, glowstone);
		editor.fillRectSolid(rand, x + 5, y - 1, z + 2, x + 5, y, z + 2, jungleLeaf, true, true);
				
		return true;
	}
	
	public int getSize(){
		return 8;
	}

}
