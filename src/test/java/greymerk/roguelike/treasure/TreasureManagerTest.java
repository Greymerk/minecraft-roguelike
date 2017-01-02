package greymerk.roguelike.treasure;

import java.util.Random;

import org.junit.Test;

import greymerk.roguelike.util.WeightedChoice;
import greymerk.roguelike.util.WeightedRandomizer;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;
import net.minecraft.item.ItemStack;

public class TreasureManagerTest {

	@Test
	public void addItem() {
		Random rand = new Random();
		WeightedChoice<ItemStack> stick = new WeightedChoice<ItemStack>(null, 1);
		WeightedRandomizer<ItemStack> loot = new WeightedRandomizer<ItemStack>();
		loot.add(stick);
		
		TreasureManager treasure = new TreasureManager();		
		MockChest toAdd = new MockChest(Treasure.ARMOUR, 0);
		treasure.add(toAdd);
		treasure.addItem(rand, Treasure.ARMOUR, 0, loot, 1);
		
		treasure.addItem(rand, Treasure.ARMOUR, 1, loot, 1);
		
		treasure.addItem(rand, Treasure.WEAPONS, 0, loot, 1);
		
		treasure.addItem(rand, Treasure.WEAPONS, 1, loot, 1);
		
		treasure.addItem(rand, Treasure.ARMOUR, loot, 1);
		
		treasure.addItem(rand, Treasure.WEAPONS, loot, 1);
		
		treasure.addItem(rand, 0, loot, 1);
		
		treasure.addItem(rand, 1, loot, 1);
		
	}

	
	private class MockChest implements ITreasureChest{
		
		Treasure type;
		int level;
		
		public MockChest(Treasure type, int level){
			this.type = type;
			this.level = level;
		}
		
		@Override
		public ITreasureChest generate(IWorldEditor editor, Random rand, Coord pos, int level, boolean trapped) {
			return this;
		}

		@Override
		public boolean setSlot(int slot, ItemStack item) {
			return false;
		}

		@Override
		public boolean setRandomEmptySlot(ItemStack item) {
			return true;
		}

		@Override
		public boolean isEmptySlot(int slot) {
			return true;
		}

		@Override
		public Treasure getType() {
			return this.type;
		}

		@Override
		public int getSize() {
			return 1;
		}

		@Override
		public int getLevel() {
			return this.level;
		}
		
	}
	
}
