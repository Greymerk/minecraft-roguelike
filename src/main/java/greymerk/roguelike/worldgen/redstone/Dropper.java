package greymerk.roguelike.worldgen.redstone;

import net.minecraft.block.BlockDropper;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityDropper;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;

public class Dropper {

  public boolean generate(IWorldEditor editor, Cardinal dir, Coord pos) {

    MetaBlock container = new MetaBlock(Blocks.DROPPER);
    container.withProperty(BlockDropper.FACING, Cardinal.facing(dir));
    container.set(editor, pos);
    return true;
  }

  public void add(IWorldEditor editor, Coord pos, int slot, ItemStack item) {

    TileEntity te = editor.getTileEntity(pos);
    if (te == null) {
      return;
    }
    if (!(te instanceof TileEntityDropper)) {
      return;
    }
    TileEntityDropper dropper = (TileEntityDropper) te;
    dropper.setInventorySlotContents(slot, item);
  }
}
