package greymerk.roguelike.dungeon.settings;

import net.minecraft.init.Bootstrap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.treasure.ITreasureChest;
import greymerk.roguelike.treasure.Treasure;
import greymerk.roguelike.treasure.TreasureManager;
import greymerk.roguelike.treasure.loot.LootTableRule;
import greymerk.roguelike.util.WeightedChoice;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;

import static greymerk.roguelike.treasure.Treasure.REWARD;
import static greymerk.roguelike.treasure.loot.LootTableRule.newLootTableRule;
import static net.minecraft.init.Items.BOAT;
import static net.minecraft.init.Items.COAL;
import static net.minecraft.init.Items.DIAMOND;
import static net.minecraft.init.Items.STICK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

public class SettingsResolverTest {

  private SettingsContainer settingsContainer;
  private TreasureManager treasureManager;
  private SettingsResolver settingsResolver;

  @Before
  public void setUp() {
    Bootstrap.register();
    RogueConfig.testing = true;

    settingsContainer = new SettingsContainer();
    treasureManager = new TreasureManager();

    settingsResolver = new SettingsResolver(settingsContainer);
  }

  @Test
  public void ResolveInheritOneLevel() throws Exception {
    DungeonSettings main = new DungeonSettings("main");
    DungeonSettings toInherit = new DungeonSettings("inherit");

    settingsContainer.put(main, toInherit);

    main.getInherit().add(toInherit.getId());

    ItemStack stick = new ItemStack(STICK);

    toInherit.getLootRules().add(REWARD, new WeightedChoice<>(stick, 1), 0, true, 1);

    DungeonSettings assembled = settingsResolver.processInheritance(main);

    MockChest chest = new MockChest(REWARD, 0);
    treasureManager.add(chest);

    assertEquals(0, chest.count(stick));
    assembled.getLootRules().process(new Random(), treasureManager);
    assertEquals(1, chest.count(stick));
  }

  @Test
  public void ResolveInheritTwoLevel() throws Exception {
    DungeonSettings main = new DungeonSettings("main");
    DungeonSettings child = new DungeonSettings("child");
    DungeonSettings grandchild = new DungeonSettings("grandchild");

    settingsContainer.put(main, child, grandchild);

    main.getInherit().add(child.getId());
    child.getInherit().add(grandchild.getId());

    ItemStack stick = new ItemStack(STICK);
    ItemStack diamond = new ItemStack(DIAMOND);

    child.getLootRules().add(REWARD, stick);
    grandchild.getLootRules().add(REWARD, diamond);

    DungeonSettings assembled = settingsResolver.processInheritance(main);

    MockChest chest = new MockChest(REWARD, 0);
    treasureManager.add(chest);

    assertEquals(0, chest.count(stick));
    assertEquals(0, chest.count(diamond));

    assembled.getLootRules().process(new Random(), treasureManager);

    assertEquals(1, chest.count(stick));
    assertEquals(0, chest.count(new ItemStack(BOAT)));
    assertEquals(1, chest.count(diamond));
  }

  @Test
  public void ResolveInheritTwoLevelMultiple() throws Exception {
    DungeonSettings main = new DungeonSettings("main");
    DungeonSettings child = new DungeonSettings("child");
    DungeonSettings sibling = new DungeonSettings("sibling");
    DungeonSettings grandchild = new DungeonSettings("grandchild");

    settingsContainer.put(main, child, sibling, grandchild);

    main.getInherit().add(child.getId());
    main.getInherit().add(sibling.getId());
    child.getInherit().add(grandchild.getId());

    ItemStack stick = new ItemStack(STICK);
    ItemStack coal = new ItemStack(COAL);
    ItemStack diamond = new ItemStack(DIAMOND);

    child.getLootRules().add(REWARD, stick);
    sibling.getLootRules().add(REWARD, coal);
    grandchild.getLootRules().add(REWARD, diamond);

    DungeonSettings assembled = settingsResolver.processInheritance(main);

    MockChest chest = new MockChest(REWARD, 0);
    treasureManager.add(chest);

    assertEquals(0, chest.count(stick));
    assertEquals(0, chest.count(coal));
    assertEquals(0, chest.count(diamond));

    assembled.getLootRules().process(new Random(), treasureManager);

    assertEquals(0, chest.count(new ItemStack(BOAT)));
    assertEquals(1, chest.count(coal));
    assertEquals(1, chest.count(stick));
    assertEquals(1, chest.count(diamond));
  }

  @Test
  public void processInheritance_BothChildAndParentLootTablesAreKept() throws Exception {
    ItemStack stick = new ItemStack(STICK);
    ItemStack coal = new ItemStack(COAL);

    DungeonSettings parent = new DungeonSettings("parent");
    parent.getLootRules().add(REWARD, stick);
    settingsContainer.put(parent);

    DungeonSettings child = new DungeonSettings("child");
    child.getLootRules().add(REWARD, coal);
    child.getInherits().add(parent.getId());
    settingsContainer.put(child);

    DungeonSettings actual = settingsResolver.processInheritance(child);

    MockChest chest = new MockChest(REWARD, 0);
    treasureManager.add(chest);
    actual.getLootRules().process(new Random(), treasureManager);

    assertEquals(1, chest.count(coal));
    assertEquals(1, chest.count(stick));
  }

  @Test
  public void processInheritance_GivesParentLootRulesAndLootTablesToChildren() throws Exception {
    ItemStack stick = new ItemStack(STICK);
    ItemStack coal = new ItemStack(COAL);

    DungeonSettings parent = new DungeonSettings("parent");
    LootTableRule rewardDungeonLootTable = newLootTableRule(0, "minecraft:dungeon", REWARD);
    parent.getLootTables().add(rewardDungeonLootTable);
    parent.getLootRules().add(REWARD, stick);
    settingsContainer.put(parent);

    DungeonSettings child = new DungeonSettings("child");
    child.getLootRules().add(REWARD, coal);
    child.getInherits().add(parent.getId());
    settingsContainer.put(child);

    DungeonSettings actual = settingsResolver.processInheritance(child);

    assertThat(actual.getLootTables()).contains(rewardDungeonLootTable);

    MockChest chest = new MockChest(REWARD, 0);
    treasureManager.add(chest);
    actual.getLootRules().process(new Random(), treasureManager);

    assertEquals(1, chest.count(coal));
    assertEquals(1, chest.count(stick));
  }

  @Test
  public void processInheritance_AppliesInheritedThemes() throws Exception {
    String forestThemeSettingsJson = "" +
        "{\n" +
        "  \"name\" : \"theme:forest\",\n" +
        "  \"themes\" : [\n" +
        "    {\n" +
        "      \"base\": \"DARKOAK\",\n" +
        "      \"level\" : 0,\n" +
        "      \"primary\" : {\n" +
        "        \"lightblock\": {\"type\":  \"METABLOCK\", \"data\":  {\"name\":  \"sea_lantern\"}},\n" +
        "        \"walls\" : {\"type\" : \"WEIGHTED\", \"data\" : [\n" +
        "          {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"stone\", \"meta\" : 0}, \"weight\" : 2},\n" +
        "          {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"stonebrick\", \"meta\" : 0}, \"weight\" : 5},\n" +
        "          {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"stonebrick\", \"meta\" : 2}, \"weight\" : 2},\n" +
        "\n" +
        "          {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"cobblestone\"}, \"weight\" : 3},\n" +
        "          {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"stone_stairs\"}, \"weight\" : 3},\n" +
        "          {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"gravel\"}, \"weight\" : 1},\n" +
        "\n" +
        "          {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"mossy_cobblestone\"}, \"weight\" : 5},\n" +
        "          {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"stonebrick\", \"meta\" : 1}, \"weight\" : 2},\n" +
        "          {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"leaves\"}, \"weight\" : 5},\n" +
        "\n" +
        "          {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"web\"}, \"weight\" : 1}\n" +
        "        ]\n" +
        "        },\n" +
        "        \"pillar\" : {\"type\" : \"LAYERS\", \"data\" : [\n" +
        "          {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"stonebrick\", \"meta\" : 1}},\n" +
        "          {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"stonebrick\", \"meta\" : 0}},\n" +
        "          {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"stonebrick\", \"meta\" : 3}},\n" +
        "          {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"stonebrick\", \"meta\" : 2}}\n" +
        "        ]},\n" +
        "        \"door\": {\"name\": \"dark_oak_door\"},\n" +
        "        \"floor\" : {\"type\" : \"WEIGHTED\", \"data\" : [\n" +
        "          {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"stone\"}, \"weight\" : 5},\n" +
        "          {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"stonebrick\", \"meta\" : 0}, \"weight\" : 75},\n" +
        "          {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"stonebrick\", \"meta\" : 2}, \"weight\" : 10},\n" +
        "\n" +
        "          {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"cobblestone\"}, \"weight\" : 10},\n" +
        "          {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"stone_stairs\"}, \"weight\" : 2},\n" +
        "          {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"gravel\"}, \"weight\" : 2},\n" +
        "\n" +
        "          {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"stonebrick\", \"meta\" : 1}, \"weight\" : 15},\n" +
        "          {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"mossy_cobblestone\"}, \"weight\" : 10},\n" +
        "          {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"leaves\"}, \"weight\" : 2},\n" +
        "\n" +
        "          {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"grass\"}, \"weight\" : 20},\n" +
        "          {\"type\" : \"METABLOCK\", \"data\" : {\"name\" : \"grass_path\"}, \"weight\" : 1}\n" +
        "        ]\n" +
        "        }\n" +
        "      }\n" +
        "    }\n" +
        "  ]\n" +
        "}";
    settingsContainer.put(forestThemeSettingsJson);

    String genericDungeonSettingsJson = "" +
        "{\n" +
        "  \"name\": \"dungeon:generic\"" +
        "}";
    settingsContainer.put(genericDungeonSettingsJson);

    String forestTempleId = "dungeon:forest_temple";
    String forestTempleSettingsJson = "" +
        "{\n" +
        "  \"name\" : \"" + forestTempleId + "\",\n" +
        "  \"exclusive\": true,\n" +
        "  \"inherit\" : [\n" +
        "    \"dungeon:generic\",\n" +
        "    \"theme:forest\",\n" +
        "    \"dungeon:generic\"\n" +
        "  ]\n" +
        "}";
    settingsContainer.put(forestTempleSettingsJson);
    DungeonSettings forestTempleSettings = settingsContainer.get(new SettingIdentifier(forestTempleId));
    DungeonSettings dungeonSettings = settingsResolver.processInheritance(forestTempleSettings);

    assertThat(dungeonSettings.getLevelSettings(0).getTheme().getPrimary().getLightBlock()).isEqualTo(BlockType.get(BlockType.SEA_LANTERN));
  }

  private static class MockChest implements ITreasureChest {

    Treasure type;
    int level;
    Map<Integer, ItemStack> loot;

    public MockChest(Treasure type, int level) {
      this.type = type;
      this.level = level;
      loot = new HashMap<>();
    }

    @Override
    public ITreasureChest generate(IWorldEditor editor, Random rand, Coord pos, int level, boolean trapped) {
      return this;
    }

    @Override
    public boolean setSlot(int slot, ItemStack item) {
      loot.put(slot, item);
      return true;
    }

    @Override
    public boolean setRandomEmptySlot(ItemStack item) {
      for (int i = 0; i < getSize(); ++i) {
        if (!isEmptySlot(i)) {
          continue;
        }
        setSlot(i, item);
        return true;
      }

      return false;
    }

    @Override
    public boolean isEmptySlot(int slot) {
      return !loot.containsKey(slot);
    }

    @Override
    public Treasure getType() {
      return type;
    }

    @Override
    public int getSize() {
      return 27;
    }

    @Override
    public int getLevel() {
      return level;
    }

    public int count(ItemStack type) {
      int count = 0;

      for (int i = 0; i < getSize(); ++i) {
        if (!loot.containsKey(i)) {
          continue;
        }
        ItemStack item = loot.get(i);
        if (item.getItem() != type.getItem()) {
          continue;
        }
        count += item.getCount();
      }

      return count;
    }

    @Override
    public void setLootTable(ResourceLocation table) {
    }
  }
}
