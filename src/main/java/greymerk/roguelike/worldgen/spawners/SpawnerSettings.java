package greymerk.roguelike.worldgen.spawners;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import greymerk.roguelike.util.WeightedChoice;
import greymerk.roguelike.util.WeightedRandomizer;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;


public class SpawnerSettings {

  private Map<Spawner, WeightedRandomizer<Spawnable>> spawners;

  public SpawnerSettings() {
    this.spawners = new HashMap<>();
  }

  public SpawnerSettings(SpawnerSettings toCopy) {
    this();
    for (Spawner type : toCopy.spawners.keySet()) {
      if (this.spawners.get(type) == null) {
        this.spawners.put(type, new WeightedRandomizer<>());
      }
      this.spawners.get(type).merge(toCopy.spawners.get(type));
    }
  }

  public SpawnerSettings(SpawnerSettings base, SpawnerSettings other) {
    this();

    for (Spawner type : base.spawners.keySet()) {
      if (this.spawners.get(type) == null) {
        this.spawners.put(type, new WeightedRandomizer<>());
      }
      this.spawners.get(type).merge(base.spawners.get(type));
    }

    for (Spawner type : other.spawners.keySet()) {
      if (this.spawners.get(type) == null) {
        this.spawners.put(type, new WeightedRandomizer<>());
      }
      this.spawners.get(type).merge(other.spawners.get(type));
    }
  }

  public void add(JsonObject entry) throws Exception {
    if (!entry.has("type")) {
      throw new Exception("Spawners entry missing type");
    }

    String typeName = entry.get("type").getAsString().toUpperCase();
    Spawner type = Spawner.valueOf(typeName);

    JsonElement potentials = entry.get("potentials");
    Spawnable spawn = new Spawnable(potentials);

    int weight = entry.has("weight") ? entry.get("weight").getAsInt() : 1;

    if (!this.spawners.containsKey(type)) {
      this.spawners.put(type, new WeightedRandomizer<>());
    }

    WeightedChoice<Spawnable> toAdd = new WeightedChoice<>(spawn, weight);

    this.spawners.get(type).add(toAdd);
  }

  public void generate(IWorldEditor editor, Random rand, Coord cursor, Spawner type, int level) {
    Spawnable toSpawn = spawners.containsKey(type) ? spawners.get(type).get(rand) : new Spawnable(type);
    toSpawn.generate(editor, rand, cursor, level);
  }

  @Override
  public String toString() {
    return this.spawners.keySet().toString();
  }
}
