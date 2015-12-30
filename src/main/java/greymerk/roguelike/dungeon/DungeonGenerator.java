package greymerk.roguelike.dungeon;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.settings.ISettings;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.dungeon.towers.Tower;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;

public class DungeonGenerator {
	public static final int VERTICAL_SPACING = 10;
	public static final int TOPLEVEL = 50;
	
	private List<IDungeonLevel> levels;
	
	public DungeonGenerator(){
		this.levels = new ArrayList<IDungeonLevel>();
	}
	
	public void generate(IWorldEditor editor, ISettings settings, int inX, int inZ){
		
		int x = inX;
		int y = TOPLEVEL;
		int z = inZ;
		
		Random rand = Dungeon.getRandom(editor, inX, inZ);

		int numLevels = settings.getNumLevels();
		
		Coord start = new Coord(x, y, z);
		
		DungeonNode oldEnd = null;
		
		for (int i = 0; i < numLevels; ++i){
			LevelSettings levelSettings = settings.getLevelSettings(i);
			DungeonLevel level = new DungeonLevel(editor, rand, levelSettings, new Coord(start));
			
			ILevelGenerator generator = LevelGenerator.getGenerator(editor, rand, levelSettings.getGenerator(), level);
			level.generate(generator, new Coord(start), oldEnd);
			
			rand = Dungeon.getRandom(editor, start.getX(), start.getZ());
			oldEnd = generator.getEnd();			
			x = oldEnd.getPosition().getX();
			y = y - VERTICAL_SPACING;
			z = oldEnd.getPosition().getZ();
			start = new Coord(x, y, z);
			levels.add(level);
		}
		
		Tower tower = settings.getTower().getTower();
		rand = Dungeon.getRandom(editor, inX, inZ);
		Tower.get(tower).generate(editor, rand, settings.getTower().getTheme(), new Coord(inX, TOPLEVEL, inZ));
	}
	
	public List<IDungeonLevel> getLevels(){
		return this.levels;
	}
}
