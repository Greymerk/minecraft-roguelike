package greymerk.roguelike.dungeon.rooms;

import java.util.Random;

import greymerk.roguelike.config.RogueConfig;
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
import greymerk.roguelike.worldgen.blocks.BrewingStand;
import greymerk.roguelike.worldgen.blocks.ColorBlock;
import greymerk.roguelike.worldgen.blocks.Crops;
import greymerk.roguelike.worldgen.blocks.DyeColor;
import greymerk.roguelike.worldgen.blocks.FlowerPot;
import greymerk.roguelike.worldgen.blocks.Slab;
import greymerk.roguelike.worldgen.blocks.StairType;
import greymerk.roguelike.worldgen.redstone.Torch;

public class DungeonLab extends DungeonBase {

	@Override
	public boolean generate(WorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances, Coord origin) {
		
		int x = origin.getX();
		int y = origin.getY();
		int z = origin.getZ();
		ITheme theme = settings.getTheme();
		
		IBlockFactory blocks = theme.getPrimaryWall();
		
		
		MetaBlock air = BlockType.get(BlockType.AIR);
		// Air
		editor.fillRectSolid(rand, new Coord(x - 7, y, z - 7), new Coord(x + 7, y + 3, z + 7), air, true, true);

		IBlockFactory roof = theme.getSecondaryWall();
		// Wood upper Roof
		editor.fillRectSolid(rand, new Coord(x - 6, y + 5, z - 6), new Coord(x + 6, y + 5, z + 6), roof, true, true);
		editor.fillRectSolid(rand, new Coord(x - 1, y + 4, z - 1), new Coord(x + 1, y + 4, z + 1), air, true, true);
		editor.fillRectSolid(rand, new Coord(x - 5, y + 4, z - 1), new Coord(x - 3, y + 4, z + 1), air, true, true);
		editor.fillRectSolid(rand, new Coord(x + 3, y + 4, z - 1), new Coord(x + 5, y + 4, z + 1), air, true, true);
		editor.fillRectSolid(rand, new Coord(x - 1, y + 4, z - 5), new Coord(x + 1, y + 4, z - 3), air, true, true);
		editor.fillRectSolid(rand, new Coord(x - 1, y + 4, z + 3), new Coord(x + 1, y + 4, z + 5), air, true, true);
		
		// shell
		editor.fillRectHollow(rand, new Coord(x - 8, y - 1, z - 8), new Coord(x + 8, y + 4, z + 8), blocks, false, true);
		editor.fillRectSolid(rand, new Coord(x - 8, y - 1, z - 8), new Coord(x + 8, y - 1, z + 8), theme.getPrimaryFloor(), false, true);
		
		
		// corner rooms
		southWest(editor, rand, settings, theme, x - 7, y, z + 2);
		southEast(editor, rand, theme, x + 2, y, z + 2);
		northWest(editor, rand, theme, x - 7, y, z - 7);
		northEast(editor, rand, theme, x + 2, y, z - 7);
		
		// outer walls
		editor.fillRectSolid(rand, new Coord(x - 8, y, z - 7), new Coord(x - 8, y + 3, z - 7), blocks, true, true);
		editor.fillRectSolid(rand, new Coord(x + 8, y, z - 7), new Coord(x + 8, y + 3, z - 7), blocks, true, true);
		editor.fillRectSolid(rand, new Coord(x + 8, y, z - 7), new Coord(x + 8, y + 3, z - 7), blocks, true, true);
		
		IBlockFactory backWalls = theme.getSecondaryWall();
		
		// wall planks
		editor.fillRectSolid(rand, new Coord(x - 8, y + 1, z - 6), new Coord(x - 8, y + 3, z - 3), backWalls, true, true);
		editor.fillRectSolid(rand, new Coord(x - 8, y + 1, z + 3), new Coord(x - 8, y + 3, z + 6), backWalls, true, true);
		editor.fillRectSolid(rand, new Coord(x + 8, y + 1, z - 6), new Coord(x + 8, y + 3, z - 3), backWalls, true, true);
		editor.fillRectSolid(rand, new Coord(x + 8, y + 1, z + 3), new Coord(x + 8, y + 3, z + 6), backWalls, true, true);
		
		editor.fillRectSolid(rand, new Coord(x - 6, y + 1, z - 8), new Coord(x - 3, y + 3, z - 8), backWalls, true, true);
		editor.fillRectSolid(rand, new Coord(x + 3, y + 1, z - 8), new Coord(x + 6, y + 3, z - 8), backWalls, true, true);
		editor.fillRectSolid(rand, new Coord(x - 6, y + 1, z + 8), new Coord(x - 3, y + 3, z + 8), backWalls, true, true);
		editor.fillRectSolid(rand, new Coord(x + 3, y + 1, z + 8), new Coord(x + 6, y + 3, z + 8), backWalls, true, true);
		
		
		return false;
	}

	private static void corner(WorldEditor editor, Random rand, ITheme theme, int x, int y, int z){
		
		MetaBlock air = BlockType.get(BlockType.AIR);
		MetaBlock doubleSlab = Slab.get(Slab.STONE, false, true, true);
		MetaBlock cobble = BlockType.get(BlockType.COBBLESTONE);
		MetaBlock cyan = ColorBlock.get(ColorBlock.CLAY, DyeColor.CYAN);
		
		// pillars
		pillar(editor, rand, theme, x, y, z);
		pillar(editor, rand, theme, x + 5, y, z);
		pillar(editor, rand, theme, x, y, z + 5);
		pillar(editor, rand, theme, x + 5, y, z + 5);
		
		// tile floor
		editor.fillRectSolid( rand, new Coord(x, y - 1, z), new Coord(x + 5, y - 1, z + 5), cyan, true, true);
		editor.fillRectSolid( rand, new Coord(x + 1, y - 1, z + 2), new Coord(x + 4, y - 1, z + 3), doubleSlab, true, true);
		editor.fillRectSolid( rand, new Coord(x + 2, y - 1, z + 1), new Coord(x + 3, y - 1, z + 4), doubleSlab, true, true);
		
		// ceiling dome
		editor.fillRectSolid( rand, new Coord(x + 2, y + 4, z + 2), new Coord(x + 3, y + 8, z + 3), air, true, true);
		editor.setBlock(new Coord(x + 3, y + 4, z + 1), air);
		editor.setBlock(new Coord(x + 4, y + 4, z + 1), air);
		editor.setBlock(new Coord(x + 3, y + 4, z + 4), air);
		editor.setBlock(new Coord(x + 4, y + 4, z + 4), air);
		
		editor.setBlock(new Coord(x + 1, y + 4, z + 3), air);
		editor.setBlock(new Coord(x + 1, y + 4, z + 4), air);
		editor.setBlock(new Coord(x + 4, y + 4, z + 3), air);
		editor.setBlock(new Coord(x + 4, y + 4, z + 4), air);
		
		editor.fillRectHollow(rand, new Coord(x + 1, y + 4, z + 1), new Coord(x + 4, y + 8, z + 4), cobble, false, true);
		editor.fillRectSolid( rand, new Coord(x + 2, y + 8, z + 2), new Coord(x + 3, y + 8, z + 3), air, true, true);
	}
	
	
	private static void southWest(WorldEditor editor, Random rand, LevelSettings settings, ITheme theme, int x, int y, int z){
		
		corner(editor, rand, theme, x, y, z);
		
		IStair stair = theme.getSecondaryStair();
		stair.setOrientation(Cardinal.NORTH, true);
		editor.fillRectSolid(rand, new Coord(x + 1, y, z + 5), new Coord(x + 4, y, z + 5), stair, true, true);
		stair.setOrientation(Cardinal.EAST, true);
		editor.fillRectSolid(rand, new Coord(x, y, z + 1), new Coord(x, y, z + 4), stair, true, true);
		
		if(RogueConfig.getBoolean(RogueConfig.GENEROUS)){
			editor.setBlock(new Coord(x + 1, y + 1, z + 5), BrewingStand.get());
		}
		
		Treasure.generate(editor, rand, settings, new Coord(x, y + 1, z + 4), Treasure.POTIONS);
		
		
	}
	
	// fountains
	private static void southEast(WorldEditor editor, Random rand, ITheme theme, int x, int y, int z){
		
		MetaBlock stone = BlockType.get(BlockType.STONE_BRICK);
		IStair stair = new MetaStair(StairType.STONEBRICK);
		MetaBlock slab = Slab.get(Slab.STONEBRICK, false, false, false);
		MetaBlock water = BlockType.get(BlockType.WATER_FLOWING);
		
		corner(editor, rand, theme, x, y, z);
		
		editor.fillRectSolid(rand, new Coord(x + 1, y, z + 5), new Coord(x + 4, y, z + 5), stone, true, true);
		editor.setBlock(new Coord(x + 1, y + 1, z + 5), stair.setOrientation(Cardinal.WEST, false));
		editor.setBlock(new Coord(x + 2, y + 1, z + 5), water);
		editor.setBlock(new Coord(x + 2, y + 2, z + 5), slab);
		editor.setBlock(new Coord(x + 3, y + 1, z + 5), stair.setOrientation(Cardinal.EAST, false));
		
		editor.fillRectSolid(rand, new Coord(x + 5, y, z + 1), new Coord(x + 5, y, z + 4), stone, true, true);
		editor.setBlock(new Coord(x + 5, y + 1, z + 1), stair.setOrientation(Cardinal.NORTH, false));
		editor.setBlock(new Coord(x + 5, y + 1, z + 2), water);
		editor.setBlock(new Coord(x + 5, y + 2, z + 2), slab);
		editor.setBlock(new Coord(x + 5, y + 1, z + 3), stair.setOrientation(Cardinal.SOUTH, false));
		
		editor.fillRectSolid(rand, new Coord(x + 3, y, z + 3), new Coord(x + 4, y, z + 4), stone, true, true);
		Torch.generate(editor, Torch.WOODEN, Cardinal.UP, new Coord(x + 3, y + 1, z + 3));
		
		editor.setBlock(rand, new Coord(x + 4, y, z + 1), stair.setOrientation(Cardinal.NORTH, false), true, true);
		editor.setBlock(rand, new Coord(x + 3, y, z + 2), stair.setOrientation(Cardinal.WEST, false), true, true);
		editor.setBlock(rand, new Coord(x + 2, y, z + 3), stair.setOrientation(Cardinal.NORTH, false), true, true);
		editor.setBlock(rand, new Coord(x + 1, y, z + 4), stair.setOrientation(Cardinal.WEST, false), true, true);
		
		
	}
	
	private static void northWest(WorldEditor editor, Random rand, ITheme theme, int x, int y, int z){
		
		MetaBlock stone = BlockType.get(BlockType.STONE_BRICK);
		MetaBlock redstone = BlockType.get(BlockType.REDSTONE_BLOCK);
		MetaBlock lamp = BlockType.get(BlockType.REDSTONE_LAMP_LIT);
		MetaBlock farmland = BlockType.get(BlockType.FARMLAND);
		MetaBlock soul_sand = BlockType.get(BlockType.SOUL_SAND);
		
		corner(editor, rand, theme, x, y, z);
		
		editor.setBlock(new Coord(x + 1, y, z), stone);
		FlowerPot.generate(editor, rand, new Coord(x + 1, y + 1, z));
		editor.setBlock(new Coord(x + 2, y, z), farmland);
		editor.setBlock(new Coord(x + 2, y + 1, z), Crops.get(Crops.CARROTS));
		editor.setBlock(new Coord(x + 3, y, z), farmland);
		editor.setBlock(new Coord(x + 3, y + 1, z), Crops.get(Crops.CARROTS));
		editor.setBlock(new Coord(x + 4, y, z), stone);
		FlowerPot.generate(editor, rand, new Coord(x + 4, y + 1, z));
		
		editor.setBlock(new Coord(x, y, z + 1), stone);
		FlowerPot.generate(editor, rand, new Coord(x, y + 1, z + 1));
		editor.setBlock(new Coord(x, y, z + 2), soul_sand);
		editor.setBlock(new Coord(x, y + 1, z + 2), Crops.get(Crops.NETHERWART));
		editor.setBlock(new Coord(x, y, z + 3), soul_sand);
		editor.setBlock(new Coord(x, y + 1, z + 3), Crops.get(Crops.NETHERWART));
		editor.setBlock(new Coord(x, y, z + 4), stone);
		FlowerPot.generate(editor, rand, new Coord(x, y + 1, z + 4));
		
		editor.setBlock(new Coord(x + 1, y, z + 1), stone);
		
		IStair stair = new MetaStair(StairType.STONEBRICK);
		editor.fillRectSolid(rand, new Coord(x + 2, y, z + 1), new Coord(x + 4, y, z + 1), stair.setOrientation(Cardinal.SOUTH, false), true, true);
		editor.fillRectSolid(rand, new Coord(x + 1, y, z + 2), new Coord(x + 1, y, z + 4), stair.setOrientation(Cardinal.EAST, false), true, true);
		
		editor.setBlock(new Coord(x + 2, y - 1, z + 2), redstone);
		editor.setBlock(new Coord(x + 3, y - 1, z + 2), lamp);
		editor.setBlock(new Coord(x + 2, y - 1, z + 3), lamp);
		
		editor.setBlock(new Coord(x, y, z), BlockType.get(BlockType.WATER_FLOWING));
	}
	
	private static void northEast(WorldEditor editor, Random rand, ITheme theme, int x, int y, int z){
		
		MetaBlock stone = BlockType.get(BlockType.STONE_BRICK);
		MetaBlock redstone = BlockType.get(BlockType.REDSTONE_BLOCK);
		MetaBlock lamp = BlockType.get(BlockType.REDSTONE_LAMP_LIT);
		MetaBlock farmland = BlockType.get(BlockType.FARMLAND);
		
		corner(editor, rand, theme, x, y, z);
		
		editor.setBlock(new Coord(x + 1, y, z), stone);
		FlowerPot.generate(editor, rand, new Coord(x + 1, y + 1, z));
		editor.setBlock(new Coord(x + 2, y, z), farmland);
		editor.setBlock(new Coord(x + 2, y + 1, z), Crops.get(Crops.MELON));
		editor.setBlock(new Coord(x + 3, y, z), farmland);
		editor.setBlock(new Coord(x + 4, y, z), stone);
		FlowerPot.generate(editor, rand, new Coord(x + 4, y + 1, z));
		
		editor.setBlock(new Coord(x + 5, y, z + 1), stone);
		FlowerPot.generate(editor, rand, new Coord(x + 5, y + 1, z + 1));
		editor.setBlock(new Coord(x + 5, y, z + 2), farmland);
		editor.setBlock(new Coord(x + 5, y + 1, z + 2), Crops.get(Crops.PUMPKIN));
		editor.setBlock(new Coord(x + 5, y, z + 3), farmland);
		editor.setBlock(new Coord(x + 5, y, z + 4), stone);
		FlowerPot.generate(editor, rand, new Coord(x + 5, y + 1, z + 4));
		
		editor.setBlock(new Coord(x + 4, y, z + 1), stone);
		
		IStair stair = new MetaStair(StairType.STONEBRICK);
		
		editor.fillRectSolid(rand, new Coord(x + 1, y, z + 1), new Coord(x + 3, y, z + 1), stair.setOrientation(Cardinal.SOUTH, false), true, true);
		editor.fillRectSolid(rand, new Coord(x + 4, y, z + 2), new Coord(x + 4, y, z + 4), stair.setOrientation(Cardinal.WEST, false), true, true);
		
		editor.setBlock(new Coord(x + 3, y - 1, z + 2), redstone);
		editor.setBlock(new Coord(x + 2, y - 1, z + 2), lamp);
		editor.setBlock(new Coord(x + 3, y - 1, z + 3), lamp);
		
		editor.setBlock(new Coord(x + 5, y, z), BlockType.get(BlockType.WATER_FLOWING));
	}
	
	private static void pillar(WorldEditor editor, Random rand, ITheme theme, int x, int y, int z){
		
		editor.fillRectSolid(rand, new Coord(x, y, z), new Coord(x, y + 2, z), theme.getSecondaryPillar(), true, true);
		editor.setBlock(rand, new Coord(x, y + 3, z), theme.getPrimaryWall(), true, true);
		IStair stair = theme.getSecondaryStair();
		stair.setOrientation(Cardinal.EAST, true).setBlock(editor, new Coord(x + 1, y + 3, z));
		stair.setOrientation(Cardinal.WEST, true).setBlock(editor, new Coord(x - 1, y + 3, z));
		stair.setOrientation(Cardinal.SOUTH, true).setBlock(editor, new Coord(x, y + 3, z + 1));
		stair.setOrientation(Cardinal.NORTH, true).setBlock(editor, new Coord(x, y + 3, z - 1));
	
	}
	
	public int getSize(){
		return 9;
	}
	
}
