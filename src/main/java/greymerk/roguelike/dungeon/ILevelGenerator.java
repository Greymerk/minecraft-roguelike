package greymerk.roguelike.dungeon;

import greymerk.roguelike.worldgen.Coord;

public interface ILevelGenerator {

  void generate(Coord start);

  ILevelLayout getLayout();

}
