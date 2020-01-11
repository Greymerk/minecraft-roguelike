package greymerk.roguelike.theme;

import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.MetaStair;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.StairType;

public class ThemeIce extends ThemeBase {

  public ThemeIce() {

    MetaBlock walls = BlockType.get(BlockType.SNOW);
    MetaStair stair = new MetaStair(StairType.QUARTZ);
    MetaBlock pillar = BlockType.get(BlockType.ICE_PACKED);

    this.primary = new BlockSet(walls, stair, pillar);
    this.secondary = this.primary;
  }
}
