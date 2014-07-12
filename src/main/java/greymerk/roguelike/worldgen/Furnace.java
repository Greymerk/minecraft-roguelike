package greymerk.roguelike.worldgen;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.world.World;

public class Furnace {

	public static final int FUEL_SLOT = 1;
	public static final int OUTPUT_SLOT = 2;
	
	public static void generate(World world, Cardinal dir, Coord pos){
		generate(world, null, false, dir, pos);
	}
	
	public static void generate(World world, boolean lit, Cardinal dir, Coord pos){
		generate(world, null, lit, dir, pos);
	}
	
	public static void generate(World world, ItemStack fuel, boolean lit, Cardinal dir, Coord pos){
		if(lit){
			if(!WorldGenPrimitive.setBlock(world, pos, Blocks.lit_furnace)) return;
		} else {
			if(!WorldGenPrimitive.setBlock(world, pos, Blocks.furnace)) return;
		}
		
		int meta = 0;
		switch(dir){
		case NORTH: meta = 2; break;
		case SOUTH: meta = 3; break;
		case WEST: meta = 4; break;
		case EAST: meta = 5; break;
		}
		
		world.setBlockMetadataWithNotify(pos.getX(), pos.getY(), pos.getZ(), meta, 2);
		
		if(fuel == null) return;
		
		TileEntity te = world.getTileEntity(pos.getX(), pos.getY(), pos.getZ());
		if(te == null) return;
		if(!(te instanceof TileEntityFurnace)) return;
		TileEntityFurnace furnace = (TileEntityFurnace)te;
		furnace.setInventorySlotContents(FUEL_SLOT, fuel);
	}
}
