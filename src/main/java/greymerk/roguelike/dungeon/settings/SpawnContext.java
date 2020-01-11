package greymerk.roguelike.dungeon.settings;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

import java.util.List;

import greymerk.roguelike.worldgen.IPositionInfo;

public class SpawnContext implements ISpawnContext {

  private IPositionInfo info;

  public SpawnContext(IPositionInfo info) {
    this.info = info;
  }

  @Override
  public boolean biomeHasType(Type type) {
    return BiomeDictionary.hasType(info.getBiome(), type);
  }

  @Override
  public Biome getBiome() {
    return info.getBiome();
  }

  @Override
  public boolean includesBiome(List<ResourceLocation> biomeNames) {
    return biomeNames.contains(info.getBiome().getRegistryName());
  }

  @Override
  public boolean includesBiomeType(List<Type> biomeTypes) {

    for (BiomeDictionary.Type type : biomeTypes) {
      if (biomeHasType(type)) {
        return true;
      }
    }

    return false;
  }

  @Override
  public int getDimension() {
    return this.info.getDimension();
  }


}
