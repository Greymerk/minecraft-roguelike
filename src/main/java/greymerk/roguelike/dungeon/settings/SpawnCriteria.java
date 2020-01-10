package greymerk.roguelike.dungeon.settings;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.BiomeDictionary;

import java.util.ArrayList;
import java.util.List;

public class SpawnCriteria {

  private int weight;
  private List<ResourceLocation> biomes = new ArrayList<>();
  private List<BiomeDictionary.Type> biomeTypes = new ArrayList<>();
  private boolean everywhere;

  public SpawnCriteria() {
    this.weight = 1;
    this.everywhere = false;
  }

  public SpawnCriteria(JsonObject data) {
    this();

    this.weight = data.has("weight") ? data.get("weight").getAsInt() : 1;

    if (data.has("biomes")) {
      JsonArray biomeList = data.get("biomes").getAsJsonArray();
      for (JsonElement e : biomeList) {
        String name = e.getAsString();
        this.biomes.add(new ResourceLocation(name));
      }
    }

    if (data.has("biomeTypes")) {
      JsonArray biomeTypeList = data.get("biomeTypes").getAsJsonArray();
      for (JsonElement e : biomeTypeList) {
        String type = e.getAsString().toUpperCase();
        BiomeDictionary.Type t = BiomeDictionary.Type.getType(type);
        if (BiomeDictionary.getBiomes(t).size() > 0) {
          this.biomeTypes.add(t);
        }
      }
    }

    this.everywhere = this.biomes.isEmpty() && this.biomeTypes.isEmpty();
  }

  public int getWeight() {
    return weight;
  }

  public void setWeight(int weight) {
    this.weight = weight;
  }

  public void setBiomeTypes(List<BiomeDictionary.Type> biomeTypes) {
    this.biomeTypes = biomeTypes;
    this.everywhere = this.biomes.isEmpty() && this.biomeTypes.isEmpty();
  }


  public boolean isValid(ISpawnContext context) {

    if (this.everywhere) {
      return true;
    }

    boolean biomeFound = false;

    if (this.biomes != null) {
      biomeFound = context.includesBiome(biomes);
    }

    if (this.biomeTypes != null) {
      biomeFound = context.includesBiomeType(this.biomeTypes);
    }

    return biomeFound;
  }

  public static boolean isValidDimension(int dim, List<Integer> wl, List<Integer> bl) {
    if (bl.contains(dim)) {
      return false;
    }
    if (wl.isEmpty()) {
      return true;
    }
    return wl.contains(dim);
  }

}