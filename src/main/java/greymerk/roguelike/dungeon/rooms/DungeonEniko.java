package greymerk.roguelike.dungeon.rooms;

import java.util.List;
import java.util.Random;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.treasure.ITreasureChest;
import greymerk.roguelike.treasure.TreasureChestEmpty;
import greymerk.roguelike.treasure.loot.provider.ItemNovelty;
import greymerk.roguelike.worldgen.BlockFactoryCheckers;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.MetaStair;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.ColorBlock;
import greymerk.roguelike.worldgen.blocks.DyeColor;
import greymerk.roguelike.worldgen.blocks.Quartz;
import greymerk.roguelike.worldgen.blocks.Slab;
import greymerk.roguelike.worldgen.blocks.StairType;


public class DungeonEniko extends DungeonBase {

	@Override
	public boolean generate(WorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances, Coord origin) {
		
		int x = origin.getX();
		int y = origin.getY();
		int z = origin.getZ();
		
		MetaBlock air = BlockType.get(BlockType.AIR);
		MetaBlock coal = BlockType.get(BlockType.COAL_BLOCK);
		MetaBlock netherBrick = BlockType.get(BlockType.NETHERBRICK);
		
		editor.fillRectSolid(rand, x - 5, y, z - 5, x + 5, y + 4, z + 5, air);
		
		pillar(editor, rand, x - 5, y, z - 5);
		pillar(editor, rand, x - 5, y, z - 2);
		pillar(editor, rand, x - 5, y, z + 2);
		pillar(editor, rand, x - 5, y, z + 5);
		
		pillar(editor, rand, x - 2, y, z - 5);
		pillar(editor, rand, x - 2, y, z + 5);
		
		pillar(editor, rand, x + 2, y, z - 5);
		pillar(editor, rand, x + 2, y, z + 5);
		
		pillar(editor, rand, x + 5, y, z - 5);
		pillar(editor, rand, x + 5, y, z - 2);
		pillar(editor, rand, x + 5, y, z + 2);
		pillar(editor, rand, x + 5, y, z + 5);

		IStair shelf = new MetaStair(StairType.STONEBRICK);
		shelf.setOrientation(Cardinal.EAST, true);
		editor.fillRectSolid(rand, x - 6, y + 1, z - 4, x - 6, y + 3, z - 3, coal);
		editor.fillRectSolid(rand, x - 5, y, z - 4, x - 5, y, z - 3, shelf, true, true);
		editor.fillRectSolid(rand, x - 6, y + 1, z + 3, x - 6, y + 3, z + 4, coal);
		editor.fillRectSolid(rand, x - 5, y, z + 3, x - 5, y, z + 4, shelf, true, true);
		
		shelf.setOrientation(Cardinal.WEST, true);
		editor.fillRectSolid(rand, x + 6, y + 1, z - 4, x + 6, y + 3, z - 3, coal);
		editor.fillRectSolid(rand, x + 5, y, z - 4, x + 5, y, z - 3, shelf, true, true);
		editor.fillRectSolid(rand, x + 6, y + 1, z + 3, x + 6, y + 3, z + 4, coal);
		editor.fillRectSolid(rand, x + 5, y, z + 3, x + 5, y, z + 4, shelf, true, true);
		
		shelf.setOrientation(Cardinal.SOUTH, true);
		editor.fillRectSolid(rand, x - 4, y + 1, z - 6, x - 3, y + 3, z - 6, coal);
		editor.fillRectSolid(rand, x - 4, y, z - 5, x - 3, y, z - 5, shelf, true, true);
		editor.fillRectSolid(rand, x + 3, y + 1, z - 6, x + 4, y + 3, z - 6, coal);
		editor.fillRectSolid(rand, x + 3, y, z - 5, x + 4, y, z - 5, shelf, true, true);
		
		shelf.setOrientation(Cardinal.NORTH, true);
		editor.fillRectSolid(rand, x - 4, y + 1, z + 6, x - 3, y + 3, z + 6, coal);
		editor.fillRectSolid(rand, x - 4, y, z + 5, x - 3, y, z + 5, shelf, true, true);
		editor.fillRectSolid(rand, x + 3, y + 1, z + 6, x + 4, y + 3, z + 6, coal);
		editor.fillRectSolid(rand, x + 3, y, z + 5, x + 4, y, z + 5, shelf, true, true);
		
		ITreasureChest eniChest = new TreasureChestEmpty().generate(editor, rand, settings.getLoot(), new Coord(x + 3, y + 1, z + 5), 0, false);
		if(rand.nextBoolean()){
			eniChest.setInventorySlot(eniChest.getSize() / 2, ItemNovelty.getItem(ItemNovelty.ENIKOBOW));
		} else {
			eniChest.setInventorySlot(eniChest.getSize() / 2, ItemNovelty.getItem(ItemNovelty.ENIKOSWORD));
		}
		
		// floor
		editor.fillRectSolid(rand, x - 5, y - 1, z - 5, x + 5, y - 1, z + 5, BlockType.get(BlockType.STONE_BRICK));
		MetaBlock blockOne = RogueConfig.getBoolean(RogueConfig.PRECIOUSBLOCKS) ? BlockType.get(BlockType.LAPIS_BLOCK) : ColorBlock.get(ColorBlock.CLAY, DyeColor.BLUE);
		MetaBlock blockTwo = Quartz.get(Quartz.SMOOTH);
		BlockFactoryCheckers checkers = new BlockFactoryCheckers(blockOne, blockTwo);
		
		editor.fillRectSolid(rand, x - 3, y - 1, z - 3, x + 3, y - 1, z + 3, checkers);
		
		MetaBlock lamp = BlockType.get(BlockType.REDSTONE_LAMP_LIT);
		MetaBlock redBlock = BlockType.get(BlockType.REDSTONE_BLOCK);
		
		editor.setBlock(x - 2, y - 1, z - 2, lamp);
		editor.setBlock(x - 2, y - 2, z - 2, redBlock);
		
		editor.setBlock(x - 2, y - 1, z + 2, lamp);
		editor.setBlock(x - 2, y - 2, z + 2, redBlock);
		
		editor.setBlock(x + 2, y - 1, z - 2, lamp);
		editor.setBlock(x + 2, y - 2, z - 2, redBlock);
		
		editor.setBlock(x + 2, y - 1, z + 2, lamp);
		editor.setBlock(x + 2, y - 2, z + 2, redBlock);
		
		// roof
		editor.fillRectSolid(rand, x - 6, y + 4, z - 6, x - 4, y + 4, z + 6, netherBrick);
		editor.fillRectSolid(rand, x - 3, y + 4, z - 6, x + 4, y + 4, z - 4, netherBrick);
		editor.fillRectSolid(rand, x - 3, y + 4, z + 4, x + 4, y + 4, z + 6, netherBrick);
		editor.fillRectSolid(rand, x + 4, y + 4, z - 6, x + 6, y + 4, z + 6, netherBrick);
		
		editor.fillRectSolid(rand, x - 4, y + 5, z - 4, x + 4, y + 5, z + 4, netherBrick);
		
		MetaBlock upsideDownNetherSlab = Slab.get(Slab.STONE, true, false, false);
		
		editor.fillRectSolid(rand, x - 3, y + 4, z - 3, x - 3, y + 4, z + 3, upsideDownNetherSlab, true, true);
		editor.fillRectSolid(rand, x + 3, y + 4, z - 3, x + 3, y + 4, z + 3, upsideDownNetherSlab, true, true);
		
		editor.fillRectSolid(rand, x - 2, y + 4, z - 3, x + 2, y + 4, z - 3, upsideDownNetherSlab, true, true);
		editor.fillRectSolid(rand, x - 2, y + 4, z + 3, x + 2, y + 4, z + 3, upsideDownNetherSlab, true, true);
		
		return true;
	}

	
	private static void pillar(WorldEditor editor, Random rand, int x, int y, int z){
		
		IStair stair = new MetaStair(StairType.STONEBRICK);
		editor.fillRectSolid(rand, x, y, z, x, y + 3, z, BlockType.get(BlockType.STONE_BRICK));
		editor.setBlock(rand, x + 1, y + 3, z, stair.setOrientation(Cardinal.EAST, true), true, false);
		editor.setBlock(rand, x - 1, y + 3, z, stair.setOrientation(Cardinal.WEST, true), true, false);
		editor.setBlock(rand, x, y + 3, z + 1, stair.setOrientation(Cardinal.SOUTH, true), true, false);
		editor.setBlock(rand, x, y + 3, z - 1, stair.setOrientation(Cardinal.NORTH, true), true, false);
		
	}
	
	public int getSize(){
		return 7;
	}
	
	@Override
	public boolean validLocation(WorldEditor editor, Cardinal dir, int x, int y, int z){
		
		int size = getSize();
		List<Coord> box = editor.getRectHollow(x - size, y - 2, z - size, x + size, y + 5, z + size);
		
		for(Coord pos : box){
			if(editor.isAirBlock(pos)) return false;
		}
		
		return true;
	}
	
}
