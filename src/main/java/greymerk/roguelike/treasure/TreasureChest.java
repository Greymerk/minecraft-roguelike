package greymerk.roguelike.treasure;

import java.util.Random;

import greymerk.roguelike.treasure.loot.LootSettings;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;

public class TreasureChest implements ITreasureChest{

	protected Inventory inventory;
	protected Treasure type;
	protected Random rand;

	public TreasureChest(Treasure type){
		this.type = type;
	}
	
	public ITreasureChest generate(WorldEditor editor, Random rand, LootSettings loot, Coord pos, int level, boolean trapped) {

		this.rand = rand;
		
		MetaBlock chestType = new MetaBlock(trapped ? Blocks.trapped_chest : Blocks.chest);
		
		if(!chestType.setBlock(editor, pos)){
			return null;
		}
		
		TileEntityChest chest = (TileEntityChest) editor.getTileEntity(pos);
		this.inventory = new Inventory(rand, chest);

		Treasure.fillChest(this, rand, loot, level);
		
		return this;
	}
	
	@Override
	public boolean setSlot(int slot, ItemStack item){
		return this.inventory.setInventorySlot(slot, item);
	}
	
	@Override
	public boolean setRandomEmptySlot(ItemStack item){
		return this.inventory.setRandomEmptySlot(item);
	}
	
	@Override
	public boolean isEmptySlot(int slot){
		return this.inventory.isEmptySlot(slot);
	}

	@Override
	public Treasure getType(){
		return this.type;
	}
	
	@Override
	public int getSize(){
		return this.inventory.getInventorySize();
	}
}
