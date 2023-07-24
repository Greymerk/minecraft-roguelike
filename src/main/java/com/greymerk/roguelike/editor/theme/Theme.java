package com.greymerk.roguelike.editor.theme;

import java.util.Random;

import com.greymerk.roguelike.editor.theme.themes.ThemeTower;

public enum Theme {

	OAK, SPRUCE, CRYPT, MOSSY, MUDDY, NETHER, SANDSTONE, QUARTZ, BLING, CHECKER,
	RAINBOW, SNOW, JUNGLE, BRICK, DARKOAK, ICE, ENIKO, ENIKO2, ENIQUARTZ, ENIICE, TOWER,
	ETHO, CAVE, SEWER, ENDER, MINESHAFT, ETHOTOWER, PYRAMID, DARKHALL, TEMPLE, SANDSTONERED,
	HOUSE, GREY, PURPUR, HELL, TERRACOTTA, STONE, BUMBO;
	
	public static ITheme getTheme(Theme type){
		
		ITheme theme;
		
		switch(type){
		case TOWER: theme = new ThemeTower(); break;
		default: return new ThemeTower();
		}
		
		return theme;
	}
	
	public static ITheme create(ITheme toCopy){
		BlockSet primary = toCopy.getPrimary() != null ? new BlockSet(toCopy.getPrimary()) : null;
		BlockSet secondary = toCopy.getSecondary() != null ? new BlockSet(toCopy.getSecondary()) : null;
		return new ThemeBase(primary, secondary);
	}
	
	public static ITheme create(ITheme base, ITheme other){
		if(base == null && other == null){
			return null;
		}
		
		if(other == null && base != null){
			return create(base);
		}
		
		if(other != null && base == null){
			return create(other);
		}
		
		BlockSet primary = other.getPrimary() != null
				? new BlockSet(
						base.getPrimary() != null 
							? new BlockSet(base.getPrimary()) 
							: null,
						other.getPrimary()
						)
				: base.getPrimary() != null 
					? new BlockSet(base.getPrimary())
					: null;
		BlockSet secondary = other.getSecondary() != null
				? new BlockSet(
						base.getPrimary() != null 
							? new BlockSet(base.getPrimary()) 
							: null,
						other.getSecondary()
						)
				: base.getSecondary() != null
					? new BlockSet(base.getSecondary())
					: null;
		return new ThemeBase(primary, secondary);
	}
	
	public static Theme get(String name) throws Exception{
		if(!contains(name.toUpperCase())){
			throw new Exception("No such theme: " + name);
		}
		
		return valueOf(name.toUpperCase());
	}
	
	public static boolean contains(String name){
		for(Theme value : values()){
			if(value.toString().equals(name)) return true;
		}
		return false;
	}

	public static ITheme getRandom(Random rand) {
		return Theme.getTheme(Theme.values()[rand.nextInt(Theme.values().length)]);
	}
	
}
