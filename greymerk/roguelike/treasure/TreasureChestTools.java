package greymerk.roguelike.treasure;

import greymerk.roguelike.catacomb.Catacomb;
import greymerk.roguelike.catacomb.dungeon.Dungeon;
import greymerk.roguelike.treasure.loot.Loot;
import net.minecraft.src.Block;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntityChest;

public class TreasureChestTools extends TreasureChestBase{
	
	@Override
	protected void fillChest(TileEntityChest chest){

		int rank = Catacomb.getRank(posY);
		int middle = chest.getSizeInventory()/2;
						
		ItemStack item;		
		
		item = new ItemStack(Block.torchWood, 4 + rand.nextInt(12));
		chest.setInventorySlotContents(middle - 1, item);
		
		item = Loot.getLootByCategory(Loot.TOOL, rand, rank);
		chest.setInventorySlotContents(middle, item);
		
		item = Loot.getLootByCategory(Loot.ORE, rand, 0);
		chest.setInventorySlotContents(middle + 1, item);
	}
}
