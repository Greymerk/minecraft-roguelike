package greymerk.roguelike.dungeon.tasks;

import java.util.List;

import greymerk.roguelike.dungeon.DungeonStage;

public interface IDungeonTaskRegistry {

  void addTask(IDungeonTask task, DungeonStage stage);

  List<IDungeonTask> getTasks(DungeonStage stage);

}
