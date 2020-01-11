package greymerk.roguelike.worldgen.blocks;

import net.minecraft.block.BlockFurnace;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;

public class Furnace {

  public static final int FUEL_SLOT = 1;
  public static final int OUTPUT_SLOT = 2;

  public static void generate(IWorldEditor editor, Cardinal dir, Coord pos) {
    generate(editor, null, false, dir, pos);
  }

  public static void generate(IWorldEditor editor, boolean lit, Cardinal dir, Coord pos) {
    generate(editor, null, lit, dir, pos);
  }

  public static void generate(IWorldEditor editor, ItemStack fuel, boolean lit, Cardinal dir, Coord pos) {

    if (!RogueConfig.getBoolean(RogueConfig.FURNITURE)) {
      return;
    }

    MetaBlock furnace;

    if (lit) {
      furnace = new MetaBlock(Blocks.LIT_FURNACE);
    } else {
      furnace = new MetaBlock(Blocks.FURNACE);
    }

    furnace.withProperty(BlockFurnace.FACING, Cardinal.facing(Cardinal.reverse(dir)));

    furnace.set(editor, pos);

    if (fuel == null) {
      return;
    }

    TileEntity te = editor.getTileEntity(pos);
    if (te == null) {
      return;
    }
    if (!(te instanceof TileEntityFurnace)) {
      return;
    }
    TileEntityFurnace teFurnace = (TileEntityFurnace) te;
    teFurnace.setInventorySlotContents(FUEL_SLOT, fuel);
  }
}
