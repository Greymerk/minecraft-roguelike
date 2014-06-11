package greymerk.roguelike.catacomb.dungeon.room;

import greymerk.roguelike.catacomb.dungeon.IDungeon;
import greymerk.roguelike.catacomb.theme.ITheme;
import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Log;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class DungeonsWood implements IDungeon{
	
	private static final int HEIGHT = 3;
	private static final int WIDTH = 2;
	private static final int LENGTH = 3;
	private static final int NUM_CHESTS = 2;
	
	private int originX;
	private int originY;
	private int originZ;

	private int woodType;
	
	public DungeonsWood() {
	}

	@Override
	public boolean generate(World inWorld, Random inRandom, ITheme theme, int inOriginX, int inOriginY, int inOriginZ) {
		
		originX = inOriginX;
		originY = inOriginY;
		originZ = inOriginZ;
		woodType = inRandom.nextInt(6);
		Log type = Log.values()[inRandom.nextInt(Log.values().length)];
		MetaBlock pillar = Log.getLog(type);
		MetaBlock glowstone = new MetaBlock(Blocks.glowstone);
		MetaBlock air = new MetaBlock(Blocks.air);
		MetaBlock planks = new MetaBlock(Blocks.planks, woodType);
		
		WorldGenPrimitive.fillRectSolid(inWorld, inRandom, originX - WIDTH, originY, originZ - LENGTH, originX + WIDTH, originY + HEIGHT, originZ + LENGTH, air);
		WorldGenPrimitive.fillRectHollow(inWorld, inRandom, originX - WIDTH - 1, originY - 1, originZ - LENGTH - 1, originX + WIDTH + 1, originY + HEIGHT + 1, originZ + LENGTH + 1, planks, false, true);
		
		// log beams
		WorldGenPrimitive.fillRectSolid(inWorld, inRandom, originX - WIDTH, originY, originZ - LENGTH, originX - WIDTH, originY + HEIGHT, originZ - LENGTH, pillar, true, true);
		WorldGenPrimitive.fillRectSolid(inWorld, inRandom, originX - WIDTH, originY, originZ + LENGTH, originX - WIDTH, originY + HEIGHT, originZ + LENGTH, pillar, true, true);
		WorldGenPrimitive.fillRectSolid(inWorld, inRandom, originX + WIDTH, originY, originZ - LENGTH, originX + WIDTH, originY + HEIGHT, originZ - LENGTH, pillar, true, true);
		WorldGenPrimitive.fillRectSolid(inWorld, inRandom, originX + WIDTH, originY, originZ + LENGTH, originX + WIDTH, originY + HEIGHT, originZ + LENGTH, pillar, true, true);

		// glowstone
		WorldGenPrimitive.setBlock(inWorld, originX - WIDTH + 1, originY - 1, originZ - LENGTH + 1, glowstone);
		WorldGenPrimitive.setBlock(inWorld, originX - WIDTH + 1, originY - 1, originZ + LENGTH - 1, glowstone);
		WorldGenPrimitive.setBlock(inWorld, originX + WIDTH - 1, originY - 1, originZ - LENGTH + 1, glowstone);
		WorldGenPrimitive.setBlock(inWorld, originX + WIDTH - 1, originY - 1, originZ + LENGTH - 1, glowstone);
		
		WorldGenPrimitive.setBlock(inWorld, inRandom, originX, originY, originZ, planks, true, true);
		WorldGenPrimitive.setBlock(inWorld, originX, originY + 1, originZ, Blocks.cake);
		
		List<Coord> space = new ArrayList<Coord>();
		space.add(new Coord(originX - WIDTH, originY, originZ - LENGTH + 1));
		space.add(new Coord(originX - WIDTH, originY, originZ + LENGTH - 1));
		space.add(new Coord(originX + WIDTH, originY, originZ - LENGTH + 1));
		space.add(new Coord(originX + WIDTH, originY, originZ + LENGTH - 1));
		
		TreasureChest.generate(inWorld, inRandom, space, TreasureChest.FOOD);
		
		return true;
	}
	
	public int getSize(){
		return 6;
	}

}
