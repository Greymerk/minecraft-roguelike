package greymerk.roguelike.treasure;

import greymerk.roguelike.treasure.loot.Loot;
import greymerk.roguelike.treasure.loot.LootSettings;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;

public class TreasureChestReward extends TreasureChestBase implements ITreasureChest{

	@Override
	protected void fillChest(TileEntityChest chest, LootSettings loot, int level) {
		int middle = chest.getSizeInventory()/2;
		
		ItemStack item;
		
		item = loot.get(Loot.REWARD, rand);
		chest.setInventorySlotContents(middle, item);
	}

}
