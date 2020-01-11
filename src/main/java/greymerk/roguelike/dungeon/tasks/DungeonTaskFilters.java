package greymerk.roguelike.dungeon.tasks;

import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.IDungeon;
import greymerk.roguelike.dungeon.IDungeonLevel;
import greymerk.roguelike.dungeon.settings.ISettings;
import greymerk.roguelike.worldgen.IWorldEditor;

public class DungeonTaskFilters implements IDungeonTask {

  @Override
  public void execute(IWorldEditor editor, Random rand, IDungeon dungeon, ISettings settings) {

    List<IDungeonLevel> levels = dungeon.getLevels();

    for (IDungeonLevel level : levels) {
      level.applyFilters(editor, rand);
    }
  }
}
