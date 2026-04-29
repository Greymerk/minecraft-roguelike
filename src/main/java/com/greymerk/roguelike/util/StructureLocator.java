package com.greymerk.roguelike.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.structure.BuiltinStructureSets;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;

public class StructureLocator {

	public static List<ResourceKey<StructureSet>> UNDERGROUND = List.of(BuiltinStructureSets.TRIAL_CHAMBERS);
	
	public static boolean hasVillage(IWorldEditor editor, ChunkPos cpos) {
		return hasStructure(editor, BuiltinStructureSets.VILLAGES, cpos);
	}
	
	public static boolean hasStructure(IWorldEditor editor, ResourceKey<StructureSet> type, ChunkPos cpos) {
		return editor.getInfo().getStructureLocation(type, cpos).isPresent();
	}
	
	public static Set<Coord> scan(IWorldEditor editor, Coord origin, List<ResourceKey<StructureSet>> types, int range){
		Set<Coord> locations = new HashSet<Coord>();
		
		ChunkSet chunks = new ChunkSet(origin, range);
		chunks.forEach(cpos -> {
			types.forEach(type -> {
				editor.getInfo().getStructureLocation(type, cpos).ifPresent(pos -> {
					locations.add(pos);
				});
			});
		});
		
		return locations;
	}
}
