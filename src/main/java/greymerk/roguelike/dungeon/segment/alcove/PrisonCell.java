package greymerk.roguelike.dungeon.segment.alcove;

import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.segment.IAlcove;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.Spawner;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.Door;

public class PrisonCell implements IAlcove{

	private static int RECESSED = 5;
	private ITheme theme;
	
	@Override
	public void generate(IWorldEditor editor, Random rand, LevelSettings settings, int x, int y, int z, Cardinal dir) {
		
		this.theme = settings.getTheme();
		IBlockFactory walls = theme.getPrimaryWall();
		MetaBlock air = BlockType.get(BlockType.AIR);
		MetaBlock plate = BlockType.get(BlockType.PRESSURE_PLATE_STONE);
		
		Coord origin = new Coord(x, y, z);
		
		Coord start = new Coord(origin);
		start.add(dir, RECESSED);
		Coord end = new Coord(start);
		start.add(-2, -1, -2);
		end.add(2, 3, 2);
		walls.fillRectHollow(editor, rand, start, end, true, true);
		
		start = new Coord(origin);
		end = new Coord(origin);
		end.add(dir, RECESSED);
		end.add(Cardinal.UP);
		air.fillRectSolid(editor, rand, start, end, true, true);
		
		Coord cursor = new Coord(origin);
		cursor.add(dir, RECESSED - 1);
		plate.setBlock(editor, cursor);
		cursor.add(Cardinal.DOWN);
		if(rand.nextBoolean()) Spawner.generate(editor, rand, settings, cursor, Spawner.ZOMBIE);
		
		cursor = new Coord(origin);
		cursor.add(dir, 3);
		Door.generate(editor, cursor, Cardinal.reverse(dir), Door.IRON);
		
	}

	@Override
	public boolean isValidLocation(IWorldEditor editor, int x, int y, int z, Cardinal dir) {

		Coord centre = new Coord(x, y, z);
		centre.add(dir, RECESSED);
		x = centre.getX();
		y = centre.getY();
		z = centre.getZ();
		
		List<Coord> toCheck = editor.getRectSolid(new Coord(x - 2, y, z - 2), new Coord(x + 2, y, z + 2));

		for(Coord c : toCheck){
			if (editor.isAirBlock(c)) return false;
		}
		
		return true;
	}
}
