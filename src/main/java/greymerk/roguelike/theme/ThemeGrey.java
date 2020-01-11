package greymerk.roguelike.theme;

import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.MetaStair;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.StairType;

public class ThemeGrey extends ThemeBase {

  public ThemeGrey() {

    MetaBlock andesite = BlockType.get(BlockType.ANDESITE);
    MetaBlock smoothAndesite = BlockType.get(BlockType.ANDESITE_POLISHED);

    MetaStair stair = new MetaStair(StairType.STONEBRICK);

    this.primary = new BlockSet(andesite, smoothAndesite, stair, smoothAndesite);
    this.secondary = this.primary;
  }
}
