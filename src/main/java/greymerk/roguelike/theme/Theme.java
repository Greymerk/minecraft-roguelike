package greymerk.roguelike.theme;

import com.google.gson.JsonObject;

public enum Theme {

	OAK, SPRUCE, CRYPT, MOSSY, MUDDY, NETHER, SANDSTONE, QUARTZ, BLING, CHECKER,
	RAINBOW, SNOW, JUNGLE, BRICK, DARKOAK, ICE, ENIKO, ENIKO2, ENIQUARTZ, ENIICE, TOWER,
	ETHO, CAVE, SEWER, ENDER, MINESHAFT, ETHOTOWER, PYRAMID, DARKHALL, TEMPLE, SANDSTONERED;
	
	public static ITheme getTheme(Theme type){
		
		ITheme theme;
		
		switch(type){
		case OAK: theme = new ThemeOak(); break;
		case SPRUCE: theme = new ThemeSpruce(); break;
		case CRYPT: theme = new ThemeCrypt(); break;
		case MOSSY: theme = new ThemeMossy(); break;
		case MUDDY: theme = new ThemeMuddy(); break;
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
		case ICE: theme = new ThemeIce(); break;
		case ENIKO: theme = new ThemeEniko(); break;
		case ENIKO2: theme = new ThemeEniko2(); break;
		case ENIQUARTZ: theme = new ThemeEniQuartz(); break;
		case ENIICE: theme = new ThemeEniIce(); break;
		case TOWER: theme = new ThemeTower(); break;
		case ETHO: theme = new ThemeEtho(); break;
		case CAVE: theme = new ThemeCave(); break;
		case SEWER: theme = new ThemeSewer(); break;
		case ENDER: theme = new ThemeEnder(); break;
		case MINESHAFT: theme = new ThemeMineShaft(); break;
		case ETHOTOWER: theme = new ThemeEthoTower(); break;
		case PYRAMID: theme = new ThemePyramid(); break;
		case DARKHALL: theme = new ThemeDarkHall(); break;
		case TEMPLE: theme = new ThemeTemple(); break;
		case SANDSTONERED: theme = new ThemeSandstoneRed(); break;
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
			theme = Theme.getTheme(Theme.OAK);
			return new ThemeBase((ThemeBase) theme, primary, secondary);
		}
	}
}
