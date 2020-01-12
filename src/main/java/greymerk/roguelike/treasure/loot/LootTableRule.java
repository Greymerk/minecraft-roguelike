package greymerk.roguelike.treasure.loot;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

import greymerk.roguelike.treasure.ITreasureChest;
import greymerk.roguelike.treasure.Treasure;
import greymerk.roguelike.treasure.TreasureManager;

import static com.google.common.collect.Lists.newArrayList;

public class LootTableRule {

  List<Integer> levels = newArrayList();
  private ResourceLocation table;
  private List<Treasure> types = newArrayList();

  public LootTableRule() { }

  public LootTableRule(
      List<Integer> levels,
      ResourceLocation table,
      List<Treasure> types
  ) {
    this.levels = levels;
    this.table = table;
    this.types = types;
  }

  public LootTableRule(JsonObject json) throws Exception {
    this(
        parseLevels(json),
        parseTable(json),
        parseType(json)
    );
  }

  public static LootTableRule newLootTableRule(
      int level,
      String table,
      Treasure treasure
  ) {
    LootTableRule lootTableRule = new LootTableRule();
    lootTableRule.addLevel(level);
    lootTableRule.setTable(table);
    lootTableRule.addTreasureType(treasure);
    return lootTableRule;
  }

  public void addLevel(int level) {
    this.levels.add(level);
  }

  public void setTable(ResourceLocation table) {
    this.table = table;
  }

  public void setTable(String table) {
    this.table = new ResourceLocation(table);
  }

  public void addTreasureType(Treasure type) {
    this.types.add(type);
  }

  private static List<Integer> parseLevels(JsonObject json) {
    if (!json.has("level")) {
      return null;
    }
    JsonElement level = json.get("level");
    List<Integer> levels = new ArrayList<>();
    if (!level.isJsonArray()) {
      levels.add(level.getAsInt());
    } else {
      for (JsonElement i : level.getAsJsonArray()) {
        levels.add(i.getAsInt());
      }
    }
    return levels;

  }

  private static ResourceLocation parseTable(JsonObject json) throws Exception {
    if (!json.has("table")) {
      throw new Exception("Loot table requires a table field");
    }
    return new ResourceLocation(json.get("table").getAsString());
  }

  private static List<Treasure> parseType(JsonObject json) {
    List<Treasure> type = newArrayList();
    if (!json.has("type")) {
      return type;
    }
    JsonElement typeElement = json.get("type");
    if (!typeElement.isJsonArray()) {
      type.add(Treasure.valueOf(typeElement.getAsString()));
    } else {
      for (JsonElement treasure : typeElement.getAsJsonArray()) {
        type.add(Treasure.valueOf(treasure.getAsString()));
      }
    }
    return type;
  }

  public void process(TreasureManager treasure) {
    getMatching(treasure).stream()
        .filter(chest -> chest.getType() != Treasure.EMPTY)
        .forEach(chest -> chest.setLootTable(table));
  }

  private List<ITreasureChest> getMatching(TreasureManager treasure) {
    if (types == null && levels == null) {
      return treasure.getChests();
    }

    List<ITreasureChest> chests = new ArrayList<>();
    if (types == null) {
      for (int level : levels) {
        chests.addAll(treasure.getChests(level));
      }
    }

    if (levels == null) {
      types.stream().map(treasure::getChests).forEach(chests::addAll);
    }
    return chests;
  }

}
