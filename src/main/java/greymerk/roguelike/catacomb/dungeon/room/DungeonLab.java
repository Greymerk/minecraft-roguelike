package greymerk.roguelike.catacomb.dungeon.room;

import greymerk.roguelike.catacomb.dungeon.DungeonBase;
import greymerk.roguelike.catacomb.settings.CatacombLevelSettings;
import greymerk.roguelike.catacomb.theme.ITheme;
import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.treasure.loot.LootSettings;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.FlowerPot;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class DungeonLab extends DungeonBase {

	@Override
	public boolean generate(World inWorld, Random inRandom, CatacombLevelSettings settings, Cardinal[] entrances, int inOriginX, int inOriginY, int inOriginZ) {
	
		ITheme theme = settings.getTheme();
		
		IBlockFactory blocks = theme.getPrimaryWall();
		
		
		MetaBlock air = new MetaBlock(Blocks.air);
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
		southWest(inWorld, inRandom, settings.getLoot(), theme, inOriginX - 7, inOriginY, inOriginZ + 2);
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
		
		MetaBlock air = new MetaBlock(Blocks.air);
		MetaBlock doubleSlab = new MetaBlock(Blocks.double_stone_slab, 8);
		MetaBlock cobble = new MetaBlock(Blocks.cobblestone);
		
		// pillars
		pillar(world, rand, theme, x, y, z);
		pillar(world, rand, theme, x + 5, y, z);
		pillar(world, rand, theme, x, y, z + 5);
		pillar(world, rand, theme, x + 5, y, z + 5);
		
		// tile floor
		WorldGenPrimitive.fillRectSolid(world,  rand, x, y - 1, z, x + 5, y - 1, z + 5, new MetaBlock(Blocks.stained_hardened_clay, 9, 2), true, true);
		WorldGenPrimitive.fillRectSolid(world,  rand, x + 1, y - 1, z + 2, x + 4, y - 1, z + 3, doubleSlab, true, true);
		WorldGenPrimitive.fillRectSolid(world,  rand, x + 2, y - 1, z + 1, x + 3, y - 1, z + 4, doubleSlab, true, true);
		
		// ceiling dome
		WorldGenPrimitive.fillRectSolid(world,  rand, x + 2, y + 4, z + 2, x + 3, y + 8, z + 3, air);
		WorldGenPrimitive.setBlock(world, x + 3, y + 4, z + 1, air);
		WorldGenPrimitive.setBlock(world, x + 4, y + 4, z + 1, air);
		WorldGenPrimitive.setBlock(world, x + 3, y + 4, z + 4, air);
		WorldGenPrimitive.setBlock(world, x + 4, y + 4, z + 4, air);
		
		WorldGenPrimitive.setBlock(world, x + 1, y + 4, z + 3, air);
		WorldGenPrimitive.setBlock(world, x + 1, y + 4, z + 4, air);
		WorldGenPrimitive.setBlock(world, x + 4, y + 4, z + 3, air);
		WorldGenPrimitive.setBlock(world, x + 4, y + 4, z + 4, air);
		
		WorldGenPrimitive.fillRectHollow(world, rand, x + 1, y + 4, z + 1, x + 4, y + 8, z + 4, cobble, false, true);
		WorldGenPrimitive.fillRectSolid(world,  rand, x + 2, y + 8, z + 2, x + 3, y + 8, z + 3, air);
	}
	
	
	private static void southWest(World world, Random rand, LootSettings loot, ITheme theme, int x, int y, int z){
		
		corner(world, rand, theme, x, y, z);
		
		MetaBlock stair = theme.getSecondaryStair();
		stair.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.NORTH, true));
		WorldGenPrimitive.fillRectSolid(world, rand, x + 1, y, z + 5, x + 4, y, z + 5, stair, true, true);
		stair.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.EAST, true));
		WorldGenPrimitive.fillRectSolid(world, rand, x, y, z + 1, x, y, z + 4, stair, true, true);
		
		if(RogueConfig.getBoolean(RogueConfig.GENEROUS)){
			WorldGenPrimitive.setBlock(world, x + 1, y + 1, z + 5, Blocks.brewing_stand);
		}
		
		TreasureChest.generate(world, rand, loot, x, y + 1, z + 4, TreasureChest.POTIONS);
		
		
	}
	
	// fountains
	private static void southEast(World world, Random rand, ITheme theme, int x, int y, int z){
		
		MetaBlock stone = new MetaBlock(Blocks.stonebrick);
		MetaBlock stair = new MetaBlock(Blocks.stone_brick_stairs);
		MetaBlock slab = new MetaBlock(Blocks.stone_slab, 5);
		
		corner(world, rand, theme, x, y, z);
		
		WorldGenPrimitive.fillRectSolid(world, rand, x + 1, y, z + 5, x + 4, y, z + 5, new MetaBlock(Blocks.stonebrick, 0, 2), true, true);
		WorldGenPrimitive.setBlock(world, x + 1, y + 1, z + 5, WorldGenPrimitive.blockOrientation(stair, Cardinal.WEST, false));
		WorldGenPrimitive.setBlock(world, x + 2, y + 1, z + 5, Blocks.flowing_water);
		world.markBlockForUpdate(x + 2, y + 1, z + 5);
		WorldGenPrimitive.setBlock(world, x + 2, y + 2, z + 5, slab);
		WorldGenPrimitive.setBlock(world, x + 3, y + 1, z + 5, WorldGenPrimitive.blockOrientation(stair, Cardinal.EAST, false));
		
		WorldGenPrimitive.fillRectSolid(world, rand, x + 5, y, z + 1, x + 5, y, z + 4, stone);
		WorldGenPrimitive.setBlock(world, x + 5, y + 1, z + 1, WorldGenPrimitive.blockOrientation(stair, Cardinal.NORTH, false));
		WorldGenPrimitive.setBlock(world, x + 5, y + 1, z + 2, Blocks.flowing_water);
		world.markBlockForUpdate(x + 5, y + 1, z + 2);
		WorldGenPrimitive.setBlock(world, x + 5, y + 2, z + 2, slab);
		WorldGenPrimitive.setBlock(world, x + 5, y + 1, z + 3, WorldGenPrimitive.blockOrientation(stair, Cardinal.SOUTH, false));
		
		WorldGenPrimitive.fillRectSolid(world, rand, x + 3, y, z + 3, x + 4, y, z + 4, stone);
		WorldGenPrimitive.setBlock(world, x + 3, y + 1, x + 3, Blocks.torch);
		
		WorldGenPrimitive.setBlock(world, rand, x + 4, y, z + 1, WorldGenPrimitive.blockOrientation(stair, Cardinal.NORTH, false), true, true);
		WorldGenPrimitive.setBlock(world, rand, x + 3, y, z + 2, WorldGenPrimitive.blockOrientation(stair, Cardinal.WEST, false), true, true);
		WorldGenPrimitive.setBlock(world, rand, x + 2, y, z + 3, WorldGenPrimitive.blockOrientation(stair, Cardinal.NORTH, false), true, true);
		WorldGenPrimitive.setBlock(world, rand, x + 1, y, z + 4, WorldGenPrimitive.blockOrientation(stair, Cardinal.WEST, false), true, true);
		
		
	}
	
	private static void northWest(World world, Random rand, ITheme theme, int x, int y, int z){
		
		corner(world, rand, theme, x, y, z);
		
		WorldGenPrimitive.setBlock(world, x + 1, y, z, Blocks.stonebrick);
		FlowerPot.generate(world, rand, new Coord(x + 1, y + 1, z));
		WorldGenPrimitive.setBlock(world, x + 2, y, z, Blocks.farmland);
		WorldGenPrimitive.setBlock(world, x + 2, y + 1, z, Blocks.carrots);
		WorldGenPrimitive.setBlock(world, x + 3, y, z, Blocks.farmland);
		WorldGenPrimitive.setBlock(world, x + 3, y + 1, z, Blocks.carrots);
		WorldGenPrimitive.setBlock(world, x + 4, y, z, Blocks.stonebrick);
		FlowerPot.generate(world, rand, new Coord(x + 4, y + 1, z));
		
		WorldGenPrimitive.setBlock(world, x, y, z + 1, Blocks.stonebrick);
		FlowerPot.generate(world, rand, new Coord(x, y + 1, z + 1));
		WorldGenPrimitive.setBlock(world, x, y, z + 2, Blocks.soul_sand);
		WorldGenPrimitive.setBlock(world, x, y + 1, z + 2, Blocks.nether_wart);
		WorldGenPrimitive.setBlock(world, x, y, z + 3, Blocks.soul_sand);
		WorldGenPrimitive.setBlock(world, x, y + 1, z + 3, Blocks.nether_wart);
		WorldGenPrimitive.setBlock(world, x, y, z + 4, Blocks.stonebrick);
		FlowerPot.generate(world, rand, new Coord(x, y + 1, z + 4));
		
		WorldGenPrimitive.setBlock(world, x + 1, y, z + 1, Blocks.stonebrick);
		
		WorldGenPrimitive.fillRectSolid(world, rand, x + 2, y, z + 1, x + 4, y, z + 1, new MetaBlock(Blocks.stone_brick_stairs, Cardinal.getBlockMeta(Cardinal.SOUTH), 2), true, true);
		WorldGenPrimitive.fillRectSolid(world, rand, x + 1, y, z + 2, x + 1, y, z + 4, new MetaBlock(Blocks.stone_brick_stairs, Cardinal.getBlockMeta(Cardinal.EAST), 2), true, true);
		
		WorldGenPrimitive.setBlock(world, x + 2, y - 1, z + 2, Blocks.redstone_block);
		WorldGenPrimitive.setBlock(world, x + 3, y - 1, z + 2, Blocks.redstone_lamp);
		WorldGenPrimitive.setBlock(world, x + 2, y - 1, z + 3, Blocks.redstone_lamp);
		
		WorldGenPrimitive.setBlock(world, x, y, z, Blocks.water);
	}
	
	private static void northEast(World world, Random rand, ITheme theme, int x, int y, int z){
		
		corner(world, rand, theme, x, y, z);
		
		WorldGenPrimitive.setBlock(world, x + 1, y, z, Blocks.stonebrick);
		FlowerPot.generate(world, rand, new Coord(x + 1, y + 1, z));
		WorldGenPrimitive.setBlock(world, x + 2, y, z, Blocks.farmland);
		WorldGenPrimitive.setBlock(world, x + 2, y + 1, z, Blocks.melon_stem);
		WorldGenPrimitive.setBlock(world, x + 3, y, z, Blocks.farmland);
		WorldGenPrimitive.setBlock(world, x + 4, y, z, Blocks.stonebrick);
		FlowerPot.generate(world, rand, new Coord(x + 4, y + 1, z));
		
		WorldGenPrimitive.setBlock(world, x + 5, y, z + 1, Blocks.stonebrick);
		FlowerPot.generate(world, rand, new Coord(x + 5, y + 1, z + 1));
		WorldGenPrimitive.setBlock(world, x + 5, y, z + 2, Blocks.farmland);
		WorldGenPrimitive.setBlock(world, x + 5, y + 1, z + 2, Blocks.pumpkin_stem);
		WorldGenPrimitive.setBlock(world, x + 5, y, z + 3, Blocks.farmland);
		WorldGenPrimitive.setBlock(world, x + 5, y, z + 4, Blocks.stonebrick);
		WorldGenPrimitive.setBlock(world, x + 5, y + 1, z + 4, Blocks.flower_pot, rand.nextInt(11) + 1, 2, true, true);
		FlowerPot.generate(world, rand, new Coord(x + 5, y + 1, z + 4));
		
		WorldGenPrimitive.setBlock(world, x + 4, y, z + 1, Blocks.stonebrick);
		
		WorldGenPrimitive.fillRectSolid(world, rand, x + 1, y, z + 1, x + 3, y, z + 1, new MetaBlock (Blocks.stone_brick_stairs, Cardinal.getBlockMeta(Cardinal.SOUTH), 2), true, true);
		WorldGenPrimitive.fillRectSolid(world, rand, x + 4, y, z + 2, x + 4, y, z + 4, new MetaBlock (Blocks.stone_brick_stairs, Cardinal.getBlockMeta(Cardinal.WEST), 2), true, true);
		
		WorldGenPrimitive.setBlock(world, x + 3, y - 1, z + 2, Blocks.redstone_block);
		WorldGenPrimitive.setBlock(world, x + 2, y - 1, z + 2, Blocks.redstone_lamp);
		WorldGenPrimitive.setBlock(world, x + 3, y - 1, z + 3, Blocks.redstone_lamp);
		
		WorldGenPrimitive.setBlock(world, x + 5, y, z, Blocks.water);
	}
	
	private static void pillar(World world, Random rand, ITheme theme, int x, int y, int z){
		
		WorldGenPrimitive.fillRectSolid(world, rand, x, y, z, x, y + 2, z, theme.getSecondaryPillar(), true, true);
		WorldGenPrimitive.setBlock(world, rand, x, y + 3, z, theme.getPrimaryWall(), true, true);
		MetaBlock stair = theme.getSecondaryStair();
		stair.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.EAST, true));
		stair.setBlock(world, x + 1, y + 3, z);
		stair.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.WEST, true));
		stair.setBlock(world, x - 1, y + 3, z);
		stair.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.SOUTH, true));
		stair.setBlock(world, x, y + 3, z + 1);
		stair.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.NORTH, true));
		stair.setBlock(world, x, y + 3, z - 1);	
	}
	
	public int getSize(){
		return 9;
	}
	
}
