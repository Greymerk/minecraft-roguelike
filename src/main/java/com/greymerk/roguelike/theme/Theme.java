package com.greymerk.roguelike.theme;

import com.greymerk.roguelike.theme.themes.ThemeBlack;
import com.greymerk.roguelike.theme.themes.ThemeCopper;
import com.greymerk.roguelike.theme.themes.ThemeCrumbledMossy;
import com.greymerk.roguelike.theme.themes.ThemeCrumbledStone;
import com.greymerk.roguelike.theme.themes.ThemeDarkOak;
import com.greymerk.roguelike.theme.themes.ThemeDefault;
import com.greymerk.roguelike.theme.themes.ThemeMossy;
import com.greymerk.roguelike.theme.themes.ThemeNether;
import com.greymerk.roguelike.theme.themes.ThemeOak;
import com.greymerk.roguelike.theme.themes.ThemeRedNether;
import com.greymerk.roguelike.theme.themes.ThemeSlate;
import com.greymerk.roguelike.theme.themes.ThemeSpruce;
import com.greymerk.roguelike.theme.themes.ThemeStone;
import com.greymerk.roguelike.theme.themes.ThemeTiledSlate;
import com.greymerk.roguelike.theme.themes.ThemeTower;
import com.greymerk.roguelike.theme.themes.ThemeWarped;

import net.minecraft.util.math.random.Random;

public enum Theme {

	DEFAULT, TOWER, OAK, SPRUCE, DARKOAK, COPPER,
	STONE, CRUMBLEDSTONE, MOSSY, CRUMBLEDMOSSY,
	SLATE, TILEDSLATE, NETHER, REDNETHER, WARPED, BLACK;
	
	public static ITheme getTheme(Theme type){
		
		switch(type){
		case DEFAULT: return new ThemeDefault();
		case TOWER: return new ThemeTower();
		case OAK: return new ThemeOak();
		case SPRUCE: return new ThemeSpruce();
		case DARKOAK: return new ThemeDarkOak();
		case COPPER: return new ThemeCopper();
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
	
	public static ITheme of(BlockSet primary, BlockSet secondary) {
		return new ThemeCustom(primary, secondary);
	}
	
	private static class ThemeCustom implements ITheme {

		BlockSet primary;
		BlockSet secondary;
		
		public ThemeCustom(BlockSet primary, BlockSet secondary) {
			this.primary = primary;
			this.secondary = secondary;
		}
		
		@Override
		public IBlockSet getPrimary() {
			return this.primary;
		}

		@Override
		public IBlockSet getSecondary() {
			return this.secondary;
		}

		@Override
		public String getName() {
			return "custom";
		}
		
	}
}
