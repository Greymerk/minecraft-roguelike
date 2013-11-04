package greymerk.roguelike.treasure;

import greymerk.roguelike.catacomb.Catacomb;
import greymerk.roguelike.catacomb.dungeon.Dungeon;
import greymerk.roguelike.treasure.loot.ItemLoot;
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

	public TreasureChestBase(){
	}
		

	public boolean generate(World world, Random rand, int posX, int posY, int posZ, boolean trapped) {
		this.world = world;
		this.rand = rand;
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;

		int type = trapped ? Block.chestTrapped.blockID : Block.chest.blockID;
		
		
		if(!WorldGenPrimitive.setBlock(world, posX, posY, posZ, type)){
			return false;
		}
	
		TileEntityChest chest;
		
		chest = (TileEntityChest) world.getBlockTileEntity(posX, posY, posZ);
		
		try{
			
			for (int i = 0; i < 15; i++) {
				ItemStack item = ItemLoot.getJunk(rand, Catacomb.getRank(posY));
				chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), item);
			}
			
			fillChest(chest);
			
		} catch(NullPointerException e){
			return false;
		}
		

		

		return true;
	}
	
	@Override
	public boolean generate(World world, Random rand, int posX, int posY, int posZ) {
		return generate(world, rand, posX, posY, posZ, false);
	}
	
	protected abstract void fillChest(TileEntityChest chest);
	
}
