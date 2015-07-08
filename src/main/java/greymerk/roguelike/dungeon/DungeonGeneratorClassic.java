package greymerk.roguelike.dungeon;

import greymerk.roguelike.dungeon.settings.ISettings;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.dungeon.towers.Tower;
import greymerk.roguelike.worldgen.Coord;

import java.util.Random;

import net.minecraft.world.World;

public class DungeonGeneratorClassic {
	public static final int VERTICAL_SPACING = 10;
	public static final int TOPLEVEL = 50;
	
	public DungeonGeneratorClassic(){
		
	}
	
	public void generate(World world, ISettings settings, int inX, int inZ){
		
		int x = inX;
		int y = TOPLEVEL;
		int z = inZ;
		
		Random rand = Dungeon.getRandom(world, inX, inZ);

		int numLevels = settings.getNumLevels();
		
		Coord start = new Coord(x, y, z);
		
		DungeonNode oldEnd = null;
		
		for (int i = 0; i < numLevels; ++i){
			LevelSettings levelSettings = settings.getLevelSettings(i);
			DungeonLevel level = new DungeonLevel(world, rand, levelSettings, new Coord(start));
			
			LevelGeneratorClassic generator = new LevelGeneratorClassic(world, rand, level, new Coord(start), oldEnd);
			level.generate(generator);
			
			rand = Dungeon.getRandom(world, start.getX(), start.getZ());
			oldEnd = generator.getEnd();			
			x = oldEnd.getPosition().getX();
			y = y - VERTICAL_SPACING;
			z = oldEnd.getPosition().getZ();
			start = new Coord(x, y, z);
		}
		
		Tower tower = settings.getTower().getTower();
		rand = Dungeon.getRandom(world, inX, inZ);
		Tower.get(tower).generate(world, rand, settings.getTower().getTheme(), inX, TOPLEVEL, inZ);
	}
}
