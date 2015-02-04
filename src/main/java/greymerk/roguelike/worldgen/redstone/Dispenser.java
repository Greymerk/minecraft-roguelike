package greymerk.roguelike.worldgen.redstone;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.world.World;

public class Dispenser {
	
	public static boolean generate(World world, Cardinal dir, Coord pos){
		int meta = 0;
		switch(dir){
		case DOWN: meta = 0; break;
		case UP: meta = 1; break;
		case NORTH: meta = 2; break;
		case SOUTH: meta = 3; break;
		case WEST: meta = 4; break;
		case EAST: meta = 5; break;
		}

		MetaBlock container = new MetaBlock(Blocks.dispenser);
		container.setBlock(world, pos);
		world.setBlockMetadataWithNotify(pos.getX(), pos.getY(), pos.getZ(), meta, 2);
		return true;
	}
	
	public static void add(World world, Coord pos, int slot, ItemStack item){
		
		TileEntity te = world.getTileEntity(pos.getX(), pos.getY(), pos.getZ());
		if(te == null) return;
		if(!(te instanceof TileEntityDispenser)) return;
		TileEntityDispenser dispenser = (TileEntityDispenser) te;
		dispenser.setInventorySlotContents(slot, item);
	}
}
