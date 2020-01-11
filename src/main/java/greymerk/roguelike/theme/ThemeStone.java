package greymerk.roguelike.theme;

import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.MetaStair;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.Stair;
import greymerk.roguelike.worldgen.blocks.StairType;

public class ThemeStone extends ThemeBase {

  public ThemeStone() {
    MetaBlock walls = BlockType.get(BlockType.STONE_BRICK);
    MetaStair stair = Stair.get(StairType.STONEBRICK);
    MetaBlock pillar = BlockType.get(BlockType.ANDESITE_POLISHED);

    this.primary = new BlockSet(walls, stair, pillar);
    this.secondary = primary;
  }
}
