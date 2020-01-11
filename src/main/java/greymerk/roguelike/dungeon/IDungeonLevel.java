package greymerk.roguelike.dungeon;

import java.util.Random;

import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.filter.IFilter;

public interface IDungeonLevel {

  LevelSettings getSettings();

  boolean hasNearbyNode(Coord pos);

  ILevelLayout getLayout();

  void encase(IWorldEditor editor, Random rand);

  void generate(ILevelGenerator generator, Coord start);

  void applyFilters(IWorldEditor editor, Random rand);

  void filter(IWorldEditor editor, Random rand, IFilter filter);
}
