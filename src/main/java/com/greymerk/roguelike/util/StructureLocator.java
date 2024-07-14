package com.greymerk.roguelike.util;

import java.util.HashSet;
import java.util.Set;

import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;

import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.structure.StructureSet;
import net.minecraft.structure.StructureSetKeys;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.random.CheckedRandom;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.world.gen.chunk.placement.SpreadType;
import net.minecraft.world.gen.chunk.placement.StructurePlacement;

public enum StructureLocator {

	VILLAGE, TRIAL_CHAMBER;
	
	public static boolean hasVillage(long seed, ChunkPos cpos) {
		return hasStructure(seed, VILLAGE, cpos);
	}
	
	public static boolean hasVillage(IWorldEditor editor, ChunkPos cpos) {
		DynamicRegistryManager reg = editor.getRegistryManager();
		Registry<StructureSet> structures = reg.get(RegistryKeys.STRUCTURE_SET);
		RegistryKey<StructureSet> villages = StructureSetKeys.VILLAGES;
		StructureSet v = structures.get(villages);
		StructurePlacement placement = v.placement();
		return placement.shouldGenerate(editor.getPlacementCalculator(), cpos.x, cpos.z);
	}
	
	public static boolean hasStructure(long seed, StructureLocator type, ChunkPos cpos) {
		switch(type) {
		case VILLAGE: return hasRandomScatteredStructure(seed, cpos, 34, 8, SpreadType.LINEAR, 10387312);
		case TRIAL_CHAMBER: return hasRandomScatteredStructure(seed, cpos, 34, 12, SpreadType.LINEAR, 94251327);
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
	
	public static Set<Coord> scan(long seed, Coord origin, StructureLocator type, int range) {
		Set<Coord> locations = new HashSet<Coord>();
		
		ChunkSet chunks = new ChunkSet(origin, range);
		chunks.forEach(cpos -> {
			if(hasStructure(seed, type, cpos)) {
				locations.add(Coord.of(cpos.getCenterAtY(0)));
			}
		});
		
		return locations;
	}
}
