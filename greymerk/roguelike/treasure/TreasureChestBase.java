package greymerk.roguelike.treasure;

import greymerk.roguelike.catacomb.Catacomb;
import greymerk.roguelike.catacomb.dungeon.Dungeon;
import greymerk.roguelike.treasure.loot.Loot;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntityChest;
import net.minecraft.src.World;

public abstract class TreasureChestBase implements ITreasureChest{

	protected World world;
	protected Random rand;
	protected int posX;
	protected int posY;
	protected int posZ;
	protected TileEntityChest chest;
	
	public TreasureChestBase(){
	}
		

	public ITreasureChest generate(World world, Random rand, int posX, int posY, int posZ, boolean trapped) {
		this.world = world;
		this.rand = rand;
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;

		int type = trapped ? Block.chestTrapped.blockID : Block.chest.blockID;
		
		
		if(!WorldGenPrimitive.setBlock(world, posX, posY, posZ, type)){
			return null;
		}
		
		chest = (TileEntityChest) world.getBlockTileEntity(posX, posY, posZ);
		
		try{
			
			for (int i = 0; i < 15; i++) {
				ItemStack item = Loot.getLootByCategory(Loot.JUNK, rand, Catacomb.getLevel(posY));
				chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), item);
			}
			
			fillChest(chest);
			
		} catch(NullPointerException e){
			return null;
		}
		
		return this;
	}
	
	@Override
	public ITreasureChest generate(World world, Random rand, int posX, int posY, int posZ) {
		return generate(world, rand, posX, posY, posZ, false);
	}
	
	public boolean setInventorySlot(ItemStack item, int slot){
		try{
			chest.setInventorySlotContents(slot, item);
			return true;
		} catch(NullPointerException e){
			return false;
		}
	}
	
	public int getInventorySize(){
		
		if(chest == null){
			return 0;
		}
		
		return chest.getSizeInventory();
	}
	
	protected abstract void fillChest(TileEntityChest chest);
	
}
