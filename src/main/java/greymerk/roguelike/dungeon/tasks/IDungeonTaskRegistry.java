package greymerk.roguelike.dungeon.tasks;

import java.util.List;

import greymerk.roguelike.dungeon.DungeonStage;

public interface IDungeonTaskRegistry {

	public void addTask(IDungeonTask task, DungeonStage stage);
	
	public List<IDungeonTask> getTasks(DungeonStage stage);
	
}
