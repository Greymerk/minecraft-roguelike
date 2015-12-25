package greymerk.roguelike.dungeon.settings;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;

public class SpawnCriteria {

	int weight;
	List<String> biomes;
	List<BiomeDictionary.Type> biomeTypes;
	List<Integer> dimensionBlackList;
	List<Integer> dimensionWhiteList;
	
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
		
		if(data.has("dimensionBL")){
			JsonArray blackList = data.get("dimensionBL").getAsJsonArray();
			this.dimensionBlackList = new ArrayList<Integer>();
			for(JsonElement e : blackList){
				int id = e.getAsInt();
				this.dimensionBlackList.add(id);
			}
		}
		
		if(data.has("dimensionWL")){
			JsonArray whiteList = data.get("dimensionWL").getAsJsonArray();
			this.dimensionWhiteList = new ArrayList<Integer>();
			for(JsonElement e : whiteList){
				int id = e.getAsInt();
				this.dimensionWhiteList.add(id);
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
	
	public void setDimBlackList(List<Integer> blackList){
		this.dimensionBlackList = blackList;
	}
	
	public void setDimWhiteList(List<Integer> whiteList){
		this.dimensionWhiteList = whiteList;
	}
	
	public boolean isValid(WorldEditor editor, Coord pos){
		
		Integer dimID = editor.getDimension();
		
		List<Integer> dimBL = new ArrayList<Integer>();
		
		if(this.dimensionBlackList != null){
			this.dimensionBlackList.addAll(this.dimensionBlackList);
		}
		
		dimBL.addAll(RogueConfig.getIntList(RogueConfig.DIMENSIONBL));
		
		if(dimBL.contains(dimID)) return false;
		
		List<Integer> dimWL = new ArrayList<Integer>();
		
		if(this.dimensionWhiteList != null){
			dimWL.addAll(this.dimensionWhiteList);
		}
		
		dimWL.addAll(RogueConfig.getIntList(RogueConfig.DIMENSIONWL));
		
		// 
		if(!dimWL.isEmpty()){
			if(!dimWL.contains(dimID)) return false;
		}
		
		if(this.biomes == null && this.biomeTypes == null) return true;
		
		boolean biomeFound = false;
		
		BiomeGenBase biome = editor.getBiome(pos);
		
		if(this.biomes != null){
			if(this.biomes.contains(biome.biomeName)) biomeFound = true;
		}
		
		if(this.biomeTypes != null){
			for(BiomeDictionary.Type type : this.biomeTypes){
				if(BiomeDictionary.isBiomeOfType(biome, type)) biomeFound = true;
			}
		}
		
		return biomeFound;
	}
	
}