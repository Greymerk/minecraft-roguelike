package greymerk.roguelike.dungeon;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import greymerk.roguelike.dungeon.settings.ISettings;
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
    IntStream.range(0, numLevels)
        .mapToObj(settings::getLevelSettings)
        .map(levelSettings -> new DungeonLevel(editor, rand, levelSettings, new Coord(start)))
        .forEach(levels::add);

    Arrays.stream(DungeonStage.values())
        .flatMap(stage -> tasks.getTasks(stage).stream())
        .forEach(task -> task.execute(editor, rand, dungeon, settings));
  }
}
