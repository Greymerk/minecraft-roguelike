package greymerk.roguelike.theme;

import greymerk.roguelike.worldgen.BlockCheckers;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.MetaStair;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.StairType;

public class ThemeChecker extends ThemeBase {

  public ThemeChecker() {

    MetaBlock one = BlockType.get(BlockType.OBSIDIAN);
    MetaBlock two = BlockType.get(BlockType.QUARTZ);

    IBlockFactory checks = new BlockCheckers(one, two);

    MetaStair stair = new MetaStair(StairType.QUARTZ);

    this.primary = new BlockSet(checks, stair, checks);

    this.secondary = primary;

  }
}
