package greymerk.roguelike.worldgen;

import net.minecraft.world.biome.Biome;

public interface IPositionInfo {

  int getDimension();

  Biome getBiome();

}
