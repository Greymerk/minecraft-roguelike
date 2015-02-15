package greymerk.roguelike.catacomb.dungeon.room;

import greymerk.roguelike.catacomb.dungeon.DungeonBase;
import greymerk.roguelike.catacomb.settings.CatacombLevelSettings;
import greymerk.roguelike.config.RogueConfig;
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
import greymerk.roguelike.worldgen.Log;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class DungeonBTeam extends DungeonBase {

	@Override
	public boolean generate(World world, Random rand, CatacombLevelSettings settings, Cardinal[] entrances, int x, int y, int z) {
		
		
		MetaBlock air = new MetaBlock(Blocks.air);
		MetaBlock oakLog = Log.getLog(Log.OAK);
		MetaBlock stair = new MetaBlock(Blocks.spruce_stairs);
		MetaBlock stonebrick = new MetaBlock(Blocks.stonebrick);
		
		WorldGenPrimitive.fillRectSolid(world, rand, x - 4, y, z - 4, x + 4, y + 4, z + 5, air);
		
		WorldGenPrimitive.fillRectSolid(world, rand, x - 4, y - 1, z - 4, x + 4, y - 1, z + 5, new MetaBlock(Blocks.cobblestone));
		WorldGenPrimitive.fillRectSolid(world, rand, x - 3, y - 1, z - 2, x + 3, y - 1, z + 3, new MetaBlock(Blocks.stained_hardened_clay, 9), true, true);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 2, y - 1, z - 1, x + 2, y - 1, z + 2, new MetaBlock(Blocks.double_stone_slab, 8), true, true);
		
		// wood panel walls
		
		BlockFactoryCheckers panels = new BlockFactoryCheckers(Log.getLog(Log.SPRUCE, Cardinal.UP), Log.getLog(Log.SPRUCE, Cardinal.EAST));
		WorldGenPrimitive.fillRectSolid(world, rand, x - 4, y + 1, z + 6, x + 4, y + 3, z + 6, panels, true, true);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 4, y + 1, z - 5, x + 4, y + 3, z - 5, panels, true, true);
	
		MetaBlock spruce = new MetaBlock(Blocks.planks, 1); 
		WorldGenPrimitive.fillRectSolid(world, rand, x - 4, y, z + 6, x + 4, y, z + 6, spruce, true, true);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 4, y, z - 5, x + 4, y, z - 5, spruce, true, true);
		
		WorldGenPrimitive.fillRectSolid(world, rand, x - 5, y - 1, z - 4, x - 5, y, z + 5, spruce, true, true);
		WorldGenPrimitive.fillRectSolid(world, rand, x + 5, y - 1, z - 4, x + 5, y, z + 5, spruce, true, true);
		
		WorldGenPrimitive.fillRectSolid(world, rand, x - 4, y + 4, z - 4, x + 4, y + 4, z - 4, new MetaBlock(Blocks.spruce_stairs, WorldGenPrimitive.blockOrientation(Cardinal.SOUTH, true)), true, true);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 4, y + 4, z + 5, x + 4, y + 4, z + 5, new MetaBlock(Blocks.spruce_stairs, WorldGenPrimitive.blockOrientation(Cardinal.NORTH, true)), true, true);
		
		// doors
		WorldGenPrimitive.fillRectSolid(world, rand, x - 1, y, z - 5, x + 1, y + 2, z - 5, new MetaBlock(Blocks.cobblestone));
		WorldGenPrimitive.fillRectSolid(world, rand, x, y, z - 5, x, y + 1, z - 5, air);
		
		WorldGenPrimitive.fillRectSolid(world, rand, x - 1, y, z + 6, x + 1, y + 2, z + 6, new MetaBlock(Blocks.cobblestone));
		WorldGenPrimitive.fillRectSolid(world, rand, x, y, z + 6, x, y + 1, z + 6, air);
		
		// west wall
		WorldGenPrimitive.fillRectSolid(world, rand, x - 5, y, z - 5, x - 5, y + 4, z + 6, spruce, true, true);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 5, y, z - 1, x - 5, y, z + 2, new MetaBlock(Blocks.noteblock));
		WorldGenPrimitive.fillRectSolid(world, rand, x - 5, y + 1, z - 3, x - 5, y + 3, z + 4, new MetaBlock(Blocks.bookshelf));
		WorldGenPrimitive.fillRectSolid(world, rand, x - 5, y + 1, z - 1, x - 5, y + 3, z + 2, new MetaBlock(Blocks.wool, 15), true, true);
		
		MetaBlock cocao = new MetaBlock(Blocks.cocoa, 9);
		WorldGenPrimitive.setBlock(world, x - 5, y + 2, z - 2, Log.getLog(Log.JUNGLE, Cardinal.EAST));
		cocao.setBlock(world, new Coord(x - 4, y + 2, z - 2));
		WorldGenPrimitive.setBlock(world, x - 5, y + 2, z + 3, Log.getLog(Log.JUNGLE, Cardinal.EAST));
		cocao.setBlock(world, new Coord(x - 4, y + 2, z + 3));
		
		lamp(world, x - 2, y, z - 4);
		lamp(world, x + 2, y, z - 4);
		lamp(world, x - 2, y, z + 5);
		lamp(world, x + 2, y, z + 5);
		
		// east wall
		WorldGenPrimitive.fillRectSolid(world, rand, x + 5, y + 1, z - 4, x + 5, y + 4, z + 5, stonebrick);
		
		MetaBlock greenBlock = RogueConfig.getBoolean(RogueConfig.PRECIOUSBLOCKS) ? new MetaBlock(Blocks.emerald_block) : new MetaBlock(Blocks.wool, 5);
		
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
		MetaBlock spruceSlabUpsideDown = new MetaBlock(Blocks.wooden_slab, 9);
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
		
		ITreasureChest recordChest = new TreasureChestEmpty().generate(world, rand, settings.getLoot(), x - 4, y, z - 4);
		recordChest.setInventorySlot(Record.getRecord(Record.STAL), recordChest.getInventorySize() / 2);
		WorldGenPrimitive.setBlock(world, x - 3, y, z - 4, Blocks.jukebox);
		
		ITreasureChest bdubsChest = new TreasureChestEmpty().generate(world, rand, settings.getLoot(), x - 3, y, z + 5);
		bdubsChest.setInventorySlot(ItemNovelty.getItem(ItemNovelty.BDOUBLEO), (bdubsChest.getInventorySize() / 2) - 2);
		
		ItemStack shirt = new ItemStack(Items.leather_chestplate);
		Loot.setItemName(shirt, "Pink Sweater", null);
		Loot.setItemLore(shirt, "\"It's chinese red!\"");
		ItemArmour.dyeArmor(shirt, 250, 96, 128);
		
		bdubsChest.setInventorySlot(shirt, (bdubsChest.getInventorySize() / 2) + 2);
		
		ITreasureChest gennybChest = new TreasureChestEmpty().generate(world, rand, settings.getLoot(), x + 3, y, z + 5);
		gennybChest.setInventorySlot(ItemNovelty.getItem(ItemNovelty.GENERIKB), gennybChest.getInventorySize() / 2);
		
		
		WorldGenPrimitive.setBlock(world, x + 4, y, z - 4, Blocks.bookshelf);
		WorldGenPrimitive.setBlock(world, x + 4, y + 1, z - 4, Blocks.brewing_stand);
		
		ITreasureChest contraband = new TreasureChestEmpty().generate(world, rand, settings.getLoot(), x + 3, y, z - 4);
		
		for(int i = 0; i < 8; ++i){
			ItemStack booze = PotionMixture.getBooze(rand);
			contraband.setInventorySlot(booze, rand.nextInt(contraband.getInventorySize()));
		}
		
		return true;
	}

	private static void lamp(World world, int x, int y, int z){
		MetaBlock spruce = new MetaBlock(Blocks.planks, 1);
		spruce.setBlock(world, new Coord(x, y + 4, z));
		WorldGenPrimitive.setBlock(world, x, y + 3, z, Blocks.fence);
		
		WorldGenPrimitive.setBlock(world, x, y + 2, z, Blocks.glowstone);
		MetaBlock fence = new MetaBlock(Blocks.trapdoor, 4);
		fence.setBlock(world, new Coord(x, y + 2, z - 1));
		fence.setMeta(5);
		fence.setBlock(world, new Coord(x, y + 2, z + 1));
		fence.setMeta(6);
		fence.setBlock(world, new Coord(x - 1, y + 2, z));		
		fence.setMeta(7);
		fence.setBlock(world, new Coord(x + 1, y + 2, z));
		
		WorldGenPrimitive.setBlock(world, x, y + 1, z, Blocks.fence);
		spruce.setBlock(world, new Coord(x, y, z));
	}
	
	public int getSize(){
		return 8;
	}
	
	public boolean validLocation(World world, Cardinal dir, int x, int y, int z){
		
		if(!(dir == Cardinal.NORTH || dir == Cardinal.SOUTH)) return false;
		
		List<Coord> box = WorldGenPrimitive.getRectHollow(x - 7, y - 2, z - 7, x + 7, y + 5, z + 7);
		
		for(Coord pos : box){
			Block b = world.getBlock(pos.getX(), pos.getY(), pos.getZ());
			if(!b.getMaterial().isSolid()) return false;
		}
		
		return true;
	}
}
