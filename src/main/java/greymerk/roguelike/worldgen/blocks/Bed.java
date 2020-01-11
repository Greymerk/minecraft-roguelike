package greymerk.roguelike.worldgen.blocks;

import net.minecraft.block.BlockBed;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityBed;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;

public class Bed {

  public static void generate(IWorldEditor editor, Cardinal dir, Coord pos, DyeColor color) {
    Coord cursor = new Coord(pos);


    if (RogueConfig.getBoolean(RogueConfig.FURNITURE)) {
      MetaBlock head = new MetaBlock(Blocks.BED);
      head.withProperty(BlockBed.PART, BlockBed.EnumPartType.HEAD);
      head.withProperty(BlockBed.FACING, Cardinal.facing(dir));
      head.set(editor, cursor);
      TileEntityBed bed = (TileEntityBed) editor.getTileEntity(cursor);
      bed.setColor(DyeColor.get(color));
    } else {
      ColorBlock.get(ColorBlock.WOOL, DyeColor.WHITE).set(editor, cursor);
    }

    cursor.add(dir);
    if (RogueConfig.getBoolean(RogueConfig.FURNITURE)) {
      MetaBlock foot = new MetaBlock(Blocks.BED);
      foot.withProperty(BlockBed.PART, BlockBed.EnumPartType.FOOT);
      foot.withProperty(BlockBed.FACING, Cardinal.facing(dir));
      foot.set(editor, cursor);
      TileEntityBed bed = (TileEntityBed) editor.getTileEntity(cursor);
      bed.setColor(DyeColor.get(color));
    } else {
      ColorBlock.get(ColorBlock.WOOL, DyeColor.RED).set(editor, cursor);
    }
  }

  public static void generate(IWorldEditor editor, Cardinal dir, Coord pos) {
    generate(editor, dir, pos, DyeColor.RED);
  }
}
