package greymerk.roguelike.catacomb.dungeon.room;

import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.TileEntitySkull;
import net.minecraft.src.World;
import greymerk.roguelike.catacomb.Catacomb;
import greymerk.roguelike.catacomb.dungeon.IDungeon;
import greymerk.roguelike.worldgen.BlockFactoryProvider;
import greymerk.roguelike.worldgen.BlockRandomizer;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

public class DungeonOssuary implements IDungeon {

	@Override
	public boolean generate(World world, Random rand, int x, int y, int z) {
			
		MetaBlock stair = getStair(y);
		
		IBlockFactory walls = BlockFactoryProvider.getRandomizer(Catacomb.getLevel(y), rand);
		WorldGenPrimitive.fillRectHollow(world, x - 8, y - 1, z - 8, x + 8, y + 6, z + 8, walls, false, true);
		WorldGenPrimitive.fillRectSolid(world, x - 7, y + 5, z - 7, x + 7, y + 5, z + 7, walls);
		
		// any missing floor
		BlockRandomizer cracked = new BlockRandomizer(rand, new MetaBlock(Block.stoneBrick.blockID, 2));
		cracked.addBlock(new MetaBlock(0), 3);
		cracked.addBlock(new MetaBlock(Block.cobblestone.blockID), 5);
		cracked.addBlock(new MetaBlock(Block.gravel.blockID), 5);
		
		WorldGenPrimitive.fillRectSolid(world, x - 7, y - 1, z - 7, x + 7, y - 1, z + 7, cracked, true, false);
		
		// arches
		// west
		stair.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.EAST, true));
		WorldGenPrimitive.fillRectSolid(world, x - 7, y, z - 2, x - 6, y + 4, z - 2, walls);
		WorldGenPrimitive.fillRectSolid(world, x - 5, y + 3, z - 2, x - 5, y + 4, z - 2, walls);
		WorldGenPrimitive.setBlock(world, x - 5, y + 2, z - 2, stair);
		WorldGenPrimitive.fillRectSolid(world, x - 4, y + 4, z - 2, x - 4, y + 4, z - 2, walls);
		WorldGenPrimitive.setBlock(world, x - 4, y + 3, z - 2, stair);
		WorldGenPrimitive.setBlock(world, x - 3, y + 4, z - 2, stair);
		
		WorldGenPrimitive.fillRectSolid(world, x - 7, y, z + 2, x - 6, y + 5, z + 2, walls);
		WorldGenPrimitive.fillRectSolid(world, x - 5, y + 3, z + 2, x - 5, y + 4, z + 2, walls);
		WorldGenPrimitive.setBlock(world, x - 5, y + 2, z + 2, stair);
		WorldGenPrimitive.fillRectSolid(world, x - 4, y + 4, z + 2, x - 4, y + 4, z + 2, walls);
		WorldGenPrimitive.setBlock(world, x - 4, y + 3, z + 2, stair);
		WorldGenPrimitive.setBlock(world, x - 3, y + 4, z + 2, stair);
		
		WorldGenPrimitive.fillRectSolid(world, x - 7, y + 3, z - 1, x - 7, y + 5, z - 1, walls);
		WorldGenPrimitive.fillRectSolid(world, x - 7, y + 3, z + 1, x - 7, y + 5, z + 1, walls);
		WorldGenPrimitive.fillRectSolid(world, x - 7, y + 4, z, x - 7, y + 5, z, walls);
		walls.setBlock(world, x - 6, y + 4, z - 1);
		walls.setBlock(world, x - 6, y + 4, z + 1);
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
		WorldGenPrimitive.fillRectSolid(world, x + 6, y, z - 2, x + 7, y + 5, z - 2, walls);
		WorldGenPrimitive.fillRectSolid(world, x + 5, y + 3, z - 2, x + 5, y + 4, z - 2, walls);
		WorldGenPrimitive.setBlock(world, x + 5, y + 2, z - 2, stair);
		WorldGenPrimitive.fillRectSolid(world, x + 4, y + 4, z - 2, x + 4, y + 4, z - 2, walls);
		WorldGenPrimitive.setBlock(world, x + 4, y + 3, z - 2, stair);
		WorldGenPrimitive.setBlock(world, x + 3, y + 4, z - 2, stair);
		
		WorldGenPrimitive.fillRectSolid(world, x + 6, y, z + 2, x + 7, y + 5, z + 2, walls);
		WorldGenPrimitive.fillRectSolid(world, x + 5, y + 3, z + 2, x + 5, y + 4, z + 2, walls);
		WorldGenPrimitive.setBlock(world, x + 5, y + 2, z - 2, stair);
		WorldGenPrimitive.fillRectSolid(world, x + 4, y + 4, z + 2, x + 4, y + 4, z + 2, walls);
		WorldGenPrimitive.setBlock(world, x + 4, y + 3, z + 2, stair);
		WorldGenPrimitive.setBlock(world, x + 3, y + 4, z + 2, stair);
		
		WorldGenPrimitive.fillRectSolid(world, x + 7, y + 3, z - 1, x + 7, y + 5, z - 1, walls);
		WorldGenPrimitive.fillRectSolid(world, x + 7, y + 3, z + 1, x + 7, y + 5, z + 1, walls);
		WorldGenPrimitive.fillRectSolid(world, x + 7, y + 4, z, x + 7, y + 5, z, walls);
		walls.setBlock(world, x + 6, y + 4, z - 1);
		walls.setBlock(world, x + 6, y + 4, z + 1);
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
		WorldGenPrimitive.fillRectSolid(world, x - 2, y, z - 7, x - 2, y + 5, z - 6, walls);
		WorldGenPrimitive.fillRectSolid(world, x - 2, y + 3, z - 5, x - 2, y + 4, z - 5, walls);
		WorldGenPrimitive.setBlock(world, x - 2, y + 2, z - 5, stair);
		WorldGenPrimitive.fillRectSolid(world, x - 2, y + 4, z - 4, x - 2, y + 4, z - 4, walls);
		WorldGenPrimitive.setBlock(world, x - 2, y + 3, z - 4, stair);
		WorldGenPrimitive.setBlock(world, x - 2, y + 4, z - 3, stair);
		
		WorldGenPrimitive.fillRectSolid(world, x + 2, y, z - 7, x + 2, y + 5, z - 6, walls);
		WorldGenPrimitive.fillRectSolid(world, x + 2, y + 3, z - 5, x + 2, y + 4, z - 5, walls);
		WorldGenPrimitive.setBlock(world, x + 2, y + 2, z - 5, stair);
		WorldGenPrimitive.fillRectSolid(world, x + 2, y + 4, z - 4, x + 2, y + 4, z - 4, walls);
		WorldGenPrimitive.setBlock(world, x + 2, y + 3, z - 4, stair);
		WorldGenPrimitive.setBlock(world, x + 2, y + 4, z - 3, stair);
		
		WorldGenPrimitive.fillRectSolid(world, x - 1, y + 3, z - 7, x - 1, y + 5, z - 7, walls);
		WorldGenPrimitive.fillRectSolid(world, x + 1, y + 3, z - 7, x + 1, y + 5, z - 7, walls);
		WorldGenPrimitive.fillRectSolid(world, x, y + 4, z - 7, x, y + 5, z - 7, walls);
		walls.setBlock(world, x - 1, y + 3, z - 6);
		walls.setBlock(world, x + 1, y + 3, z - 6);
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
		WorldGenPrimitive.fillRectSolid(world, x - 2, y, z + 6, x - 2, y + 5, z + 7, walls);
		WorldGenPrimitive.fillRectSolid(world, x - 2, y + 3, z + 5, x - 2, y + 4, z + 5, walls);
		WorldGenPrimitive.setBlock(world, x - 2, y + 2, z + 5, stair);
		WorldGenPrimitive.fillRectSolid(world, x - 2, y + 4, z + 4, x - 2, y + 4, z + 4, walls);
		WorldGenPrimitive.setBlock(world, x - 2, y + 3, z + 4, stair);
		WorldGenPrimitive.setBlock(world, x - 2, y + 4, z + 3, stair);
		
		WorldGenPrimitive.fillRectSolid(world, x + 2, y, z + 6, x + 2, y + 5, z + 7, walls);
		WorldGenPrimitive.fillRectSolid(world, x + 2, y + 3, z + 5, x + 2, y + 4, z + 5, walls);
		WorldGenPrimitive.setBlock(world, x + 2, y + 2, z + 5, stair);
		WorldGenPrimitive.fillRectSolid(world, x + 2, y + 4, z + 4, x + 2, y + 4, z + 4, walls);
		WorldGenPrimitive.setBlock(world, x + 2, y + 3, z + 4, stair);
		WorldGenPrimitive.setBlock(world, x + 2, y + 4, z + 3, stair);
		
		WorldGenPrimitive.fillRectSolid(world, x - 1, y + 3, z + 7, x - 1, y + 5, z + 7, walls);
		WorldGenPrimitive.fillRectSolid(world, x + 1, y + 3, z + 7, x + 1, y + 5, z + 7, walls);
		WorldGenPrimitive.fillRectSolid(world, x, y + 4, z + 7, x, y + 5, z + 7, walls);
		walls.setBlock(world, x - 1, y + 4, z + 6);
		walls.setBlock(world, x + 1, y + 4, z + 6);
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
		WorldGenPrimitive.fillRectSolid(world, x - 1, y + 5, z - 1, x + 1, y + 5, z + 1, 0);
		WorldGenPrimitive.setBlock(world, x, y + 6, z, 0);
		
		WorldGenPrimitive.setBlock(world, x - 2, y + 5, z, 0);
		stair.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.WEST, true));
		WorldGenPrimitive.setBlock(world, x - 3, y + 5, z, stair);
		WorldGenPrimitive.setBlock(world, x + 1, y + 6, z, stair);
		WorldGenPrimitive.setBlock(world, x + 1, y + 5, z + 2, stair);
		WorldGenPrimitive.setBlock(world, x + 1, y + 5, z - 2, stair);
		WorldGenPrimitive.setBlock(world, x - 4, y + 5, z, 0);
		
		WorldGenPrimitive.setBlock(world, x + 2, y + 5, z, 0);
		stair.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.EAST, true));
		WorldGenPrimitive.setBlock(world, x + 3, y + 5, z, stair);
		WorldGenPrimitive.setBlock(world, x - 1, y + 6, z, stair);
		WorldGenPrimitive.setBlock(world, x - 1, y + 5, z + 2, stair);
		WorldGenPrimitive.setBlock(world, x - 1, y + 5, z - 2, stair);
		WorldGenPrimitive.setBlock(world, x + 4, y + 5, z, 0);
		
		WorldGenPrimitive.setBlock(world, x, y + 5, z - 2, 0);
		stair.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.NORTH, true));
		WorldGenPrimitive.setBlock(world, x, y + 5, z - 3, stair);
		WorldGenPrimitive.setBlock(world, x, y + 6, z + 1, stair);
		WorldGenPrimitive.setBlock(world, x + 2, y + 5, z + 1, stair);
		WorldGenPrimitive.setBlock(world, x - 2, y + 5, z + 1, stair);
		WorldGenPrimitive.setBlock(world, x, y + 5, z - 4, 0);
		
		WorldGenPrimitive.setBlock(world, x, y + 5, z + 2, 0);
		stair.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.SOUTH, true));
		WorldGenPrimitive.setBlock(world, x, y + 5, z + 3, stair);
		WorldGenPrimitive.setBlock(world, x, y + 6, z - 1, stair);
		WorldGenPrimitive.setBlock(world, x + 2, y + 5, z - 1, stair);
		WorldGenPrimitive.setBlock(world, x - 2, y + 5, z - 1, stair);
		
		
		// corners
		WorldGenPrimitive.fillRectSolid(world, x - 7, y, z - 7, x - 6, y + 5, z - 6, walls);
		WorldGenPrimitive.fillRectSolid(world, x - 6, y + 6, z - 6, x - 3, y + 6, z - 3, walls);
		stairCeiling(world, rand, x - 4, y + 5, z - 4);
		stairArch(world, rand, x - 6, y + 4, z - 4, Cardinal.EAST);
		stairArch(world, rand, x - 4, y + 4, z - 6, Cardinal.NORTH);
		
		WorldGenPrimitive.fillRectSolid(world, x - 7, y, z + 6, x - 6, y + 5, z + 7, walls);
		WorldGenPrimitive.fillRectSolid(world, x - 6, y + 6, z + 3, x - 3, y + 6, z + 6, walls);
		stairCeiling(world, rand, x - 4, y + 5, z + 4);
		stairArch(world, rand, x - 6, y + 4, z + 4, Cardinal.EAST);
		stairArch(world, rand, x - 4, y + 4, z + 6, Cardinal.NORTH);
		
		WorldGenPrimitive.fillRectSolid(world, x + 6, y, z - 7, x + 7, y + 5, z - 6, walls);
		WorldGenPrimitive.fillRectSolid(world, x + 3, y + 6, z - 6, x + 6, y + 6, z - 3, walls);
		stairCeiling(world, rand, x + 4, y + 5, z - 4);
		stairArch(world, rand, x + 6, y + 4, z - 4, Cardinal.EAST);
		stairArch(world, rand, x + 4, y + 4, z - 6, Cardinal.NORTH);
		
		WorldGenPrimitive.fillRectSolid(world, x + 6, y, z + 6, x + 7, y + 5, z + 7, walls);
		WorldGenPrimitive.fillRectSolid(world, x + 3, y + 6, z + 3, x + 6, y + 6, z + 6, walls);
		stairCeiling(world, rand, x + 4, y + 5, z + 4);
		stairArch(world, rand, x + 6, y + 4, z + 4, Cardinal.EAST);
		stairArch(world, rand, x + 4, y + 4, z + 6, Cardinal.NORTH);
		
		// shelves
		WorldGenPrimitive.fillRectSolid(world, x - 5, y, z - 7, x - 3, y, z - 7, walls);
		skull(world, rand, x - 5, y + 1, z - 7, Cardinal.SOUTH);
		skull(world, rand, x - 3, y + 1, z - 7, Cardinal.SOUTH);
		WorldGenPrimitive.fillRectSolid(world, x - 5, y + 2, z - 7, x - 3, y + 2, z - 7, walls);
		skull(world, rand, x - 5, y + 3, z - 7, Cardinal.SOUTH);
		skull(world, rand, x - 3, y + 3, z - 7, Cardinal.SOUTH);
		WorldGenPrimitive.fillRectSolid(world, x - 5, y + 4, z - 7, x - 3, y + 5, z - 7, walls);
		WorldGenPrimitive.fillRectSolid(world, x - 4, y, z - 7, x - 4, y + 4, z - 7, walls);
		
		WorldGenPrimitive.fillRectSolid(world, x + 3, y, z - 7, x + 5, y, z - 7, walls);
		skull(world, rand, x + 5, y + 1, z - 7, Cardinal.SOUTH);
		skull(world, rand, x + 3, y + 1, z - 7, Cardinal.SOUTH);
		WorldGenPrimitive.fillRectSolid(world, x + 3, y + 2, z - 7, x + 5, y + 2, z - 7, walls);
		skull(world, rand, x + 5, y + 3, z - 7, Cardinal.SOUTH);
		skull(world, rand, x + 3, y + 3, z - 7, Cardinal.SOUTH);
		WorldGenPrimitive.fillRectSolid(world, x + 3, y + 4, z - 7, x + 5, y + 5, z - 7, walls);
		WorldGenPrimitive.fillRectSolid(world, x + 4, y, z - 7, x + 4, y + 4, z - 7, walls);
		
		WorldGenPrimitive.fillRectSolid(world, x - 5, y, z + 7, x - 3, y, z + 7, walls);
		skull(world, rand, x - 5, y + 1, z + 7, Cardinal.NORTH);
		skull(world, rand, x - 3, y + 1, z + 7, Cardinal.NORTH);
		WorldGenPrimitive.fillRectSolid(world, x - 5, y + 2, z + 7, x - 3, y + 2, z + 7, walls);
		skull(world, rand, x - 5, y + 3, z + 7, Cardinal.NORTH);
		skull(world, rand, x - 3, y + 3, z + 7, Cardinal.NORTH);
		WorldGenPrimitive.fillRectSolid(world, x - 5, y + 4, z + 7, x - 3, y + 5, z + 7, walls);
		WorldGenPrimitive.fillRectSolid(world, x - 4, y, z + 7, x - 4, y + 4, z + 7, walls);
		
		WorldGenPrimitive.fillRectSolid(world, x + 3, y, z + 7, x + 5, y, z + 7, walls);
		skull(world, rand, x + 5, y + 1, z + 7, Cardinal.NORTH);
		skull(world, rand, x + 3, y + 1, z + 7, Cardinal.NORTH);
		WorldGenPrimitive.fillRectSolid(world, x + 3, y + 2, z + 7, x + 5, y + 2, z + 7, walls);
		skull(world, rand, x + 5, y + 3, z + 7, Cardinal.NORTH);
		skull(world, rand, x + 3, y + 3, z + 7, Cardinal.NORTH);
		WorldGenPrimitive.fillRectSolid(world, x + 3, y + 4, z + 7, x + 5, y + 5, z + 7, walls);
		WorldGenPrimitive.fillRectSolid(world, x + 4, y, z + 7, x + 4, y + 4, z + 7, walls);
		
		WorldGenPrimitive.fillRectSolid(world, x - 7, y, z - 5, x - 7, y, z - 3, walls);
		skull(world, rand, x - 7, y + 1, z - 5, Cardinal.EAST);
		skull(world, rand, x - 7, y + 1, z - 3, Cardinal.EAST);
		WorldGenPrimitive.fillRectSolid(world, x - 7, y + 2, z - 5, x - 7, y + 2, z - 3, walls);
		skull(world, rand, x - 7, y + 3, z - 5, Cardinal.EAST);
		skull(world, rand, x - 7, y + 3, z - 3, Cardinal.EAST);
		WorldGenPrimitive.fillRectSolid(world, x - 7, y + 4, z - 5, x - 7, y + 5, z - 3, walls);
		WorldGenPrimitive.fillRectSolid(world, x - 7, y, z - 4, x - 7, y + 4, z - 4, walls);
		
		WorldGenPrimitive.fillRectSolid(world, x - 7, y, z + 3, x - 7, y, z + 5, walls);
		skull(world, rand, x - 7, y + 1, z + 5, Cardinal.EAST);
		skull(world, rand, x - 7, y + 1, z + 3, Cardinal.EAST);
		WorldGenPrimitive.fillRectSolid(world, x - 7, y + 2, z + 3, x - 7, y + 2, z + 5, walls);
		skull(world, rand, x - 7, y + 3, z + 5, Cardinal.EAST);
		skull(world, rand, x - 7, y + 3, z + 3, Cardinal.EAST);
		WorldGenPrimitive.fillRectSolid(world, x - 7, y + 4, z + 3, x - 7, y + 5, z + 5, walls);
		WorldGenPrimitive.fillRectSolid(world, x - 7, y, z + 4, x - 7, y + 4, z + 4, walls);
		
		WorldGenPrimitive.fillRectSolid(world, x + 7, y, z - 5, x + 7, y, z - 3, walls);
		skull(world, rand, x + 7, y + 1, z - 5, Cardinal.WEST);
		skull(world, rand, x + 7, y + 1, z - 3, Cardinal.WEST);
		WorldGenPrimitive.fillRectSolid(world, x + 7, y + 2, z - 5, x + 7, y + 2, z - 3, walls);
		skull(world, rand, x + 7, y + 3, z - 5, Cardinal.WEST);
		skull(world, rand, x + 7, y + 3, z - 3, Cardinal.WEST);
		WorldGenPrimitive.fillRectSolid(world, x + 7, y + 4, z - 5, x + 7, y + 5, z - 3, walls);
		WorldGenPrimitive.fillRectSolid(world, x + 7, y, z - 4, x + 7, y + 4, z - 4, walls);
		
		WorldGenPrimitive.fillRectSolid(world, x + 7, y, z + 3, x + 7, y, z + 5, walls);
		skull(world, rand, x + 7, y + 1, z + 5, Cardinal.WEST);
		skull(world, rand, x + 7, y + 1, z + 3, Cardinal.WEST);
		WorldGenPrimitive.fillRectSolid(world, x + 7, y + 2, z + 3, x + 7, y + 2, z + 5, walls);
		skull(world, rand, x + 7, y + 3, z + 5, Cardinal.WEST);
		skull(world, rand, x + 7, y + 3, z + 3, Cardinal.WEST);
		WorldGenPrimitive.fillRectSolid(world, x + 7, y + 4, z + 3, x + 7, y + 5, z + 5, walls);
		WorldGenPrimitive.fillRectSolid(world, x + 7, y, z + 4, x + 7, y + 4, z + 4, walls);
		
		return false;
	}

	@Override
	public int getSize() {
		return 9;
	}
	
	private void stairCeiling(World world, Random rand, int x, int y, int z){
		
		MetaBlock stair = getStair(y);
		
		WorldGenPrimitive.setBlock(world, x, y, z, 0);
		stair.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.EAST, true));
		WorldGenPrimitive.setBlock(world, x - 1, y, z, stair);
		stair.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.WEST, true));
		WorldGenPrimitive.setBlock(world, x + 1, y, z, stair);
		
		stair.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.SOUTH, true));
		WorldGenPrimitive.setBlock(world, x, y, z - 1, stair);
		stair.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.NORTH, true));
		WorldGenPrimitive.setBlock(world, x, y, z + 1, stair);
		
	}
	
	private void stairArch(World world, Random rand, int x, int y, int z, Cardinal dir){
		
		MetaBlock stair = getStair(y);
		
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
	
	private MetaBlock getStair(int y){
		return new MetaBlock(Catacomb.getLevel(y) < 3 ? Block.stairsStoneBrick.blockID : Block.stairsCobblestone.blockID);
	}
	
	private void skull(World world, Random rand, int x, int y, int z, Cardinal dir){
		
		if(rand.nextBoolean()){
			return;
		}
		
		MetaBlock skull = new MetaBlock(Block.skull.blockID, 1);
		
		WorldGenPrimitive.setBlock(world, x, y, z, skull, true, true);
		
		TileEntitySkull skullEntity;
		
		try{
			skullEntity = (TileEntitySkull) world.getBlockTileEntity(x, y, z);
		} catch (Exception e){
			return;
		}
		
		if(rand.nextInt(10) == 0){
			skullEntity.setSkullType(1, "");
		}
		
		switch(dir){
		case SOUTH: skullEntity.setSkullRotation(8);
		break;
		case NORTH: skullEntity.setSkullRotation(0);
		break;
		case WEST: skullEntity.setSkullRotation(12);
		break;
		case EAST: skullEntity.setSkullRotation(4);
		break;
		}
		
	}
}
