package greymerk.roguelike.catacomb.dungeon;

import greymerk.roguelike.catacomb.Catacomb;
import greymerk.roguelike.catacomb.dungeon.room.DungeonCorner;
import greymerk.roguelike.catacomb.dungeon.room.DungeonEtho;
import greymerk.roguelike.catacomb.dungeon.room.DungeonLab;
import greymerk.roguelike.catacomb.dungeon.room.DungeonMess;
import greymerk.roguelike.catacomb.dungeon.room.DungeonsBrick;
import greymerk.roguelike.catacomb.dungeon.room.DungeonsCreeperDen;
import greymerk.roguelike.catacomb.dungeon.room.DungeonsCrypt;
import greymerk.roguelike.catacomb.dungeon.room.DungeonsEnchant;
import greymerk.roguelike.catacomb.dungeon.room.DungeonsEnder;
import greymerk.roguelike.catacomb.dungeon.room.DungeonsFire;
import greymerk.roguelike.catacomb.dungeon.room.DungeonsMusic;
import greymerk.roguelike.catacomb.dungeon.room.DungeonsNetherBrick;
import greymerk.roguelike.catacomb.dungeon.room.DungeonsNetherBrickFortress;
import greymerk.roguelike.catacomb.dungeon.room.DungeonsPit;
import greymerk.roguelike.catacomb.dungeon.room.DungeonsPrison;
import greymerk.roguelike.catacomb.dungeon.room.DungeonsSlime;
import greymerk.roguelike.catacomb.dungeon.room.DungeonsSmithy;
import greymerk.roguelike.catacomb.dungeon.room.DungeonsSpiderNest;
import greymerk.roguelike.catacomb.dungeon.room.DungeonsWood;

import java.util.Random;

import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.World;

public enum Dungeon {
	
	BASE, BRICK, CREEPER, CRYPT, ENCHANT, ENDER, FIRE, MUSIC, NETHER, NETHERFORT, PIT, PRISON,
	SLIME, SMITH, SPIDER, CAKE, LAB, CORNER, MESS, ETHO;
	
	public static IDungeon getInstance(Dungeon choice){
		
		switch(choice){
		case BASE: return new DungeonsBase();
		case BRICK: return new DungeonsBrick();
		case CREEPER: return new DungeonsCreeperDen();
		case CRYPT: return new DungeonsCrypt();
		case ENCHANT: return new DungeonsEnchant();
		case ENDER: return new DungeonsEnder();
		case FIRE: return new DungeonsFire();
		case MUSIC: return new DungeonsMusic();
		case NETHER: return new DungeonsNetherBrick();
		case NETHERFORT: return new DungeonsNetherBrickFortress();
		case PIT: return new DungeonsPit();
		case PRISON: return new DungeonsPrison();
		case SLIME: return new DungeonsSlime();
		case SMITH: return new DungeonsSmithy();
		case SPIDER: return new DungeonsSpiderNest();
		case CAKE: return new DungeonsWood();
		case LAB: return new DungeonLab();
		case CORNER: return new DungeonCorner();
		case MESS: return new DungeonMess();
		case ETHO: return new DungeonEtho();
		default: return new DungeonsBase();
		}
		
	}
		

}
