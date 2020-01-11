package greymerk.roguelike.theme;

import greymerk.roguelike.worldgen.BlockCheckers;
import greymerk.roguelike.worldgen.BlockJumble;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.MetaStair;
import greymerk.roguelike.worldgen.blocks.door.Door;

import static greymerk.roguelike.worldgen.blocks.BlockType.ANDESITE;
import static greymerk.roguelike.worldgen.blocks.BlockType.ANDESITE_POLISHED;
import static greymerk.roguelike.worldgen.blocks.BlockType.COBBLESTONE;
import static greymerk.roguelike.worldgen.blocks.BlockType.GRAVEL;
import static greymerk.roguelike.worldgen.blocks.BlockType.STONE_BRICK;
import static greymerk.roguelike.worldgen.blocks.BlockType.STONE_BRICK_CRACKED;
import static greymerk.roguelike.worldgen.blocks.BlockType.STONE_BRICK_MOSSY;
import static greymerk.roguelike.worldgen.blocks.BlockType.get;
import static greymerk.roguelike.worldgen.blocks.StairType.STONEBRICK;
import static greymerk.roguelike.worldgen.blocks.door.DoorType.IRON;

public class ThemeCrypt extends ThemeBase {

  public ThemeCrypt() {

    BlockJumble stone = new BlockJumble();
    stone.addBlock(get(STONE_BRICK));
    stone.addBlock(get(STONE_BRICK_CRACKED));
    stone.addBlock(get(STONE_BRICK_MOSSY));

    BlockWeightedRandom walls = new BlockWeightedRandom();
    walls.addBlock(stone, 100);
    walls.addBlock(get(COBBLESTONE), 10);
    walls.addBlock(get(GRAVEL), 5);

    MetaBlock andesite = get(ANDESITE);
    MetaBlock smoothAndesite = get(ANDESITE_POLISHED);
    BlockCheckers pillar = new BlockCheckers(andesite, smoothAndesite);

    MetaStair stair = new MetaStair(STONEBRICK);

    this.primary = new BlockSet(walls, walls, stair, pillar, new Door(IRON));
    this.secondary = this.primary;

  }
}
