package greymerk.roguelike.dungeon.segment.part;

import java.util.Random;

import greymerk.roguelike.dungeon.IDungeonLevel;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.redstone.Lever;
import greymerk.roguelike.worldgen.redstone.Torch;
import net.minecraft.init.Blocks;

public class SegmentLamp extends SegmentBase{

	@Override
	protected void genWall(WorldEditor editor, Random rand, IDungeonLevel level, Cardinal dir, ITheme theme, int x, int y, int z) {
		
		
		Coord origin = new Coord(x, y, z);
		IStair stair = theme.getPrimaryStair();
		IBlockFactory wall = theme.getPrimaryWall();
		MetaBlock air = new MetaBlock(Blocks.air);
		
		Coord cursor;
		Coord start;
		Coord end;
		
		Cardinal[] orth = Cardinal.getOrthogonal(dir);
		
		start = new Coord(origin);
		start.add(dir, 2);
		end = new Coord(start);
		start.add(orth[0]);
		end.add(orth[1]);
		end.add(Cardinal.UP, 2);
		editor.fillRectSolid(rand, start, end, air, true, true);
		
		start = new Coord(origin);
		start.add(Cardinal.UP, 3);
		end = new Coord(start);
		start.add(dir);
		start.add(orth[0]);
		end.add(Cardinal.reverse(dir));
		end.add(orth[1]);
		editor.fillRectSolid(rand, start, end, air, true, true);
		
		start = new Coord(origin);
		start.add(dir, 3);
		end = new Coord(start);
		start.add(orth[0]);
		end.add(orth[1]);
		end.add(dir, 2);
		end.add(Cardinal.UP, 6);
		editor.fillRectSolid(rand, start, end, wall, true, true);
		start = new Coord(end);
		start.add(Cardinal.DOWN, 2);
		start.add(Cardinal.reverse(dir), 6);
		start.add(orth[0], 2);
		editor.fillRectSolid(rand, start, end, wall, true, true);
		
		for (Cardinal side : orth){
			
			cursor = new Coord(origin);
			cursor.add(dir, 2);
			cursor.add(side);
			stair.setOrientation(Cardinal.reverse(side), false).setBlock(editor, cursor);
			cursor.add(Cardinal.UP, 2);
			stair.setOrientation(Cardinal.reverse(side), true).setBlock(editor, cursor);
		}
		
		cursor = new Coord(origin);
		cursor.add(Cardinal.UP, 4);
		overheadLight(editor, rand, theme, cursor);
		
		cursor = new Coord(origin);
		cursor.add(Cardinal.UP);
		cursor.add(dir, 2);
		
		Coord lever = new Coord(cursor);
		cursor.add(dir);
		editor.setBlock(cursor, Blocks.hardened_clay);
		Lever.generate(editor, Cardinal.reverse(dir), lever, false);
		cursor.add(dir);
		Torch.generate(editor, Torch.REDSTONE, dir, cursor);
		cursor.add(Cardinal.UP, 2);
		Torch.generate(editor, Torch.REDSTONE, Cardinal.UP, cursor);
		cursor.add(Cardinal.UP, 2);
		start = new Coord(cursor);
		end = new Coord(start);
		end.add(Cardinal.reverse(dir), 3);
		MetaBlock wire = new MetaBlock(Blocks.redstone_wire);
		editor.fillRectSolid(rand, start, end, wire, true, true);
	}
	
	private void overheadLight(WorldEditor editor, Random rand, ITheme theme, Coord origin){
		
		IStair stair = theme.getPrimaryStair();
		
		Coord cursor;
		
		editor.setBlock(origin, Blocks.air);
		
		for(Cardinal dir : Cardinal.directions){
			cursor = new Coord(origin);
			cursor.add(dir);
			stair.setOrientation(Cardinal.reverse(dir), true).setBlock(editor, cursor);
			cursor.add(Cardinal.getOrthogonal(dir)[0]);
			stair.setBlock(editor, cursor);
		}
		
		cursor = new Coord(origin);
		cursor.add(Cardinal.UP);
		editor.setBlock(cursor, Blocks.redstone_lamp);
	}
	

}
