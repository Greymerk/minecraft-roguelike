package greymerk.roguelike.dungeon.rooms;

import java.util.Random;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.treasure.Treasure;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.MetaStair;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.BrewingStand;
import greymerk.roguelike.worldgen.blocks.ColorBlock;
import greymerk.roguelike.worldgen.blocks.Crops;
import greymerk.roguelike.worldgen.blocks.DyeColor;
import greymerk.roguelike.worldgen.blocks.FlowerPot;
import greymerk.roguelike.worldgen.blocks.Slab;
import greymerk.roguelike.worldgen.blocks.StairType;
import greymerk.roguelike.worldgen.redstone.Torch;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class DungeonLab extends DungeonBase {

	@Override
	public boolean generate(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances, Coord origin) {
		
		int x = origin.getX();
		int y = origin.getY();
		int z = origin.getZ();
		ITheme theme = settings.getTheme();
		
		IBlockFactory blocks = theme.getPrimaryWall();
		
		
		MetaBlock air = BlockType.get(BlockType.AIR);
		// Air
		air.fill(editor, rand, new RectSolid(new Coord(x - 7, y, z - 7), new Coord(x + 7, y + 3, z + 7)));

		IBlockFactory roof = theme.getSecondaryWall();
		// Wood upper Roof
		RectSolid.fill(editor, rand, new Coord(x - 6, y + 5, z - 6), new Coord(x + 6, y + 5, z + 6), roof);
		RectSolid.fill(editor, rand, new Coord(x - 1, y + 4, z - 1), new Coord(x + 1, y + 4, z + 1), air);
		RectSolid.fill(editor, rand, new Coord(x - 5, y + 4, z - 1), new Coord(x - 3, y + 4, z + 1), air);
		RectSolid.fill(editor, rand, new Coord(x + 3, y + 4, z - 1), new Coord(x + 5, y + 4, z + 1), air);
		RectSolid.fill(editor, rand, new Coord(x - 1, y + 4, z - 5), new Coord(x + 1, y + 4, z - 3), air);
		RectSolid.fill(editor, rand, new Coord(x - 1, y + 4, z + 3), new Coord(x + 1, y + 4, z + 5), air);
		
		// shell
		RectHollow.fill(editor, rand, new Coord(x - 8, y - 1, z - 8), new Coord(x + 8, y + 4, z + 8), blocks, false, true);
		RectSolid.fill(editor, rand, new Coord(x - 8, y - 1, z - 8), new Coord(x + 8, y - 1, z + 8), theme.getPrimaryFloor(), false, true);
		
		
		// corner rooms
		southWest(editor, rand, settings, theme, x - 7, y, z + 2);
		southEast(editor, rand, theme, x + 2, y, z + 2);
		northWest(editor, rand, theme, x - 7, y, z - 7);
		northEast(editor, rand, theme, x + 2, y, z - 7);
		
		// outer walls
		RectSolid.fill(editor, rand, new Coord(x - 8, y, z - 7), new Coord(x - 8, y + 3, z - 7), blocks);
		RectSolid.fill(editor, rand, new Coord(x + 8, y, z - 7), new Coord(x + 8, y + 3, z - 7), blocks);
		RectSolid.fill(editor, rand, new Coord(x + 8, y, z - 7), new Coord(x + 8, y + 3, z - 7), blocks);
		
		IBlockFactory backWalls = theme.getSecondaryWall();
		
		// wall planks
		RectSolid.fill(editor, rand, new Coord(x - 8, y + 1, z - 6), new Coord(x - 8, y + 3, z - 3), backWalls);
		RectSolid.fill(editor, rand, new Coord(x - 8, y + 1, z + 3), new Coord(x - 8, y + 3, z + 6), backWalls);
		RectSolid.fill(editor, rand, new Coord(x + 8, y + 1, z - 6), new Coord(x + 8, y + 3, z - 3), backWalls);
		RectSolid.fill(editor, rand, new Coord(x + 8, y + 1, z + 3), new Coord(x + 8, y + 3, z + 6), backWalls);
		
		RectSolid.fill(editor, rand, new Coord(x - 6, y + 1, z - 8), new Coord(x - 3, y + 3, z - 8), backWalls);
		RectSolid.fill(editor, rand, new Coord(x + 3, y + 1, z - 8), new Coord(x + 6, y + 3, z - 8), backWalls);
		RectSolid.fill(editor, rand, new Coord(x - 6, y + 1, z + 8), new Coord(x - 3, y + 3, z + 8), backWalls);
		RectSolid.fill(editor, rand, new Coord(x + 3, y + 1, z + 8), new Coord(x + 6, y + 3, z + 8), backWalls);
		
		return false;
	}

	private static void corner(IWorldEditor editor, Random rand, ITheme theme, int x, int y, int z){
		
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
		RectSolid.fill(editor, rand, new Coord(x, y - 1, z), new Coord(x + 5, y - 1, z + 5), cyan);
		RectSolid.fill(editor, rand, new Coord(x + 1, y - 1, z + 2), new Coord(x + 4, y - 1, z + 3), doubleSlab);
		RectSolid.fill(editor, rand, new Coord(x + 2, y - 1, z + 1), new Coord(x + 3, y - 1, z + 4), doubleSlab);
		
		// ceiling dome
		RectSolid.fill(editor, rand, new Coord(x + 2, y + 4, z + 2), new Coord(x + 3, y + 8, z + 3), air);
		air.setBlock(editor, new Coord(x + 3, y + 4, z + 1));
		air.setBlock(editor, new Coord(x + 4, y + 4, z + 1));
		air.setBlock(editor, new Coord(x + 3, y + 4, z + 4));
		air.setBlock(editor, new Coord(x + 4, y + 4, z + 4));
		
		air.setBlock(editor, new Coord(x + 1, y + 4, z + 3));
		air.setBlock(editor, new Coord(x + 1, y + 4, z + 4));
		air.setBlock(editor, new Coord(x + 4, y + 4, z + 3));
		air.setBlock(editor, new Coord(x + 4, y + 4, z + 4));
		
		RectHollow.fill(editor, rand, new Coord(x + 1, y + 4, z + 1), new Coord(x + 4, y + 8, z + 4), cobble, false, true);
		RectSolid.fill(editor, rand, new Coord(x + 2, y + 8, z + 2), new Coord(x + 3, y + 8, z + 3), air);
	}
	
	
	private void southWest(IWorldEditor editor, Random rand, LevelSettings settings, ITheme theme, int x, int y, int z){
		
		corner(editor, rand, theme, x, y, z);
		
		IStair stair = theme.getSecondaryStair();
		stair.setOrientation(Cardinal.NORTH, true);
		RectSolid.fill(editor, rand, new Coord(x + 1, y, z + 5), new Coord(x + 4, y, z + 5), stair);
		stair.setOrientation(Cardinal.EAST, true);
		RectSolid.fill(editor, rand, new Coord(x, y, z + 1), new Coord(x, y, z + 4), stair);
		
		if(RogueConfig.getBoolean(RogueConfig.GENEROUS)){
			editor.setBlock(new Coord(x + 1, y + 1, z + 5), BrewingStand.get());
		}
		
		Treasure.generate(editor, rand, new Coord(x, y + 1, z + 4), Treasure.POTIONS, Dungeon.getLevel(y));
	}
	
	// fountains
	private static void southEast(IWorldEditor editor, Random rand, ITheme theme, int x, int y, int z){
		
		MetaBlock stone = BlockType.get(BlockType.STONE_BRICK);
		IStair stair = new MetaStair(StairType.STONEBRICK);
		MetaBlock slab = Slab.get(Slab.STONEBRICK, false, false, false);
		MetaBlock water = BlockType.get(BlockType.WATER_FLOWING);
		
		corner(editor, rand, theme, x, y, z);
		
		RectSolid.fill(editor, rand, new Coord(x + 1, y, z + 5), new Coord(x + 4, y, z + 5), stone);
		editor.setBlock(new Coord(x + 1, y + 1, z + 5), stair.setOrientation(Cardinal.WEST, false));
		editor.setBlock(new Coord(x + 2, y + 1, z + 5), water);
		editor.setBlock(new Coord(x + 2, y + 2, z + 5), slab);
		editor.setBlock(new Coord(x + 3, y + 1, z + 5), stair.setOrientation(Cardinal.EAST, false));
		
		RectSolid.fill(editor, rand, new Coord(x + 5, y, z + 1), new Coord(x + 5, y, z + 4), stone);
		editor.setBlock(new Coord(x + 5, y + 1, z + 1), stair.setOrientation(Cardinal.NORTH, false));
		editor.setBlock(new Coord(x + 5, y + 1, z + 2), water);
		editor.setBlock(new Coord(x + 5, y + 2, z + 2), slab);
		editor.setBlock(new Coord(x + 5, y + 1, z + 3), stair.setOrientation(Cardinal.SOUTH, false));
		
		RectSolid.fill(editor, rand, new Coord(x + 3, y, z + 3), new Coord(x + 4, y, z + 4), stone);
		Torch.generate(editor, Torch.WOODEN, Cardinal.UP, new Coord(x + 3, y + 1, z + 3));
		
		stair.setOrientation(Cardinal.NORTH, false).setBlock(editor, new Coord(x + 4, y, z + 1));
		stair.setOrientation(Cardinal.WEST, false).setBlock(editor, new Coord(x + 3, y, z + 2));
		stair.setOrientation(Cardinal.NORTH, false).setBlock(editor, new Coord(x + 2, y, z + 3));
		stair.setOrientation(Cardinal.WEST, false).setBlock(editor, new Coord(x + 1, y, z + 4));
		
		
	}
	
	private static void northWest(IWorldEditor editor, Random rand, ITheme theme, int x, int y, int z){
		
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
		stair.setOrientation(Cardinal.SOUTH, false).fill(editor, rand, new RectSolid(new Coord(x + 2, y, z + 1), new Coord(x + 4, y, z + 1)));
		stair.setOrientation(Cardinal.EAST, false).fill(editor, rand, new RectSolid(new Coord(x + 1, y, z + 2), new Coord(x + 1, y, z + 4)));
		
		editor.setBlock(new Coord(x + 2, y - 1, z + 2), redstone);
		editor.setBlock(new Coord(x + 3, y - 1, z + 2), lamp);
		editor.setBlock(new Coord(x + 2, y - 1, z + 3), lamp);
		
		editor.setBlock(new Coord(x, y, z), BlockType.get(BlockType.WATER_FLOWING));
	}
	
	private static void northEast(IWorldEditor editor, Random rand, ITheme theme, int x, int y, int z){
		
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
		
		stair.setOrientation(Cardinal.SOUTH, false).fill(editor, rand, new RectSolid(new Coord(x + 1, y, z + 1), new Coord(x + 3, y, z + 1)));
		stair.setOrientation(Cardinal.WEST, false).fill(editor, rand, new RectSolid(new Coord(x + 4, y, z + 2), new Coord(x + 4, y, z + 4)));
		
		editor.setBlock(new Coord(x + 3, y - 1, z + 2), redstone);
		editor.setBlock(new Coord(x + 2, y - 1, z + 2), lamp);
		editor.setBlock(new Coord(x + 3, y - 1, z + 3), lamp);
		
		editor.setBlock(new Coord(x + 5, y, z), BlockType.get(BlockType.WATER_FLOWING));
	}
	
	private static void pillar(IWorldEditor editor, Random rand, ITheme theme, int x, int y, int z){
		
		theme.getSecondaryPillar().fill(editor, rand, new RectSolid(new Coord(x, y, z), new Coord(x, y + 2, z)));
		theme.getPrimaryWall().setBlock(editor, rand, new Coord(x, y + 3, z));
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
