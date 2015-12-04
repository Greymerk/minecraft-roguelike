package greymerk.roguelike.dungeon.rooms;

import java.util.Random;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.treasure.Treasure;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.MetaStair;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.ColorBlock;
import greymerk.roguelike.worldgen.blocks.DyeColor;
import greymerk.roguelike.worldgen.blocks.Furnace;
import greymerk.roguelike.worldgen.blocks.StairType;
import greymerk.roguelike.worldgen.redstone.Torch;



public class DungeonMess extends DungeonBase {

	IBlockFactory plank;
	IStair stair;
	IBlockFactory log;
	
	@Override
	public boolean generate(WorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances, Coord origin) {
		
		int x = origin.getX();
		int y = origin.getY();
		int z = origin.getZ();
		ITheme theme = settings.getTheme();
		
		plank = theme.getSecondaryWall();
		stair = theme.getSecondaryStair();
		log = theme.getSecondaryPillar();
		
		MetaBlock air = BlockType.get(BlockType.AIR);
		
		// air		
		editor.fillRectSolid(rand, x - 6, y, z - 6, x + 6, y + 2, z + 6, air);

		// ceiling
		editor.fillRectSolid(rand, x - 6, y + 3, z - 6, x + 6, y + 4, z + 6, plank, true, true);
		editor.fillRectSolid(rand, x - 4, y + 3, z - 2, x + 4, y + 3, z - 2, stair.setOrientation(Cardinal.SOUTH, true), true, true);
		editor.fillRectSolid(rand, x - 4, y + 3, z + 2, x + 4, y + 3, z + 2, stair.setOrientation(Cardinal.NORTH, true), true, true);
		
		editor.fillRectSolid(rand, x - 2, y + 3, z - 4, x - 2, y + 3, z + 4, stair.setOrientation(Cardinal.EAST, true), true, true);
		editor.fillRectSolid(rand, x + 2, y + 3, z - 4, x + 2, y + 3, z + 4, stair.setOrientation(Cardinal.WEST, true), true, true);
		
		editor.fillRectSolid(rand, x - 1, y + 3, z - 4, x + 1, y + 3, z + 4, air);
		editor.fillRectSolid(rand, x - 4, y + 3, z - 1, x + 4, y + 3, z + 1, air);
		
		MetaBlock brownClay = ColorBlock.get(ColorBlock.CLAY, DyeColor.BROWN);
		
		// floor
		editor.fillRectSolid(rand, x - 7, y - 1, z - 7, x + 7, y - 1, z + 7, brownClay, true, true);
		editor.fillRectSolid(rand, x - 1, y - 1, z - 6, x + 1, y - 1, z + 6, log, true, true);
		editor.fillRectSolid(rand, x - 5, y - 1, z - 1, x - 2, y - 1, z + 1, log, true, true);
		editor.fillRectSolid(rand, x + 2, y - 1, z - 1, x + 5, y - 1, z + 1, log, true, true);

		
		
		// walls
		editor.fillRectSolid(rand, x + 7, y, z - 2, x + 7, y + 2, z + 6, plank, false, true);
		editor.fillRectSolid(rand, x - 7, y, z - 6, x - 7, y + 2, z + 6, plank, false, true);
		
		editor.fillRectSolid(rand, x - 6, y, z - 7, x + 2, y + 2, z - 7, plank, false, true);
		editor.fillRectSolid(rand, x - 6, y, z + 7, x + 6, y + 2, z + 7, plank, false, true);
		
		// pillars
		for(int i = - 6; i <= 6; i = i += 4){
			for(int j = - 6; j <= 6; j += 4){
				if(i % 6 == 0 || j % 6 == 0){
					pillar(editor, rand, theme, 2, x + i, y, z + j);	
				} else {
					pillar(editor, rand, theme, 3, x + i, y, z + j);
				}
				
			}
		}
				
		// stove
		stove(editor, rand, x + 4, y, z - 4);
		
		// storage
		storage(editor, rand, settings, x + 4, y, z + 4);
		
		// table north
		northTable(editor, rand, x - 4, y, z - 4);
		
		
		// table south
		southTable(editor, rand, x - 4, y, z + 4);

		return true;
	}

	private void stove(WorldEditor editor, Random rand, int x, int y, int z){
		
		MetaBlock brick = BlockType.get(BlockType.BRICK);
		IStair stair = new MetaStair(StairType.BRICK);
		
		// floor
		
		// fire pit
		editor.fillRectSolid(rand, x - 1, y - 1, z - 4, x + 2, y - 1, z + 1, brick);
		editor.fillRectSolid(rand, x - 1, y, z - 4, x + 1, y + 2, z - 3, brick);
		editor.setBlock(rand, x - 1, y, z - 2, stair.setOrientation(Cardinal.EAST, false), true, true);
		editor.setBlock(rand, x - 1, y + 1, z - 2, stair.setOrientation(Cardinal.EAST, true), true, true);
		
		editor.setBlock(rand, x + 1, y, z - 2, stair.setOrientation(Cardinal.WEST, false), true, true);
		editor.setBlock(rand, x + 1, y + 1, z - 2, stair.setOrientation(Cardinal.WEST, true), true, true);
		
		editor.fillRectSolid(rand, x - 1, y + 2, z - 2, x + 1, y + 2, z - 2, brick);
		editor.fillRectSolid(rand, x - 1, y + 2, z - 1, x + 2, y + 2, z - 1, stair.setOrientation(Cardinal.SOUTH, true), true, true);
		
		editor.setBlock(x, y - 1, z - 3, BlockType.get(BlockType.NETHERRACK));
		editor.setBlock(x, y, z - 3, BlockType.get(BlockType.FIRE));
		editor.setBlock(rand, x, y + 1, z - 3, stair.setOrientation(Cardinal.SOUTH, true), true, true);

		// furnace
		editor.fillRectSolid(rand, x + 2, y, z - 1, x + 2, y + 2, z + 1, brick);
		editor.fillRectSolid(rand, x + 1, y + 2, z - 1, x + 1, y + 2, z + 1, stair.setOrientation(Cardinal.WEST, true), true, true);
		
		Furnace.generate(editor, Cardinal.WEST, new Coord(x + 2, y, z));
		editor.setBlock(rand, x + 2, y + 1, z, stair.setOrientation(Cardinal.WEST, true), true, true);
		
		// ceiling
		editor.fillRectSolid(rand, x - 1, y + 3, z - 1, x + 1, y + 3, z + 1, brick);
	}
	
	private void storage(WorldEditor editor, Random rand, LevelSettings settings, int x, int y, int z){
		
		// floor
		editor.fillRectSolid(rand, x - 1, y - 1, z - 1, x + 1, y - 1, z + 1, plank, true, true);
		
		// east shelf
		editor.setBlock(rand, x + 2, y, z - 1, stair.setOrientation(Cardinal.SOUTH, true), true, true);
		editor.setBlock(rand, x + 2, y, z, stair.setOrientation(Cardinal.WEST, true), true, true);
		editor.setBlock(rand, x + 2, y, z + 1, stair.setOrientation(Cardinal.NORTH, true), true, true);
		Treasure.generate(editor, rand, settings, new Coord(x + 2, y + 1, z), Treasure.FOOD, 1, false);
		
		// south shelf
		editor.setBlock(rand, x - 1, y, z + 2, stair.setOrientation(Cardinal.EAST, true), true, true);
		editor.setBlock(rand, x, y, z + 2, stair.setOrientation(Cardinal.NORTH, true), true, true);
		editor.setBlock(rand, x + 1, y, z + 2, stair.setOrientation(Cardinal.WEST, true), true, true);
	}
	
	private void northTable(WorldEditor editor, Random rand, int x, int y, int z){
		// floor
		editor.fillRectSolid(rand, x - 1, y - 1, z - 1, x + 1, y - 1, z + 1, plank, true, true);
		
		// benches
		editor.fillRectSolid(rand, x - 1, y, z - 2, x + 1, y, z - 2, stair.setOrientation(Cardinal.SOUTH, false), true, true);
		editor.fillRectSolid(rand, x - 2, y, z - 1, x - 2, y, z + 1, stair.setOrientation(Cardinal.EAST, false), true, true);
		
		// table
		editor.setBlock(rand, x, y, z, stair.setOrientation(Cardinal.NORTH, true), true, true);
		editor.setBlock(rand, x + 1, y, z, stair.setOrientation(Cardinal.EAST, true), true, true);
		editor.setBlock(rand, x + 1, y, z + 1, stair.setOrientation(Cardinal.SOUTH, true), true, true);
		editor.setBlock(rand, x, y, z + 1, stair.setOrientation(Cardinal.WEST, true), true, true);
		Torch.generate(editor, Torch.WOODEN, Cardinal.UP, new Coord(x, y + 1, z));
	}

	private void southTable(WorldEditor editor, Random rand, int x, int y, int z){
		// floor
		editor.fillRectSolid(rand, x - 1, y - 1, z - 1, x + 1, y - 1, z + 1, plank, true, true);
		
		// benches
		editor.fillRectSolid(rand, x - 1, y, z + 2, x + 1, y, z + 2, stair.setOrientation(Cardinal.NORTH, false), true, true);
		editor.fillRectSolid(rand, x - 2, y, z - 1, x - 2, y, z + 1, stair.setOrientation(Cardinal.EAST, false), true, true);
		
		// table
		editor.setBlock(rand, x, y, z - 1, stair.setOrientation(Cardinal.NORTH, true), true, true);
		editor.setBlock(rand, x + 1, y, z - 1, stair.setOrientation(Cardinal.EAST, true), true, true);
		editor.setBlock(rand, x + 1, y, z, stair.setOrientation(Cardinal.SOUTH, true), true, true);
		editor.setBlock(rand, x, y, z, stair.setOrientation(Cardinal.WEST, true), true, true);
		Torch.generate(editor, Torch.WOODEN, Cardinal.UP, new Coord(x, y + 1, z));
	}
	
	private static void pillar(WorldEditor editor, Random rand, ITheme theme, int height, int x, int y, int z){
		
		IStair stair = theme.getSecondaryStair();
		IBlockFactory pillar = theme.getSecondaryPillar();
		IBlockFactory wall = theme.getSecondaryWall();
		
		editor.fillRectSolid(rand, x, y, z, x, y + height - 1, z, pillar, true, true);
		editor.setBlock(rand, x, y + height, z, wall, true, true);
		editor.setBlock(rand, x + 1, y + height, z, stair.setOrientation(Cardinal.EAST, true), true, true);
		editor.setBlock(rand, x - 1, y + height, z, stair.setOrientation(Cardinal.WEST, true), true, true);
		editor.setBlock(rand, x, y + height, z + 1, stair.setOrientation(Cardinal.SOUTH, true), true, true);
		editor.setBlock(rand, x, y + height, z - 1, stair.setOrientation(Cardinal.NORTH, true), true, true);
	}
	
	public int getSize(){
		return 10;
	}
}
