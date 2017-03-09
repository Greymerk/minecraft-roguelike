package greymerk.roguelike.treasure.loot;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import greymerk.roguelike.treasure.MockChest;
import greymerk.roguelike.treasure.Treasure;
import greymerk.roguelike.treasure.TreasureManager;
import greymerk.roguelike.util.WeightedChoice;
import net.minecraft.init.Bootstrap;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class LootRuleManagerTest {

	@Before
	public void setUp() throws Exception {
		Bootstrap.register();
	}

	@Test
	public void testAdd() {
		LootRuleManager manager = new LootRuleManager();
		manager.add(Treasure.STARTER, new WeightedChoice<ItemStack>(new ItemStack(Items.SHEARS), 1), 0, true, 1);
		TreasureManager treasure = new TreasureManager();
		
		MockChest chest = new MockChest(Treasure.STARTER, 0);
		treasure.add(chest);
		
		manager.process(new Random(), treasure);
		
		assert(!chest.contains(new ItemStack(Items.APPLE)));
		assert(chest.contains(new ItemStack(Items.SHEARS)));
		
	}
	
	@Test
	public void testMerge(){
		
		LootRuleManager base = new LootRuleManager();
		base.add(Treasure.STARTER, new WeightedChoice<ItemStack>(new ItemStack(Items.SHEARS), 1), 0, true, 1);

		LootRuleManager other = new LootRuleManager();
		other.add(Treasure.STARTER, new WeightedChoice<ItemStack>(new ItemStack(Items.APPLE), 1), 0, true, 1);

		base.add(other);
		
		TreasureManager treasure = new TreasureManager();
		MockChest chest = new MockChest(Treasure.STARTER, 0);
		treasure.add(chest);
		
		base.process(new Random(), treasure);
		
		assert(chest.contains(new ItemStack(Items.APPLE)));
		assert(chest.contains(new ItemStack(Items.SHEARS)));
	}

}
