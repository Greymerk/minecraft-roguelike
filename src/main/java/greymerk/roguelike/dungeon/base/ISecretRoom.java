package greymerk.roguelike.dungeon.base;

import java.util.Random;

import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;

public interface ISecretRoom {

  IDungeonRoom genRoom(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal dir, Coord pos);

  int getCount();

  void add(int count);

}
