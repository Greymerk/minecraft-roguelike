package greymerk.roguelike.catacomb.dungeon.room;

import greymerk.roguelike.catacomb.dungeon.DungeonBase;
import greymerk.roguelike.catacomb.theme.ITheme;
import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.Log;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class DungeonsMusic extends DungeonBase {
	World world;
	Random rand;
	int originX;
	int originY;
	int originZ;
	
	public DungeonsMusic() {
	}

	public boolean generate(World inWorld, Random inRandom, ITheme theme, int inOriginX, int inOriginY, int inOriginZ) {
		world = inWorld;
		rand = inRandom;
		originX = inOriginX;
		originY = inOriginY;
		originZ = inOriginZ;

		MetaBlock air = new MetaBlock(Blocks.air);
		IBlockFactory wall = theme.getPrimaryWall();
		IBlockFactory deco = theme.getSecondaryWall();
		MetaBlock rug1 = new MetaBlock(Blocks.carpet, rand.nextInt(16));
		MetaBlock rug2 = new MetaBlock(Blocks.carpet, rand.nextInt(16));
		MetaBlock rug3 = new MetaBlock(Blocks.carpet, rand.nextInt(16));
		
		// fill air
		WorldGenPrimitive.fillRectSolid(world, inRandom, originX - 5, originY, originZ - 5, originX + 5, originY + 3, originZ + 5, air);
		
		// shell
		WorldGenPrimitive.fillRectHollow(world, rand, originX - 6, originY - 2, originZ - 6, originX + 6, originY + 5, originZ + 6, wall, false, true);
		
		// floor
		WorldGenPrimitive.fillRectSolid(world, rand, originX - 5, originY - 1, originZ - 5, originX + 5, originY - 1, originZ + 5, deco, true, true);
		WorldGenPrimitive.fillRectSolid(world, rand, originX - 3, originY, originZ - 3, originX + 3, originY, originZ + 3, rug1, true, true);
		WorldGenPrimitive.fillRectSolid(world, rand, originX - 2, originY, originZ - 2, originX + 2, originY, originZ + 2, rug2, true, true);
		WorldGenPrimitive.fillRectSolid(world, rand, originX - 1, originY, originZ - 1, originX + 1, originY, originZ + 1, rug3, true, true);
		
		//WALLS
		MetaBlock log = Log.getLog(Log.OAK);
		
		// vertical beams
		WorldGenPrimitive.fillRectSolid(world, inRandom, originX - 2, originY, originZ - 5, originX - 2, originY + 2, originZ - 5, log, true, true);
		WorldGenPrimitive.fillRectSolid(world, inRandom, originX + 2, originY, originZ - 5, originX + 2, originY + 2, originZ - 5, log, true, true);

		WorldGenPrimitive.fillRectSolid(world, inRandom, originX - 2, originY, originZ + 5, originX - 2, originY + 2, originZ + 5, log, true, true);
		WorldGenPrimitive.fillRectSolid(world, inRandom, originX + 2, originY, originZ + 5, originX + 2, originY + 2, originZ + 5, log, true, true);

		WorldGenPrimitive.fillRectSolid(world, rand, originX - 5, originY, originZ - 2, originX - 5, originY + 2, originZ - 2, log, true, true);
		WorldGenPrimitive.fillRectSolid(world, rand, originX - 5, originY, originZ + 2, originX - 5, originY + 2, originZ + 2, log, true, true);

		WorldGenPrimitive.fillRectSolid(world, rand, originX + 5, originY, originZ - 2, originX + 5, originY + 2, originZ - 2, log, true, true);
		WorldGenPrimitive.fillRectSolid(world, rand, originX + 5, originY, originZ + 2, originX + 5, originY + 2, originZ + 2, log, true, true);

		WorldGenPrimitive.fillRectSolid(world, rand, originX - 5, originY, originZ - 5, originX - 5, originY + 2, originZ - 5, log, true, true);
		WorldGenPrimitive.fillRectSolid(world, rand, originX - 5, originY, originZ + 5, originX - 5, originY + 2, originZ + 5, log, true, true);
		WorldGenPrimitive.fillRectSolid(world, rand, originX + 5, originY, originZ - 5, originX + 5, originY + 2, originZ - 5, log, true, true);
		WorldGenPrimitive.fillRectSolid(world, rand, originX + 5, originY, originZ + 5, originX + 5, originY + 2, originZ + 5, log, true, true);

		// shelves
		WorldGenPrimitive.fillRectSolid(world, rand, originX - 4, originY, originZ - 5, originX - 3, originY, originZ - 5, deco, true, true);
		WorldGenPrimitive.fillRectSolid(world, rand, originX + 3, originY, originZ - 5, originX + 4, originY, originZ - 5, deco, true, true);
		
		WorldGenPrimitive.fillRectSolid(world, rand, originX - 4, originY, originZ + 5, originX - 3, originY, originZ + 5, deco, true, true);
		WorldGenPrimitive.fillRectSolid(world, rand, originX + 3, originY, originZ + 5, originX + 4, originY, originZ + 5, deco, true, true);
		
		WorldGenPrimitive.fillRectSolid(world, rand, originX - 5, originY, originZ - 4, originX - 5, originY, originZ - 3, deco, true, true);
		WorldGenPrimitive.fillRectSolid(world, rand, originX - 5, originY, originZ + 3, originX - 5, originY, originZ + 4, deco, true, true);
		
		WorldGenPrimitive.fillRectSolid(world, rand, originX + 5, originY, originZ - 4, originX + 5, originY, originZ - 3, deco, true, true);
		WorldGenPrimitive.fillRectSolid(world, rand, originX + 5, originY, originZ + 3, originX + 5, originY, originZ + 4, deco, true, true);
		
		HashSet<Coord> chestSpace = new HashSet<Coord>();
		chestSpace.addAll(WorldGenPrimitive.getRectSolid(originX - 4, originY + 1, originZ - 5, originX - 3, originY + 1, originZ - 5));
		chestSpace.addAll(WorldGenPrimitive.getRectSolid(originX + 3, originY + 1, originZ - 5, originX + 4, originY + 1, originZ - 5));
		
		chestSpace.addAll(WorldGenPrimitive.getRectSolid(originX - 4, originY + 1, originZ + 5, originX - 3, originY + 1, originZ + 5));
		chestSpace.addAll(WorldGenPrimitive.getRectSolid(originX + 3, originY + 1, originZ + 5, originX + 4, originY + 1, originZ + 5));
		
		chestSpace.addAll(WorldGenPrimitive.getRectSolid(originX - 5, originY + 1, originZ - 4, originX - 5, originY + 1, originZ - 3));
		chestSpace.addAll(WorldGenPrimitive.getRectSolid(originX - 5, originY + 1, originZ + 3, originX - 5, originY + 1, originZ + 4));
		
		chestSpace.addAll(WorldGenPrimitive.getRectSolid(originX + 5, originY + 1, originZ - 4, originX + 5, originY + 1, originZ - 3));
		chestSpace.addAll(WorldGenPrimitive.getRectSolid(originX + 5, originY + 1, originZ + 3, originX + 5, originY + 1, originZ + 4));

		TreasureChest.generate(world, rand, new ArrayList<Coord>(chestSpace), TreasureChest.MUSIC);
		
		// horizontal beams
		WorldGenPrimitive.fillRectSolid(world, rand, originX - 5, originY + 3, originZ - 5, originX - 5, originY + 3, originZ + 5, log, true, true);
		WorldGenPrimitive.fillRectSolid(world, rand, originX - 5, originY + 3, originZ - 5, originX + 5, originY + 3, originZ - 5, log, true, true);
		WorldGenPrimitive.fillRectSolid(world, rand, originX - 5, originY + 3, originZ + 5, originX + 5, originY + 3, originZ + 5, log, true, true);
		WorldGenPrimitive.fillRectSolid(world, rand, originX + 5, originY + 3, originZ - 5, originX + 5, originY + 3, originZ + 5, log, true, true);
		
		// ceiling cross beams
		WorldGenPrimitive.fillRectSolid(world, rand, originX - 2, originY + 4, originZ - 5, originX - 2, originY + 4, originZ + 5, log, true, true);
		WorldGenPrimitive.fillRectSolid(world, rand, originX + 2, originY + 4, originZ - 5, originX + 2, originY + 4, originZ + 5, log, true, true);
		WorldGenPrimitive.fillRectSolid(world, rand, originX - 5, originY + 4, originZ - 2, originX + 5, originY + 4, originZ - 2, log, true, true);
		WorldGenPrimitive.fillRectSolid(world, rand, originX - 5, originY + 4, originZ + 2, originX + 5, originY + 4, originZ + 2, log, true, true);
		
		// ceiling lamp
		WorldGenPrimitive.setBlock(world, originX, originY + 4, originZ, Blocks.redstone_block);
		WorldGenPrimitive.setBlock(world, originX - 1, originY + 4, originZ, Blocks.redstone_lamp);
		WorldGenPrimitive.setBlock(world, originX + 1, originY + 4, originZ, Blocks.redstone_lamp);
		WorldGenPrimitive.setBlock(world, originX, originY + 4, originZ - 1, Blocks.redstone_lamp);
		WorldGenPrimitive.setBlock(world, originX, originY + 4, originZ + 1, Blocks.redstone_lamp);
		
		// ceiling fill
		WorldGenPrimitive.fillRectSolid(world, rand, originX - 5, originY + 4, originZ - 5, originX + 5, originY + 4, originZ + 5, deco, true, false);
		
		// music box
		WorldGenPrimitive.setBlock(world, originX, originY, originZ, Blocks.jukebox);
		
		return true;
	}
	
	public boolean isValidDungeonLocation(World world, int originX, int originY, int originZ) {
		return false;
	}

	public int getSize(){
		return 7;
	}
	
}
