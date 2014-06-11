package greymerk.roguelike.catacomb.theme;

import greymerk.roguelike.catacomb.segment.Segment;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.world.biome.BiomeGenBase;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public enum Theme {

	OAK, SPRUCE, STONE, MOSSY, NETHER, SANDSTONE, QUARTZ, BLING, CHECKER, RAINBOW, SNOW, JUNGLE, BRICK;
	
	public static ITheme getTheme(Theme type){
		
		ITheme theme;
		
		switch(type){
		case OAK: theme = new ThemeOak(); break;
		case SPRUCE: theme = new ThemeSpruce(); break;
		case STONE: theme = new ThemeStone(); break;
		case MOSSY: theme = new ThemeMossy(); break;
		case NETHER: theme = new ThemeNether(); break;
		case SANDSTONE: theme = new ThemeSandstone(); break;
		case QUARTZ: theme = new ThemeQuartz(); break;
		case BLING: theme = new ThemeBling(); break;
		case CHECKER: theme = new ThemeChecker(); break;
		case RAINBOW: theme = new ThemeRainbow(); break;
		case SNOW: theme = new ThemeSnow(); break;
		case JUNGLE: theme = new ThemeJungle(); break;
		case BRICK: theme = new ThemeBrick(); break;
		default: return null;
		}
		
		return theme;
	}
	
	public static ITheme create(JsonObject json) throws Exception{
				
		ITheme theme;
		BlockSet primary = null;
		BlockSet secondary = null;
		List<Segment> segments = null;
		Segment arch = null;

		// primary blocks
		if(json.has("primary")){
			JsonObject data = json.get("primary").getAsJsonObject();		
			primary = new BlockSet(data);
		}
		
		// secondary blocks
		if(json.has("secondary")){
			JsonObject data = json.get("secondary").getAsJsonObject();		
			secondary = new BlockSet(data);
		}
	
		if(json.has("segments")){
			segments = new ArrayList<Segment>();
			JsonArray data = json.get("segments").getAsJsonArray();
			for(JsonElement e : data){
				segments.add(Segment.valueOf(e.getAsString()));
			}
		}
		
		if(json.has("arch")){
			String s = json.get("arch").getAsString();
			arch = Segment.valueOf(s);
		}
		
		if(json.has("base")){
			theme = Theme.getTheme(Theme.valueOf(json.get("base").getAsString()));
			return new ThemeBase((ThemeBase) theme, primary, secondary, segments, arch);
		} else {
			return new ThemeBase(primary, secondary, segments, arch);
		}
	}
	
	public static ITheme getByLevel(BiomeGenBase biome, int level){
		
		boolean hot = biome.temperature >= 1.0F;
		boolean cold = biome.temperature <= 0.1F;
		boolean wet = biome.rainfall >= 0.85F;
		boolean dry = biome.rainfall <= 0.1;
		
		switch(level){
		case 0:
			if(cold) return getTheme(SPRUCE);
			if(hot && dry) return getTheme(SANDSTONE);
			if(hot && wet) return getTheme(JUNGLE);
			return getTheme(OAK);
		case 1:
			if(hot && dry) return getTheme(SANDSTONE);
			if(hot && wet) return getTheme(JUNGLE);
			return getTheme(SPRUCE);
		case 2:
			if(hot && wet) return getTheme(MOSSY);
			return getTheme(STONE);
		case 3:
			if(hot && dry) return getTheme(STONE);
			return getTheme(MOSSY);
		case 4: return getTheme(NETHER);
		default: return null;
		}
	}
}
