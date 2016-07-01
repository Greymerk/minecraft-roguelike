package greymerk.roguelike.worldgen.blocks;

import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBrewingStand;

public enum BrewingStand {

	LEFT(0), MIDDLE(1), RIGHT(2), INGREDIENT(3), FUEL(4);
	
	private int id;
	BrewingStand(int id){
		this.id = id;
	}
	
	public static boolean generate(IWorldEditor editor, Coord pos){
		MetaBlock stand = new MetaBlock(Blocks.BREWING_STAND);
		return stand.set(editor, pos);
	}
	
	public static TileEntityBrewingStand get(IWorldEditor editor, Coord pos){
		MetaBlock stand = editor.getBlock(pos);
		if(stand.getBlock() != Blocks.BREWING_STAND) return null;
		TileEntity te = editor.getTileEntity(pos);
		if(te == null) return null;
		if(!(te instanceof TileEntityBrewingStand)) return null;
		TileEntityBrewingStand brewingTE = (TileEntityBrewingStand)te;
		return brewingTE;
	}
	
	public static boolean add(IWorldEditor editor, Coord pos, BrewingStand slot, ItemStack item){
		TileEntityBrewingStand stand = get(editor, pos);
		if(stand == null) return false;
		stand.setInventorySlotContents(slot.id, item);
		return true;
	}
}
