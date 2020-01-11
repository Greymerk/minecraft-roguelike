package greymerk.roguelike.dungeon.settings;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

import java.util.List;

public interface ISpawnContext {

  boolean biomeHasType(BiomeDictionary.Type type);

  Biome getBiome();

  boolean includesBiome(List<ResourceLocation> biomes);

  boolean includesBiomeType(List<Type> biomeTypes);

  int getDimension();

}
