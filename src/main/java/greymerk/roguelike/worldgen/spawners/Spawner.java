package greymerk.roguelike.worldgen.spawners;

import java.util.Random;

import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;

public enum Spawner {
	
	CREEPER, CAVESPIDER, SPIDER, SKELETON, ZOMBIE, SILVERFISH, ENDERMAN, WITCH, WITHERBOSS, BAT,
	LAVASLIME, BLAZE, SLIME, PRIMEDTNT, PIGZOMBIE;
	
	private static final Spawner[] common = {SPIDER, SKELETON, ZOMBIE};
	
	public static String getName(Spawner type) {
		switch(type){
		case CREEPER: return "Creeper";
		case CAVESPIDER: return "CaveSpider";
		case SPIDER: return "Spider";
		case SKELETON: return "Skeleton";
		case ZOMBIE: return "Zombie";
		case SILVERFISH: return "Silverfish";
		case ENDERMAN: return "Enderman";
		case WITCH:	return "Witch";
		case WITHERBOSS: return "WitherBoss";
		case BAT: return "Bat";
		case LAVASLIME:	return "LavaSlime";
		case BLAZE:	return "Blaze";
		case SLIME:	return "Slime";
		case PRIMEDTNT:	return "PrimedTnt";
		case PIGZOMBIE:	return "PigZombie";
		default: return "pig";
		}
	}

	public static void generate(IWorldEditor editor, Random rand, LevelSettings settings, Coord cursor){
		Spawner type = common[rand.nextInt(common.length)];
		generate(editor, rand, settings, cursor, type);
	}
	
	public static void generate(IWorldEditor editor, Random rand, LevelSettings settings, Coord cursor, Spawner type) {
		
		int difficulty = settings.getDifficulty(cursor);
		
		
		SpawnerSettings spawners = settings.getSpawners();
		if(spawners == null){
			new Spawnable(type).generate(editor, rand, cursor, difficulty);
			return;
		}
		
		spawners.generate(editor, rand, cursor, type, difficulty);
	}
}
