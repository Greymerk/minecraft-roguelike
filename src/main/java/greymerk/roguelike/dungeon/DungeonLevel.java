package greymerk.roguelike.dungeon;

import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBounded;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.filter.Filter;
import greymerk.roguelike.worldgen.filter.IFilter;

public class DungeonLevel implements IDungeonLevel {

  private LevelSettings settings;
  private ILevelGenerator generator;

  public DungeonLevel(IWorldEditor editor, Random rand, LevelSettings settings, Coord origin) {
    this.settings = settings;
  }

  @Override
  public void generate(ILevelGenerator generator, Coord start) {
    this.generator = generator;
    generator.generate(start);
  }

  public int nodeCount() {
    return this.generator.getLayout().getNodes().size();
  }

  @Override
  public LevelSettings getSettings() {
    return this.settings;
  }

  @Override
  public boolean hasNearbyNode(Coord pos) {

    for (DungeonNode node : this.generator.getLayout().getNodes()) {
      int dist = (int) node.getPosition().distance(pos);

      if (dist < node.getSize()) {
        return true;
      }
    }
    return false;
  }

  @Override
  public ILevelLayout getLayout() {
    return this.generator.getLayout();
  }

  @Override
  public void encase(IWorldEditor editor, Random rand) {
    List<DungeonNode> nodes = this.generator.getLayout().getNodes();
    List<DungeonTunnel> tunnels = this.generator.getLayout().getTunnels();
    DungeonNode start = this.generator.getLayout().getStart();
    DungeonNode end = this.generator.getLayout().getEnd();

    for (DungeonNode node : nodes) {
      if (node == start || node == end) {
        continue;
      }
      node.encase(editor, rand, this.settings.getTheme());
    }

    for (DungeonTunnel t : tunnels) {
      t.encase(editor, rand, this.settings.getTheme());
    }
  }

  @Override
  public void applyFilters(IWorldEditor editor, Random rand) {
    for (Filter type : this.settings.getFilters()) {
      IFilter filter = Filter.get(type);
      this.filter(editor, rand, filter);
    }
  }

  @Override
  public void filter(IWorldEditor editor, Random rand, IFilter filter) {
    for (IBounded box : this.generator.getLayout().getBoundingBoxes()) {
      filter.apply(editor, rand, settings.getTheme(), box);
    }
  }
}
