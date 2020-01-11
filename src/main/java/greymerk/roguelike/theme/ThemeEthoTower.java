package greymerk.roguelike.theme;

import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.MetaStair;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.ColorBlock;
import greymerk.roguelike.worldgen.blocks.StairType;

public class ThemeEthoTower extends ThemeBase {

  public ThemeEthoTower() {

    MetaBlock primaryWall = ColorBlock.get(ColorBlock.CLAY, DyeColor.CYAN);
    MetaStair stair = new MetaStair(StairType.SANDSTONE);
    MetaBlock secondaryWall = BlockType.get(BlockType.SANDSTONE_SMOOTH);

    this.primary = new BlockSet(primaryWall, primaryWall, stair, primaryWall);
    this.secondary = new BlockSet(secondaryWall, secondaryWall, stair, secondaryWall);
  }

}
