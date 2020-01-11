package greymerk.roguelike.theme;

import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.worldgen.BlockStripes;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.MetaStair;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.ColorBlock;
import greymerk.roguelike.worldgen.blocks.Quartz;
import greymerk.roguelike.worldgen.blocks.StairType;

public class ThemeEniQuartz extends ThemeBase {

  public ThemeEniQuartz() {

    BlockWeightedRandom red = new BlockWeightedRandom();
    red.addBlock(ColorBlock.get(ColorBlock.CLAY, DyeColor.RED), 100);
    red.addBlock(BlockType.get(BlockType.WATER_FLOWING), 5);
    red.addBlock(BlockType.get(BlockType.REDSTONE_BLOCK), 1);

    BlockStripes floor = new BlockStripes();
    floor.addBlock(red);
    floor.addBlock(BlockType.get(BlockType.OBSIDIAN));

    MetaBlock walls = BlockType.get(BlockType.BRICK);

    MetaStair stair = new MetaStair(StairType.BRICK);

    this.primary = new BlockSet(floor, walls, stair, walls);

    MetaBlock quartz = BlockType.get(BlockType.QUARTZ);
    MetaStair quartzStair = new MetaStair(StairType.QUARTZ);
    MetaBlock quartzPillar = Quartz.getPillar(Cardinal.UP);

    this.secondary = new BlockSet(floor, quartz, quartzStair, quartzPillar);
  }
}
