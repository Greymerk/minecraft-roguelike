package greymerk.roguelike.treasure;

import java.util.Random;

import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;

public class TreasureChest implements ITreasureChest{

	protected Inventory inventory;
	protected Treasure type;
	protected Random rand;
	private int level;

	public TreasureChest(Treasure type){
		this.type = type;
		this.level = 0;
	}
	
	public ITreasureChest generate(IWorldEditor editor, Random rand, Coord pos, int level, boolean trapped) throws ChestPlacementException {

		this.rand = rand;
		this.level = level;
		
		MetaBlock chestType = new MetaBlock(trapped ? Blocks.TRAPPED_CHEST : Blocks.CHEST);
		
		boolean success = chestType.set(editor, pos);
		
		if(!success){
			throw new ChestPlacementException("Failed to place chest in world");
		}
		
		TileEntityChest chest = (TileEntityChest) editor.getTileEntity(pos);
		this.inventory = new Inventory(rand, chest);

		editor.addChest(this);
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

	@Override
	public int getLevel() {
		if(level < 0) return 0;
		if(level > 4) return 4;
		return this.level;
	}
}
