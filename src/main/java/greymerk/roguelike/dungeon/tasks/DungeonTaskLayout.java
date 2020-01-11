package greymerk.roguelike.dungeon.tasks;

import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.DungeonNode;
import greymerk.roguelike.dungeon.IDungeon;
import greymerk.roguelike.dungeon.IDungeonLevel;
import greymerk.roguelike.dungeon.ILevelGenerator;
import greymerk.roguelike.dungeon.ILevelLayout;
import greymerk.roguelike.dungeon.LevelGenerator;
import greymerk.roguelike.dungeon.base.DungeonRoom;
import greymerk.roguelike.dungeon.base.IDungeonFactory;
import greymerk.roguelike.dungeon.base.IDungeonRoom;
import greymerk.roguelike.dungeon.settings.ISettings;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;

public class DungeonTaskLayout implements IDungeonTask {

  @Override
  public void execute(IWorldEditor editor, Random rand, IDungeon dungeon, ISettings settings) {
    List<IDungeonLevel> levels = dungeon.getLevels();
    Coord start = dungeon.getPosition();


    // generate level layouts
    for (IDungeonLevel level : levels) {
      ILevelGenerator generator = LevelGenerator.getGenerator(editor, rand, level.getSettings().getGenerator(), level);

      try {
        level.generate(generator, start);
      } catch (Exception e) {
        e.printStackTrace();
      }

      ILevelLayout layout = generator.getLayout();
      rand = Dungeon.getRandom(editor, start);
      start = new Coord(layout.getEnd().getPosition());
      start.add(Cardinal.DOWN, Dungeon.VERTICAL_SPACING);
    }


    // assign dungeon rooms
    for (IDungeonLevel level : levels) {
      ILevelLayout layout = level.getLayout();
      IDungeonFactory rooms = level.getSettings().getRooms();

      int count = 0;
      while (layout.hasEmptyRooms()) {
        IDungeonRoom toGenerate = count < level.getSettings().getNumRooms()
            ? rooms.get(rand)
            : DungeonRoom.getInstance(DungeonRoom.CORNER);
        DungeonNode node = layout.getBestFit(toGenerate);
        node.setDungeon(toGenerate);
        ++count;
      }
    }
  }
}
