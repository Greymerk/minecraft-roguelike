package greymerk.roguelike.dungeon.tasks;

import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.DungeonTunnel;
import greymerk.roguelike.dungeon.IDungeon;
import greymerk.roguelike.dungeon.IDungeonLevel;
import greymerk.roguelike.dungeon.settings.ISettings;
import greymerk.roguelike.worldgen.IWorldEditor;

public class DungeonTaskTunnels implements IDungeonTask {

  @Override
  public void execute(IWorldEditor editor, Random rand, IDungeon dungeon, ISettings settings) {
    List<IDungeonLevel> levels = dungeon.getLevels();

    // generate tunnels
    for (IDungeonLevel level : levels) {
      for (DungeonTunnel t : level.getLayout().getTunnels()) {
        t.construct(editor, rand, level.getSettings());
      }
    }
  }
}
