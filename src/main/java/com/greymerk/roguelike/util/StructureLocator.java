package com.greymerk.roguelike.util;

import net.minecraft.server.world.ServerChunkManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.gen.chunk.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.gen.chunk.placement.SpreadType;
import net.minecraft.world.gen.chunk.placement.StructurePlacement;
import net.minecraft.world.gen.chunk.placement.StructurePlacementCalculator;

public enum StructureLocator {

	VILLAGE, STRONGHOLD;
	
	public static StructurePlacement getPlacement(StructureLocator type) {
		
		switch(type) {
		//case STRONGHOLD: return new ConcentricRingsStructurePlacement(32, 3, 128, registryEntryLookup2.getOrThrow(BiomeTags.STRONGHOLD_BIASED_TO));
		case VILLAGE: return new RandomSpreadStructurePlacement(34, 8, SpreadType.LINEAR, 10387312);
		default:
			return null;
		}
	}
	
	public static boolean hasVillage(ServerWorld overworld, long seed, ChunkPos cpos) {
		ServerChunkManager cm = overworld.getChunkManager();
		StructurePlacement placement = (StructurePlacement)new RandomSpreadStructurePlacement(34, 8, SpreadType.LINEAR, 10387312);
		StructurePlacementCalculator calc = cm.getStructurePlacementCalculator();
		return placement.shouldGenerate(calc, cpos.x, cpos.z);
	}
}
