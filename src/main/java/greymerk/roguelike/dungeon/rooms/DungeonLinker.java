package greymerk.roguelike.dungeon.rooms;

import java.util.Random;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;

public class DungeonLinker extends DungeonBase{

	@Override
	public boolean generate(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances, Coord origin) {
		
		ITheme theme = settings.getTheme();
		
		IBlockFactory pillar = theme.getPrimaryPillar();
		IBlockFactory wall = theme.getPrimaryWall();
		IBlockFactory floor = theme.getPrimaryFloor();
		MetaBlock bars = BlockType.get(BlockType.IRON_BAR);

		Coord start;
		Coord end;
		
		start = new Coord(origin);
		end = new Coord(origin);
		start.add(-4, -1, -4);
		end.add(4, 9, 4);
		wall.fillRectHollow(editor, rand, start, end, false, true);
		
		start = new Coord(origin);
		end = new Coord(origin);
		start.add(-4, 9, -4);
		end.add(4, 9, 4);
		wall.fillRectSolid(editor, rand, start, end, true, true);
		
		start = new Coord(origin);
		end = new Coord(origin);
		start.add(-4, -1, -4);
		end.add(4, -1, 4);
		floor.fillRectSolid(editor, rand, start, end, true, true);
		
		for(Cardinal dir : Cardinal.directions){
			
			Cardinal[] orth = Cardinal.getOrthogonal(dir);
			
			start = new Coord(origin);
			start.add(dir, 4);
			end = new Coord(start);
			end.add(Cardinal.UP, 8);
			start.add(Cardinal.DOWN);
			start.add(orth[0], 4);
			end.add(orth[1], 4);
			bars.fillRectSolid(editor, rand, start, end, true, false);
			
			start = new Coord(origin);
			end = new Coord(origin);
			start.add(dir, 3);
			start.add(orth[0], 3);
			end.add(dir, 4);
			end.add(orth[0], 4);
			end.add(Cardinal.UP, 8);
			pillar.fillRectSolid(editor, rand, start, end, true, true);
		}
		

		
		
		return true;
	}

	@Override
	public int getSize() {
		return 6;
	}

}
