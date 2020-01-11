package greymerk.roguelike.theme;

import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.MetaStair;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.Log;
import greymerk.roguelike.worldgen.blocks.StairType;
import greymerk.roguelike.worldgen.blocks.Wood;
import greymerk.roguelike.worldgen.blocks.WoodBlock;

public class ThemeHouse extends ThemeBase {

  public ThemeHouse() {

    IBlockFactory walls = BlockType.get(BlockType.BRICK);
    MetaStair stair = new MetaStair(StairType.BRICK);
    MetaBlock pillar = BlockType.get(BlockType.GRANITE_POLISHED);
    MetaBlock floor = BlockType.get(BlockType.GRANITE_POLISHED);
    this.primary = new BlockSet(floor, walls, stair, pillar);

    MetaBlock secondaryWalls = Wood.get(Wood.OAK, WoodBlock.PLANK);
    MetaBlock secondaryFloor = BlockType.get(BlockType.ANDESITE_POLISHED);
    MetaStair secondaryStair = new MetaStair(StairType.OAK);
    MetaBlock secondaryPillar = Log.getLog(Wood.OAK);
    this.secondary = new BlockSet(secondaryFloor, secondaryWalls, secondaryStair, secondaryPillar);

  }
}
