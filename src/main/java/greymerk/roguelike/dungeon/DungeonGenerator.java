package greymerk.roguelike.dungeon;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.settings.ISettings;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.dungeon.towers.Tower;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;

public class DungeonGenerator {
	public static final int VERTICAL_SPACING = 10;
	public static final int TOPLEVEL = 50;
	
	private Coord origin;
	private List<IDungeonLevel> levels;
	
	public DungeonGenerator(){
		this.levels = new ArrayList<IDungeonLevel>();
	}
	
	public void generate(IWorldEditor editor, ISettings settings, Coord pos){
		this.origin = new Coord(pos.getX(), TOPLEVEL, pos.getZ());
		Coord start = new Coord(this.origin);
		Random rand = Dungeon.getRandom(editor, start);
		int numLevels = settings.getNumLevels();
		DungeonNode oldEnd = null;
		
		for (int i = 0; i < numLevels; ++i){
			LevelSettings levelSettings = settings.getLevelSettings(i);
			DungeonLevel level = new DungeonLevel(editor, rand, levelSettings, new Coord(start));
			
			ILevelGenerator generator = LevelGenerator.getGenerator(editor, rand, levelSettings.getGenerator(), level);
			try{
				level.generate(generator, new Coord(start), oldEnd);
			} catch(Exception e){
				e.printStackTrace();
			}
			
			rand = Dungeon.getRandom(editor, start);
			oldEnd = generator.getEnd();
			start = new Coord(oldEnd.getPosition());
			start.add(Cardinal.DOWN, VERTICAL_SPACING);
			levels.add(level);
		}
		
		Tower tower = settings.getTower().getTower();
		rand = Dungeon.getRandom(editor, new Coord(this.origin));
		Tower.get(tower).generate(editor, rand, settings.getTower().getTheme(), new Coord(this.origin));
	}
	
	public List<IDungeonLevel> getLevels(){
		return this.levels;
	}
	
	public boolean isGenerated(){
		return this.origin != null;
	}
	
	public Coord getPosition(){
		if(!this.isGenerated()){
			return null;
		}
		
		return new Coord(this.origin);
	}
}
