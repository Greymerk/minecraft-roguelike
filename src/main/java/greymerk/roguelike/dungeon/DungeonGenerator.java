package greymerk.roguelike.dungeon;

import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.settings.ISettings;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.dungeon.tasks.IDungeonTask;
import greymerk.roguelike.dungeon.tasks.IDungeonTaskRegistry;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;

public class DungeonGenerator {

  public static void generate(IWorldEditor editor, IDungeon dungeon, ISettings settings, IDungeonTaskRegistry tasks) {
    Coord start = dungeon.getPosition();
    Random rand = Dungeon.getRandom(editor, start);
    List<IDungeonLevel> levels = dungeon.getLevels();
    int numLevels = settings.getNumLevels();

    // create level objects
    for (int i = 0; i < numLevels; ++i) {
      LevelSettings levelSettings = settings.getLevelSettings(i);
      DungeonLevel level = new DungeonLevel(editor, rand, levelSettings, new Coord(start));
      levels.add(level);
    }

    for (DungeonStage stage : DungeonStage.values()) {
      for (IDungeonTask task : tasks.getTasks(stage)) {
        task.execute(editor, rand, dungeon, settings);
      }
    }
  }
}
