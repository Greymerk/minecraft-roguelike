package greymerk.roguelike.dungeon.rooms;

import java.util.Random;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.blocks.Skull;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.init.Blocks;

public class DungeonOssuary extends DungeonBase {

	@Override
	public boolean generate(WorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances, Coord origin) {
		
		int x = origin.getX();
		int y = origin.getY();
		int z = origin.getZ();
		ITheme theme = settings.getTheme();
		
		IStair stair = theme.getPrimaryStair();
		MetaBlock air = new MetaBlock(Blocks.air);
		
		IBlockFactory walls = theme.getPrimaryWall();
		editor.fillRectHollow(rand, new Coord(x - 8, y - 1, z - 8), new Coord(x + 8, y + 6, z + 8), walls, false, true);
		editor.fillRectSolid(rand, x - 7, y + 5, z - 7, x + 7, y + 5, z + 7, walls);
		
		// any missing floor
		BlockWeightedRandom cracked = new BlockWeightedRandom();
		MetaBlock crack = new MetaBlock(Blocks.stonebrick);
		crack.withProperty(BlockStoneBrick.VARIANT_PROP, BlockStoneBrick.EnumType.CRACKED);
		cracked.addBlock(crack, 10);
		cracked.addBlock(air, 3);
		cracked.addBlock(new MetaBlock(Blocks.cobblestone), 5);
		cracked.addBlock(new MetaBlock(Blocks.gravel), 5);
		
		editor.fillRectSolid(rand, x - 7, y - 1, z - 7, x + 7, y - 1, z + 7, cracked, true, false);
		
		// arches
		// west
		stair.setOrientation(Cardinal.EAST, true);
		editor.fillRectSolid(rand, x - 7, y, z - 2, x - 6, y + 4, z - 2, walls);
		editor.fillRectSolid(rand, x - 5, y + 3, z - 2, x - 5, y + 4, z - 2, walls);
		stair.setBlock(editor, new Coord(x - 5, y + 2, z - 2));
		editor.fillRectSolid(rand, x - 4, y + 4, z - 2, x - 4, y + 4, z - 2, walls);
		stair.setBlock(editor, new Coord(x - 4, y + 3, z - 2));
		stair.setBlock(editor, new Coord(x - 3, y + 4, z - 2));
		
		editor.fillRectSolid(rand, x - 7, y, z + 2, x - 6, y + 5, z + 2, walls);
		editor.fillRectSolid(rand, x - 5, y + 3, z + 2, x - 5, y + 4, z + 2, walls);
		stair.setBlock(editor, new Coord(x - 5, y + 2, z + 2));
		editor.fillRectSolid(rand, x - 4, y + 4, z + 2, x - 4, y + 4, z + 2, walls);
		stair.setBlock(editor, new Coord(x - 4, y + 3, z + 2));
		stair.setBlock(editor, new Coord(x - 3, y + 4, z + 2));
		
		editor.fillRectSolid(rand, x - 7, y + 3, z - 1, x - 7, y + 5, z - 1, walls);
		editor.fillRectSolid(rand, x - 7, y + 3, z + 1, x - 7, y + 5, z + 1, walls);
		editor.fillRectSolid(rand, x - 7, y + 4, z, x - 7, y + 5, z, walls);
		walls.setBlock(editor, rand, new Coord(x - 6, y + 4, z - 1));
		walls.setBlock(editor, rand, new Coord(x - 6, y + 4, z + 1));
		stair.setBlock(editor, new Coord(x - 6, y + 3, z - 1));
		stair.setBlock(editor, new Coord(x - 6, y + 3, z + 1));
		stair.setBlock(editor, new Coord(x - 7, y + 3, z));
		stair.setBlock(editor, new Coord(x - 5, y + 4, z - 1));
		stair.setBlock(editor, new Coord(x - 5, y + 4, z + 1));
		stair.setBlock(editor, new Coord(x - 6, y + 4, z));
		stair.setBlock(editor, new Coord(x - 5, y + 5, z));
		
		stair.setOrientation(Cardinal.SOUTH, true);
		stair.setBlock(editor, new Coord(x - 7, y + 2, z - 1));
		stair.setBlock(editor, new Coord(x - 4, y + 5, z - 1));
		stair.setOrientation(Cardinal.NORTH, true);
		stair.setBlock(editor, new Coord(x - 7, y + 2, z + 1));
		stair.setBlock(editor, new Coord(x - 4, y + 5, z + 1));
		
		// east
		stair.setOrientation(Cardinal.WEST, true);
		editor.fillRectSolid(rand, x + 6, y, z - 2, x + 7, y + 5, z - 2, walls);
		editor.fillRectSolid(rand, x + 5, y + 3, z - 2, x + 5, y + 4, z - 2, walls);
		stair.setBlock(editor, new Coord(x + 5, y + 2, z - 2));
		editor.fillRectSolid(rand, x + 4, y + 4, z - 2, x + 4, y + 4, z - 2, walls);
		stair.setBlock(editor, new Coord(x + 4, y + 3, z - 2));
		stair.setBlock(editor, new Coord(x + 3, y + 4, z - 2));
		
		editor.fillRectSolid(rand, x + 6, y, z + 2, x + 7, y + 5, z + 2, walls);
		editor.fillRectSolid(rand, x + 5, y + 3, z + 2, x + 5, y + 4, z + 2, walls);
		stair.setBlock(editor, new Coord(x + 5, y + 2, z - 2));
		editor.fillRectSolid(rand, x + 4, y + 4, z + 2, x + 4, y + 4, z + 2, walls);
		stair.setBlock(editor, new Coord(x + 4, y + 3, z + 2));
		stair.setBlock(editor, new Coord(x + 3, y + 4, z + 2));
		
		editor.fillRectSolid(rand, x + 7, y + 3, z - 1, x + 7, y + 5, z - 1, walls);
		editor.fillRectSolid(rand, x + 7, y + 3, z + 1, x + 7, y + 5, z + 1, walls);
		editor.fillRectSolid(rand, x + 7, y + 4, z, x + 7, y + 5, z, walls);
		walls.setBlock(editor, rand, new Coord(x + 6, y + 4, z - 1));
		walls.setBlock(editor, rand, new Coord(x + 6, y + 4, z + 1));
		stair.setBlock(editor, new Coord(x + 6, y + 3, z - 1));
		stair.setBlock(editor, new Coord(x + 6, y + 3, z + 1));
		stair.setBlock(editor, new Coord(x + 7, y + 3, z));
		stair.setBlock(editor, new Coord(x + 5, y + 4, z - 1));
		stair.setBlock(editor, new Coord(x + 5, y + 4, z + 1));
		stair.setBlock(editor, new Coord(x + 6, y + 4, z));
		stair.setBlock(editor, new Coord(x + 5, y + 5, z));
		stair.setOrientation(Cardinal.SOUTH, true);
		stair.setBlock(editor, new Coord(x + 7, y + 2, z - 1));
		stair.setBlock(editor, new Coord(x + 4, y + 5, z - 1));
		stair.setOrientation(Cardinal.NORTH, true);
		stair.setBlock(editor, new Coord(x + 7, y + 2, z + 1));
		stair.setBlock(editor, new Coord(x + 4, y + 5, z + 1));
		
		// north
		stair.setOrientation(Cardinal.SOUTH, true);
		editor.fillRectSolid(rand, x - 2, y, z - 7, x - 2, y + 5, z - 6, walls);
		editor.fillRectSolid(rand, x - 2, y + 3, z - 5, x - 2, y + 4, z - 5, walls);
		stair.setBlock(editor, new Coord(x - 2, y + 2, z - 5));
		editor.fillRectSolid(rand, x - 2, y + 4, z - 4, x - 2, y + 4, z - 4, walls);
		stair.setBlock(editor, new Coord(x - 2, y + 3, z - 4));
		stair.setBlock(editor, new Coord(x - 2, y + 4, z - 3));
		
		editor.fillRectSolid(rand, x + 2, y, z - 7, x + 2, y + 5, z - 6, walls);
		editor.fillRectSolid(rand, x + 2, y + 3, z - 5, x + 2, y + 4, z - 5, walls);
		stair.setBlock(editor, new Coord(x + 2, y + 2, z - 5));
		editor.fillRectSolid(rand, x + 2, y + 4, z - 4, x + 2, y + 4, z - 4, walls);
		stair.setBlock(editor, new Coord(x + 2, y + 3, z - 4));
		stair.setBlock(editor, new Coord(x + 2, y + 4, z - 3));
		
		editor.fillRectSolid(rand, x - 1, y + 3, z - 7, x - 1, y + 5, z - 7, walls);
		editor.fillRectSolid(rand, x + 1, y + 3, z - 7, x + 1, y + 5, z - 7, walls);
		editor.fillRectSolid(rand, x, y + 4, z - 7, x, y + 5, z - 7, walls);
		walls.setBlock(editor, rand, new Coord(x - 1, y + 3, z - 6));
		walls.setBlock(editor, rand, new Coord(x + 1, y + 3, z - 6));
		stair.setBlock(editor, new Coord(x - 1, y + 3, z - 6));
		stair.setBlock(editor, new Coord(x + 1, y + 3, z - 6));
		stair.setBlock(editor, new Coord(x, y + 3, z - 7));
		stair.setBlock(editor, new Coord(x - 1, y + 4, z - 5));
		stair.setBlock(editor, new Coord(x + 1, y + 4, z - 5));
		stair.setBlock(editor, new Coord(x, y + 4, z - 6));
		stair.setBlock(editor, new Coord(x, y + 5, z - 5));
		stair.setOrientation(Cardinal.EAST, true);
		stair.setBlock(editor, new Coord(x - 1, y + 2, z - 7));
		stair.setBlock(editor, new Coord(x - 1, y + 5, z - 4));
		stair.setOrientation(Cardinal.WEST, true);
		stair.setBlock(editor, new Coord(x + 1, y + 2, z - 7));
		stair.setBlock(editor, new Coord(x + 1, y + 5, z - 4));
		
		// south
		stair.setOrientation(Cardinal.NORTH, true);
		editor.fillRectSolid(rand, x - 2, y, z + 6, x - 2, y + 5, z + 7, walls);
		editor.fillRectSolid(rand, x - 2, y + 3, z + 5, x - 2, y + 4, z + 5, walls);
		stair.setBlock(editor, new Coord(x - 2, y + 2, z + 5));
		editor.fillRectSolid(rand, x - 2, y + 4, z + 4, x - 2, y + 4, z + 4, walls);
		stair.setBlock(editor, new Coord(x - 2, y + 3, z + 4));
		stair.setBlock(editor, new Coord(x - 2, y + 4, z + 3));
		
		editor.fillRectSolid(rand, x + 2, y, z + 6, x + 2, y + 5, z + 7, walls);
		editor.fillRectSolid(rand, x + 2, y + 3, z + 5, x + 2, y + 4, z + 5, walls);
		stair.setBlock(editor, new Coord(x + 2, y + 2, z + 5));
		editor.fillRectSolid(rand, x + 2, y + 4, z + 4, x + 2, y + 4, z + 4, walls);
		stair.setBlock(editor, new Coord(x + 2, y + 3, z + 4));
		stair.setBlock(editor, new Coord(x + 2, y + 4, z + 3));
		
		editor.fillRectSolid(rand, x - 1, y + 3, z + 7, x - 1, y + 5, z + 7, walls);
		editor.fillRectSolid(rand, x + 1, y + 3, z + 7, x + 1, y + 5, z + 7, walls);
		editor.fillRectSolid(rand, x, y + 4, z + 7, x, y + 5, z + 7, walls);
		walls.setBlock(editor, rand, new Coord(x - 1, y + 4, z + 6));
		walls.setBlock(editor, rand, new Coord(x + 1, y + 4, z + 6));
		stair.setBlock(editor, new Coord(x - 1, y + 3, z + 6));
		stair.setBlock(editor, new Coord(x + 1, y + 3, z + 6));
		stair.setBlock(editor, new Coord(x, y + 3, z + 7));
		stair.setBlock(editor, new Coord(x - 1, y + 4, z + 5));
		stair.setBlock(editor, new Coord(x + 1, y + 4, z + 5));
		stair.setBlock(editor, new Coord(x, y + 4, z + 6));
		stair.setBlock(editor, new Coord(x, y + 5, z + 5));
		stair.setOrientation(Cardinal.EAST, true);
		stair.setBlock(editor, new Coord(x - 1, y + 2, z + 7));
		stair.setBlock(editor, new Coord(x - 1, y + 5, z + 4));
		stair.setOrientation(Cardinal.WEST, true);
		stair.setBlock(editor, new Coord(x + 1, y + 2, z + 7));
		stair.setBlock(editor, new Coord(x + 1, y + 5, z + 4));
		
		// ceiling
		editor.fillRectSolid(rand, x - 1, y + 5, z - 1, x + 1, y + 5, z + 1, air);
		editor.setBlock(x, y + 6, z, air);
		
		editor.setBlock(x - 2, y + 5, z, air);
		stair.setOrientation(Cardinal.WEST, true);
		stair.setBlock(editor, new Coord(x - 3, y + 5, z));
		stair.setBlock(editor, new Coord(x + 1, y + 6, z));
		stair.setBlock(editor, new Coord(x + 1, y + 5, z + 2));
		stair.setBlock(editor, new Coord(x + 1, y + 5, z - 2));
		editor.setBlock(x - 4, y + 5, z, air);
		
		editor.setBlock(x + 2, y + 5, z, air);
		stair.setOrientation(Cardinal.EAST, true);
		stair.setBlock(editor, new Coord(x + 3, y + 5, z));
		stair.setBlock(editor, new Coord(x - 1, y + 6, z));
		stair.setBlock(editor, new Coord(x - 1, y + 5, z + 2));
		stair.setBlock(editor, new Coord(x - 1, y + 5, z - 2));
		editor.setBlock(x + 4, y + 5, z, air);
		
		editor.setBlock(x, y + 5, z - 2, air);
		stair.setOrientation(Cardinal.NORTH, true);
		stair.setBlock(editor, new Coord(x, y + 5, z - 3));
		stair.setBlock(editor, new Coord(x, y + 6, z + 1));
		stair.setBlock(editor, new Coord(x + 2, y + 5, z + 1));
		stair.setBlock(editor, new Coord(x - 2, y + 5, z + 1));
		editor.setBlock(x, y + 5, z - 4, air);
		
		editor.setBlock(x, y + 5, z + 2, air);
		stair.setOrientation(Cardinal.SOUTH, true);
		stair.setBlock(editor, new Coord(x, y + 5, z + 3));
		stair.setBlock(editor, new Coord(x, y + 6, z - 1));
		stair.setBlock(editor, new Coord(x + 2, y + 5, z - 1));
		stair.setBlock(editor, new Coord(x - 2, y + 5, z - 1));
		
		
		// corners
		editor.fillRectSolid(rand, x - 7, y, z - 7, x - 6, y + 5, z - 6, walls);
		editor.fillRectSolid(rand, x - 6, y + 6, z - 6, x - 3, y + 6, z - 3, walls);
		stairCeiling(editor, rand, theme, x - 4, y + 5, z - 4);
		stairArch(editor, rand, theme, x - 6, y + 4, z - 4, Cardinal.EAST);
		stairArch(editor, rand, theme, x - 4, y + 4, z - 6, Cardinal.NORTH);
		
		editor.fillRectSolid(rand, x - 7, y, z + 6, x - 6, y + 5, z + 7, walls);
		editor.fillRectSolid(rand, x - 6, y + 6, z + 3, x - 3, y + 6, z + 6, walls);
		stairCeiling(editor, rand, theme, x - 4, y + 5, z + 4);
		stairArch(editor, rand, theme, x - 6, y + 4, z + 4, Cardinal.EAST);
		stairArch(editor, rand, theme, x - 4, y + 4, z + 6, Cardinal.NORTH);
		
		editor.fillRectSolid(rand, x + 6, y, z - 7, x + 7, y + 5, z - 6, walls);
		editor.fillRectSolid(rand, x + 3, y + 6, z - 6, x + 6, y + 6, z - 3, walls);
		stairCeiling(editor, rand, theme, x + 4, y + 5, z - 4);
		stairArch(editor, rand, theme, x + 6, y + 4, z - 4, Cardinal.EAST);
		stairArch(editor, rand, theme, x + 4, y + 4, z - 6, Cardinal.NORTH);
		
		editor.fillRectSolid(rand, x + 6, y, z + 6, x + 7, y + 5, z + 7, walls);
		editor.fillRectSolid(rand, x + 3, y + 6, z + 3, x + 6, y + 6, z + 6, walls);
		stairCeiling(editor, rand, theme, x + 4, y + 5, z + 4);
		stairArch(editor, rand, theme, x + 6, y + 4, z + 4, Cardinal.EAST);
		stairArch(editor, rand, theme, x + 4, y + 4, z + 6, Cardinal.NORTH);
				
		// shelves
		editor.fillRectSolid(rand, x - 5, y, z - 7, x - 3, y, z - 7, walls);
		placeSkull(editor, rand, x - 5, y + 1, z - 7, Cardinal.SOUTH);
		placeSkull(editor, rand, x - 3, y + 1, z - 7, Cardinal.SOUTH);
		editor.fillRectSolid(rand, x - 5, y + 2, z - 7, x - 3, y + 2, z - 7, walls);
		placeSkull(editor, rand, x - 5, y + 3, z - 7, Cardinal.SOUTH);
		placeSkull(editor, rand, x - 3, y + 3, z - 7, Cardinal.SOUTH);
		editor.fillRectSolid(rand, x - 5, y + 4, z - 7, x - 3, y + 5, z - 7, walls);
		editor.fillRectSolid(rand, x - 4, y, z - 7, x - 4, y + 4, z - 7, walls);
		
		editor.fillRectSolid(rand, x + 3, y, z - 7, x + 5, y, z - 7, walls);
		placeSkull(editor, rand, x + 5, y + 1, z - 7, Cardinal.SOUTH);
		placeSkull(editor, rand, x + 3, y + 1, z - 7, Cardinal.SOUTH);
		editor.fillRectSolid(rand, x + 3, y + 2, z - 7, x + 5, y + 2, z - 7, walls);
		placeSkull(editor, rand, x + 5, y + 3, z - 7, Cardinal.SOUTH);
		placeSkull(editor, rand, x + 3, y + 3, z - 7, Cardinal.SOUTH);
		editor.fillRectSolid(rand, x + 3, y + 4, z - 7, x + 5, y + 5, z - 7, walls);
		editor.fillRectSolid(rand, x + 4, y, z - 7, x + 4, y + 4, z - 7, walls);
		
		editor.fillRectSolid(rand, x - 5, y, z + 7, x - 3, y, z + 7, walls);
		placeSkull(editor, rand, x - 5, y + 1, z + 7, Cardinal.NORTH);
		placeSkull(editor, rand, x - 3, y + 1, z + 7, Cardinal.NORTH);
		editor.fillRectSolid(rand, x - 5, y + 2, z + 7, x - 3, y + 2, z + 7, walls);
		placeSkull(editor, rand, x - 5, y + 3, z + 7, Cardinal.NORTH);
		placeSkull(editor, rand, x - 3, y + 3, z + 7, Cardinal.NORTH);
		editor.fillRectSolid(rand, x - 5, y + 4, z + 7, x - 3, y + 5, z + 7, walls);
		editor.fillRectSolid(rand, x - 4, y, z + 7, x - 4, y + 4, z + 7, walls);
		
		editor.fillRectSolid(rand, x + 3, y, z + 7, x + 5, y, z + 7, walls);
		placeSkull(editor, rand, x + 5, y + 1, z + 7, Cardinal.NORTH);
		placeSkull(editor, rand, x + 3, y + 1, z + 7, Cardinal.NORTH);
		editor.fillRectSolid(rand, x + 3, y + 2, z + 7, x + 5, y + 2, z + 7, walls);
		placeSkull(editor, rand, x + 5, y + 3, z + 7, Cardinal.NORTH);
		placeSkull(editor, rand, x + 3, y + 3, z + 7, Cardinal.NORTH);
		editor.fillRectSolid(rand, x + 3, y + 4, z + 7, x + 5, y + 5, z + 7, walls);
		editor.fillRectSolid(rand, x + 4, y, z + 7, x + 4, y + 4, z + 7, walls);
		
		editor.fillRectSolid(rand, x - 7, y, z - 5, x - 7, y, z - 3, walls);
		placeSkull(editor, rand, x - 7, y + 1, z - 5, Cardinal.EAST);
		placeSkull(editor, rand, x - 7, y + 1, z - 3, Cardinal.EAST);
		editor.fillRectSolid(rand, x - 7, y + 2, z - 5, x - 7, y + 2, z - 3, walls);
		placeSkull(editor, rand, x - 7, y + 3, z - 5, Cardinal.EAST);
		placeSkull(editor, rand, x - 7, y + 3, z - 3, Cardinal.EAST);
		editor.fillRectSolid(rand, x - 7, y + 4, z - 5, x - 7, y + 5, z - 3, walls);
		editor.fillRectSolid(rand, x - 7, y, z - 4, x - 7, y + 4, z - 4, walls);
		
		editor.fillRectSolid(rand, x - 7, y, z + 3, x - 7, y, z + 5, walls);
		placeSkull(editor, rand, x - 7, y + 1, z + 5, Cardinal.EAST);
		placeSkull(editor, rand, x - 7, y + 1, z + 3, Cardinal.EAST);
		editor.fillRectSolid(rand, x - 7, y + 2, z + 3, x - 7, y + 2, z + 5, walls);
		placeSkull(editor, rand, x - 7, y + 3, z + 5, Cardinal.EAST);
		placeSkull(editor, rand, x - 7, y + 3, z + 3, Cardinal.EAST);
		editor.fillRectSolid(rand, x - 7, y + 4, z + 3, x - 7, y + 5, z + 5, walls);
		editor.fillRectSolid(rand, x - 7, y, z + 4, x - 7, y + 4, z + 4, walls);
		
		editor.fillRectSolid(rand, x + 7, y, z - 5, x + 7, y, z - 3, walls);
		placeSkull(editor, rand, x + 7, y + 1, z - 5, Cardinal.WEST);
		placeSkull(editor, rand, x + 7, y + 1, z - 3, Cardinal.WEST);
		editor.fillRectSolid(rand, x + 7, y + 2, z - 5, x + 7, y + 2, z - 3, walls);
		placeSkull(editor, rand, x + 7, y + 3, z - 5, Cardinal.WEST);
		placeSkull(editor, rand, x + 7, y + 3, z - 3, Cardinal.WEST);
		editor.fillRectSolid(rand, x + 7, y + 4, z - 5, x + 7, y + 5, z - 3, walls);
		editor.fillRectSolid(rand, x + 7, y, z - 4, x + 7, y + 4, z - 4, walls);
		
		editor.fillRectSolid(rand, x + 7, y, z + 3, x + 7, y, z + 5, walls);
		placeSkull(editor, rand, x + 7, y + 1, z + 5, Cardinal.WEST);
		placeSkull(editor, rand, x + 7, y + 1, z + 3, Cardinal.WEST);
		editor.fillRectSolid(rand, x + 7, y + 2, z + 3, x + 7, y + 2, z + 5, walls);
		placeSkull(editor, rand, x + 7, y + 3, z + 5, Cardinal.WEST);
		placeSkull(editor, rand, x + 7, y + 3, z + 3, Cardinal.WEST);
		editor.fillRectSolid(rand, x + 7, y + 4, z + 3, x + 7, y + 5, z + 5, walls);
		editor.fillRectSolid(rand, x + 7, y, z + 4, x + 7, y + 4, z + 4, walls);
		
		return false;
	}

	@Override
	public int getSize() {
		return 9;
	}
	
	private void stairCeiling(WorldEditor editor, Random rand, ITheme theme, int x, int y, int z){
		
		IStair stair = theme.getPrimaryStair();
		
		editor.setBlock(x, y, z, new MetaBlock(Blocks.air));
		stair.setOrientation(Cardinal.EAST, true);
		stair.setBlock(editor, new Coord(x - 1, y, z));
		stair.setOrientation(Cardinal.WEST, true);
		stair.setBlock(editor, new Coord(x + 1, y, z));
		
		stair.setOrientation(Cardinal.SOUTH, true);
		stair.setBlock(editor, new Coord(x, y, z - 1));
		stair.setOrientation(Cardinal.NORTH, true);
		stair.setBlock(editor, new Coord(x, y, z + 1));
		
	}
	
	private void stairArch(WorldEditor editor, Random rand, ITheme theme, int x, int y, int z, Cardinal dir){
		
		IStair stair = theme.getPrimaryStair();
		
		if(dir == Cardinal.NORTH || dir == Cardinal.SOUTH){
			stair.setOrientation(Cardinal.EAST, true);
			stair.setBlock(editor, new Coord(x - 1, y, z));
			stair.setOrientation(Cardinal.WEST, true);
			stair.setBlock(editor, new Coord(x + 1, y, z));
		} else {
			stair.setOrientation(Cardinal.SOUTH, true);
			stair.setBlock(editor, new Coord(x, y, z - 1));
			stair.setOrientation(Cardinal.NORTH, true);
			stair.setBlock(editor, new Coord(x, y, z + 1));
		}
	}
	
	private void placeSkull(WorldEditor editor, Random rand, int x, int y, int z, Cardinal dir){
		if(rand.nextBoolean()) return;
		Skull type = rand.nextInt(10) == 0 ? Skull.WITHER : Skull.SKELETON;
		Skull.set(editor, rand, x, y, z, dir, type);
	}
}
