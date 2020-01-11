package greymerk.roguelike.worldgen.blocks;

import net.minecraft.block.BlockDoublePlant;
import net.minecraft.init.Blocks;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;

public enum TallPlant {

  SUNFLOWER,
  LILAC,
  TALLGRASS,
  FERN,
  ROSE,
  PEONY;

  public static void generate(IWorldEditor editor, TallPlant type, Coord pos) {
    Coord cursor;

    MetaBlock upper = new MetaBlock(Blocks.DOUBLE_PLANT.getStateFromMeta(getMeta(type, true)));
    MetaBlock lower = new MetaBlock(Blocks.DOUBLE_PLANT.getStateFromMeta(getMeta(type, false)));

    cursor = new Coord(pos);
    lower.set(editor, cursor);
    cursor.add(Cardinal.UP);
    upper.set(editor, cursor);
  }

  public static int getMeta(TallPlant type, boolean top) {
    if (top) {
      return 8;
    }

    switch (type) {
      case SUNFLOWER:
        return 0;
      case LILAC:
        return 1;
      case TALLGRASS:
        return 2;
      case FERN:
        return 3;
      case ROSE:
        return 4;
      case PEONY:
        return 5;
      default:
        return 0;
    }

  }

  public static BlockDoublePlant.EnumPlantType getType(TallPlant type) {
    switch (type) {
      case SUNFLOWER:
        return BlockDoublePlant.EnumPlantType.SUNFLOWER;
      case LILAC:
        return BlockDoublePlant.EnumPlantType.SYRINGA;
      case TALLGRASS:
        return BlockDoublePlant.EnumPlantType.GRASS;
      case FERN:
        return BlockDoublePlant.EnumPlantType.FERN;
      case ROSE:
        return BlockDoublePlant.EnumPlantType.ROSE;
      case PEONY:
        return BlockDoublePlant.EnumPlantType.PAEONIA;
      default:
        return BlockDoublePlant.EnumPlantType.GRASS;
    }
  }

}
