package greymerk.roguelike.catacomb.theme;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.minecraft.src.BiomeGenBase;

public enum Themes {

	OAK, SPRUCE, STONE, MOSSY, NETHER, SANDSTONE, QUARTZ, BLING, TOWER, CHECKER, RAINBOW, SNOW, JUNGLE;
	
	private static Map<String, ITheme> themeCache = new HashMap<String, ITheme>();
	
	public static ITheme getTheme(Themes type, Random rand){
		if(themeCache.containsKey(type.name())){
			return themeCache.get(type.name());
		}
		
		ITheme theme;
		
		switch(type){
		case OAK: theme = new ThemeOak(rand); break;
		case SPRUCE: theme = new ThemeSpruce(rand); break;
		case STONE: theme = new ThemeStone(rand); break;
		case MOSSY: theme = new ThemeMossy(rand); break;
		case NETHER: theme = new ThemeNether(rand); break;
		case SANDSTONE: theme = new ThemeSandstone(rand); break;
		case QUARTZ: theme = new ThemeQuartz(rand); break;
		case BLING: theme = new ThemeBling(rand); break;
		case TOWER: theme = new ThemeTower(rand); break;
		case CHECKER: theme = new ThemeChecker(rand); break;
		case RAINBOW: theme = new ThemeRainbow(rand); break;
		case SNOW: theme = new ThemeSnow(rand); break;
		case JUNGLE: theme = new ThemeJungle(rand); break;
		default: return null;
		}
		
		themeCache.put(type.name(), theme);
		return theme;
	}
	
	public static ITheme getByLevel(BiomeGenBase biome, int level, Random rand){
		
		boolean hot = biome.temperature >= 1.0F;
		boolean cold = biome.temperature <= 0.1F;
		boolean wet = biome.rainfall >= 0.85F;
		boolean dry = biome.rainfall <= 0.1;
		
		
		switch(level){
		case 0: 
			if(hot && dry) return getTheme(SANDSTONE, rand);
			if(hot && wet) return getTheme(JUNGLE, rand);
			return getTheme(OAK, rand);
		case 1:
			if(hot && dry) return getTheme(SANDSTONE, rand);
			if(hot && wet) return getTheme(JUNGLE, rand);
			return getTheme(SPRUCE, rand);
		case 2: return getTheme(STONE, rand);
		case 3: return getTheme(MOSSY, rand);
		case 4: return getTheme(NETHER, rand);
		default: return null;
		}
	}
}
