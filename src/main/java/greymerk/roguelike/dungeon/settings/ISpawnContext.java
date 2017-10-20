package greymerk.roguelike.dungeon.settings;

import java.util.List;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public interface ISpawnContext {

	public boolean biomeHasType(BiomeDictionary.Type type);

	public Biome getBiome();

	public boolean includesBiome(List<ResourceLocation> biomes);

	public boolean includesBiomeType(List<Type> biomeTypes);
	
	public int getDimension();
	
}
