package greymerk.roguelike.dungeon.tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import greymerk.roguelike.dungeon.DungeonStage;

public class DungeonTaskRegistry implements IDungeonTaskRegistry {

  public static DungeonTaskRegistry registry;
  private Map<DungeonStage, List<IDungeonTask>> tasks;

  public DungeonTaskRegistry() {
    this.tasks = new HashMap<>();
    this.addTask(new DungeonTaskLayout(), DungeonStage.LAYOUT);
    this.addTask(new DungeonTaskEncase(), DungeonStage.ENCASE);
    this.addTask(new DungeonTaskTunnels(), DungeonStage.TUNNELS);
    this.addTask(new DungeonTaskRooms(), DungeonStage.ROOMS);
    this.addTask(new DungeonTaskSegments(), DungeonStage.SEGMENTS);
    this.addTask(new DungeonTaskLinks(), DungeonStage.LINKS);
    this.addTask(new DungeonTaskTower(), DungeonStage.TOWER);
    this.addTask(new DungeonTaskFilters(), DungeonStage.FILTERS);
    this.addTask(new DungeonTaskLoot(), DungeonStage.LOOT);
  }

  public static IDungeonTaskRegistry getTaskRegistry() {
    if (registry == null) {
      registry = new DungeonTaskRegistry();
    }

    return registry;
  }

  public void addTask(IDungeonTask task, DungeonStage stage) {
    if (!tasks.containsKey(stage)) {
      this.tasks.put(stage, new ArrayList<>());
    }

    tasks.get(stage).add(task);
  }

  public List<IDungeonTask> getTasks(DungeonStage stage) {
    if (!this.tasks.containsKey(stage)) {
      return new ArrayList<>();
    }
    return this.tasks.get(stage);
  }
}
