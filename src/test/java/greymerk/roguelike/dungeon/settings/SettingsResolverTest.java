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

  @Before
  public void setUp() {
    Bootstrap.register();
    RogueConfig.testing = true;

    settingsContainer = new SettingsContainer();
    treasureManager = new TreasureManager();
  }

  @Test
  public void ResolveInheritOneLevel() throws Exception {
    DungeonSettings main = new DungeonSettings("main");
    DungeonSettings toInherit = new DungeonSettings("inherit");

    settingsContainer.put(main, toInherit);

    main.getInherit().add(toInherit.getId());

    ItemStack stick = new ItemStack(STICK);

    toInherit.getLootRules().add(REWARD, new WeightedChoice<>(stick, 1), 0, true, 1);

    DungeonSettings assembled = SettingsResolver.processInheritance(main, settingsContainer);

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

    DungeonSettings assembled = SettingsResolver.processInheritance(main, settingsContainer);

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

    DungeonSettings assembled = SettingsResolver.processInheritance(main, settingsContainer);

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

    DungeonSettings actual = SettingsResolver.processInheritance(child, settingsContainer);

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

    DungeonSettings actual = SettingsResolver.processInheritance(child, settingsContainer);

    assertThat(actual.getLootTables()).contains(rewardDungeonLootTable);

    MockChest chest = new MockChest(REWARD, 0);
    treasureManager.add(chest);
    actual.getLootRules().process(new Random(), treasureManager);

    assertEquals(1, chest.count(coal));
    assertEquals(1, chest.count(stick));
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
