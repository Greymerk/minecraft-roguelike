package greymerk.roguelike.dungeon.tasks;

import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.DungeonNode;
import greymerk.roguelike.dungeon.IDungeon;
import greymerk.roguelike.dungeon.IDungeonLevel;
import greymerk.roguelike.dungeon.LevelGenerator;
import greymerk.roguelike.dungeon.settings.ISettings;
import greymerk.roguelike.worldgen.IWorldEditor;

public class DungeonTaskLinks implements IDungeonTask {

  @Override
  public void execute(IWorldEditor editor, Random rand, IDungeon dungeon, ISettings settings) {

    List<IDungeonLevel> levels = dungeon.getLevels();

    // generate level links
    IDungeonLevel previous = null;
    for (IDungeonLevel level : levels) {
      DungeonNode upper = previous == null ? null : previous.getLayout().getEnd();
      DungeonNode lower = level.getLayout().getStart();
      LevelGenerator.generateLevelLink(editor, rand, level.getSettings(), lower, upper);
      previous = level;
    }

  }

}
