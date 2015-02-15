package greymerk.roguelike.catacomb.dungeon.room;

import greymerk.roguelike.catacomb.dungeon.DungeonBase;
import greymerk.roguelike.catacomb.settings.CatacombLevelSettings;
import greymerk.roguelike.catacomb.theme.ITheme;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.Skull;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class DungeonOssuary extends DungeonBase {

	@Override
	public boolean generate(World world, Random rand, CatacombLevelSettings settings, Cardinal[] entrances, Coord origin) {
		
		int x = origin.getX();
		int y = origin.getY();
		int z = origin.getZ();
		ITheme theme = settings.getTheme();
		
		MetaBlock stair = theme.getPrimaryStair();
		MetaBlock air = new MetaBlock(Blocks.air);
		
		IBlockFactory walls = theme.getPrimaryWall();
		WorldGenPrimitive.fillRectHollow(world, rand, x - 8, y - 1, z - 8, x + 8, y + 6, z + 8, walls, false, true);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 7, y + 5, z - 7, x + 7, y + 5, z + 7, walls);
		
		// any missing floor
		BlockWeightedRandom cracked = new BlockWeightedRandom();
		cracked.addBlock(new MetaBlock(Blocks.stonebrick, 2), 10);
		cracked.addBlock(air, 3);
		cracked.addBlock(new MetaBlock(Blocks.cobblestone), 5);
		cracked.addBlock(new MetaBlock(Blocks.gravel), 5);
		
		WorldGenPrimitive.fillRectSolid(world, rand, x - 7, y - 1, z - 7, x + 7, y - 1, z + 7, cracked, true, false);
		
		// arches
		// west
		stair.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.EAST, true));
		WorldGenPrimitive.fillRectSolid(world, rand, x - 7, y, z - 2, x - 6, y + 4, z - 2, walls);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 5, y + 3, z - 2, x - 5, y + 4, z - 2, walls);
		WorldGenPrimitive.setBlock(world, x - 5, y + 2, z - 2, stair);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 4, y + 4, z - 2, x - 4, y + 4, z - 2, walls);
		WorldGenPrimitive.setBlock(world, x - 4, y + 3, z - 2, stair);
		WorldGenPrimitive.setBlock(world, x - 3, y + 4, z - 2, stair);
		
		WorldGenPrimitive.fillRectSolid(world, rand, x - 7, y, z + 2, x - 6, y + 5, z + 2, walls);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 5, y + 3, z + 2, x - 5, y + 4, z + 2, walls);
		WorldGenPrimitive.setBlock(world, x - 5, y + 2, z + 2, stair);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 4, y + 4, z + 2, x - 4, y + 4, z + 2, walls);
		WorldGenPrimitive.setBlock(world, x - 4, y + 3, z + 2, stair);
		WorldGenPrimitive.setBlock(world, x - 3, y + 4, z + 2, stair);
		
		WorldGenPrimitive.fillRectSolid(world, rand, x - 7, y + 3, z - 1, x - 7, y + 5, z - 1, walls);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 7, y + 3, z + 1, x - 7, y + 5, z + 1, walls);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 7, y + 4, z, x - 7, y + 5, z, walls);
		walls.setBlock(world, rand, new Coord(x - 6, y + 4, z - 1));
		walls.setBlock(world, rand, new Coord(x - 6, y + 4, z + 1));
		WorldGenPrimitive.setBlock(world, x - 6, y + 3, z - 1, stair);
		WorldGenPrimitive.setBlock(world, x - 6, y + 3, z + 1, stair);
		WorldGenPrimitive.setBlock(world, x - 7, y + 3, z, stair);
		WorldGenPrimitive.setBlock(world, x - 5, y + 4, z - 1, stair);
		WorldGenPrimitive.setBlock(world, x - 5, y + 4, z + 1, stair);
		WorldGenPrimitive.setBlock(world, x - 6, y + 4, z, stair);
		WorldGenPrimitive.setBlock(world, x - 5, y + 5, z, stair);
		
		stair.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.SOUTH, true));
		WorldGenPrimitive.setBlock(world, x - 7, y + 2, z - 1, stair);
		WorldGenPrimitive.setBlock(world, x - 4, y + 5, z - 1, stair);
		stair.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.NORTH, true));
		WorldGenPrimitive.setBlock(world, x - 7, y + 2, z + 1, stair);
		WorldGenPrimitive.setBlock(world, x - 4, y + 5, z + 1, stair);
		
		// east
		stair.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.WEST, true));
		WorldGenPrimitive.fillRectSolid(world, rand, x + 6, y, z - 2, x + 7, y + 5, z - 2, walls);
		WorldGenPrimitive.fillRectSolid(world, rand, x + 5, y + 3, z - 2, x + 5, y + 4, z - 2, walls);
		WorldGenPrimitive.setBlock(world, x + 5, y + 2, z - 2, stair);
		WorldGenPrimitive.fillRectSolid(world, rand, x + 4, y + 4, z - 2, x + 4, y + 4, z - 2, walls);
		WorldGenPrimitive.setBlock(world, x + 4, y + 3, z - 2, stair);
		WorldGenPrimitive.setBlock(world, x + 3, y + 4, z - 2, stair);
		
		WorldGenPrimitive.fillRectSolid(world, rand, x + 6, y, z + 2, x + 7, y + 5, z + 2, walls);
		WorldGenPrimitive.fillRectSolid(world, rand, x + 5, y + 3, z + 2, x + 5, y + 4, z + 2, walls);
		WorldGenPrimitive.setBlock(world, x + 5, y + 2, z - 2, stair);
		WorldGenPrimitive.fillRectSolid(world, rand, x + 4, y + 4, z + 2, x + 4, y + 4, z + 2, walls);
		WorldGenPrimitive.setBlock(world, x + 4, y + 3, z + 2, stair);
		WorldGenPrimitive.setBlock(world, x + 3, y + 4, z + 2, stair);
		
		WorldGenPrimitive.fillRectSolid(world, rand, x + 7, y + 3, z - 1, x + 7, y + 5, z - 1, walls);
		WorldGenPrimitive.fillRectSolid(world, rand, x + 7, y + 3, z + 1, x + 7, y + 5, z + 1, walls);
		WorldGenPrimitive.fillRectSolid(world, rand, x + 7, y + 4, z, x + 7, y + 5, z, walls);
		walls.setBlock(world, rand, new Coord(x + 6, y + 4, z - 1));
		walls.setBlock(world, rand, new Coord(x + 6, y + 4, z + 1));
		WorldGenPrimitive.setBlock(world, x + 6, y + 3, z - 1, stair);
		WorldGenPrimitive.setBlock(world, x + 6, y + 3, z + 1, stair);
		WorldGenPrimitive.setBlock(world, x + 7, y + 3, z, stair);
		WorldGenPrimitive.setBlock(world, x + 5, y + 4, z - 1, stair);
		WorldGenPrimitive.setBlock(world, x + 5, y + 4, z + 1, stair);
		WorldGenPrimitive.setBlock(world, x + 6, y + 4, z, stair);
		WorldGenPrimitive.setBlock(world, x + 5, y + 5, z, stair);
		stair.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.SOUTH, true));
		WorldGenPrimitive.setBlock(world, x + 7, y + 2, z - 1, stair);
		WorldGenPrimitive.setBlock(world, x + 4, y + 5, z - 1, stair);
		stair.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.NORTH, true));
		WorldGenPrimitive.setBlock(world, x + 7, y + 2, z + 1, stair);
		WorldGenPrimitive.setBlock(world, x + 4, y + 5, z + 1, stair);
		
		// north
		stair.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.SOUTH, true));
		WorldGenPrimitive.fillRectSolid(world, rand, x - 2, y, z - 7, x - 2, y + 5, z - 6, walls);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 2, y + 3, z - 5, x - 2, y + 4, z - 5, walls);
		WorldGenPrimitive.setBlock(world, x - 2, y + 2, z - 5, stair);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 2, y + 4, z - 4, x - 2, y + 4, z - 4, walls);
		WorldGenPrimitive.setBlock(world, x - 2, y + 3, z - 4, stair);
		WorldGenPrimitive.setBlock(world, x - 2, y + 4, z - 3, stair);
		
		WorldGenPrimitive.fillRectSolid(world, rand, x + 2, y, z - 7, x + 2, y + 5, z - 6, walls);
		WorldGenPrimitive.fillRectSolid(world, rand, x + 2, y + 3, z - 5, x + 2, y + 4, z - 5, walls);
		WorldGenPrimitive.setBlock(world, x + 2, y + 2, z - 5, stair);
		WorldGenPrimitive.fillRectSolid(world, rand, x + 2, y + 4, z - 4, x + 2, y + 4, z - 4, walls);
		WorldGenPrimitive.setBlock(world, x + 2, y + 3, z - 4, stair);
		WorldGenPrimitive.setBlock(world, x + 2, y + 4, z - 3, stair);
		
		WorldGenPrimitive.fillRectSolid(world, rand, x - 1, y + 3, z - 7, x - 1, y + 5, z - 7, walls);
		WorldGenPrimitive.fillRectSolid(world, rand, x + 1, y + 3, z - 7, x + 1, y + 5, z - 7, walls);
		WorldGenPrimitive.fillRectSolid(world, rand, x, y + 4, z - 7, x, y + 5, z - 7, walls);
		walls.setBlock(world, rand, new Coord(x - 1, y + 3, z - 6));
		walls.setBlock(world, rand, new Coord(x + 1, y + 3, z - 6));
		WorldGenPrimitive.setBlock(world, x - 1, y + 3, z - 6, stair);
		WorldGenPrimitive.setBlock(world, x + 1, y + 3, z - 6, stair);
		WorldGenPrimitive.setBlock(world, x, y + 3, z - 7, stair);
		WorldGenPrimitive.setBlock(world, x - 1, y + 4, z - 5, stair);
		WorldGenPrimitive.setBlock(world, x + 1, y + 4, z - 5, stair);
		WorldGenPrimitive.setBlock(world, x, y + 4, z - 6, stair);
		WorldGenPrimitive.setBlock(world, x, y + 5, z - 5, stair);
		stair.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.EAST, true));
		WorldGenPrimitive.setBlock(world, x - 1, y + 2, z - 7, stair);
		WorldGenPrimitive.setBlock(world, x - 1, y + 5, z - 4, stair);
		stair.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.WEST, true));
		WorldGenPrimitive.setBlock(world, x + 1, y + 2, z - 7, stair);
		WorldGenPrimitive.setBlock(world, x + 1, y + 5, z - 4, stair);
		
		// south
		stair.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.NORTH, true));
		WorldGenPrimitive.fillRectSolid(world, rand, x - 2, y, z + 6, x - 2, y + 5, z + 7, walls);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 2, y + 3, z + 5, x - 2, y + 4, z + 5, walls);
		WorldGenPrimitive.setBlock(world, x - 2, y + 2, z + 5, stair);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 2, y + 4, z + 4, x - 2, y + 4, z + 4, walls);
		WorldGenPrimitive.setBlock(world, x - 2, y + 3, z + 4, stair);
		WorldGenPrimitive.setBlock(world, x - 2, y + 4, z + 3, stair);
		
		WorldGenPrimitive.fillRectSolid(world, rand, x + 2, y, z + 6, x + 2, y + 5, z + 7, walls);
		WorldGenPrimitive.fillRectSolid(world, rand, x + 2, y + 3, z + 5, x + 2, y + 4, z + 5, walls);
		WorldGenPrimitive.setBlock(world, x + 2, y + 2, z + 5, stair);
		WorldGenPrimitive.fillRectSolid(world, rand, x + 2, y + 4, z + 4, x + 2, y + 4, z + 4, walls);
		WorldGenPrimitive.setBlock(world, x + 2, y + 3, z + 4, stair);
		WorldGenPrimitive.setBlock(world, x + 2, y + 4, z + 3, stair);
		
		WorldGenPrimitive.fillRectSolid(world, rand, x - 1, y + 3, z + 7, x - 1, y + 5, z + 7, walls);
		WorldGenPrimitive.fillRectSolid(world, rand, x + 1, y + 3, z + 7, x + 1, y + 5, z + 7, walls);
		WorldGenPrimitive.fillRectSolid(world, rand, x, y + 4, z + 7, x, y + 5, z + 7, walls);
		walls.setBlock(world, rand, new Coord(x - 1, y + 4, z + 6));
		walls.setBlock(world, rand, new Coord(x + 1, y + 4, z + 6));
		WorldGenPrimitive.setBlock(world, x - 1, y + 3, z + 6, stair);
		WorldGenPrimitive.setBlock(world, x + 1, y + 3, z + 6, stair);
		WorldGenPrimitive.setBlock(world, x, y + 3, z + 7, stair);
		WorldGenPrimitive.setBlock(world, x - 1, y + 4, z + 5, stair);
		WorldGenPrimitive.setBlock(world, x + 1, y + 4, z + 5, stair);
		WorldGenPrimitive.setBlock(world, x, y + 4, z + 6, stair);
		WorldGenPrimitive.setBlock(world, x, y + 5, z + 5, stair);
		stair.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.EAST, true));
		WorldGenPrimitive.setBlock(world, x - 1, y + 2, z + 7, stair);
		WorldGenPrimitive.setBlock(world, x - 1, y + 5, z + 4, stair);
		stair.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.WEST, true));
		WorldGenPrimitive.setBlock(world, x + 1, y + 2, z + 7, stair);
		WorldGenPrimitive.setBlock(world, x + 1, y + 5, z + 4, stair);
		
		// ceiling
		WorldGenPrimitive.fillRectSolid(world, rand, x - 1, y + 5, z - 1, x + 1, y + 5, z + 1, air);
		WorldGenPrimitive.setBlock(world, x, y + 6, z, air);
		
		WorldGenPrimitive.setBlock(world, x - 2, y + 5, z, air);
		stair.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.WEST, true));
		WorldGenPrimitive.setBlock(world, x - 3, y + 5, z, stair);
		WorldGenPrimitive.setBlock(world, x + 1, y + 6, z, stair);
		WorldGenPrimitive.setBlock(world, x + 1, y + 5, z + 2, stair);
		WorldGenPrimitive.setBlock(world, x + 1, y + 5, z - 2, stair);
		WorldGenPrimitive.setBlock(world, x - 4, y + 5, z, air);
		
		WorldGenPrimitive.setBlock(world, x + 2, y + 5, z, air);
		stair.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.EAST, true));
		WorldGenPrimitive.setBlock(world, x + 3, y + 5, z, stair);
		WorldGenPrimitive.setBlock(world, x - 1, y + 6, z, stair);
		WorldGenPrimitive.setBlock(world, x - 1, y + 5, z + 2, stair);
		WorldGenPrimitive.setBlock(world, x - 1, y + 5, z - 2, stair);
		WorldGenPrimitive.setBlock(world, x + 4, y + 5, z, air);
		
		WorldGenPrimitive.setBlock(world, x, y + 5, z - 2, air);
		stair.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.NORTH, true));
		WorldGenPrimitive.setBlock(world, x, y + 5, z - 3, stair);
		WorldGenPrimitive.setBlock(world, x, y + 6, z + 1, stair);
		WorldGenPrimitive.setBlock(world, x + 2, y + 5, z + 1, stair);
		WorldGenPrimitive.setBlock(world, x - 2, y + 5, z + 1, stair);
		WorldGenPrimitive.setBlock(world, x, y + 5, z - 4, air);
		
		WorldGenPrimitive.setBlock(world, x, y + 5, z + 2, air);
		stair.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.SOUTH, true));
		WorldGenPrimitive.setBlock(world, x, y + 5, z + 3, stair);
		WorldGenPrimitive.setBlock(world, x, y + 6, z - 1, stair);
		WorldGenPrimitive.setBlock(world, x + 2, y + 5, z - 1, stair);
		WorldGenPrimitive.setBlock(world, x - 2, y + 5, z - 1, stair);
		
		
		// corners
		WorldGenPrimitive.fillRectSolid(world, rand, x - 7, y, z - 7, x - 6, y + 5, z - 6, walls);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 6, y + 6, z - 6, x - 3, y + 6, z - 3, walls);
		stairCeiling(world, rand, theme, x - 4, y + 5, z - 4);
		stairArch(world, rand, theme, x - 6, y + 4, z - 4, Cardinal.EAST);
		stairArch(world, rand, theme, x - 4, y + 4, z - 6, Cardinal.NORTH);
		
		WorldGenPrimitive.fillRectSolid(world, rand, x - 7, y, z + 6, x - 6, y + 5, z + 7, walls);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 6, y + 6, z + 3, x - 3, y + 6, z + 6, walls);
		stairCeiling(world, rand, theme, x - 4, y + 5, z + 4);
		stairArch(world, rand, theme, x - 6, y + 4, z + 4, Cardinal.EAST);
		stairArch(world, rand, theme, x - 4, y + 4, z + 6, Cardinal.NORTH);
		
		WorldGenPrimitive.fillRectSolid(world, rand, x + 6, y, z - 7, x + 7, y + 5, z - 6, walls);
		WorldGenPrimitive.fillRectSolid(world, rand, x + 3, y + 6, z - 6, x + 6, y + 6, z - 3, walls);
		stairCeiling(world, rand, theme, x + 4, y + 5, z - 4);
		stairArch(world, rand, theme, x + 6, y + 4, z - 4, Cardinal.EAST);
		stairArch(world, rand, theme, x + 4, y + 4, z - 6, Cardinal.NORTH);
		
		WorldGenPrimitive.fillRectSolid(world, rand, x + 6, y, z + 6, x + 7, y + 5, z + 7, walls);
		WorldGenPrimitive.fillRectSolid(world, rand, x + 3, y + 6, z + 3, x + 6, y + 6, z + 6, walls);
		stairCeiling(world, rand, theme, x + 4, y + 5, z + 4);
		stairArch(world, rand, theme, x + 6, y + 4, z + 4, Cardinal.EAST);
		stairArch(world, rand, theme, x + 4, y + 4, z + 6, Cardinal.NORTH);
				
		// shelves
		WorldGenPrimitive.fillRectSolid(world, rand, x - 5, y, z - 7, x - 3, y, z - 7, walls);
		placeSkull(world, rand, x - 5, y + 1, z - 7, Cardinal.SOUTH);
		placeSkull(world, rand, x - 3, y + 1, z - 7, Cardinal.SOUTH);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 5, y + 2, z - 7, x - 3, y + 2, z - 7, walls);
		placeSkull(world, rand, x - 5, y + 3, z - 7, Cardinal.SOUTH);
		placeSkull(world, rand, x - 3, y + 3, z - 7, Cardinal.SOUTH);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 5, y + 4, z - 7, x - 3, y + 5, z - 7, walls);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 4, y, z - 7, x - 4, y + 4, z - 7, walls);
		
		WorldGenPrimitive.fillRectSolid(world, rand, x + 3, y, z - 7, x + 5, y, z - 7, walls);
		placeSkull(world, rand, x + 5, y + 1, z - 7, Cardinal.SOUTH);
		placeSkull(world, rand, x + 3, y + 1, z - 7, Cardinal.SOUTH);
		WorldGenPrimitive.fillRectSolid(world, rand, x + 3, y + 2, z - 7, x + 5, y + 2, z - 7, walls);
		placeSkull(world, rand, x + 5, y + 3, z - 7, Cardinal.SOUTH);
		placeSkull(world, rand, x + 3, y + 3, z - 7, Cardinal.SOUTH);
		WorldGenPrimitive.fillRectSolid(world, rand, x + 3, y + 4, z - 7, x + 5, y + 5, z - 7, walls);
		WorldGenPrimitive.fillRectSolid(world, rand, x + 4, y, z - 7, x + 4, y + 4, z - 7, walls);
		
		WorldGenPrimitive.fillRectSolid(world, rand, x - 5, y, z + 7, x - 3, y, z + 7, walls);
		placeSkull(world, rand, x - 5, y + 1, z + 7, Cardinal.NORTH);
		placeSkull(world, rand, x - 3, y + 1, z + 7, Cardinal.NORTH);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 5, y + 2, z + 7, x - 3, y + 2, z + 7, walls);
		placeSkull(world, rand, x - 5, y + 3, z + 7, Cardinal.NORTH);
		placeSkull(world, rand, x - 3, y + 3, z + 7, Cardinal.NORTH);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 5, y + 4, z + 7, x - 3, y + 5, z + 7, walls);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 4, y, z + 7, x - 4, y + 4, z + 7, walls);
		
		WorldGenPrimitive.fillRectSolid(world, rand, x + 3, y, z + 7, x + 5, y, z + 7, walls);
		placeSkull(world, rand, x + 5, y + 1, z + 7, Cardinal.NORTH);
		placeSkull(world, rand, x + 3, y + 1, z + 7, Cardinal.NORTH);
		WorldGenPrimitive.fillRectSolid(world, rand, x + 3, y + 2, z + 7, x + 5, y + 2, z + 7, walls);
		placeSkull(world, rand, x + 5, y + 3, z + 7, Cardinal.NORTH);
		placeSkull(world, rand, x + 3, y + 3, z + 7, Cardinal.NORTH);
		WorldGenPrimitive.fillRectSolid(world, rand, x + 3, y + 4, z + 7, x + 5, y + 5, z + 7, walls);
		WorldGenPrimitive.fillRectSolid(world, rand, x + 4, y, z + 7, x + 4, y + 4, z + 7, walls);
		
		WorldGenPrimitive.fillRectSolid(world, rand, x - 7, y, z - 5, x - 7, y, z - 3, walls);
		placeSkull(world, rand, x - 7, y + 1, z - 5, Cardinal.EAST);
		placeSkull(world, rand, x - 7, y + 1, z - 3, Cardinal.EAST);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 7, y + 2, z - 5, x - 7, y + 2, z - 3, walls);
		placeSkull(world, rand, x - 7, y + 3, z - 5, Cardinal.EAST);
		placeSkull(world, rand, x - 7, y + 3, z - 3, Cardinal.EAST);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 7, y + 4, z - 5, x - 7, y + 5, z - 3, walls);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 7, y, z - 4, x - 7, y + 4, z - 4, walls);
		
		WorldGenPrimitive.fillRectSolid(world, rand, x - 7, y, z + 3, x - 7, y, z + 5, walls);
		placeSkull(world, rand, x - 7, y + 1, z + 5, Cardinal.EAST);
		placeSkull(world, rand, x - 7, y + 1, z + 3, Cardinal.EAST);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 7, y + 2, z + 3, x - 7, y + 2, z + 5, walls);
		placeSkull(world, rand, x - 7, y + 3, z + 5, Cardinal.EAST);
		placeSkull(world, rand, x - 7, y + 3, z + 3, Cardinal.EAST);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 7, y + 4, z + 3, x - 7, y + 5, z + 5, walls);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 7, y, z + 4, x - 7, y + 4, z + 4, walls);
		
		WorldGenPrimitive.fillRectSolid(world, rand, x + 7, y, z - 5, x + 7, y, z - 3, walls);
		placeSkull(world, rand, x + 7, y + 1, z - 5, Cardinal.WEST);
		placeSkull(world, rand, x + 7, y + 1, z - 3, Cardinal.WEST);
		WorldGenPrimitive.fillRectSolid(world, rand, x + 7, y + 2, z - 5, x + 7, y + 2, z - 3, walls);
		placeSkull(world, rand, x + 7, y + 3, z - 5, Cardinal.WEST);
		placeSkull(world, rand, x + 7, y + 3, z - 3, Cardinal.WEST);
		WorldGenPrimitive.fillRectSolid(world, rand, x + 7, y + 4, z - 5, x + 7, y + 5, z - 3, walls);
		WorldGenPrimitive.fillRectSolid(world, rand, x + 7, y, z - 4, x + 7, y + 4, z - 4, walls);
		
		WorldGenPrimitive.fillRectSolid(world, rand, x + 7, y, z + 3, x + 7, y, z + 5, walls);
		placeSkull(world, rand, x + 7, y + 1, z + 5, Cardinal.WEST);
		placeSkull(world, rand, x + 7, y + 1, z + 3, Cardinal.WEST);
		WorldGenPrimitive.fillRectSolid(world, rand, x + 7, y + 2, z + 3, x + 7, y + 2, z + 5, walls);
		placeSkull(world, rand, x + 7, y + 3, z + 5, Cardinal.WEST);
		placeSkull(world, rand, x + 7, y + 3, z + 3, Cardinal.WEST);
		WorldGenPrimitive.fillRectSolid(world, rand, x + 7, y + 4, z + 3, x + 7, y + 5, z + 5, walls);
		WorldGenPrimitive.fillRectSolid(world, rand, x + 7, y, z + 4, x + 7, y + 4, z + 4, walls);
		
		return false;
	}

	@Override
	public int getSize() {
		return 9;
	}
	
	private void stairCeiling(World world, Random rand, ITheme theme, int x, int y, int z){
		
		MetaBlock stair = theme.getPrimaryStair();
		
		WorldGenPrimitive.setBlock(world, x, y, z, new MetaBlock(Blocks.air));
		stair.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.EAST, true));
		WorldGenPrimitive.setBlock(world, x - 1, y, z, stair);
		stair.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.WEST, true));
		WorldGenPrimitive.setBlock(world, x + 1, y, z, stair);
		
		stair.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.SOUTH, true));
		WorldGenPrimitive.setBlock(world, x, y, z - 1, stair);
		stair.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.NORTH, true));
		WorldGenPrimitive.setBlock(world, x, y, z + 1, stair);
		
	}
	
	private void stairArch(World world, Random rand, ITheme theme, int x, int y, int z, Cardinal dir){
		
		MetaBlock stair = theme.getPrimaryStair();
		
		if(dir == Cardinal.NORTH || dir == Cardinal.SOUTH){
			stair.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.EAST, true));
			WorldGenPrimitive.setBlock(world, x - 1, y, z, stair);
			stair.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.WEST, true));
			WorldGenPrimitive.setBlock(world, x + 1, y, z, stair);
		} else {
			stair.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.SOUTH, true));
			WorldGenPrimitive.setBlock(world, x, y, z - 1, stair);
			stair.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.NORTH, true));
			WorldGenPrimitive.setBlock(world, x, y, z + 1, stair);
		}
	}
	
	private void placeSkull(World world, Random rand, int x, int y, int z, Cardinal dir){
		if(rand.nextBoolean()) return;
		Skull type = rand.nextInt(10) == 0 ? Skull.WITHER : Skull.SKELETON;
		Skull.set(world, rand, x, y, z, dir, type);
	}
}
