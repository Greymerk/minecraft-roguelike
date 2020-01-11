package greymerk.roguelike.worldgen;

import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class PositionInfo implements IPositionInfo {

  private World world;
  private Coord pos;

  public PositionInfo(World world, Coord pos) {
    this.world = world;
    this.pos = pos;
  }

  @Override
  public int getDimension() {
    return world.provider.getDimension();
  }

  @Override
  public Biome getBiome() {
    return world.getBiome(pos.getBlockPos());
  }
}
