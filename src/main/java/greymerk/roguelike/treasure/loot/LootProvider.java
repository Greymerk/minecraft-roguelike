package greymerk.roguelike.treasure.loot;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class LootProvider implements ILoot {

	Map<Integer, LootSettings> loot;
	
	public LootProvider(){
		loot = new HashMap<Integer, LootSettings>();
	}
	
	public void put(int level, LootSettings settings){
		loot.put(level, settings);
	}
	
	@Override
	public ItemStack get(Random rand, Loot type, int level) {
		if(!loot.containsKey(level)) return new ItemStack(Items.stick);
		return loot.get(level).get(type, rand);
	}

}
