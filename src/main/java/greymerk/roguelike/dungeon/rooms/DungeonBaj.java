package greymerk.roguelike.dungeon.rooms;

import java.util.Random;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.treasure.ITreasureChest;
import greymerk.roguelike.treasure.TreasureChestEmpty;
import greymerk.roguelike.treasure.loot.provider.ItemNovelty;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.Crops;
import greymerk.roguelike.worldgen.redstone.Torch;

public class DungeonBaj extends DungeonBase {

	@Override
	public boolean generate(WorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances, Coord origin) {

		
		int x = origin.getX();
		int y = origin.getY();
		int z = origin.getZ();
		
		//WorldGenPrimitive.fillRectHollow(rand, x - 5, y - 2, z - 5, x + 5, y + 4, z + 5, new MetaBlock(Blocks.stone), true, true);
		editor.fillRectSolid(rand, x - 4, y - 1, z - 4, x + 4, y + 3, z + 4, BlockType.get(BlockType.AIR), true, true);
		
		MetaBlock dirt = BlockType.get(BlockType.DIRT);
		
		editor.setBlock(rand, x - 5, y, z, dirt, true, false);
		editor.setBlock(rand, x + 5, y, z, dirt, true, false);
		editor.setBlock(rand, x, y, z - 5, dirt, true, false);
		editor.setBlock(rand, x, y, z + 5, dirt, true, false);
		
		editor.fillRectSolid(rand, x - 3, y - 1, z - 3, x + 3, y - 1, z + 3, BlockType.get(BlockType.WATER_FLOWING));
		
		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.addBlock(BlockType.get(BlockType.STONE_SMOOTH), 100);
		walls.addBlock(BlockType.get(BlockType.DIRT), 20);
		walls.addBlock(BlockType.get(BlockType.GRAVEL), 40);
		
		editor.fillRectSolid(rand, x - 4, y - 1, z - 4, x + 4, y - 1, z - 4, walls);
		editor.fillRectSolid(rand, x - 4, y - 1, z - 4, x - 4, y - 1, z + 4, walls);
		editor.fillRectSolid(rand, x - 4, y - 1, z + 4, x + 4, y - 1, z + 4, walls);
		editor.fillRectSolid(rand, x + 4, y - 1, z - 4, x + 4, y - 1, z + 4, walls);
		
		editor.fillRectSolid(rand, x - 4, y, z - 4, x - 4, y + 3, z - 4, walls);
		editor.fillRectSolid(rand, x - 3, y, z - 4, x - 3, y + 3, z - 4, walls);
		editor.fillRectSolid(rand, x - 4, y, z - 3, x - 4, y + 3, z - 3, walls);
		editor.fillRectSolid(rand, x - 3, y - 1, z - 3, x - 3, y, z - 3, walls);
		Torch.generate(editor, Torch.WOODEN, Cardinal.UP, new Coord(x - 3, y + 1, z - 3));
		walls.setBlock(editor, rand, new Coord(x - 3, y - 1, z - 2));
		walls.setBlock(editor, rand, new Coord(x - 2, y - 1, z - 3));

		editor.fillRectSolid(rand, x - 4, y, z + 4, x - 4, y + 3, z + 4, walls);
		editor.fillRectSolid(rand, x - 3, y, z + 4, x - 3, y + 3, z + 4, walls);
		editor.fillRectSolid(rand, x - 4, y, z + 3, x - 4, y + 3, z + 3, walls);
		editor.fillRectSolid(rand, x - 3, y - 1, z + 3, x - 3, y, z + 3, walls);
		Torch.generate(editor, Torch.WOODEN, Cardinal.UP, new Coord(x - 3, y + 1, z + 3));

		walls.setBlock(editor, rand, new Coord(x - 3, y - 1, z + 2));
		walls.setBlock(editor, rand, new Coord(x - 2, y - 1, z + 3));
		
		editor.fillRectSolid(rand, x + 4, y, z - 4, x + 4, y + 3, z - 4, walls);
		editor.fillRectSolid(rand, x + 3, y, z - 4, x + 3, y + 3, z - 4, walls);
		editor.fillRectSolid(rand, x + 4, y, z - 3, x + 4, y + 3, z - 3, walls);
		editor.fillRectSolid(rand, x + 3, y - 1, z - 3, x + 3, y, z - 3, walls);
		Torch.generate(editor, Torch.WOODEN, Cardinal.UP, new Coord(x + 3, y + 1, z - 3));

		walls.setBlock(editor, rand, new Coord(x + 3, y - 1, z - 2));
		walls.setBlock(editor, rand, new Coord(x + 2, y - 1, z - 3));
		
		editor.fillRectSolid(rand, x + 4, y, z + 4, x + 4, y + 3, z + 4, walls);
		editor.fillRectSolid(rand, x + 3, y, z + 4, x + 3, y + 3, z + 4, walls);
		editor.fillRectSolid(rand, x + 4, y, z + 3, x + 4, y + 3, z + 3, walls);
		editor.fillRectSolid(rand, x + 3, y - 1, z + 3, x + 3, y, z + 3, walls);
		Torch.generate(editor, Torch.WOODEN, Cardinal.UP, new Coord(x + 3, y + 1, z + 3));

		walls.setBlock(editor, rand, new Coord(x + 3, y - 1, z + 2));
		walls.setBlock(editor, rand, new Coord(x + 2, y - 1, z + 3));
		
		BlockWeightedRandom roof = new BlockWeightedRandom();
		roof.addBlock(BlockType.get(BlockType.STONE_SMOOTH), 100);
		roof.addBlock(BlockType.get(BlockType.AIR), 50);
		roof.addBlock(BlockType.get(BlockType.DIRT), 20);
		
		editor.fillRectSolid(rand, x - 4, y + 3, z - 4, x + 4, y + 3, z + 4, roof, true, false);
		
		crops(editor, rand, x - 1, y - 1, z - 3);
		crops(editor, rand, x - 2, y - 1, z - 2);
		crops(editor, rand, x - 1, y - 1, z - 2);
		crops(editor, rand, x + 1, y - 1, z - 2);
		crops(editor, rand, x + 2, y - 1, z - 2);
		crops(editor, rand, x + 3, y - 1, z - 1);
		crops(editor, rand, x - 3, y - 1, z);
		crops(editor, rand, x - 2, y - 1, z);
		crops(editor, rand, x + 1, y - 1, z);
		crops(editor, rand, x - 2, y - 1, z + 1);
		crops(editor, rand, x, y - 1, z + 1);
		crops(editor, rand, x + 2, y - 1, z + 2);
		crops(editor, rand, x - 1, y - 1, z + 3);
		crops(editor, rand, x, y - 1, z + 3);
		crops(editor, rand, x + 1, y - 1, z + 3);
		
		editor.setBlock(x, y - 1, z, BlockType.get(BlockType.DIRT));
		ITreasureChest chest = new TreasureChestEmpty().generate(editor, rand, settings.getLoot(), new Coord(x, y, z), 0, false);
		chest.setInventorySlot(chest.getSize() / 2, ItemNovelty.getItem(ItemNovelty.BAJ));
		
		return false;
	}

	@Override
	public int getSize() {
		return 8;
	}

	private void crops(WorldEditor editor, Random rand, int x, int y, int z){
		
		if(rand.nextInt(10) == 0){
			editor.setBlock(x, y, z, BlockType.get(BlockType.GRAVEL));
			return;
		}
		
		if(rand.nextInt(5) == 0){
			editor.setBlock(x, y, z, BlockType.get(BlockType.DIRT));
			editor.setBlock(x, y + 1, z, BlockType.get(BlockType.REEDS));
			return;
		}
		
		editor.setBlock(x, y, z, BlockType.get(BlockType.FARMLAND));
		editor.setBlock(x, y + 1, z, Crops.get(Crops.WHEAT));
	}
	
}
