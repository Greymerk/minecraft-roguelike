package com.greymerk.roguelike.editor.theme;

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
import com.greymerk.roguelike.editor.theme.themes.ThemeSpruce;
import com.greymerk.roguelike.editor.theme.themes.ThemeStone;
import com.greymerk.roguelike.editor.theme.themes.ThemeTiledSlate;
import com.greymerk.roguelike.editor.theme.themes.ThemeTower;
import com.greymerk.roguelike.editor.theme.themes.ThemeWarped;

import net.minecraft.util.math.random.Random;

public enum Theme {

	DEFAULT, TOWER, OAK, SPRUCE, DARKOAK, 
	STONE, CRUMBLEDSTONE, MOSSY, CRUMBLEDMOSSY,
	SLATE, TILEDSLATE, NETHER, REDNETHER, WARPED, BLACK;
	
	public static ITheme getTheme(Theme type){
		
		switch(type){
		case DEFAULT: return new ThemeDefault();
		case TOWER: return new ThemeTower();
		case OAK: return new ThemeOak();
		case SPRUCE: return new ThemeSpruce();
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
