package greymerk.roguelike.treasure;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.treasure.loot.Loot;
import greymerk.roguelike.treasure.loot.LootSettings;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.World;

public abstract class TreasureChestBase implements ITreasureChest, Iterable<InventorySlot>{

	protected World world;
	protected Random rand;
	protected TileEntityChest chest;

	private static final int CHEST_MAX = 27;
	private List<InventorySlot> slots;

	
	public TreasureChestBase(){
		slots = new ArrayList<InventorySlot>();
		for(int i = 0; i < CHEST_MAX; ++i){
			slots.add(new InventorySlot(i, this));
		}
	}
		

	public ITreasureChest generate(World world, Random rand, LootSettings loot, Coord pos, int level, boolean trapped) {
		this.world = world;
		this.rand = rand;

		Collections.shuffle(slots, rand);
		
		MetaBlock chestType = new MetaBlock(trapped ? Blocks.trapped_chest : Blocks.chest);
		
		
		if(!chestType.setBlock(world, pos)){
			return null;
		}
		
		chest = (TileEntityChest) WorldGenPrimitive.getTileEntity(world, pos);

		try{
			
			fillChest(chest, loot, level);
			
			int amount = RogueConfig.getBoolean(RogueConfig.GENEROUS) ? 9 : 4;
			
			Iterator<InventorySlot> slots = this.iterator(); 
			
			while(slots.hasNext() && amount > 0){
				InventorySlot slot = slots.next();
				
				if(!slot.empty()) continue;
				
				slot.set(loot.get(Loot.JUNK, rand));
				--amount;
			}
			
		} catch(NullPointerException e){
			return null;
		}
		
		return this;
	}
	
	public boolean setInventorySlot(ItemStack item, int slot){
		try{
			chest.setInventorySlotContents(slot, item);
			return true;
		} catch(NullPointerException e){
			return false;
		}
	}
	
	public boolean slotEmpty(int slot){
		return chest.getStackInSlot(slot) == null;
	}
	
	public int getInventorySize(){
		
		if(chest == null){
			return 1;
		}
		
		try{
			return chest.getSizeInventory();
		} catch(NullPointerException e){
			return 1;
		}
	}
	
	@Override
	public Iterator<InventorySlot> iterator() {
		return this.slots.iterator();
	}
	
	
	protected abstract void fillChest(TileEntityChest chest, LootSettings loot, int level);
	
	

}
