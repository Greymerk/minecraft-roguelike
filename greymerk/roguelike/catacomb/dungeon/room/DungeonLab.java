package greymerk.roguelike.catacomb.dungeon.room;

import greymerk.roguelike.catacomb.dungeon.IDungeon;
import greymerk.roguelike.catacomb.theme.ITheme;
import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.BlockFlowerPot;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class DungeonLab implements IDungeon {

	@Override
	public boolean generate(World inWorld, Random inRandom, ITheme theme, int inOriginX, int inOriginY, int inOriginZ) {
	
		IBlockFactory blocks = theme.getPrimaryWall();
		
		
		MetaBlock air = new MetaBlock(0);
		// Air
		WorldGenPrimitive.fillRectSolid(inWorld, inRandom, inOriginX - 7, inOriginY, inOriginZ - 7, inOriginX + 7, inOriginY + 3, inOriginZ + 7, air);

		IBlockFactory roof = theme.getSecondaryWall();
		// Wood upper Roof
		WorldGenPrimitive.fillRectSolid(inWorld, inRandom, inOriginX - 6, inOriginY + 5, inOriginZ - 6, inOriginX + 6, inOriginY + 5, inOriginZ + 6, roof, true, true);
		WorldGenPrimitive.fillRectSolid(inWorld, inRandom, inOriginX - 1, inOriginY + 4, inOriginZ - 1, inOriginX + 1, inOriginY + 4, inOriginZ + 1, air);
		WorldGenPrimitive.fillRectSolid(inWorld, inRandom, inOriginX - 5, inOriginY + 4, inOriginZ - 1, inOriginX - 3, inOriginY + 4, inOriginZ + 1, air);
		WorldGenPrimitive.fillRectSolid(inWorld, inRandom, inOriginX + 3, inOriginY + 4, inOriginZ - 1, inOriginX + 5, inOriginY + 4, inOriginZ + 1, air);
		WorldGenPrimitive.fillRectSolid(inWorld, inRandom, inOriginX - 1, inOriginY + 4, inOriginZ - 5, inOriginX + 1, inOriginY + 4, inOriginZ - 3, air);
		WorldGenPrimitive.fillRectSolid(inWorld, inRandom, inOriginX - 1, inOriginY + 4, inOriginZ + 3, inOriginX + 1, inOriginY + 4, inOriginZ + 5, air);
		
		// shell
		WorldGenPrimitive.fillRectHollow(inWorld, inRandom, inOriginX - 8, inOriginY - 1, inOriginZ - 8, inOriginX + 8, inOriginY + 4, inOriginZ + 8, blocks, false, true);
		
		// corner rooms
		southWest(inWorld, inRandom, theme, inOriginX - 7, inOriginY, inOriginZ + 2);
		southEast(inWorld, inRandom, theme, inOriginX + 2, inOriginY, inOriginZ + 2);
		northWest(inWorld, inRandom, theme, inOriginX - 7, inOriginY, inOriginZ - 7);
		northEast(inWorld, inRandom, theme, inOriginX + 2, inOriginY, inOriginZ - 7);
		
		// outer walls
		WorldGenPrimitive.fillRectSolid(inWorld, inRandom, inOriginX - 8, inOriginY, inOriginZ - 7, inOriginX - 8, inOriginY + 3, inOriginZ - 7, blocks);
		WorldGenPrimitive.fillRectSolid(inWorld, inRandom, inOriginX + 8, inOriginY, inOriginZ - 7, inOriginX + 8, inOriginY + 3, inOriginZ - 7, blocks);
		WorldGenPrimitive.fillRectSolid(inWorld, inRandom, inOriginX + 8, inOriginY, inOriginZ - 7, inOriginX + 8, inOriginY + 3, inOriginZ - 7, blocks);
		
		IBlockFactory backWalls = theme.getSecondaryWall();
		
		// wall planks
		WorldGenPrimitive.fillRectSolid(inWorld, inRandom, inOriginX - 8, inOriginY + 1, inOriginZ - 6, inOriginX - 8, inOriginY + 3, inOriginZ - 3, backWalls, true, true);
		WorldGenPrimitive.fillRectSolid(inWorld, inRandom, inOriginX - 8, inOriginY + 1, inOriginZ + 3, inOriginX - 8, inOriginY + 3, inOriginZ + 6, backWalls, true, true);
		WorldGenPrimitive.fillRectSolid(inWorld, inRandom, inOriginX + 8, inOriginY + 1, inOriginZ - 6, inOriginX + 8, inOriginY + 3, inOriginZ - 3, backWalls, true, true);
		WorldGenPrimitive.fillRectSolid(inWorld, inRandom, inOriginX + 8, inOriginY + 1, inOriginZ + 3, inOriginX + 8, inOriginY + 3, inOriginZ + 6, backWalls, true, true);
		
		WorldGenPrimitive.fillRectSolid(inWorld, inRandom, inOriginX - 6, inOriginY + 1, inOriginZ - 8, inOriginX - 3, inOriginY + 3, inOriginZ - 8, backWalls, true, true);
		WorldGenPrimitive.fillRectSolid(inWorld, inRandom, inOriginX + 3, inOriginY + 1, inOriginZ - 8, inOriginX + 6, inOriginY + 3, inOriginZ - 8, backWalls, true, true);
		WorldGenPrimitive.fillRectSolid(inWorld, inRandom, inOriginX - 6, inOriginY + 1, inOriginZ + 8, inOriginX - 3, inOriginY + 3, inOriginZ + 8, backWalls, true, true);
		WorldGenPrimitive.fillRectSolid(inWorld, inRandom, inOriginX + 3, inOriginY + 1, inOriginZ + 8, inOriginX + 6, inOriginY + 3, inOriginZ + 8, backWalls, true, true);
		
		
		return false;
	}

	private static void corner(World world, Random rand, ITheme theme, int x, int y, int z){
		
		MetaBlock air = new MetaBlock(0);
		
		// pillars
		pillar(world, rand, theme, x, y, z);
		pillar(world, rand, theme, x + 5, y, z);
		pillar(world, rand, theme, x, y, z + 5);
		pillar(world, rand, theme, x + 5, y, z + 5);
		
		// tile floor
		WorldGenPrimitive.fillRectSolid(world,  rand, x, y - 1, z, x + 5, y - 1, z + 5, Block.stainedClay.blockID, 9, 2, true, true);
		WorldGenPrimitive.fillRectSolid(world,  rand, x + 1, y - 1, z + 2, x + 4, y - 1, z + 3, 43, 8, 2, true, true);
		WorldGenPrimitive.fillRectSolid(world,  rand, x + 2, y - 1, z + 1, x + 3, y - 1, z + 4, 43, 8, 2, true, true);
		
		// ceiling dome
		WorldGenPrimitive.fillRectSolid(world,  rand, x + 2, y + 4, z + 2, x + 3, y + 8, z + 3, air);
		WorldGenPrimitive.setBlock(world, x + 3, y + 4, z + 1, 0);
		WorldGenPrimitive.setBlock(world, x + 4, y + 4, z + 1, 0);
		WorldGenPrimitive.setBlock(world, x + 3, y + 4, z + 4, 0);
		WorldGenPrimitive.setBlock(world, x + 4, y + 4, z + 4, 0);
		
		WorldGenPrimitive.setBlock(world, x + 1, y + 4, z + 3, 0);
		WorldGenPrimitive.setBlock(world, x + 1, y + 4, z + 4, 0);
		WorldGenPrimitive.setBlock(world, x + 4, y + 4, z + 3, 0);
		WorldGenPrimitive.setBlock(world, x + 4, y + 4, z + 4, 0);
		
		WorldGenPrimitive.fillRectHollow(world, rand, x + 1, y + 4, z + 1, x + 4, y + 8, z + 4, Block.cobblestone.blockID, 0, 2, false, true);
		WorldGenPrimitive.fillRectSolid(world,  rand, x + 2, y + 8, z + 2, x + 3, y + 8, z + 3, air);
	}
	
	
	private static void southWest(World world, Random rand, ITheme theme, int x, int y, int z){
		
		corner(world, rand, theme, x, y, z);
		
		MetaBlock stair = theme.getSecondaryStair();
		stair.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.NORTH, true));
		WorldGenPrimitive.fillRectSolid(world, rand, x + 1, y, z + 5, x + 4, y, z + 5, stair, true, true);
		stair.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.EAST, true));
		WorldGenPrimitive.fillRectSolid(world, rand, x, y, z + 1, x, y, z + 4, stair, true, true);
		
		if(RogueConfig.getBoolean(RogueConfig.GENEROUS)){
			WorldGenPrimitive.setBlock(world, x, y + 1, z + 1, Block.brewingStand.blockID);
			WorldGenPrimitive.setBlock(world, x + 1, y + 1, z + 5, Block.brewingStand.blockID);
		}
		
		TreasureChest.generate(world, rand, x, y + 1, z + 4, TreasureChest.POTIONS);
		TreasureChest.generate(world, rand, x + 4, y + 1, z + 5, TreasureChest.POTIONS);
		
		
	}
	
	// fountains
	private static void southEast(World world, Random rand, ITheme theme, int x, int y, int z){
		
		MetaBlock stone = new MetaBlock(Block.stoneBrick.blockID, 0, 2);
		
		corner(world, rand, theme, x, y, z);
		
		WorldGenPrimitive.fillRectSolid(world, rand, x + 1, y, z + 5, x + 4, y, z + 5, new MetaBlock(Block.stoneBrick.blockID, 0, 2), true, true);
		WorldGenPrimitive.setBlock(world, x + 1, y + 1, z + 5, Block.stairsStoneBrick.blockID, Cardinal.getBlockMeta(Cardinal.WEST), 2, true, true);
		WorldGenPrimitive.setBlock(world, x + 2, y + 1, z + 5, Block.waterMoving.blockID);
		world.markBlockForUpdate(x + 2, y + 1, z + 5);
		WorldGenPrimitive.setBlock(world, x + 2, y + 2, z + 5, Block.stoneSingleSlab.blockID);
		WorldGenPrimitive.setBlock(world, x + 3, y + 1, z + 5, Block.stairsStoneBrick.blockID, Cardinal.getBlockMeta(Cardinal.EAST), 2, true, true);
		
		WorldGenPrimitive.fillRectSolid(world, rand, x + 5, y, z + 1, x + 5, y, z + 4, Block.stoneBrick.blockID, 0, 2, true, true);
		WorldGenPrimitive.setBlock(world, x + 5, y + 1, z + 1, Block.stairsStoneBrick.blockID, Cardinal.getBlockMeta(Cardinal.NORTH), 2, true, true);
		WorldGenPrimitive.setBlock(world, x + 5, y + 1, z + 2, Block.waterMoving.blockID);
		world.markBlockForUpdate(x + 5, y + 1, z + 2);
		WorldGenPrimitive.setBlock(world, x + 5, y + 2, z + 2, Block.stoneSingleSlab.blockID);
		WorldGenPrimitive.setBlock(world, x + 5, y + 1, z + 3, Block.stairsStoneBrick.blockID, Cardinal.getBlockMeta(Cardinal.SOUTH), 2, true, true);
		
		WorldGenPrimitive.fillRectSolid(world, rand, x + 3, y, z + 3, x + 4, y, z + 4, stone);
		WorldGenPrimitive.setBlock(world, x + 3, y + 1, x + 3, Block.torchWood.blockID);
		
		WorldGenPrimitive.setBlock(world, x + 4, y, z + 1, Block.stairsStoneBrick.blockID, Cardinal.getBlockMeta(Cardinal.NORTH), 2, true, true);
		WorldGenPrimitive.setBlock(world, x + 3, y, z + 2, Block.stairsStoneBrick.blockID, Cardinal.getBlockMeta(Cardinal.WEST), 2, true, true);
		WorldGenPrimitive.setBlock(world, x + 2, y, z + 3, Block.stairsStoneBrick.blockID, Cardinal.getBlockMeta(Cardinal.NORTH), 2, true, true);
		WorldGenPrimitive.setBlock(world, x + 1, y, z + 4, Block.stairsStoneBrick.blockID, Cardinal.getBlockMeta(Cardinal.WEST), 2, true, true);
		
		
	}
	
	private static void northWest(World world, Random rand, ITheme theme, int x, int y, int z){
		
		MetaBlock stone = new MetaBlock(Block.stoneBrick.blockID, 0, 2);
		
		corner(world, rand, theme, x, y, z);
		
		WorldGenPrimitive.setBlock(world, x + 1, y, z, Block.stoneBrick.blockID);
		WorldGenPrimitive.setBlock(world, x + 1, y + 1, z, Block.flowerPot.blockID, rand.nextInt(11) + 1, 2, true, true);
		WorldGenPrimitive.setBlock(world, x + 2, y, z, Block.tilledField.blockID);
		WorldGenPrimitive.setBlock(world, x + 2, y + 1, z, Block.carrot.blockID);
		WorldGenPrimitive.setBlock(world, x + 3, y, z, Block.tilledField.blockID);
		WorldGenPrimitive.setBlock(world, x + 3, y + 1, z, Block.carrot.blockID);
		WorldGenPrimitive.setBlock(world, x + 4, y, z, Block.stoneBrick.blockID);
		WorldGenPrimitive.setBlock(world, x + 4, y + 1, z, Block.flowerPot.blockID, rand.nextInt(11) + 1, 2, true, true);
		
		WorldGenPrimitive.setBlock(world, x, y, z + 1, Block.stoneBrick.blockID);
		WorldGenPrimitive.setBlock(world, x, y + 1, z + 1, Block.flowerPot.blockID, rand.nextInt(11) + 1, 2, true, true);
		WorldGenPrimitive.setBlock(world, x, y, z + 2, Block.slowSand.blockID);
		WorldGenPrimitive.setBlock(world, x, y + 1, z + 2, Block.netherStalk.blockID);
		WorldGenPrimitive.setBlock(world, x, y, z + 3, Block.slowSand.blockID);
		WorldGenPrimitive.setBlock(world, x, y + 1, z + 3, Block.netherStalk.blockID);
		WorldGenPrimitive.setBlock(world, x, y, z + 4, Block.stoneBrick.blockID);
		WorldGenPrimitive.setBlock(world, x, y + 1, z + 4, Block.flowerPot.blockID, rand.nextInt(11) + 1, 2, true, true);
		
		WorldGenPrimitive.setBlock(world, x + 1, y, z + 1, Block.stoneBrick.blockID);
		
		WorldGenPrimitive.fillRectSolid(world, rand, x + 2, y, z + 1, x + 4, y, z + 1, new MetaBlock(Block.stairsStoneBrick.blockID, Cardinal.getBlockMeta(Cardinal.SOUTH), 2), true, true);
		WorldGenPrimitive.fillRectSolid(world, rand, x + 1, y, z + 2, x + 1, y, z + 4, new MetaBlock(Block.stairsStoneBrick.blockID, Cardinal.getBlockMeta(Cardinal.EAST), 2), true, true);
		
		WorldGenPrimitive.setBlock(world, x + 2, y - 1, z + 2, Block.blockRedstone.blockID);
		WorldGenPrimitive.setBlock(world, x + 3, y - 1, z + 2, Block.redstoneLampActive.blockID);
		WorldGenPrimitive.setBlock(world, x + 2, y - 1, z + 3, Block.redstoneLampActive.blockID);
		
		WorldGenPrimitive.setBlock(world, x, y, z, Block.waterStill.blockID);
	}
	
	private static void northEast(World world, Random rand, ITheme theme, int x, int y, int z){
		
		MetaBlock stone = new MetaBlock(Block.stoneBrick.blockID, 0, 2);
		corner(world, rand, theme, x, y, z);
		
		WorldGenPrimitive.setBlock(world, x + 1, y, z, Block.stoneBrick.blockID);
		WorldGenPrimitive.setBlock(world, x + 1, y + 1, z, Block.flowerPot.blockID, rand.nextInt(11) + 1, 2, true, true);
		WorldGenPrimitive.setBlock(world, x + 2, y, z, Block.tilledField.blockID);
		WorldGenPrimitive.setBlock(world, x + 2, y + 1, z, Block.melonStem.blockID);
		WorldGenPrimitive.setBlock(world, x + 3, y, z, Block.tilledField.blockID);
		WorldGenPrimitive.setBlock(world, x + 4, y, z, Block.stoneBrick.blockID);
		WorldGenPrimitive.setBlock(world, x + 4, y + 1, z, Block.flowerPot.blockID, rand.nextInt(11) + 1, 2, true, true);
		
		WorldGenPrimitive.setBlock(world, x + 5, y, z + 1, Block.stoneBrick.blockID);
		WorldGenPrimitive.setBlock(world, x + 5, y + 1, z + 1, Block.flowerPot.blockID, rand.nextInt(11) + 1, 2, true, true);
		WorldGenPrimitive.setBlock(world, x + 5, y, z + 2, Block.tilledField.blockID);
		WorldGenPrimitive.setBlock(world, x + 5, y + 1, z + 2, Block.pumpkinStem.blockID);
		WorldGenPrimitive.setBlock(world, x + 5, y, z + 3, Block.tilledField.blockID);
		WorldGenPrimitive.setBlock(world, x + 5, y, z + 4, Block.stoneBrick.blockID);
		WorldGenPrimitive.setBlock(world, x + 5, y + 1, z + 4, Block.flowerPot.blockID, rand.nextInt(11) + 1, 2, true, true);
		
		WorldGenPrimitive.setBlock(world, x + 4, y, z + 1, Block.stoneBrick.blockID);
		
		WorldGenPrimitive.fillRectSolid(world, rand, x + 1, y, z + 1, x + 3, y, z + 1, new MetaBlock (Block.stairsStoneBrick.blockID, Cardinal.getBlockMeta(Cardinal.SOUTH), 2), true, true);
		WorldGenPrimitive.fillRectSolid(world, rand, x + 4, y, z + 2, x + 4, y, z + 4, new MetaBlock (Block.stairsStoneBrick.blockID, Cardinal.getBlockMeta(Cardinal.WEST), 2), true, true);
		
		WorldGenPrimitive.setBlock(world, x + 3, y - 1, z + 2, Block.blockRedstone.blockID);
		WorldGenPrimitive.setBlock(world, x + 2, y - 1, z + 2, Block.redstoneLampActive.blockID);
		WorldGenPrimitive.setBlock(world, x + 3, y - 1, z + 3, Block.redstoneLampActive.blockID);
		
		WorldGenPrimitive.setBlock(world, x + 5, y, z, Block.waterStill.blockID);
	}
	
	private static void pillar(World world, Random rand, ITheme theme, int x, int y, int z){
		
		WorldGenPrimitive.fillRectSolid(world, rand, x, y, z, x, y + 3, z, theme.getSecondaryPillar(), true, true);
		MetaBlock stair = theme.getSecondaryStair();
		stair.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.EAST, true));
		WorldGenPrimitive.setBlock(world, x + 1, y + 3, z, stair, true, true);
		stair.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.WEST, true));
		WorldGenPrimitive.setBlock(world, x - 1, y + 3, z, stair, true, true);
		stair.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.SOUTH, true));
		WorldGenPrimitive.setBlock(world, x, y + 3, z + 1, stair, true, true);
		stair.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.NORTH, true));
		WorldGenPrimitive.setBlock(world, x, y + 3, z - 1, stair, true, true);	
	}
	
	public int getSize(){
		return 9;
	}
	
}
