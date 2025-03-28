package com.greymerk.roguelike.dungeon.fragment;

import com.greymerk.roguelike.dungeon.fragment.alcove.BookShelfAlcove;
import com.greymerk.roguelike.dungeon.fragment.alcove.FireAlcove;
import com.greymerk.roguelike.dungeon.fragment.alcove.PrisonAlcove;
import com.greymerk.roguelike.dungeon.fragment.alcove.SafetyCell;
import com.greymerk.roguelike.dungeon.fragment.alcove.SilverfishNest;
import com.greymerk.roguelike.dungeon.fragment.alcove.TombAlcove;
import com.greymerk.roguelike.dungeon.fragment.parts.ArchWay;
import com.greymerk.roguelike.dungeon.fragment.parts.CellSupport;
import com.greymerk.roguelike.dungeon.fragment.parts.CryptFragment;
import com.greymerk.roguelike.dungeon.fragment.parts.Fungus;
import com.greymerk.roguelike.dungeon.fragment.parts.Sarcophagus;
import com.greymerk.roguelike.dungeon.fragment.wall.WallBannerFragment;
import com.greymerk.roguelike.dungeon.fragment.wall.WallCandles;
import com.greymerk.roguelike.dungeon.fragment.wall.WallChest;
import com.greymerk.roguelike.dungeon.fragment.wall.WallDecoratedPot;
import com.greymerk.roguelike.dungeon.fragment.wall.WallEmpty;
import com.greymerk.roguelike.dungeon.fragment.wall.WallFlowers;
import com.greymerk.roguelike.dungeon.fragment.wall.WallFoodBarrel;
import com.greymerk.roguelike.dungeon.fragment.wall.WallMushrooms;
import com.greymerk.roguelike.dungeon.fragment.wall.WallPlant;
import com.greymerk.roguelike.dungeon.fragment.wall.WallSkulls;
import com.greymerk.roguelike.dungeon.fragment.wall.WallSpawner;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.settings.ILevelSettings;

import net.minecraft.util.math.random.Random;

public enum Fragment {

	CRYPT, SARCOPHAGUS, FUNGUS, 
	CELL_SUPPORT, ARCH,
	WALL_FLOWER, WALL_BANNER, WALL_CHEST, WALL_SPAWNER, WALL_CANDLES,
	WALL_EMPTY, WALL_DECORATED_POT, WALL_FOOD_BARREL, WALL_PLANT, 
	WALL_MUSHROOMS, WALL_SKULLS,
	ALCOVE_SILVERFISH, ALCOVE_SAFETY, ALCOVE_CRYPT, BOOK_SHELF, ALCOVE_FIRE,
	ALCOVE_PRISON_CELL;
	
	public static void generate(Fragment type, IWorldEditor editor, Random rand, ILevelSettings settings, Coord pos, Cardinal dir) {
		IFragment fragment = fromType(type);
		fragment.generate(editor, rand, settings, pos.freeze(), dir);
	}
	
	public static IFragment fromType(Fragment type) {
		switch(type) {
		case CELL_SUPPORT: return new CellSupport();
		case CRYPT: return new CryptFragment();
		case SARCOPHAGUS: return new Sarcophagus();
		case ARCH: return new ArchWay();
		case WALL_FLOWER: return new WallFlowers();
		case WALL_BANNER: return new WallBannerFragment();
		case WALL_SPAWNER: return new WallSpawner();
		case WALL_CHEST: return new WallChest();
		case WALL_CANDLES: return new WallCandles();
		case WALL_EMPTY: return new WallEmpty();
		case WALL_DECORATED_POT: return new WallDecoratedPot();
		case WALL_MUSHROOMS: return new WallMushrooms();
		case WALL_FOOD_BARREL: return new WallFoodBarrel();
		case WALL_PLANT: return new WallPlant();
		case WALL_SKULLS: return new WallSkulls();
		case ALCOVE_SILVERFISH: return new SilverfishNest();
		case ALCOVE_SAFETY: return new SafetyCell();
		case ALCOVE_CRYPT: return new TombAlcove();
		case ALCOVE_FIRE: return new FireAlcove();
		case ALCOVE_PRISON_CELL: return new PrisonAlcove();
		case BOOK_SHELF: return new BookShelfAlcove();
		case FUNGUS: return new Fungus();
		default: return new WallEmpty();
		}
	}
}
