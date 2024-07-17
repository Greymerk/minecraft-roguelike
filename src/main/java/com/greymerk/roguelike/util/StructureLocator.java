package com.greymerk.roguelike.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;

import net.minecraft.registry.RegistryKey;
import net.minecraft.structure.StructureSet;
import net.minecraft.structure.StructureSetKeys;
import net.minecraft.util.math.ChunkPos;

public class StructureLocator {

	public static List<RegistryKey<StructureSet>> UNDERGROUND = List.of(
			StructureSetKeys.ANCIENT_CITIES,
			StructureSetKeys.TRIAL_CHAMBERS);
	
	public static boolean hasVillage(IWorldEditor editor, ChunkPos cpos) {
		return hasStructure(editor, StructureSetKeys.VILLAGES, cpos);
	}
	
	public static boolean hasStructure(IWorldEditor editor, RegistryKey<StructureSet> type, ChunkPos cpos) {
		return editor.getStructureLocation(type, cpos).isPresent();
	}
	
	public static Set<Coord> scan(IWorldEditor editor, Coord origin, List<RegistryKey<StructureSet>> types, int range){
		Set<Coord> locations = new HashSet<Coord>();
		
		ChunkSet chunks = new ChunkSet(origin, range);
		chunks.forEach(cpos -> {
			types.forEach(type -> {
				editor.getStructureLocation(type, cpos).ifPresent(pos -> {
					locations.add(pos);
				});
			});
		});
		
		return locations;
	}
}
