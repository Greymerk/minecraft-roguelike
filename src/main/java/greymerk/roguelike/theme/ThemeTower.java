package greymerk.roguelike.theme;

import greymerk.roguelike.worldgen.BlockJumble;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaStair;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.StairType;

public class ThemeTower extends ThemeBase {

  public ThemeTower() {

    BlockJumble stone = new BlockJumble();
    stone.addBlock(BlockType.get(BlockType.STONE_BRICK));
    stone.addBlock(BlockType.get(BlockType.STONE_BRICK_CRACKED));
    stone.addBlock(BlockType.get(BlockType.STONE_BRICK_MOSSY));

    BlockWeightedRandom walls = new BlockWeightedRandom();
    walls.addBlock(BlockType.get(BlockType.AIR), 5);
    walls.addBlock(stone, 30);
    walls.addBlock(BlockType.get(BlockType.COBBLESTONE), 10);
    walls.addBlock(BlockType.get(BlockType.GRAVEL), 5);

    MetaStair stair = new MetaStair(StairType.STONEBRICK);

    IBlockFactory pillar = BlockType.get(BlockType.ANDESITE_POLISHED);
    this.primary = new BlockSet(walls, stair, pillar);
    this.secondary = this.primary;

  }
}
