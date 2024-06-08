package com.greymerk.roguelike.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;

import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.random.CheckedRandom;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.world.gen.chunk.placement.SpreadType;

public enum StructureLocator {

	VILLAGE;
	
	public static Coord locate(IWorldEditor editor, StructureLocator type, Coord pos, int radius) {
		return search(editor.getSeed(), VILLAGE, pos, radius);
	}
	
	public static boolean hasVillage(long seed, ChunkPos cpos) {
		return hasStructure(seed, VILLAGE, cpos);
	}
	
	public static boolean hasStructure(long seed, StructureLocator type, ChunkPos cpos) {
		switch(type) {
		case VILLAGE: return hasRandomScatteredStructure(seed, cpos, 34, 8, SpreadType.LINEAR, 10387312);
		default: return false;
		}
	}

	public static boolean hasRandomScatteredStructure(long seed, ChunkPos cpos, int spacing, int separation, SpreadType spread, int salt) {
        int i = Math.floorDiv(cpos.x, spacing);
        int j = Math.floorDiv(cpos.z, spacing);
        ChunkRandom chunkRandom = new ChunkRandom(new CheckedRandom(0L));
        chunkRandom.setRegionSeed(seed, i, j, salt);
        int k = spacing - separation;
        int l = spread.get(chunkRandom, k);
        int m = spread.get(chunkRandom, k);
        ChunkPos cp2 = new ChunkPos(i * spacing + l, j * spacing + m);
        return cp2.x == cpos.x && cp2.z == cpos.z;
    }

	public static boolean hasConcentricRingStructure(long seed, ChunkPos cpos) {
		return false;
	}
	
	public static Coord search(long seed, StructureLocator type, Coord pos, int radius) {
		ChunkPos cpos = pos.getChunkPos();
		
		List<Coord> locations = new ArrayList<Coord>();
		
		BoundingBox.of(new Coord(cpos.x, 0, cpos.z))
			.grow(Cardinal.directions, radius)
			.forEach(c -> {
				ChunkPos cp = new ChunkPos(c.getX(), c.getZ());
				if(hasStructure(seed, type, cp)) {
					locations.add(Coord.of(cp));
				}
			});
		
		locations.sort(new Comparator<Coord>() {
			public int compare(Coord a, Coord b) {
				return (int)a.distance(pos) - (int)b.distance(pos);
			}
		});
		
		return locations.getFirst();
	}
}
