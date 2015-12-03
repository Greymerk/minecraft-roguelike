package greymerk.roguelike.dungeon.rooms;

import java.util.List;
import java.util.Random;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.treasure.ITreasureChest;
import greymerk.roguelike.treasure.Treasure;
import greymerk.roguelike.treasure.loot.Loot;
import greymerk.roguelike.treasure.loot.PotionMixture;
import greymerk.roguelike.treasure.loot.Record;
import greymerk.roguelike.treasure.loot.provider.ItemArmour;
import greymerk.roguelike.treasure.loot.provider.ItemNovelty;
import greymerk.roguelike.worldgen.BlockFactoryCheckers;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.MetaStair;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.BrewingStand;
import greymerk.roguelike.worldgen.blocks.ColorBlock;
import greymerk.roguelike.worldgen.blocks.Crops;
import greymerk.roguelike.worldgen.blocks.DyeColor;
import greymerk.roguelike.worldgen.blocks.Log;
import greymerk.roguelike.worldgen.blocks.Slab;
import greymerk.roguelike.worldgen.blocks.StairType;
import greymerk.roguelike.worldgen.blocks.Trapdoor;
import greymerk.roguelike.worldgen.blocks.Wood;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class DungeonBTeam extends DungeonBase {

	@Override
	public boolean generate(WorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances, Coord origin) {
		
		int x = origin.getX();
		int y = origin.getY();
		int z = origin.getZ();
		
		
		MetaBlock air = BlockType.get(BlockType.AIR);
		MetaBlock oakLog = Log.getLog(Wood.OAK);
		IStair stair = new MetaStair(StairType.SPRUCE);
		MetaBlock stonebrick = BlockType.get(BlockType.STONE_BRICK);
		MetaBlock cyan = ColorBlock.get(ColorBlock.CLAY, DyeColor.CYAN);
		MetaBlock doubleSlab = Slab.get(Slab.STONE, false, true, false);
		MetaBlock sprucePlank = Wood.getPlank(Wood.SPRUCE);
		MetaBlock cobble = BlockType.get(BlockType.COBBLESTONE);
		
		editor.fillRectSolid(rand, x - 4, y, z - 4, x + 4, y + 4, z + 5, air);
		editor.fillRectSolid(rand, x - 4, y - 1, z - 4, x + 4, y - 1, z + 5, cobble);
		editor.fillRectSolid(rand, x - 3, y - 1, z - 2, x + 3, y - 1, z + 3, cyan, true, true);
		editor.fillRectSolid(rand, x - 2, y - 1, z - 1, x + 2, y - 1, z + 2, doubleSlab, true, true);
		
		// wood panel walls
		
		BlockFactoryCheckers panels = new BlockFactoryCheckers(Log.getLog(Wood.SPRUCE, Cardinal.UP), Log.getLog(Wood.SPRUCE, Cardinal.EAST));
		editor.fillRectSolid(rand, x - 4, y + 1, z + 6, x + 4, y + 3, z + 6, panels, true, true);
		editor.fillRectSolid(rand, x - 4, y + 1, z - 5, x + 4, y + 3, z - 5, panels, true, true);
	
		editor.fillRectSolid(rand, x - 4, y, z + 6, x + 4, y, z + 6, sprucePlank, true, true);
		editor.fillRectSolid(rand, x - 4, y, z - 5, x + 4, y, z - 5, sprucePlank, true, true);
		
		editor.fillRectSolid(rand, x - 5, y - 1, z - 4, x - 5, y, z + 5, sprucePlank, true, true);
		editor.fillRectSolid(rand, x + 5, y - 1, z - 4, x + 5, y, z + 5, sprucePlank, true, true);
		
		stair.setOrientation(Cardinal.SOUTH, false);
		editor.fillRectSolid(rand, x - 4, y + 4, z - 4, x + 4, y + 4, z - 4, stair, true, true);
		stair.setOrientation(Cardinal.NORTH, false);
		editor.fillRectSolid(rand, x - 4, y + 4, z + 5, x + 4, y + 4, z + 5, stair, true, true);
		
		// doors
		editor.fillRectSolid(rand, x - 1, y, z - 5, x + 1, y + 2, z - 5, cobble);
		editor.fillRectSolid(rand, x, y, z - 5, x, y + 1, z - 5, air);
		
		editor.fillRectSolid(rand, x - 1, y, z + 6, x + 1, y + 2, z + 6, cobble);
		editor.fillRectSolid(rand, x, y, z + 6, x, y + 1, z + 6, air);
		
		// west wall
		editor.fillRectSolid(rand, x - 5, y, z - 5, x - 5, y + 4, z + 6, sprucePlank, true, true);
		editor.fillRectSolid(rand, x - 5, y, z - 1, x - 5, y, z + 2, BlockType.get(BlockType.NOTEBLOCK));
		editor.fillRectSolid(rand, x - 5, y + 1, z - 3, x - 5, y + 3, z + 4, BlockType.get(BlockType.SHELF));
		MetaBlock black = ColorBlock.get(ColorBlock.WOOL, DyeColor.BLACK);
		editor.fillRectSolid(rand, x - 5, y + 1, z - 1, x - 5, y + 3, z + 2, black, true, true);
		
		MetaBlock cocao = Crops.getCocao(Cardinal.WEST);
		editor.setBlock(x - 5, y + 2, z - 2, Log.getLog(Wood.JUNGLE, Cardinal.EAST));
		cocao.setBlock(editor, new Coord(x - 4, y + 2, z - 2));
		editor.setBlock(x - 5, y + 2, z + 3, Log.getLog(Wood.JUNGLE, Cardinal.EAST));
		cocao.setBlock(editor, new Coord(x - 4, y + 2, z + 3));
		
		lamp(editor, rand, new Coord(x - 2, y, z - 4));
		lamp(editor, rand, new Coord(x + 2, y, z - 4));
		lamp(editor, rand, new Coord(x - 2, y, z + 5));
		lamp(editor, rand, new Coord(x + 2, y, z + 5));
		
		// east wall
		editor.fillRectSolid(rand, x + 5, y + 1, z - 4, x + 5, y + 4, z + 5, stonebrick);
		
		MetaBlock lime = ColorBlock.get(ColorBlock.WOOL, DyeColor.LIME);
		MetaBlock greenBlock = RogueConfig.getBoolean(RogueConfig.PRECIOUSBLOCKS) ? BlockType.get(BlockType.EMERALD_BLOCK) : lime;
		
		editor.fillRectSolid(rand, x + 5, y, z - 1, x + 5, y + 4, z - 1, greenBlock, true, true);
		editor.fillRectSolid(rand, x + 5, y, z, x + 5, y, z + 1, greenBlock, true, true);
		editor.fillRectSolid(rand, x + 5, y + 1, z, x + 5, y + 1, z + 1, air);
		greenBlock.setBlock(editor, new Coord(x + 5, y + 1, z + 2));
		editor.fillRectSolid(rand, x + 5, y + 2, z, x + 5, y + 2, z + 1, greenBlock, true, true);
		editor.fillRectSolid(rand, x + 5, y + 3, z, x + 5, y + 3, z + 1, air);
		greenBlock.setBlock(editor, new Coord(x + 5, y + 3, z + 2));
		editor.fillRectSolid(rand, x + 5, y + 4, z, x + 5, y + 4, z + 1, greenBlock, true, true);
		
		// roof
		editor.fillRectSolid(rand, x - 4, y + 5, z - 5, x + 4, y + 6, z + 6, BlockType.get(BlockType.STONE_SMOOTH));
		editor.fillRectSolid(rand, x - 2, y + 5, z - 1, x + 2, y + 5, z - 1, stair.setOrientation(Cardinal.SOUTH, true), true, true);
		editor.fillRectSolid(rand, x - 2, y + 5, z + 2, x + 2, y + 5, z + 2, stair.setOrientation(Cardinal.NORTH, true), true, true);
		editor.fillRectSolid(rand, x - 3, y + 5, z, x - 3, y + 5, z + 1, stair.setOrientation(Cardinal.EAST, true), true, true);
		editor.fillRectSolid(rand, x + 3, y + 5, z, x + 3, y + 5, z + 1, stair.setOrientation(Cardinal.WEST, true), true, true);
		editor.fillRectSolid(rand, x - 2, y + 5, z, x + 2, y + 5, z + 1, BlockType.get(BlockType.REDSTONE_LAMP));
		
		editor.setBlock(x - 3, y + 5, z - 1, oakLog);
		editor.setBlock(x + 3, y + 5, z - 1, oakLog);
		
		editor.setBlock(x - 3, y + 5, z + 2, oakLog);
		editor.setBlock(x + 3, y + 5, z + 2, oakLog);
		
		
		// table
		editor.fillRectSolid(rand, x - 2, y, z, x - 2, y, z + 1, stair.setOrientation(Cardinal.WEST, true), true, true);
		editor.fillRectSolid(rand, x + 2, y, z, x + 2, y, z + 1, stair.setOrientation(Cardinal.EAST, true), true, true);
		MetaBlock spruceSlabUpsideDown = Slab.get(Slab.SPRUCE, true, false, false);
		editor.fillRectSolid(rand, x - 1, y, z, x + 1, y, z + 1, spruceSlabUpsideDown, true, true);
		
		// chairs
		IStair chair = new MetaStair(StairType.NETHERBRICK);
		chair.setOrientation(Cardinal.SOUTH, false);
		chair.setBlock(editor, rand, new Coord(x - 1, y, z - 2));
		chair.setBlock(editor, rand, new Coord(x + 1, y, z - 2));
		
		chair.setOrientation(Cardinal.NORTH, false);
		chair.setBlock(editor, rand, new Coord(x - 1, y, z + 3));
		chair.setBlock(editor, rand, new Coord(x + 1, y, z + 3));
		
		// wall entrances
		editor.fillRectSolid(rand, x - 7, y - 1, z - 4, x - 6, y + 4, z + 4, stonebrick);
		editor.fillRectSolid(rand, x + 6, y - 1, z - 4, x + 7, y + 4, z + 4, stonebrick);
		
		ITreasureChest recordChest = Treasure.generate(editor, rand, settings, new Coord(x - 4, y, z - 4), Treasure.EMPTY, 1, false); 
		chests.add(recordChest);
		recordChest.setSlot(recordChest.getSize() / 2, Record.getRecord(Record.STAL));
		editor.setBlock(x - 3, y, z - 4, BlockType.get(BlockType.JUKEBOX));
		
		ITreasureChest bdubsChest = Treasure.generate(editor, rand, settings, new Coord(x - 3, y, z + 5), Treasure.EMPTY, 1, false);
		bdubsChest.setSlot((bdubsChest.getSize() / 2) - 2, ItemNovelty.getItem(ItemNovelty.BDOUBLEO));
		chests.add(bdubsChest);
		ItemStack shirt = new ItemStack(Items.leather_chestplate);
		Loot.setItemName(shirt, "Pink Sweater", null);
		Loot.setItemLore(shirt, "\"It's chinese red!\"");
		ItemArmour.dyeArmor(shirt, 250, 96, 128);
		
		bdubsChest.setSlot((bdubsChest.getSize() / 2) + 2, shirt);
		
		ITreasureChest gennybChest = Treasure.generate(editor, rand, settings, new Coord(x + 3, y, z + 5), Treasure.EMPTY, 1, false);
		gennybChest.setSlot(gennybChest.getSize() / 2, ItemNovelty.getItem(ItemNovelty.GENERIKB));
		chests.add(gennybChest);
		
		editor.setBlock(x + 4, y, z - 4, BlockType.get(BlockType.SHELF));
		editor.setBlock(x + 4, y + 1, z - 4, BrewingStand.get());
		
		ITreasureChest contraband = Treasure.generate(editor, rand, settings, new Coord(x + 3, y, z - 4), Treasure.EMPTY, 1, false);		
		for(int i = 0; i < 8; ++i){
			ItemStack booze = PotionMixture.getBooze(rand);
			contraband.setRandomEmptySlot(booze);
		}
		chests.add(contraband);
		
		return true;
	}

	private static void lamp(WorldEditor editor, Random rand, Coord pos){
		
		MetaBlock spruce = Wood.getPlank(Wood.SPRUCE);
		MetaBlock fence = Wood.getFence(Wood.SPRUCE);
		Coord cursor = new Coord(pos);
		cursor.add(Cardinal.UP, 4);
		spruce.setBlock(editor, new Coord(cursor));
		cursor.add(Cardinal.DOWN);
		editor.setBlock(cursor, Wood.getPlank(Wood.OAK));
		cursor.add(Cardinal.DOWN);
		editor.setBlock(cursor, BlockType.get(BlockType.GLOWSTONE));
		
		for(Cardinal dir : Cardinal.directions){
			MetaBlock trapdoor = Trapdoor.get(Trapdoor.OAK, Cardinal.reverse(dir), false, true);
			Coord p = new Coord(cursor);
			p.add(dir);
			trapdoor.setBlock(editor, rand, p, true, false);
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
