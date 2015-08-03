package greymerk.roguelike.dungeon.towers;

import java.util.Random;

import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;
import net.minecraft.init.Blocks;

public class PyramidTower implements ITower{

	@Override
	public void generate(WorldEditor editor, Random rand, ITheme theme, int x, int y, int z) {

		final int height = 30;
		final int chamberDepth = 61;
		
		MetaBlock air = new MetaBlock(Blocks.air);
		
		editor.fillPyramidSolid(rand, new Coord(x, chamberDepth, z), height, theme.getPrimaryWall(), true, true);
		
		Coord chamber = new Coord(x, chamberDepth, z);
		
		Coord start = new Coord(chamber);
		Coord end = new Coord(chamber);
		start.add(Cardinal.NORTH, 5);
		start.add(Cardinal.WEST, 5);
		end.add(Cardinal.UP, 5);
		end.add(Cardinal.SOUTH, 5);
		end.add(Cardinal.EAST, 5);
		editor.fillRectSolid(rand, start, end, air, true, true);
		
		start = new Coord(chamber);
		start.add(Cardinal.DOWN);
		end = new Coord(start);
		start.add(Cardinal.NORTH, 5);
		start.add(Cardinal.WEST, 5);
		end.add(Cardinal.SOUTH, 5);
		end.add(Cardinal.EAST, 5);
		editor.fillRectSolid(rand, start, end, theme.getPrimaryWall(), true, true);
		
		Coord passage = chamber;
		passage.add(Cardinal.UP);
		passage.add(Cardinal.WEST, 5);
		
		
		
		for(int i = 0; i < height; ++i){
			passageCell(editor, rand, theme, passage);
			passage.add(Cardinal.WEST);
			passage.add(Cardinal.UP);
		}
	}
	
	private void passageCell(WorldEditor editor, Random rand, ITheme theme, Coord pos){
		Coord start = new Coord(pos);
		Coord end = new Coord(pos);
		
		start.add(Cardinal.UP, 2);
		start.add(Cardinal.NORTH, 2);
		start.add(Cardinal.WEST, 2);
		end.add(Cardinal.DOWN, 2);
		end.add(Cardinal.SOUTH, 2);
		end.add(Cardinal.EAST, 2);
		
		editor.fillRectHollow(rand, start, end, theme.getPrimaryWall(), false, true);
	}

}
