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
import net.minecraft.init.Blocks;

public class DungeonBaj extends DungeonBase {

	@Override
	public boolean generate(WorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances, Coord origin) {

		
		int x = origin.getX();
		int y = origin.getY();
		int z = origin.getZ();
		
		//WorldGenPrimitive.fillRectHollow(rand, x - 5, y - 2, z - 5, x + 5, y + 4, z + 5, new MetaBlock(Blocks.stone), true, true);
		editor.fillRectSolid(rand, x - 4, y - 1, z - 4, x + 4, y + 3, z + 4, new MetaBlock(Blocks.air), true, true);
		
		MetaBlock dirt = new MetaBlock(Blocks.dirt);
		
		editor.setBlock(rand, x - 5, y, z, dirt, true, false);
		editor.setBlock(rand, x + 5, y, z, dirt, true, false);
		editor.setBlock(rand, x, y, z - 5, dirt, true, false);
		editor.setBlock(rand, x, y, z + 5, dirt, true, false);
		
		editor.fillRectSolid(rand, x - 3, y - 1, z - 3, x + 3, y - 1, z + 3, new MetaBlock(Blocks.water));
		
		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.addBlock(new MetaBlock(Blocks.stone), 100);
		walls.addBlock(new MetaBlock(Blocks.dirt), 20);
		walls.addBlock(new MetaBlock(Blocks.gravel), 40);
		
		editor.fillRectSolid(rand, x - 4, y - 1, z - 4, x + 4, y - 1, z - 4, walls);
		editor.fillRectSolid(rand, x - 4, y - 1, z - 4, x - 4, y - 1, z + 4, walls);
		editor.fillRectSolid(rand, x - 4, y - 1, z + 4, x + 4, y - 1, z + 4, walls);
		editor.fillRectSolid(rand, x + 4, y - 1, z - 4, x + 4, y - 1, z + 4, walls);
		
		editor.fillRectSolid(rand, x - 4, y, z - 4, x - 4, y + 3, z - 4, walls);
		editor.fillRectSolid(rand, x - 3, y, z - 4, x - 3, y + 3, z - 4, walls);
		editor.fillRectSolid(rand, x - 4, y, z - 3, x - 4, y + 3, z - 3, walls);
		editor.fillRectSolid(rand, x - 3, y - 1, z - 3, x - 3, y, z - 3, walls);
		editor.setBlock(x - 3, y + 1, z - 3, Blocks.torch);
		walls.setBlock(editor, rand, new Coord(x - 3, y - 1, z - 2));
		walls.setBlock(editor, rand, new Coord(x - 2, y - 1, z - 3));

		editor.fillRectSolid(rand, x - 4, y, z + 4, x - 4, y + 3, z + 4, walls);
		editor.fillRectSolid(rand, x - 3, y, z + 4, x - 3, y + 3, z + 4, walls);
		editor.fillRectSolid(rand, x - 4, y, z + 3, x - 4, y + 3, z + 3, walls);
		editor.fillRectSolid(rand, x - 3, y - 1, z + 3, x - 3, y, z + 3, walls);
		editor.setBlock(x - 3, y + 1, z + 3, Blocks.torch);
		walls.setBlock(editor, rand, new Coord(x - 3, y - 1, z + 2));
		walls.setBlock(editor, rand, new Coord(x - 2, y - 1, z + 3));
		
		editor.fillRectSolid(rand, x + 4, y, z - 4, x + 4, y + 3, z - 4, walls);
		editor.fillRectSolid(rand, x + 3, y, z - 4, x + 3, y + 3, z - 4, walls);
		editor.fillRectSolid(rand, x + 4, y, z - 3, x + 4, y + 3, z - 3, walls);
		editor.fillRectSolid(rand, x + 3, y - 1, z - 3, x + 3, y, z - 3, walls);
		editor.setBlock(x + 3, y + 1, z - 3, Blocks.torch);
		walls.setBlock(editor, rand, new Coord(x + 3, y - 1, z - 2));
		walls.setBlock(editor, rand, new Coord(x + 2, y - 1, z - 3));
		
		editor.fillRectSolid(rand, x + 4, y, z + 4, x + 4, y + 3, z + 4, walls);
		editor.fillRectSolid(rand, x + 3, y, z + 4, x + 3, y + 3, z + 4, walls);
		editor.fillRectSolid(rand, x + 4, y, z + 3, x + 4, y + 3, z + 3, walls);
		editor.fillRectSolid(rand, x + 3, y - 1, z + 3, x + 3, y, z + 3, walls);
		editor.setBlock(x + 3, y + 1, z + 3, Blocks.torch);
		walls.setBlock(editor, rand, new Coord(x + 3, y - 1, z + 2));
		walls.setBlock(editor, rand, new Coord(x + 2, y - 1, z + 3));
		
		BlockWeightedRandom roof = new BlockWeightedRandom();
		roof.addBlock(new MetaBlock(Blocks.stone), 100);
		roof.addBlock(new MetaBlock(Blocks.air), 50);
		roof.addBlock(new MetaBlock(Blocks.dirt), 20);
		
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
		
		editor.setBlock(x, y - 1, z, Blocks.dirt);
		ITreasureChest chest = new TreasureChestEmpty().generate(editor, rand, settings.getLoot(), new Coord(x, y, z), 0, false);
		chest.setInventorySlot(ItemNovelty.getItem(ItemNovelty.BAJ), chest.getInventorySize() / 2);
		
		
		return false;
	}

	@Override
	public int getSize() {
		return 8;
	}

	private void crops(WorldEditor editor, Random rand, int x, int y, int z){
		
		if(rand.nextInt(10) == 0){
			editor.setBlock(x, y, z, Blocks.gravel);
			return;
		}
		
		if(rand.nextInt(5) == 0){
			editor.setBlock(x, y, z, Blocks.dirt);
			editor.setBlock(x, y + 1, z, Blocks.reeds);
			return;
		}
		
		editor.setBlock(x, y, z, Blocks.farmland);
		editor.setBlock(x, y + 1, z, Blocks.wheat);
	}
	
}
