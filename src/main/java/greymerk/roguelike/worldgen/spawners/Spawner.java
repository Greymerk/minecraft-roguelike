package greymerk.roguelike.worldgen.spawners;

import java.util.Random;

import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityBat;

public enum Spawner {
	
	CREEPER(EntityList.classToStringMapping.get(EntityCreeper.class)),
	CAVESPIDER(EntityList.classToStringMapping.get(EntityCaveSpider.class)),
	SPIDER(EntityList.classToStringMapping.get(EntitySpider.class)),
	SKELETON(EntityList.classToStringMapping.get(EntitySkeleton.class)),
	ZOMBIE(EntityList.classToStringMapping.get(EntityZombie.class)),
	SILVERFISH(EntityList.classToStringMapping.get(EntitySilverfish.class)),
	ENDERMAN(EntityList.classToStringMapping.get(EntityEnderman.class)),
	WITCH(EntityList.classToStringMapping.get(EntityWitch.class)),
	WITHERBOSS(EntityList.classToStringMapping.get(EntityWither.class)),
	BAT(EntityList.classToStringMapping.get(EntityBat.class)),
	LAVASLIME(EntityList.classToStringMapping.get(EntityMagmaCube.class)),
	BLAZE(EntityList.classToStringMapping.get(EntityBlaze.class)),
	SLIME(EntityList.classToStringMapping.get(EntitySlime.class)),
	PRIMEDTNT(EntityList.classToStringMapping.get(EntityTNTPrimed.class)),
	PIGZOMBIE(EntityList.classToStringMapping.get(EntityPigZombie.class));
	
	private String name;
	Spawner(String name){
		this.name = name;
	}
	
	private static final Spawner[] common = {SPIDER, SKELETON, ZOMBIE};
	
	public static String getName(Spawner type) {
		return type.name;
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
