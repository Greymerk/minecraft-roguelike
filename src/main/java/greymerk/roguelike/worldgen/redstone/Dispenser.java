package greymerk.roguelike.worldgen.redstone;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;
import net.minecraft.block.BlockDispenser;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.world.World;

public class Dispenser {
	
	public static boolean generate(World world, Cardinal dir, Coord pos){

		MetaBlock container = new MetaBlock(Blocks.dispenser);
		container.withProperty(BlockDispenser.FACING, Cardinal.getFacing(dir));
		container.setBlock(world, pos);
		return true;
	}
	
	public static void add(World world, Coord pos, int slot, ItemStack item){
		
		TileEntity te = WorldGenPrimitive.getTileEntity(world, pos);
		if(te == null) return;
		if(!(te instanceof TileEntityDispenser)) return;
		TileEntityDispenser dispenser = (TileEntityDispenser) te;
		dispenser.setInventorySlotContents(slot, item);
	}
}
