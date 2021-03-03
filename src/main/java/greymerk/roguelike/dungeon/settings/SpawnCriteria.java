package greymerk.roguelike.dungeon.settings;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.BiomeDictionary;

public class SpawnCriteria {

	int weight;
	List<ResourceLocation> biomes;
	List<BiomeDictionary.Type> biomeTypes;
	List<Integer> dimensionWL;
	List<Integer> dimensionBL;
	boolean everywhere;

	public SpawnCriteria(){
		this.weight = 1;
		this.biomes = new ArrayList<ResourceLocation>();
		this.biomeTypes = new ArrayList<BiomeDictionary.Type>();
		this.dimensionWL = new ArrayList<Integer>();
		this.dimensionBL = new ArrayList<Integer>();
		this.everywhere = false;
	}
	
	public SpawnCriteria(JsonObject data){
		this();
		
		this.weight = data.has("weight") ? data.get("weight").getAsInt() : 1;
		
		if(data.has("biomes")){
			JsonArray biomeList = data.get("biomes").getAsJsonArray();
			this.biomes = new ArrayList<ResourceLocation>();
			for(JsonElement e : biomeList){
				String name = e.getAsString();
				this.biomes.add(new ResourceLocation(name));
			}
		}
		
		if(data.has("biomeTypes")){
			JsonArray biomeTypeList = data.get("biomeTypes").getAsJsonArray();
			this.biomeTypes = new ArrayList<BiomeDictionary.Type>();
			for(JsonElement e : biomeTypeList){
				String type = e.getAsString().toUpperCase();
				BiomeDictionary.Type t = BiomeDictionary.Type.getType(type, new BiomeDictionary.Type[0]);
				if(BiomeDictionary.getBiomes(t).size() > 0) this.biomeTypes.add(t);
			}
		}

		if(data.has("dimensionWL")){
			JsonArray dimensionList = data.get("dimensionWL").getAsJsonArray();
			this.dimensionWL = new ArrayList<Integer>();
			for(JsonElement e : dimensionList){
				int id = e.getAsInt();
				this.dimensionWL.add(id);
			}
		}

		if(data.has("dimensionBL")){
			JsonArray dimensionList = data.get("dimensionBL").getAsJsonArray();
			this.dimensionBL = new ArrayList<Integer>();
			for(JsonElement e : dimensionList){
				int id = e.getAsInt();
				this.dimensionBL.add(id);
			}
		}
		
		this.everywhere = this.biomes.isEmpty() && this.biomeTypes.isEmpty() && this.dimensionWL.isEmpty() && this.dimensionBL.isEmpty();
	}
	
	public void setWeight(int weight){
		this.weight = weight;
	}
	
	public void setbiomes(List<ResourceLocation> biomes){
		this.biomes = biomes == null ? new ArrayList<ResourceLocation>() : biomes;
		this.everywhere = this.biomes.isEmpty() && this.biomeTypes.isEmpty() && this.dimensionWL.isEmpty() && this.dimensionBL.isEmpty();
	}
	
	public void setBiomeTypes(List<BiomeDictionary.Type> biomeTypes){
		this.biomeTypes = biomeTypes == null ? new ArrayList<BiomeDictionary.Type>() : biomeTypes;
		this.everywhere = this.biomes.isEmpty() && this.biomeTypes.isEmpty() && this.dimensionWL.isEmpty() && this.dimensionBL.isEmpty();
	}
	
	
	public boolean isValid(ISpawnContext context){
		
		if(this.everywhere) return true;
		
		boolean biomeFound = true;
		boolean dimensionFound = true;
		
		if(!this.biomes.isEmpty()) biomeFound = context.includesBiome(biomes);
		
		if(!this.biomeTypes.isEmpty()) biomeFound = context.includesBiomeType(this.biomeTypes);

		if(!this.dimensionWL.isEmpty()) dimensionFound = this.dimensionWL.contains(context.getDimension());
		else if(!this.dimensionBL.isEmpty()) dimensionFound = !this.dimensionBL.contains(context.getDimension());

		return biomeFound && dimensionFound;
	}
	
	public static boolean isValidDimension(int dim, List<Integer> wl, List<Integer> bl){
		if(bl.contains(dim)) return false;
		if(wl.isEmpty()) return true;
		return wl.contains(dim);
	}
	
}