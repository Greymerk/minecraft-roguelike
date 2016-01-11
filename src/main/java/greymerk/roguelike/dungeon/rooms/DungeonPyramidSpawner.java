package greymerk.roguelike.dungeon.rooms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.treasure.Treasure;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.Spawner;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;

public class DungeonPyramidSpawner extends DungeonBase {
	
	public boolean generate(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances, Coord origin) {
		
		int x = origin.getX();
		int y = origin.getY();
		int z = origin.getZ();
		
		ITheme theme = settings.getTheme();
		
		IBlockFactory blocks = theme.getPrimaryWall();
		IBlockFactory pillar = theme.getPrimaryPillar();
		MetaBlock air = BlockType.get(BlockType.AIR);
		
		// fill air inside
		editor.fillRectSolid(rand, new Coord(x - 3, y, z - 3), new Coord(x + 3, y + 3, z + 3), air, true, true);
		
		
		// shell
		editor.fillRectHollow(rand, new Coord(x - 4, y - 1, z - 4), new Coord(x + 4, y + 4, z + 4), blocks, false, true);
		editor.fillRectSolid(rand, new Coord(x - 3, y + 4, z - 3), new Coord(x + 3, y + 6, z + 3), blocks, false, true);
		editor.fillRectSolid(rand, new Coord(x - 2, y + 4, z - 2), new Coord(x + 2, y + 4, z + 2), air, true, true);

		editor.fillRectSolid(rand, new Coord(x - 4, y - 1, z - 4), new Coord(x + 4, y - 1, z + 4), theme.getPrimaryFloor(), false, true);
		
		Coord start;
		Coord end;
		Coord cursor;

		cursor = new Coord(x, y, z);
		cursor.add(Cardinal.UP, 5);
		air.setBlock(editor, cursor);
		cursor.add(Cardinal.UP, 1);
		editor.setBlock(rand, cursor, blocks, true, true);
		
		cursor = new Coord(x, y, z);
		cursor.add(Cardinal.UP, 5);
		air.setBlock(editor, cursor);
		cursor.add(Cardinal.UP);
		air.setBlock(editor, cursor);
		
		// Chests
		List<Coord> space = new ArrayList<Coord>();
		
		for(Cardinal dir : Cardinal.directions){
			
			// pillar
			cursor = new Coord(x, y, z);
			cursor.add(dir, 3);
			cursor.add(Cardinal.orthogonal(dir)[0], 3);
			start = new Coord(cursor);
			cursor.add(Cardinal.UP, 3);
			end = new Coord(cursor);
			editor.fillRectSolid(rand, start, end, pillar, true, true);
			cursor.add(Cardinal.UP, 1);
			editor.setBlock(rand, cursor, blocks, true, true);
			
			cursor = new Coord(x, y, z);
			cursor.add(Cardinal.UP, 4);
			cursor.add(dir, 2);
			blocks.setBlock(editor, rand, cursor);
			cursor.add(Cardinal.orthogonal(dir)[0], 2);
			blocks.setBlock(editor, rand, cursor);
			
			cursor = new Coord(x, y, z);
			cursor.add(Cardinal.UP, 5);
			cursor.add(Cardinal.orthogonal(dir)[0]);
			air.setBlock(editor, cursor);
			cursor.add(Cardinal.UP);
			air.setBlock(editor, cursor);
			
			for(Cardinal orth : Cardinal.orthogonal(dir)){
				
				cursor = new Coord(x, y, z);
				cursor.add(Cardinal.UP, 3);
				cursor.add(orth);
				cursor.add(dir, 3);
				blocks.setBlock(editor, rand, cursor);
				
				cursor = new Coord(x, y, z);
				cursor.add(Cardinal.UP, 4);
				cursor.add(dir, 2);
				blocks.setBlock(editor, rand, cursor);
				
				cursor = new Coord(x, y, z);
				cursor.add(dir, 3);
				cursor.add(orth, 2);
				space.add(cursor);
			}
		}

		List<Treasure> types = new ArrayList<Treasure>(Arrays.asList(Treasure.ARMOUR, Treasure.WEAPONS, Treasure.TOOLS));
		Treasure.createChests(editor, rand, 1, space, types, Dungeon.getLevel(origin.getY()));
		
		Spawner.generate(editor, rand, settings, new Coord(x, y, z));

		return true;
	}
	
	public boolean isValidDungeonLocation(IWorldEditor editor, int x, int y, int z) {
		return false;
	}
	
	public int getSize(){
		return 4;
	}
}
