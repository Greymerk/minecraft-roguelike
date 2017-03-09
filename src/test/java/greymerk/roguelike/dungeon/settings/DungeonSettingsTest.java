package greymerk.roguelike.dungeon.settings;

import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.JsonObject;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.theme.IBlockSet;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.treasure.MockChest;
import greymerk.roguelike.treasure.Treasure;
import greymerk.roguelike.treasure.TreasureManager;
import greymerk.roguelike.treasure.loot.LootRuleManager;
import greymerk.roguelike.util.WeightedChoice;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.blocks.BlockType;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class DungeonSettingsTest {

	@Before
	public void before(){
		RogueConfig.testing = true;
	}
	
	@Test
	public void overridesMerge() {
		DungeonSettings base = new DungeonSettings();
		DungeonSettings other = new DungeonSettings();
		other.overrides.add(SettingsType.LOOTRULES);
		assert(other.overrides.contains(SettingsType.LOOTRULES));
		
		DungeonSettings merge = new DungeonSettings(base, other);
		assert(merge.overrides.contains(SettingsType.LOOTRULES));
		
		merge = new DungeonSettings(other, base);
		assert(!merge.overrides.contains(SettingsType.LOOTRULES));
	}
	
	@Test
	public void overridesCopy(){
		DungeonSettings setting = new DungeonSettings();
		setting.overrides.add(SettingsType.LOOTRULES);
		
		DungeonSettings copy = new DungeonSettings(setting);
		assertTrue(copy.overrides.contains(SettingsType.LOOTRULES));
	}
	
	@Test
	public void testJson() throws Exception{
		
		DungeonSettings setting;
		
		JsonObject json = new JsonObject();
		json.addProperty("name", "test");
		
		JsonObject levels = new JsonObject();
		json.add("levels", levels);
		
		JsonObject allLevels = new JsonObject();
		levels.add("all", allLevels);
		
		JsonObject theme = new JsonObject();
		allLevels.add("theme", theme);
		
		JsonObject primary = new JsonObject();
		theme.add("primary", primary);
		
		JsonObject floor = new JsonObject();
		primary.add("floor", floor);
		floor.addProperty("name", "minecraft:dirt");
		
		setting = new DungeonSettings(json);
		
		LevelSettings level = setting.getLevelSettings(0);
		ITheme t = level.getTheme();
		IBlockSet bs = t.getPrimary();
		IBlockFactory f = bs.getFloor();
		assert(f.equals(BlockType.get(BlockType.DIRT)));
		
	}
	
	@Test
	public void testLootSettingsMerge(){
		DungeonSettings base = new DungeonSettings();
		base.lootRules.add(Treasure.STARTER, new WeightedChoice<ItemStack>(new ItemStack(Items.SHEARS), 1), 0, true, 1);
		
		DungeonSettings other = new DungeonSettings();
		other.lootRules.add(Treasure.STARTER, new WeightedChoice<ItemStack>(new ItemStack(Items.APPLE), 1), 0, true, 1);
		
		DungeonSettings merge = new DungeonSettings(base, other);
		LootRuleManager rules = merge.getLootRules();
		
		TreasureManager treasure = new TreasureManager();
		MockChest chest = new MockChest(Treasure.STARTER, 0);
		treasure.add(chest);
		
		rules.process(new Random(), treasure);
		
		assert(chest.contains(new ItemStack(Items.APPLE)));
		assert(chest.contains(new ItemStack(Items.SHEARS)));
		
	}
	
	@Test
	public void testLootSettingsOverride(){
		DungeonSettings base = new DungeonSettings();
		base.lootRules.add(Treasure.STARTER, new WeightedChoice<ItemStack>(new ItemStack(Items.SHEARS), 1), 0, true, 1);
		
		DungeonSettings other = new DungeonSettings();
		other.overrides.add(SettingsType.LOOTRULES);
		other.lootRules.add(Treasure.STARTER, new WeightedChoice<ItemStack>(new ItemStack(Items.APPLE), 1), 0, true, 1);
		
		DungeonSettings merge = new DungeonSettings(base, other);
		LootRuleManager rules = merge.getLootRules();
		
		TreasureManager treasure = new TreasureManager();
		MockChest chest = new MockChest(Treasure.STARTER, 0);
		treasure.add(chest);
		
		rules.process(new Random(), treasure);
		
		assert(!chest.contains(new ItemStack(Items.SHEARS)));
		assert(chest.contains(new ItemStack(Items.APPLE)));
		
	}
}
