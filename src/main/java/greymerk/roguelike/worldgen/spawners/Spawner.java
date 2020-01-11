package greymerk.roguelike.worldgen.spawners;

import java.util.Random;

import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;

public enum Spawner {

  CREEPER("creeper"),
  CAVESPIDER("cave_spider"),
  SPIDER("spider"),
  SKELETON("skeleton"),
  ZOMBIE("zombie"),
  SILVERFISH("silverfish"),
  ENDERMAN("enderman"),
  WITCH("witch"),
  WITHERBOSS("wither"),
  BAT("bat"),
  LAVASLIME("magma_cube"),
  BLAZE("blaze"),
  SLIME("slime"),
  PRIMEDTNT("tnt"),
  PIGZOMBIE("zombie_pigman");

  private static final Spawner[] common = {SPIDER, SKELETON, ZOMBIE};
  private String name;

  Spawner(String name) {
    this.name = name;
  }

  public static String getName(Spawner type) {
    return "minecraft:" + type.name;
  }


  public static void generate(IWorldEditor editor, Random rand, LevelSettings settings, Coord cursor) {
    Spawner type = common[rand.nextInt(common.length)];
    generate(editor, rand, settings, cursor, type);
  }

  public static void generate(IWorldEditor editor, Random rand, LevelSettings settings, Coord cursor, Spawner type) {

    int difficulty = settings.getDifficulty(cursor);


    SpawnerSettings spawners = settings.getSpawners();
    if (spawners == null) {
      new Spawnable(type).generate(editor, rand, cursor, difficulty);
      return;
    }

    spawners.generate(editor, rand, cursor, type, difficulty);
  }
}
