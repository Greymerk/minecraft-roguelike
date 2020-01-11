package greymerk.roguelike.treasure.loot;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import greymerk.roguelike.util.IWeighted;
import greymerk.roguelike.util.WeightedRandomizer;

public class LootSettings {

  private Map<Loot, IWeighted<ItemStack>> loot;

  public LootSettings(int level) {
    loot = new HashMap<>();
    for (Loot type : Loot.values()) {
      loot.put(type, Loot.getProvider(type, level));
    }
  }

  public LootSettings(LootSettings toCopy) {
    this.loot = new HashMap<>();
    this.loot.putAll(toCopy.loot);
  }

  public LootSettings(LootSettings base, LootSettings override) {
    this.loot = new HashMap<>();
    if (base != null) {
      this.loot.putAll(base.loot);
    }
    if (override != null) {
      this.loot.putAll(override.loot);
    }
  }

  public LootSettings(JsonObject data) {
    this.loot = new HashMap<>();
    for (Loot type : Loot.values()) {
      if (data.has(type.toString())) {
        JsonElement providerData = data.get(type.toString());

        if (providerData.isJsonObject()) {
          try {
            loot.put(type, new WeightedRandomLoot(providerData.getAsJsonObject(), 0));
          } catch (Exception e) {
            e.printStackTrace();
          }
        }

        if (providerData.isJsonArray()) {

          WeightedRandomizer<ItemStack> items = new WeightedRandomizer<>(0);
          JsonArray lootList = providerData.getAsJsonArray();

          for (JsonElement e : lootList) {
            items.add(parseProvider(e.getAsJsonObject()));
          }

          loot.put(type, items);
        }
      }
    }
  }

  private IWeighted<ItemStack> parseProvider(JsonObject data) {

    int weight = data.has("weight") ? data.get("weight").getAsInt() : 1;
    JsonElement loot = data.get("data");

    if (loot.isJsonObject()) {
      try {
        return new WeightedRandomLoot(loot.getAsJsonObject(), weight);
      } catch (Exception e1) {
        e1.printStackTrace();
      }
    }

    JsonArray lootList = loot.getAsJsonArray();

    WeightedRandomizer<ItemStack> items = new WeightedRandomizer<>(weight);

    for (JsonElement e : lootList) {
      items.add(parseProvider(e.getAsJsonObject()));
    }

    return items;
  }

  public ItemStack get(Loot type, Random rand) {
    IWeighted<ItemStack> provider = loot.get(type);
    return provider.get(rand);
  }

  public IWeighted<ItemStack> get(Loot type) {
    return this.loot.get(type);
  }

  public void set(Loot type, IWeighted<ItemStack> provider) {
    this.loot.put(type, provider);
  }
}