package com.greymerk.roguelike.editor.theme;

import java.util.Random;

import com.greymerk.roguelike.editor.theme.themes.ThemeBlack;
import com.greymerk.roguelike.editor.theme.themes.ThemeCrumbledMossy;
import com.greymerk.roguelike.editor.theme.themes.ThemeCrumbledStone;
import com.greymerk.roguelike.editor.theme.themes.ThemeDarkOak;
import com.greymerk.roguelike.editor.theme.themes.ThemeDefault;
import com.greymerk.roguelike.editor.theme.themes.ThemeMossy;
import com.greymerk.roguelike.editor.theme.themes.ThemeNether;
import com.greymerk.roguelike.editor.theme.themes.ThemeOak;
import com.greymerk.roguelike.editor.theme.themes.ThemeRedNether;
import com.greymerk.roguelike.editor.theme.themes.ThemeSlate;
import com.greymerk.roguelike.editor.theme.themes.ThemeStone;
import com.greymerk.roguelike.editor.theme.themes.ThemeTiledSlate;
import com.greymerk.roguelike.editor.theme.themes.ThemeTower;
import com.greymerk.roguelike.editor.theme.themes.ThemeWarped;

public enum Theme {

	DEFAULT, TOWER, STONE, OAK, DARKOAK, CRUMBLEDSTONE, MOSSY, CRUMBLEDMOSSY,
	SLATE, TILEDSLATE, NETHER, REDNETHER, WARPED, BLACK;
	
	public static ITheme getTheme(Theme type){
		
		switch(type){
		case DEFAULT: return new ThemeDefault();
		case TOWER: return new ThemeTower();
		case OAK: return new ThemeOak();
		case DARKOAK: return new ThemeDarkOak();
		case STONE: return new ThemeStone();
		case CRUMBLEDSTONE: return new ThemeCrumbledStone();
		case MOSSY: return new ThemeMossy();
		case CRUMBLEDMOSSY: return new ThemeCrumbledMossy();
		case SLATE: return new ThemeSlate();
		case TILEDSLATE: return new ThemeTiledSlate();
		case NETHER: return new ThemeNether();
		case REDNETHER: return new ThemeRedNether();
		case WARPED: return new ThemeWarped();
		case BLACK: return new ThemeBlack();
		default: return new ThemeDefault();
		}
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

	public static ITheme getFromDepth(int y) {
		if(y >= 50) return Theme.getTheme(Theme.OAK);
		if(y >= 40) return Theme.getTheme(Theme.DARKOAK);
		if(y >= 30) return Theme.getTheme(Theme.STONE);
		if(y >= 20) return Theme.getTheme(Theme.CRUMBLEDSTONE);
		if(y >= 10) return Theme.getTheme(Theme.MOSSY);
		if(y >= 0) return Theme.getTheme(Theme.CRUMBLEDMOSSY);
		if(y >= -10) return Theme.getTheme(Theme.TILEDSLATE);
		if(y >= -20) return Theme.getTheme(Theme.NETHER);
		if(y >= -30) return Theme.getTheme(Theme.REDNETHER);
		if(y >= -40) return Theme.getTheme(Theme.WARPED);
		if(y >= -50) return Theme.getTheme(Theme.BLACK);
		return Theme.getTheme(Theme.STONE);
	}
	
	public static ITheme getRandom(Random rand) {
		return Theme.getTheme(Theme.values()[rand.nextInt(Theme.values().length)]);
	}
	
}
