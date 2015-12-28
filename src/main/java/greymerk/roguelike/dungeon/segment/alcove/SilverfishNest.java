package greymerk.roguelike.dungeon.segment.alcove;

import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.segment.IAlcove;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.Spawner;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.SilverfishBlock;

public class SilverfishNest implements IAlcove{

	private static int RECESSED = 6;
	
	@Override
	public void generate(IWorldEditor editor, Random rand, LevelSettings settings, int x, int y, int z, Cardinal dir) {
		
		Coord corridor = new Coord(x, y, z);
		Coord centre = new Coord(x, y, z);
		centre.add(dir, RECESSED);
		
		nest(editor, rand, centre.getX(), centre.getY(), centre.getZ());
		
		Coord start = new Coord(corridor);
		start.add(Cardinal.UP);
		
		Coord end = new Coord(centre);
		end.add(Cardinal.UP);
		end.add(Cardinal.reverse(dir), 1);
		
		editor.fillRectSolid(rand, start, end, BlockType.get(BlockType.AIR), true, true);
		Spawner.generate(editor, rand, settings, centre, Spawner.SILVERFISH);
		
	}

	@Override
	public boolean isValidLocation(IWorldEditor editor, int x, int y, int z, Cardinal dir) {

		Coord centre = new Coord(x, y, z);
		centre.add(dir, RECESSED);
		x = centre.getX();
		y = centre.getY();
		z = centre.getZ();
		
		List<Coord> toCheck = editor.getRectSolid(new Coord(x - 2, y + 1, z - 2), new Coord(x + 2, y + 1, z + 2));

		for(Coord c : toCheck){
			if (editor.isAirBlock(c)) return false;
		}
		
		return true;
	}
	
	private void nest(IWorldEditor editor, Random rand, int x, int y, int z){
		BlockWeightedRandom fish = new BlockWeightedRandom();
		IBlockFactory egg = SilverfishBlock.getJumble();
		fish.addBlock(egg, 20);
		fish.addBlock(BlockType.get(BlockType.SOUL_SAND), 5);
		editor.fillRectHollow(rand, new Coord(x - 2, y, z - 2), new Coord(x + 2, y + 3, z + 2), fish, true, true);
		
		fish.setBlock(editor, rand, new Coord(x - 1, y + 2, z));
		fish.setBlock(editor, rand, new Coord(x + 1, y + 2, z));
		fish.setBlock(editor, rand, new Coord(x, y + 2, z - 1));
		fish.setBlock(editor, rand, new Coord(x, y + 2, z + 1));
		fish.setBlock(editor, rand, new Coord(x, y + 1, z));
		
	}
}
