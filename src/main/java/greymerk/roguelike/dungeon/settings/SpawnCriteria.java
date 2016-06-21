package greymerk.roguelike.dungeon.settings;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

public class SpawnCriteria {

	int weight;
	List<String> biomes;
	List<BiomeDictionary.Type> biomeTypes;

	public SpawnCriteria(){
		this.weight = 1;
	}
	
	public SpawnCriteria(JsonObject data){
		this.weight = data.has("weight") ? data.get("weight").getAsInt() : 1;
		
		if(data.has("biomes")){
			JsonArray biomeList = data.get("biomes").getAsJsonArray();
			this.biomes = new ArrayList<String>();
			for(JsonElement e : biomeList){
				String name = e.getAsString();
				this.biomes.add(name);
			}
		}
		
		if(data.has("biomeTypes")){
			JsonArray biomeTypeList = data.get("biomeTypes").getAsJsonArray();
			this.biomeTypes = new ArrayList<BiomeDictionary.Type>();
			for(JsonElement e : biomeTypeList){
				String type = e.getAsString();
				this.biomeTypes.add(BiomeDictionary.Type.valueOf(type));
			}
		}
	}
	
	public void setWeight(int weight){
		this.weight = weight;
	}
	
	public void setbiomes(List<String> biomes){
		this.biomes = biomes;
	}
	
	public void setBiomeTypes(List<BiomeDictionary.Type> biomeTypes){
		this.biomeTypes = biomeTypes;
	}
	
	
	public boolean isValid(IWorldEditor editor, Coord pos){
		
		if(this.biomes == null && this.biomeTypes == null) return true;
		
		boolean biomeFound = false;
		
		Biome biome = editor.getBiome(pos);
		
		if(this.biomes != null){
			if(this.biomes.contains(biome.getBiomeName())) biomeFound = true;
		}
		
		if(this.biomeTypes != null){
			for(BiomeDictionary.Type type : this.biomeTypes){
				if(BiomeDictionary.isBiomeOfType(biome, type)) biomeFound = true;
			}
		}
		
		return biomeFound;
	}
	
	public static boolean isValidDimension(int dim, List<Integer> wl, List<Integer> bl){
		
		if(bl.contains(dim)) return false;
		
		if(wl.isEmpty()) return true;
		
		return wl.contains(dim);
	}
	
}