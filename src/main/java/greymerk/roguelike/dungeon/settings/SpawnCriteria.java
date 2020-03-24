package greymerk.roguelike.dungeon.settings;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.BiomeDictionary;

import java.util.ArrayList;
import java.util.List;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;

public class SpawnCriteria {

  private int weight;
  private List<ResourceLocation> biomes = new ArrayList<>();
  private List<BiomeDictionary.Type> biomeTypes = new ArrayList<>();

  public SpawnCriteria() {
    this(new JsonObject());
  }

  public SpawnCriteria(JsonObject data) {
    weight = data.has("weight") ? data.get("weight").getAsInt() : 1;
    addBiomeCriteria(data);
    addBiomeTypeCriteria(data);
  }

  public static boolean isValidDimension(IWorldEditor editor, int chunkX, int chunkZ) {
    int dimension = getDimension(editor, chunkX, chunkZ);
    return isValidDimension(dimension, RogueConfig.getIntList(RogueConfig.DIMENSIONWL), RogueConfig.getIntList(RogueConfig.DIMENSIONBL));
  }

  private static int getDimension(IWorldEditor editor, int chunkX, int chunkZ) {
    return editor.getInfo(new Coord(chunkX * 16, 0, chunkZ * 16)).getDimension();
  }

  public static boolean isValidDimension(int dim, List<Integer> whiteList, List<Integer> blackList) {
    return !blackList.contains(dim) && (whiteList.isEmpty() || whiteList.contains(dim));
  }

  private void addBiomeCriteria(JsonObject data) {
    if (data.has("biomes")) {
      for (JsonElement biome : data.get("biomes").getAsJsonArray()) {
        biomes.add(new ResourceLocation(biome.getAsString()));
      }
    }
  }

  private void addBiomeTypeCriteria(JsonObject data) {
    if (data.has("biomeTypes")) {
      for (JsonElement biomeType : data.get("biomeTypes").getAsJsonArray()) {
        BiomeDictionary.Type t = BiomeDictionary.Type.getType(biomeType.getAsString().toUpperCase());
        if (BiomeDictionary.getBiomes(t).size() > 0) {
          biomeTypes.add(t);
        }
      }
    }
  }

  private boolean isEverywhere() {
    return biomes.isEmpty() && biomeTypes.isEmpty();
  }

  public int getWeight() {
    return weight;
  }

  public void setWeight(int weight) {
    this.weight = weight;
  }

  public void setBiomeTypes(List<BiomeDictionary.Type> biomeTypes) {
    this.biomeTypes = biomeTypes;
  }

  public boolean isValid(ISpawnContext spawnContext) {
    return isEverywhere() || isBiomeValid(spawnContext) && isBiomeTypeValid(spawnContext);
  }

  private boolean isBiomeValid(ISpawnContext spawnContext) {
    return biomes.isEmpty() || spawnContext.includesBiome(biomes);
  }

  private boolean isBiomeTypeValid(ISpawnContext spawnContext) {
    return biomeTypes.isEmpty() || spawnContext.includesBiomeType(biomeTypes);
  }

}