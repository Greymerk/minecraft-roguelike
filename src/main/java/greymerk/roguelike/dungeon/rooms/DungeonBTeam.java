package greymerk.roguelike.dungeon.rooms;

import java.util.List;
import java.util.Random;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.settings.LevelSettings;
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
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.blocks.Log;
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

public class DungeonBTeam extends DungeonBase {

	@Override
	public boolean generate(WorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances, Coord origin) {
		
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
		
		editor.fillRectSolid(rand, x - 4, y, z - 4, x + 4, y + 4, z + 5, air);
		editor.fillRectSolid(rand, x - 4, y - 1, z - 4, x + 4, y - 1, z + 5, new MetaBlock(Blocks.cobblestone));
		editor.fillRectSolid(rand, x - 3, y - 1, z - 2, x + 3, y - 1, z + 3, cyan, true, true);
		editor.fillRectSolid(rand, x - 2, y - 1, z - 1, x + 2, y - 1, z + 2, doubleSlab, true, true);
		
		// wood panel walls
		
		BlockFactoryCheckers panels = new BlockFactoryCheckers(Log.getLog(Log.SPRUCE, Cardinal.UP), Log.getLog(Log.SPRUCE, Cardinal.EAST));
		editor.fillRectSolid(rand, x - 4, y + 1, z + 6, x + 4, y + 3, z + 6, panels, true, true);
		editor.fillRectSolid(rand, x - 4, y + 1, z - 5, x + 4, y + 3, z - 5, panels, true, true);
	
		editor.fillRectSolid(rand, x - 4, y, z + 6, x + 4, y, z + 6, sprucePlank, true, true);
		editor.fillRectSolid(rand, x - 4, y, z - 5, x + 4, y, z - 5, sprucePlank, true, true);
		
		editor.fillRectSolid(rand, x - 5, y - 1, z - 4, x - 5, y, z + 5, sprucePlank, true, true);
		editor.fillRectSolid(rand, x + 5, y - 1, z - 4, x + 5, y, z + 5, sprucePlank, true, true);
		
		stair.withProperty(BlockStairs.FACING, EnumFacing.SOUTH);
		editor.fillRectSolid(rand, x - 4, y + 4, z - 4, x + 4, y + 4, z - 4, stair, true, true);
		stair.withProperty(BlockStairs.FACING, EnumFacing.NORTH);
		editor.fillRectSolid(rand, x - 4, y + 4, z + 5, x + 4, y + 4, z + 5, stair, true, true);
		
		// doors
		editor.fillRectSolid(rand, x - 1, y, z - 5, x + 1, y + 2, z - 5, new MetaBlock(Blocks.cobblestone));
		editor.fillRectSolid(rand, x, y, z - 5, x, y + 1, z - 5, air);
		
		editor.fillRectSolid(rand, x - 1, y, z + 6, x + 1, y + 2, z + 6, new MetaBlock(Blocks.cobblestone));
		editor.fillRectSolid(rand, x, y, z + 6, x, y + 1, z + 6, air);
		
		// west wall
		editor.fillRectSolid(rand, x - 5, y, z - 5, x - 5, y + 4, z + 6, sprucePlank, true, true);
		editor.fillRectSolid(rand, x - 5, y, z - 1, x - 5, y, z + 2, new MetaBlock(Blocks.noteblock));
		editor.fillRectSolid(rand, x - 5, y + 1, z - 3, x - 5, y + 3, z + 4, new MetaBlock(Blocks.bookshelf));
		MetaBlock black = new MetaBlock(Blocks.wool);
		black.withProperty(BlockColored.COLOR, EnumDyeColor.BLACK);
		editor.fillRectSolid(rand, x - 5, y + 1, z - 1, x - 5, y + 3, z + 2, black, true, true);
		
		MetaBlock cocao = new MetaBlock(Blocks.cocoa);
		cocao.withProperty(BlockCocoa.FACING, EnumFacing.EAST);
		editor.setBlock(x - 5, y + 2, z - 2, Log.getLog(Log.JUNGLE, Cardinal.EAST));
		cocao.setBlock(editor, new Coord(x - 4, y + 2, z - 2));
		editor.setBlock(x - 5, y + 2, z + 3, Log.getLog(Log.JUNGLE, Cardinal.EAST));
		cocao.setBlock(editor, new Coord(x - 4, y + 2, z + 3));
		
		lamp(editor, new Coord(x - 2, y, z - 4));
		lamp(editor, new Coord(x + 2, y, z - 4));
		lamp(editor, new Coord(x - 2, y, z + 5));
		lamp(editor, new Coord(x + 2, y, z + 5));
		
		// east wall
		editor.fillRectSolid(rand, x + 5, y + 1, z - 4, x + 5, y + 4, z + 5, stonebrick);
		
		MetaBlock lime = new MetaBlock(Blocks.wool);
		lime.withProperty(BlockColored.COLOR, EnumDyeColor.LIME);
		MetaBlock greenBlock = RogueConfig.getBoolean(RogueConfig.PRECIOUSBLOCKS) ? new MetaBlock(Blocks.emerald_block) : lime;
		
		editor.fillRectSolid(rand, x + 5, y, z - 1, x + 5, y + 4, z - 1, greenBlock, true, true);
		editor.fillRectSolid(rand, x + 5, y, z, x + 5, y, z + 1, greenBlock, true, true);
		editor.fillRectSolid(rand, x + 5, y + 1, z, x + 5, y + 1, z + 1, air);
		greenBlock.setBlock(editor, new Coord(x + 5, y + 1, z + 2));
		editor.fillRectSolid(rand, x + 5, y + 2, z, x + 5, y + 2, z + 1, greenBlock, true, true);
		editor.fillRectSolid(rand, x + 5, y + 3, z, x + 5, y + 3, z + 1, air);
		greenBlock.setBlock(editor, new Coord(x + 5, y + 3, z + 2));
		editor.fillRectSolid(rand, x + 5, y + 4, z, x + 5, y + 4, z + 1, greenBlock, true, true);
		
		// roof
		editor.fillRectSolid(rand, x - 4, y + 5, z - 5, x + 4, y + 6, z + 6, new MetaBlock(Blocks.stone));
		editor.fillRectSolid(rand, x - 2, y + 5, z - 1, x + 2, y + 5, z - 1, WorldEditor.blockOrientation(stair, Cardinal.SOUTH, true), true, true);
		editor.fillRectSolid(rand, x - 2, y + 5, z + 2, x + 2, y + 5, z + 2, WorldEditor.blockOrientation(stair, Cardinal.NORTH, true), true, true);
		editor.fillRectSolid(rand, x - 3, y + 5, z, x - 3, y + 5, z + 1, WorldEditor.blockOrientation(stair, Cardinal.EAST, true), true, true);
		editor.fillRectSolid(rand, x + 3, y + 5, z, x + 3, y + 5, z + 1, WorldEditor.blockOrientation(stair, Cardinal.WEST, true), true, true);
		editor.fillRectSolid(rand, x - 2, y + 5, z, x + 2, y + 5, z + 1, new MetaBlock(Blocks.redstone_lamp));
		
		editor.setBlock(x - 3, y + 5, z - 1, oakLog);
		editor.setBlock(x + 3, y + 5, z - 1, oakLog);
		
		editor.setBlock(x - 3, y + 5, z + 2, oakLog);
		editor.setBlock(x + 3, y + 5, z + 2, oakLog);
		
		
		// table
		editor.fillRectSolid(rand, x - 2, y, z, x - 2, y, z + 1, WorldEditor.blockOrientation(stair, Cardinal.WEST, true), true, true);
		editor.fillRectSolid(rand, x + 2, y, z, x + 2, y, z + 1, WorldEditor.blockOrientation(stair, Cardinal.EAST, true), true, true);
		MetaBlock spruceSlabUpsideDown = new MetaBlock(Blocks.wooden_slab);
		spruceSlabUpsideDown.withProperty(BlockPlanks.VARIANT_PROP, BlockPlanks.EnumType.SPRUCE);
		spruceSlabUpsideDown.withProperty(BlockSlab.HALF_PROP, BlockSlab.EnumBlockHalf.TOP);
		editor.fillRectSolid(rand, x - 1, y, z, x + 1, y, z + 1, spruceSlabUpsideDown, true, true);
		
		// chairs
		MetaBlock chair = new MetaBlock(Blocks.nether_brick_stairs);
		WorldEditor.blockOrientation(chair, Cardinal.SOUTH, false);
		chair.setBlock(editor, new Coord(x - 1, y, z - 2));
		chair.setBlock(editor, new Coord(x + 1, y, z - 2));
		
		WorldEditor.blockOrientation(chair, Cardinal.NORTH, false);
		chair.setBlock(editor, new Coord(x - 1, y, z + 3));
		chair.setBlock(editor, new Coord(x + 1, y, z + 3));
		
		// wall entrances
		editor.fillRectSolid(rand, x - 7, y - 1, z - 4, x - 6, y + 4, z + 4, stonebrick);
		editor.fillRectSolid(rand, x + 6, y - 1, z - 4, x + 7, y + 4, z + 4, stonebrick);
		
		ITreasureChest recordChest = new TreasureChestEmpty().generate(editor, rand, settings.getLoot(), new Coord(x - 4, y, z - 4), 0, false);
		recordChest.setInventorySlot(Record.getRecord(Record.STAL), recordChest.getInventorySize() / 2);
		editor.setBlock(x - 3, y, z - 4, Blocks.jukebox);
		
		ITreasureChest bdubsChest = new TreasureChestEmpty().generate(editor, rand, settings.getLoot(), new Coord(x - 3, y, z + 5), 0, false);
		bdubsChest.setInventorySlot(ItemNovelty.getItem(ItemNovelty.BDOUBLEO), (bdubsChest.getInventorySize() / 2) - 2);
		
		ItemStack shirt = new ItemStack(Items.leather_chestplate);
		Loot.setItemName(shirt, "Pink Sweater", null);
		Loot.setItemLore(shirt, "\"It's chinese red!\"");
		ItemArmour.dyeArmor(shirt, 250, 96, 128);
		
		bdubsChest.setInventorySlot(shirt, (bdubsChest.getInventorySize() / 2) + 2);
		
		ITreasureChest gennybChest = new TreasureChestEmpty().generate(editor, rand, settings.getLoot(), new Coord(x + 3, y, z + 5), 0, false);
		gennybChest.setInventorySlot(ItemNovelty.getItem(ItemNovelty.GENERIKB), gennybChest.getInventorySize() / 2);
		
		
		editor.setBlock(x + 4, y, z - 4, Blocks.bookshelf);
		editor.setBlock(x + 4, y + 1, z - 4, Blocks.brewing_stand);
		
		ITreasureChest contraband = new TreasureChestEmpty().generate(editor, rand, settings.getLoot(), new Coord(x + 3, y, z - 4), 0, false);
		
		for(int i = 0; i < 8; ++i){
			ItemStack booze = PotionMixture.getBooze(rand);
			contraband.setInventorySlot(booze, rand.nextInt(contraband.getInventorySize()));
		}
		
		return true;
	}

	private static void lamp(WorldEditor editor, Coord pos){
		
		MetaBlock spruce = new MetaBlock(Blocks.planks);
		MetaBlock fence = new MetaBlock(Blocks.oak_fence);
		spruce.withProperty(BlockPlanks.VARIANT_PROP, BlockPlanks.EnumType.SPRUCE);
		Coord cursor = new Coord(pos);
		cursor.add(Cardinal.UP, 4);
		spruce.setBlock(editor, new Coord(cursor));
		cursor.add(Cardinal.DOWN);
		editor.setBlock(cursor, Blocks.oak_fence);
		cursor.add(Cardinal.DOWN);
		editor.setBlock(cursor, Blocks.glowstone);
		
		for(Cardinal dir : Cardinal.directions){
			MetaBlock trapdoor = new MetaBlock(Blocks.trapdoor);
			trapdoor.withProperty(BlockTrapDoor.FACING_PROP, Cardinal.getFacing(dir));
			trapdoor.withProperty(BlockTrapDoor.OPEN_PROP, true);
			Coord p = new Coord(cursor);
			trapdoor.setBlock(editor, p);
			
		}
		
		cursor.add(Cardinal.DOWN);
		fence.setBlock(editor, cursor);
		cursor.add(Cardinal.DOWN);
		spruce.setBlock(editor, cursor);
	}
	
	public int getSize(){
		return 8;
	}
	
	public boolean validLocation(WorldEditor editor, Cardinal dir, int x, int y, int z){
		
		if(!(dir == Cardinal.NORTH || dir == Cardinal.SOUTH)) return false;
		
		List<Coord> box = editor.getRectHollow(x - 7, y - 2, z - 7, x + 7, y + 5, z + 7);
		
		for(Coord pos : box){
			MetaBlock b = editor.getBlock(pos);
			if(!b.getBlock().getMaterial().isSolid()) return false;
		}
		
		return true;
	}
}
