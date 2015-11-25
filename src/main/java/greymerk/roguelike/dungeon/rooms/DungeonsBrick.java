package greymerk.roguelike.dungeon.rooms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.Spawner;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;

public class DungeonsBrick extends DungeonBase {

		
	public DungeonsBrick(){
	}
	
	public boolean generate(WorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances, Coord origin) {
		
		int x = origin.getX();
		int y = origin.getY();
		int z = origin.getZ();
		
		ITheme theme = settings.getTheme();
		
		IStair stair = theme.getPrimaryStair();
		IBlockFactory blocks = theme.getPrimaryWall();
		IBlockFactory pillar = theme.getPrimaryPillar();
		MetaBlock air = BlockType.get(BlockType.AIR);
		
		// fill air inside
		editor.fillRectSolid(rand, x - 3, y, z - 3, x + 3, y + 3, z + 3, air);
		editor.fillRectSolid(rand, x - 1, y + 4, z - 1, x + 1, y + 4, z + 1, air);
		
		// shell
		editor.fillRectHollow(rand, x - 4, y - 1, z - 4, x + 4, y + 4, z + 4, blocks, false, true);

		editor.fillRectSolid(rand, new Coord(x - 4, y - 1, z - 4), new Coord(x + 4, y - 1, z + 4), theme.getPrimaryFloor(), false, true);
		
		Coord start;
		Coord end;
		Coord cursor;

		
		cursor = new Coord(x, y, z);
		cursor.add(Cardinal.UP, 5);
		air.setBlock(editor, cursor);
		cursor.add(Cardinal.UP, 1);
		editor.setBlock(rand, cursor, blocks, true, true);
		
		// Chests
		List<Coord> space = new ArrayList<Coord>();
		
		for(Cardinal dir : Cardinal.directions){
			
			// top
			cursor = new Coord(x, y, z);
			cursor.add(dir, 1);
			cursor.add(Cardinal.UP, 5);
			stair.setOrientation(Cardinal.reverse(dir), true);
			editor.setBlock(rand, cursor, stair, false, true);
			cursor.add(Cardinal.getOrthogonal(dir)[0], 1);
			editor.setBlock(rand, cursor, blocks, false, true);

			cursor = new Coord(x, y, z);
			cursor.add(dir, 2);
			cursor.add(Cardinal.UP, 4);
			air.setBlock(editor, cursor);
			cursor.add(Cardinal.UP, 1);
			editor.setBlock(rand, cursor, blocks, false, true);
			
			// pillar
			cursor = new Coord(x, y, z);
			cursor.add(dir, 3);
			cursor.add(Cardinal.getOrthogonal(dir)[0], 3);
			start = new Coord(cursor);
			cursor.add(Cardinal.UP, 2);
			end = new Coord(cursor);
			editor.fillRectSolid(rand, start, end, pillar, true, true);
			cursor.add(Cardinal.UP, 1);
			editor.setBlock(rand, cursor, blocks, true, true);
			
			// pillar stairs
			for(Cardinal orth : Cardinal.getOrthogonal(dir)){
				cursor = new Coord(x, y, z);
				cursor.add(dir, 3);
				cursor.add(orth, 2);
				cursor.add(Cardinal.UP, 3);
				stair.setOrientation(Cardinal.reverse(orth), true);
				editor.setBlock(rand, cursor, stair, true, true);
			}

			// layer above pillars
			cursor = new Coord(x, y, z);
			cursor.add(dir, 2);
			cursor.add(Cardinal.getOrthogonal(dir)[0], 2);
			cursor.add(Cardinal.UP, 4);
			editor.setBlock(rand, cursor, blocks, false, true);
			
			for(Cardinal orth : Cardinal.getOrthogonal(dir)){
				cursor = new Coord(x, y, z);
				cursor.add(Cardinal.UP, 4);
				cursor.add(dir, 2);
				cursor.add(orth, 1);
				stair.setOrientation(Cardinal.reverse(orth), true);
				editor.setBlock(rand, cursor, stair, false, true);
			}
			
			cursor = new Coord(x, y, z);
			cursor.add(dir, 1);
			cursor.add(Cardinal.getOrthogonal(dir)[0], 1);
			cursor.add(Cardinal.UP, 5);
			editor.setBlock(rand, cursor, blocks, false, true);
			
			for(Cardinal orth : Cardinal.getOrthogonal(dir)){
				cursor = new Coord(x, y, z);
				cursor.add(dir, 3);
				cursor.add(orth, 2);
				space.add(cursor);
			}
		}

		List<TreasureChest> types = new ArrayList<TreasureChest>(Arrays.asList(TreasureChest.ARMOUR, TreasureChest.WEAPONS, TreasureChest.TOOLS));
		TreasureChest.createChests(editor, rand, settings, 1, space, types);
		
		Spawner.generate(editor, rand, settings, new Coord(x, y, z));

		return true;
	}
	
	public boolean isValidDungeonLocation(WorldEditor editor, int x, int y, int z) {
		return false;
	}
	
	public int getSize(){
		return 4;
	}
}
