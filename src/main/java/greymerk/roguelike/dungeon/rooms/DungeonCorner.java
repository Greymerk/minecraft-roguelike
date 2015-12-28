package greymerk.roguelike.dungeon.rooms;

import java.util.Random;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;

public class DungeonCorner extends DungeonBase {

	@Override
	public boolean generate(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances, Coord origin) {
		
		int x = origin.getX();
		int y = origin.getY();
		int z = origin.getZ();
		ITheme theme = settings.getTheme();
		
		IStair stair = theme.getPrimaryStair();
		IBlockFactory blocks = theme.getPrimaryWall();
		IBlockFactory pillar = theme.getPrimaryPillar();
		MetaBlock air = BlockType.get(BlockType.AIR);
		
		// fill air inside
		editor.fillRectSolid(rand, new Coord(x - 2, y, z - 2), new Coord(x + 2, y + 3, z + 2), air);
		
		// shell
		editor.fillRectHollow(rand, new Coord(x - 3, y - 1, z - 3), new Coord(x + 3, y + 4, z + 3), blocks, false, true);
		
		// floor
		editor.fillRectSolid(rand, new Coord(x - 3, y - 1, z - 3), new Coord(x + 3, y - 1, z + 3), theme.getPrimaryFloor(), false, true);
		
		Coord start;
		Coord end;
		Coord cursor;
		
		cursor = new Coord(x, y, z);
		cursor.add(Cardinal.UP, 4);
		air.setBlock(editor, cursor);
		cursor.add(Cardinal.UP, 1);
		editor.setBlock(rand, cursor, blocks, true, true);
		
		for(Cardinal dir : Cardinal.directions){
			
			cursor = new Coord(x, y, z);
			cursor.add(dir, 2);
			cursor.add(Cardinal.getOrthogonal(dir)[0], 2);
			start = new Coord(cursor);
			cursor.add(Cardinal.UP, 2);
			end = new Coord(cursor);
			editor.fillRectSolid(rand, start, end, pillar, true, true);
			cursor.add(Cardinal.UP, 1);
			editor.setBlock(rand, cursor, blocks, true, true);
			
			cursor = new Coord(x, y, z);
			cursor.add(dir, 1);
			cursor.add(Cardinal.UP, 4);
			stair.setOrientation(Cardinal.reverse(dir), true);
			editor.setBlock(rand, cursor, stair, true, true);
			
			for(Cardinal orth : Cardinal.getOrthogonal(dir)){
				cursor = new Coord(x, y, z);
				cursor.add(dir, 2);
				cursor.add(orth, 1);
				cursor.add(Cardinal.UP, 3);
				stair.setOrientation(Cardinal.reverse(orth), true);
				editor.setBlock(rand, cursor, stair, true, true);
			}
		}
		
		return true;
	}
	
	public int getSize(){
		return 4;
	}

}
