package com.greymerk.roguelike.editor.theme;

import java.util.Random;

import com.greymerk.roguelike.editor.theme.themes.ThemeStone;
import com.greymerk.roguelike.editor.theme.themes.ThemeTower;

public enum Theme {

	TOWER, STONE;
	
	public static ITheme getTheme(Theme type){
		
		ITheme theme;
		
		switch(type){
		case TOWER: theme = new ThemeTower(); break;
		case STONE: theme = new ThemeStone(); break;
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
	
	public static ITheme get(String name){
		Theme type = Theme.valueOf(name.toUpperCase());
		return getTheme(type);
	}
	
	public static boolean contains(String name){
		for(Theme value : values()){
			if(value.name().equals(name)) return true;
		}
		return false;
	}

	public static ITheme getRandom(Random rand) {
		return Theme.getTheme(Theme.values()[rand.nextInt(Theme.values().length)]);
	}
	
}
