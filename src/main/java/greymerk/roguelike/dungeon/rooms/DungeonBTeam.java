package greymerk.roguelike.dungeon.rooms;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.settings.CatacombLevelSettings;
import greymerk.roguelike.treasure.ITreasureChest;
import greymerk.roguelike.treasure.TreasureChestEmpty;
import greymerk.roguelike.treasure.loot.Loot;
import greymerk.roguelike.treasure.loot.PotionMixture;
import greymerk.roguelike.treasure.loot.Record;
import greymerk.roguelike.treasure.loot.provider.ItemArmour;
import greymerk.roguelike.treasure.loot.provider.ItemNovelty;
import greymerk.roguelike.worldgen.BlockFactoryCheckers;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;
import greymerk.roguelike.worldgen.blocks.Log;

import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockCocoa;
import net.minecraft.block.BlockColored;
import net.minecraft.block.BlockDoubleStoneSlab;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockStoneSlab;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class DungeonBTeam extends DungeonBase {

	@Override
	public boolean generate(World world, Random rand, CatacombLevelSettings settings, Cardinal[] entrances, Coord origin) {
		
		int x = origin.getX();
		int y = origin.getY();
		int z = origin.getZ();
		
		
		MetaBlock air = new MetaBlock(Blocks.air);
		MetaBlock oakLog = Log.getLog(Log.OAK);
		MetaBlock stair = new MetaBlock(Blocks.spruce_stairs);
		MetaBlock stonebrick = new MetaBlock(Blocks.stonebrick);
		MetaBlock cyan = new MetaBlock(Blocks.stained_hardened_clay);
		cyan.withProperty(BlockColored.COLOR, EnumDyeColor.CYAN);
		MetaBlock doubleSlab = new MetaBlock(Blocks.double_stone_slab);
		doubleSlab.withProperty(BlockDoubleStoneSlab.VARIANT_PROP, BlockStoneSlab.EnumType.STONE);
		MetaBlock sprucePlank = new MetaBlock(Blocks.planks);
		sprucePlank.withProperty(BlockPlanks.VARIANT_PROP, BlockPlanks.EnumType.SPRUCE);
		
		WorldGenPrimitive.fillRectSolid(world, rand, x - 4, y, z - 4, x + 4, y + 4, z + 5, air);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 4, y - 1, z - 4, x + 4, y - 1, z + 5, new MetaBlock(Blocks.cobblestone));
		WorldGenPrimitive.fillRectSolid(world, rand, x - 3, y - 1, z - 2, x + 3, y - 1, z + 3, cyan, true, true);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 2, y - 1, z - 1, x + 2, y - 1, z + 2, doubleSlab, true, true);
		
		// wood panel walls
		
		BlockFactoryCheckers panels = new BlockFactoryCheckers(Log.getLog(Log.SPRUCE, Cardinal.UP), Log.getLog(Log.SPRUCE, Cardinal.EAST));
		WorldGenPrimitive.fillRectSolid(world, rand, x - 4, y + 1, z + 6, x + 4, y + 3, z + 6, panels, true, true);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 4, y + 1, z - 5, x + 4, y + 3, z - 5, panels, true, true);
	
		WorldGenPrimitive.fillRectSolid(world, rand, x - 4, y, z + 6, x + 4, y, z + 6, sprucePlank, true, true);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 4, y, z - 5, x + 4, y, z - 5, sprucePlank, true, true);
		
		WorldGenPrimitive.fillRectSolid(world, rand, x - 5, y - 1, z - 4, x - 5, y, z + 5, sprucePlank, true, true);
		WorldGenPrimitive.fillRectSolid(world, rand, x + 5, y - 1, z - 4, x + 5, y, z + 5, sprucePlank, true, true);
		
		stair.withProperty(BlockStairs.FACING, EnumFacing.SOUTH);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 4, y + 4, z - 4, x + 4, y + 4, z - 4, stair, true, true);
		stair.withProperty(BlockStairs.FACING, EnumFacing.NORTH);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 4, y + 4, z + 5, x + 4, y + 4, z + 5, stair, true, true);
		
		// doors
		WorldGenPrimitive.fillRectSolid(world, rand, x - 1, y, z - 5, x + 1, y + 2, z - 5, new MetaBlock(Blocks.cobblestone));
		WorldGenPrimitive.fillRectSolid(world, rand, x, y, z - 5, x, y + 1, z - 5, air);
		
		WorldGenPrimitive.fillRectSolid(world, rand, x - 1, y, z + 6, x + 1, y + 2, z + 6, new MetaBlock(Blocks.cobblestone));
		WorldGenPrimitive.fillRectSolid(world, rand, x, y, z + 6, x, y + 1, z + 6, air);
		
		// west wall
		WorldGenPrimitive.fillRectSolid(world, rand, x - 5, y, z - 5, x - 5, y + 4, z + 6, sprucePlank, true, true);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 5, y, z - 1, x - 5, y, z + 2, new MetaBlock(Blocks.noteblock));
		WorldGenPrimitive.fillRectSolid(world, rand, x - 5, y + 1, z - 3, x - 5, y + 3, z + 4, new MetaBlock(Blocks.bookshelf));
		MetaBlock black = new MetaBlock(Blocks.wool);
		black.withProperty(BlockColored.COLOR, EnumDyeColor.BLACK);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 5, y + 1, z - 1, x - 5, y + 3, z + 2, black, true, true);
		
		MetaBlock cocao = new MetaBlock(Blocks.cocoa);
		cocao.withProperty(BlockCocoa.FACING, EnumFacing.EAST);
		WorldGenPrimitive.setBlock(world, x - 5, y + 2, z - 2, Log.getLog(Log.JUNGLE, Cardinal.EAST));
		cocao.setBlock(world, new Coord(x - 4, y + 2, z - 2));
		WorldGenPrimitive.setBlock(world, x - 5, y + 2, z + 3, Log.getLog(Log.JUNGLE, Cardinal.EAST));
		cocao.setBlock(world, new Coord(x - 4, y + 2, z + 3));
		
		lamp(world, new Coord(x - 2, y, z - 4));
		lamp(world, new Coord(x + 2, y, z - 4));
		lamp(world, new Coord(x - 2, y, z + 5));
		lamp(world, new Coord(x + 2, y, z + 5));
		
		// east wall
		WorldGenPrimitive.fillRectSolid(world, rand, x + 5, y + 1, z - 4, x + 5, y + 4, z + 5, stonebrick);
		
		MetaBlock lime = new MetaBlock(Blocks.wool);
		lime.withProperty(BlockColored.COLOR, EnumDyeColor.LIME);
		MetaBlock greenBlock = RogueConfig.getBoolean(RogueConfig.PRECIOUSBLOCKS) ? new MetaBlock(Blocks.emerald_block) : lime;
		
		WorldGenPrimitive.fillRectSolid(world, rand, x + 5, y, z - 1, x + 5, y + 4, z - 1, greenBlock, true, true);
		WorldGenPrimitive.fillRectSolid(world, rand, x + 5, y, z, x + 5, y, z + 1, greenBlock, true, true);
		WorldGenPrimitive.fillRectSolid(world, rand, x + 5, y + 1, z, x + 5, y + 1, z + 1, air);
		greenBlock.setBlock(world, new Coord(x + 5, y + 1, z + 2));
		WorldGenPrimitive.fillRectSolid(world, rand, x + 5, y + 2, z, x + 5, y + 2, z + 1, greenBlock, true, true);
		WorldGenPrimitive.fillRectSolid(world, rand, x + 5, y + 3, z, x + 5, y + 3, z + 1, air);
		greenBlock.setBlock(world, new Coord(x + 5, y + 3, z + 2));
		WorldGenPrimitive.fillRectSolid(world, rand, x + 5, y + 4, z, x + 5, y + 4, z + 1, greenBlock, true, true);
		
		// roof
		WorldGenPrimitive.fillRectSolid(world, rand, x - 4, y + 5, z - 5, x + 4, y + 6, z + 6, new MetaBlock(Blocks.stone));
		WorldGenPrimitive.fillRectSolid(world, rand, x - 2, y + 5, z - 1, x + 2, y + 5, z - 1, WorldGenPrimitive.blockOrientation(stair, Cardinal.SOUTH, true), true, true);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 2, y + 5, z + 2, x + 2, y + 5, z + 2, WorldGenPrimitive.blockOrientation(stair, Cardinal.NORTH, true), true, true);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 3, y + 5, z, x - 3, y + 5, z + 1, WorldGenPrimitive.blockOrientation(stair, Cardinal.EAST, true), true, true);
		WorldGenPrimitive.fillRectSolid(world, rand, x + 3, y + 5, z, x + 3, y + 5, z + 1, WorldGenPrimitive.blockOrientation(stair, Cardinal.WEST, true), true, true);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 2, y + 5, z, x + 2, y + 5, z + 1, new MetaBlock(Blocks.redstone_lamp));
		
		WorldGenPrimitive.setBlock(world, x - 3, y + 5, z - 1, oakLog);
		WorldGenPrimitive.setBlock(world, x + 3, y + 5, z - 1, oakLog);
		
		WorldGenPrimitive.setBlock(world, x - 3, y + 5, z + 2, oakLog);
		WorldGenPrimitive.setBlock(world, x + 3, y + 5, z + 2, oakLog);
		
		
		// table
		WorldGenPrimitive.fillRectSolid(world, rand, x - 2, y, z, x - 2, y, z + 1, WorldGenPrimitive.blockOrientation(stair, Cardinal.WEST, true), true, true);
		WorldGenPrimitive.fillRectSolid(world, rand, x + 2, y, z, x + 2, y, z + 1, WorldGenPrimitive.blockOrientation(stair, Cardinal.EAST, true), true, true);
		MetaBlock spruceSlabUpsideDown = new MetaBlock(Blocks.wooden_slab);
		spruceSlabUpsideDown.withProperty(BlockPlanks.VARIANT_PROP, BlockPlanks.EnumType.SPRUCE);
		spruceSlabUpsideDown.withProperty(BlockSlab.HALF_PROP, BlockSlab.EnumBlockHalf.TOP);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 1, y, z, x + 1, y, z + 1, spruceSlabUpsideDown, true, true);
		
		// chairs
		MetaBlock chair = new MetaBlock(Blocks.nether_brick_stairs);
		WorldGenPrimitive.blockOrientation(chair, Cardinal.SOUTH, false);
		chair.setBlock(world, new Coord(x - 1, y, z - 2));
		chair.setBlock(world, new Coord(x + 1, y, z - 2));
		
		WorldGenPrimitive.blockOrientation(chair, Cardinal.NORTH, false);
		chair.setBlock(world, new Coord(x - 1, y, z + 3));
		chair.setBlock(world, new Coord(x + 1, y, z + 3));
		
		// wall entrances
		WorldGenPrimitive.fillRectSolid(world, rand, x - 7, y - 1, z - 4, x - 6, y + 4, z + 4, stonebrick);
		WorldGenPrimitive.fillRectSolid(world, rand, x + 6, y - 1, z - 4, x + 7, y + 4, z + 4, stonebrick);
		
		ITreasureChest recordChest = new TreasureChestEmpty().generate(world, rand, settings.getLoot(), new Coord(x - 4, y, z - 4), 0, false);
		recordChest.setInventorySlot(Record.getRecord(Record.STAL), recordChest.getInventorySize() / 2);
		WorldGenPrimitive.setBlock(world, x - 3, y, z - 4, Blocks.jukebox);
		
		ITreasureChest bdubsChest = new TreasureChestEmpty().generate(world, rand, settings.getLoot(), new Coord(x - 3, y, z + 5), 0, false);
		bdubsChest.setInventorySlot(ItemNovelty.getItem(ItemNovelty.BDOUBLEO), (bdubsChest.getInventorySize() / 2) - 2);
		
		ItemStack shirt = new ItemStack(Items.leather_chestplate);
		Loot.setItemName(shirt, "Pink Sweater", null);
		Loot.setItemLore(shirt, "\"It's chinese red!\"");
		ItemArmour.dyeArmor(shirt, 250, 96, 128);
		
		bdubsChest.setInventorySlot(shirt, (bdubsChest.getInventorySize() / 2) + 2);
		
		ITreasureChest gennybChest = new TreasureChestEmpty().generate(world, rand, settings.getLoot(), new Coord(x + 3, y, z + 5), 0, false);
		gennybChest.setInventorySlot(ItemNovelty.getItem(ItemNovelty.GENERIKB), gennybChest.getInventorySize() / 2);
		
		
		WorldGenPrimitive.setBlock(world, x + 4, y, z - 4, Blocks.bookshelf);
		WorldGenPrimitive.setBlock(world, x + 4, y + 1, z - 4, Blocks.brewing_stand);
		
		ITreasureChest contraband = new TreasureChestEmpty().generate(world, rand, settings.getLoot(), new Coord(x + 3, y, z - 4), 0, false);
		
		for(int i = 0; i < 8; ++i){
			ItemStack booze = PotionMixture.getBooze(rand);
			contraband.setInventorySlot(booze, rand.nextInt(contraband.getInventorySize()));
		}
		
		return true;
	}

	private static void lamp(World world, Coord pos){
		
		MetaBlock spruce = new MetaBlock(Blocks.planks);
		MetaBlock fence = new MetaBlock(Blocks.oak_fence);
		spruce.withProperty(BlockPlanks.VARIANT_PROP, BlockPlanks.EnumType.SPRUCE);
		Coord cursor = new Coord(pos);
		cursor.add(Cardinal.UP, 4);
		spruce.setBlock(world, new Coord(cursor));
		cursor.add(Cardinal.DOWN);
		WorldGenPrimitive.setBlock(world, cursor, Blocks.oak_fence);
		cursor.add(Cardinal.DOWN);
		WorldGenPrimitive.setBlock(world, cursor, Blocks.glowstone);
		
		for(Cardinal dir : Cardinal.directions){
			MetaBlock trapdoor = new MetaBlock(Blocks.trapdoor);
			trapdoor.withProperty(BlockTrapDoor.FACING_PROP, Cardinal.getFacing(dir));
			trapdoor.withProperty(BlockTrapDoor.OPEN_PROP, true);
			Coord p = new Coord(cursor);
			trapdoor.setBlock(world, p);
			
		}
		
		cursor.add(Cardinal.DOWN);
		fence.setBlock(world, cursor);
		cursor.add(Cardinal.DOWN);
		spruce.setBlock(world, cursor);
	}
	
	public int getSize(){
		return 8;
	}
	
	public boolean validLocation(World world, Cardinal dir, int x, int y, int z){
		
		if(!(dir == Cardinal.NORTH || dir == Cardinal.SOUTH)) return false;
		
		List<Coord> box = WorldGenPrimitive.getRectHollow(x - 7, y - 2, z - 7, x + 7, y + 5, z + 7);
		
		for(Coord pos : box){
			MetaBlock b = WorldGenPrimitive.getBlock(world, pos);
			if(!b.getBlock().getMaterial().isSolid()) return false;
		}
		
		return true;
	}
}
