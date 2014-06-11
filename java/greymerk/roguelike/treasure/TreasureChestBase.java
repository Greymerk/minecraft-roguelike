package greymerk.roguelike.treasure;

import greymerk.roguelike.catacomb.Catacomb;
import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.treasure.loot.Loot;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.World;

public abstract class TreasureChestBase implements ITreasureChest, Iterable{

	protected World world;
	protected Random rand;
	protected int posX;
	protected int posY;
	protected int posZ;
	protected TileEntityChest chest;

	private static final int CHEST_MAX = 27;
	private List<InventorySlot> slots;

	
	public TreasureChestBase(){
		slots = new ArrayList<InventorySlot>();
		for(int i = 0; i < CHEST_MAX; ++i){
			slots.add(new InventorySlot(i, this));
		}
	}
		

	public ITreasureChest generate(World world, Random rand, int posX, int posY, int posZ, int level, boolean trapped) {
		this.world = world;
		this.rand = rand;
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;

		Collections.shuffle(slots, rand);
		
		Block type = trapped ? Blocks.trapped_chest : Blocks.chest;
		
		
		if(!WorldGenPrimitive.setBlock(world, posX, posY, posZ, type)){
			return null;
		}
		
		chest = (TileEntityChest) world.getTileEntity(posX, posY, posZ);
		
		try{
			
			fillChest(chest, level);
			
			int amount = RogueConfig.getBoolean(RogueConfig.GENEROUS) ? 12 : 6;
			
			Iterator<InventorySlot> slots = this.iterator(); 
			
			while(slots.hasNext() && amount > 0){
				InventorySlot slot = slots.next();
				
				if(!slot.empty()) continue;
				
				slot.set(Loot.getLoot(Loot.JUNK, rand, level));
				--amount;
			}
			
		} catch(NullPointerException e){
			return null;
		}
		
		return this;
	}
	
	
	
	@Override
	public ITreasureChest generate(World world, Random rand, int posX, int posY, int posZ) {
		return generate(world, rand, posX, posY, posZ, Catacomb.getLevel(posY), false);
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
	public Iterator iterator() {
		return this.slots.iterator();
	}
	
	protected abstract void fillChest(TileEntityChest chest, int level);
	
	
	protected class InventorySlot{
		
		int slot;
		ITreasureChest chest;
		
		public InventorySlot(int index, ITreasureChest chest){
			this.slot = index;
			this.chest = chest;
		}
		
		public void set(ItemStack item){
			chest.setInventorySlot(item, slot);
		}
		
		public boolean empty(){
			return chest.slotEmpty(slot);
		}
	}
}
