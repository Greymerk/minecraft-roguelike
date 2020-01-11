package greymerk.roguelike.theme;

import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.MetaStair;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.Log;
import greymerk.roguelike.worldgen.blocks.StairType;
import greymerk.roguelike.worldgen.blocks.Wood;

public class ThemeSpruce extends ThemeBase {

  public ThemeSpruce() {

    BlockWeightedRandom walls = new BlockWeightedRandom();
    walls.addBlock(BlockType.get(BlockType.STONE_BRICK), 20);
    MetaBlock cracked = BlockType.get(BlockType.STONE_BRICK_CRACKED);

    walls.addBlock(cracked, 10);
    walls.addBlock(BlockType.get(BlockType.COBBLESTONE), 5);
    walls.addBlock(BlockType.get(BlockType.GRAVEL), 1);

    MetaStair stair = new MetaStair(StairType.STONEBRICK);

    this.primary = new BlockSet(walls, stair, walls);

    MetaBlock spruce = Wood.getPlank(Wood.SPRUCE);
    MetaBlock SegmentWall = spruce;
    MetaStair SegmentStair = new MetaStair(StairType.SPRUCE);

    MetaBlock pillar = Log.get(Wood.SPRUCE, Cardinal.DOWN);
    this.secondary = new BlockSet(SegmentWall, SegmentStair, pillar);

  }
}
