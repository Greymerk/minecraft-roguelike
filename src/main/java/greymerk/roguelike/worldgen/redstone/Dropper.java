package greymerk.roguelike.worldgen.redstone;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;
import net.minecraft.block.BlockDropper;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityDropper;

public class Dropper {
	
	public boolean generate(WorldEditor editor, Cardinal dir, Coord pos){

		MetaBlock container = new MetaBlock(Blocks.dropper);
		container.withProperty(BlockDropper.FACING, Cardinal.getFacing(dir));
		container.setBlock(editor, pos);
		return true;
	}
	
	public void add(WorldEditor editor, Coord pos, int slot, ItemStack item){
		
		TileEntity te = editor.getTileEntity(pos);
		if(te == null) return;
		if(!(te instanceof TileEntityDropper)) return;
		TileEntityDropper dropper = (TileEntityDropper) te;
		dropper.setInventorySlotContents(slot, item);
	}
}
