package greymerk.roguelike.dungeon;

import java.util.Random;

import greymerk.roguelike.dungeon.base.IDungeonRoom;
import greymerk.roguelike.dungeon.rooms.DungeonLinker;
import greymerk.roguelike.dungeon.rooms.DungeonLinkerTop;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.IWorldEditor;

public enum LevelGenerator {

  CLASSIC,
  MST;

  public static ILevelGenerator getGenerator(IWorldEditor editor, Random rand, LevelGenerator type, IDungeonLevel level) {
    switch (type) {
      case CLASSIC:
        return new LevelGeneratorClassic(rand, level.getSettings());
      case MST:
        return new LevelGeneratorMST(editor, rand, level.getSettings());
      default:
        return new LevelGeneratorClassic(rand, level.getSettings());
    }
  }

  public static void generateLevelLink(IWorldEditor editor, Random rand, LevelSettings settings, DungeonNode start, DungeonNode end) {

    IDungeonRoom downstairs = new DungeonLinker();
    downstairs.generate(editor, rand, settings, Cardinal.directions, start.getPosition());

    if (end == null) {
      return;
    }

    IDungeonRoom upstairs = new DungeonLinkerTop();
    upstairs.generate(editor, rand, settings, end.getEntrances(), end.getPosition());

    IStair stair = settings.getTheme().getPrimary().getStair();

    Coord cursor = new Coord(start.getPosition());
    for (int i = 0; i < end.getPosition().getY() - start.getPosition().getY(); i++) {
      editor.spiralStairStep(rand, cursor, stair, settings.getTheme().getPrimary().getPillar());
      cursor.add(Cardinal.UP);
    }
  }
}