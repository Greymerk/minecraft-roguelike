package greymerk.roguelike.theme;

import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.MetaStair;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.StairType;
import greymerk.roguelike.worldgen.blocks.Wood;
import greymerk.roguelike.worldgen.blocks.WoodBlock;

public class ThemeEtho extends ThemeBase {

  public ThemeEtho() {

    MetaBlock floor = BlockType.get(BlockType.GRASS);

    MetaBlock walls = Wood.get(WoodBlock.PLANK);

    MetaStair stair = new MetaStair(StairType.OAK);
    MetaBlock pillar = Wood.get(WoodBlock.LOG);

    this.primary = new BlockSet(floor, walls, stair, pillar);

    this.secondary = primary;
  }
}
