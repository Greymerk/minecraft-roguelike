package greymerk.roguelike.catacomb.theme;

import com.google.gson.JsonObject;

public enum Theme {

	OAK, SPRUCE, CRYPT, MOSSY, NETHER, SANDSTONE, QUARTZ, BLING, CHECKER, RAINBOW, SNOW, JUNGLE, BRICK, DARKOAK;
	
	public static ITheme getTheme(Theme type){
		
		ITheme theme;
		
		switch(type){
		case OAK: theme = new ThemeOak(); break;
		case SPRUCE: theme = new ThemeSpruce(); break;
		case CRYPT: theme = new ThemeCrypt(); break;
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
		case DARKOAK: theme = new ThemeDarkOak(); break;
		default: return null;
		}
		
		return theme;
	}
	
	public static ITheme create(JsonObject json){
				
		ITheme theme;
		BlockSet primary = null;
		BlockSet secondary = null;

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
	

		
		if(json.has("base")){
			theme = Theme.getTheme(Theme.valueOf(json.get("base").getAsString()));
			return new ThemeBase((ThemeBase) theme, primary, secondary);
		} else {
			return new ThemeBase(primary, secondary);
		}
	}
}
