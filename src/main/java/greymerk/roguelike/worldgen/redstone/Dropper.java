package greymerk.roguelike.worldgen.redstone;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityDropper;

public class Dropper {
	
	public boolean generate(WorldEditor editor, Cardinal dir, Coord pos){

		int meta = 0;
		switch(dir){
		case DOWN: meta = 0; break;
		case UP: meta = 1; break;
		case NORTH: meta = 2; break;
		case SOUTH: meta = 3; break;
		case WEST: meta = 4; break;
		case EAST: meta = 5; break;
		}

		MetaBlock container = new MetaBlock(Blocks.dropper);
		container.setBlock(editor, pos);
		editor.setBlockMetadata(pos, meta);
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
