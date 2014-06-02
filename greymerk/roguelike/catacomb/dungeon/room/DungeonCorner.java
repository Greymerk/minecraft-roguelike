package greymerk.roguelike.catacomb.dungeon.room;

import greymerk.roguelike.catacomb.Catacomb;
import greymerk.roguelike.catacomb.dungeon.IDungeon;
import greymerk.roguelike.catacomb.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.World;

public class DungeonCorner implements IDungeon {

	World world;
	Random rand;
	int originX;
	int originY;
	int originZ;
	
	@Override
	public boolean generate(World inWorld, Random inRandom, ITheme theme, int inOriginX, int inOriginY, int inOriginZ) {
		
		world = inWorld;
		rand = inRandom;
		originX = inOriginX;
		originY = inOriginY;
		originZ = inOriginZ;

		MetaBlock southStair = theme.getPrimaryStair();
		southStair.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.SOUTH, true));
		MetaBlock northStair = theme.getPrimaryStair();
		northStair.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.NORTH, true));
		MetaBlock eastStair = theme.getPrimaryStair();
		eastStair.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.EAST, true));
		MetaBlock westStair = theme.getPrimaryStair();
		westStair.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.WEST, true));
		
		IBlockFactory blocks = theme.getPrimaryWall();
		
		// fill air inside
		WorldGenPrimitive.fillRectSolid(world, 	originX - 2, originY, originZ - 2, originX + 2, originY + 3, originZ + 2, 0);
		
		// shell
		WorldGenPrimitive.fillRectHollow(world, originX - 3, originY - 1, originZ - 3, originX + 3, originY + 4, originZ + 3, blocks, false, true);
		
		// pillars
		WorldGenPrimitive.fillRectSolid(world, originX - 2, originY, originZ - 2, originX - 2, originY + 3, originZ - 2, blocks, true, true);
		WorldGenPrimitive.setBlock(world, originX - 2, originY + 3, originZ - 1, southStair, true, true);
		WorldGenPrimitive.setBlock(world, originX - 1, originY + 3, originZ - 2, eastStair, true, true);
		WorldGenPrimitive.fillRectSolid(world, originX - 2, originY, originZ + 2, originX - 2, originY + 3, originZ + 2, blocks, true, true);
		WorldGenPrimitive.setBlock(world, originX - 1, originY + 3, originZ + 2, eastStair, true, true);
		WorldGenPrimitive.setBlock(world, originX - 2, originY + 3, originZ + 1, northStair, true, true);
		WorldGenPrimitive.fillRectSolid(world, originX + 2, originY, originZ - 2, originX + 2, originY + 3, originZ - 2, blocks, true, true);
		WorldGenPrimitive.setBlock(world, originX + 1, originY + 3, originZ - 2, westStair, true, true);
		WorldGenPrimitive.setBlock(world, originX + 2, originY + 3, originZ - 1, southStair, true, true);
		WorldGenPrimitive.fillRectSolid(world, originX + 2, originY, originZ + 2, originX + 2, originY + 3, originZ + 2, blocks, true, true);
		WorldGenPrimitive.setBlock(world, originX + 1, originY + 3, originZ + 2, westStair, true, true);
		WorldGenPrimitive.setBlock(world, originX + 2, originY + 3, originZ + 1, northStair, true, true);
		
		WorldGenPrimitive.setBlock(world, originX - 1, originY + 4, originZ, eastStair, false, true);
		WorldGenPrimitive.setBlock(world, originX + 1, originY + 4, originZ, westStair, false, true);
		WorldGenPrimitive.setBlock(world, originX, originY + 4, originZ - 1, southStair, false, true);
		WorldGenPrimitive.setBlock(world, originX, originY + 4, originZ + 1, northStair, false, true);
		
		WorldGenPrimitive.setBlock(world, originX, originY + 4, originZ, 0);
		blocks.setBlock(world, originX, originY + 5, originZ, false, true);
		
		return true;
	}
	
	public int getSize(){
		return 2;
	}

}
