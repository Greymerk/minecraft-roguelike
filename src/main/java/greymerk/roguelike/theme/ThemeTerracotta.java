package greymerk.roguelike.theme;

import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.worldgen.BlockJumble;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.MetaStair;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.StairType;
import greymerk.roguelike.worldgen.blocks.Terracotta;

public class ThemeTerracotta extends ThemeBase {

  public ThemeTerracotta() {

    BlockJumble blocks = new BlockJumble();
    for (Cardinal dir : Cardinal.directions) {
      blocks.addBlock(Terracotta.get(DyeColor.MAGENTA, dir));
    }

    MetaStair stair = new MetaStair(StairType.PURPUR);
    MetaBlock pillar = BlockType.get(BlockType.PURPUR_PILLAR);
    MetaBlock deco = BlockType.get(BlockType.PURPUR_DOUBLE_SLAB);

    this.primary = new BlockSet(blocks, stair, pillar);
    this.secondary = new BlockSet(deco, stair, pillar);

  }
}
