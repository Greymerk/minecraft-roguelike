package greymerk.roguelike.worldgen.redstone;

import net.minecraft.block.BlockDispenser;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityDispenser;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;

public class Dispenser {

  public static boolean generate(IWorldEditor editor, Cardinal dir, Coord pos) {

    MetaBlock container = new MetaBlock(Blocks.DISPENSER);
    container.withProperty(BlockDispenser.FACING, Cardinal.facing(dir));
    container.set(editor, pos);
    return true;
  }

  public static void add(IWorldEditor editor, Coord pos, int slot, ItemStack item) {

    TileEntity te = editor.getTileEntity(pos);
    if (te == null) {
      return;
    }
    if (!(te instanceof TileEntityDispenser)) {
      return;
    }
    TileEntityDispenser dispenser = (TileEntityDispenser) te;
    dispenser.setInventorySlotContents(slot, item);
  }
}
