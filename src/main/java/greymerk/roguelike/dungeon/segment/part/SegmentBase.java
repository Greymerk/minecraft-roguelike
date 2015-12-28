package greymerk.roguelike.dungeon.segment.part;

import java.util.Random;

import greymerk.roguelike.dungeon.IDungeonLevel;
import greymerk.roguelike.dungeon.segment.ISegment;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;

public abstract class SegmentBase implements ISegment {

	public SegmentBase(){
	}
	
	@Override
	public void generate(IWorldEditor editor, Random rand, IDungeonLevel level, Cardinal dir, ITheme theme, int x, int y, int z) {
		
		if(level.hasNearbyNode(new Coord(x, y, z))) return;
		
		if(isValidWall(editor, dir, x, y, z)){
			genWall(editor, rand, level, dir, theme, x, y, z);
		}
	}
	
	protected abstract void genWall(IWorldEditor editor, Random rand, IDungeonLevel level, Cardinal dir, ITheme theme, int x, int y, int z);

	protected boolean isValidWall(IWorldEditor editor, Cardinal wallDirection, int x, int y, int z) {
		
		switch(wallDirection){
		case NORTH:
			if(editor.isAirBlock(new Coord(x - 1, y + 1, z - 2))) return false;
			if(editor.isAirBlock(new Coord(x + 1, y + 1, z - 2))) return false;
			break;
		case SOUTH:
			if(editor.isAirBlock(new Coord(x - 1, y + 1, z + 2))) return false;
			if(editor.isAirBlock(new Coord(x + 1, y + 1, z + 2))) return false;
			break;
		case EAST:
			if(editor.isAirBlock(new Coord(x + 2, y + 1, z - 1))) return false;
			if(editor.isAirBlock(new Coord(x + 2, y + 1, z + 1))) return false;
			break;
		case WEST:
			if(editor.isAirBlock(new Coord(x - 2, y + 1, z - 1))) return false;
			if(editor.isAirBlock(new Coord(x - 2, y + 1, z + 1))) return false;
			break;
		default: return false;
		}
		
		return true;
	}
}
