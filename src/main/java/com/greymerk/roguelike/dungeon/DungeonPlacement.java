package com.greymerk.roguelike.dungeon;

import java.util.ArrayList;
import java.util.List;

import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.WorldEditor;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerChunkManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.random.CheckedRandom;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.gen.chunk.placement.SpreadType;
import net.minecraft.world.gen.chunk.placement.StructurePlacement;
import net.minecraft.world.gen.chunk.placement.StructurePlacementCalculator;

public class DungeonPlacement {

	public static boolean validChunkPos(StructureWorldAccess world, ChunkPos cpos) {
		
		MinecraftServer server = world.getServer();
		ServerWorld overworld = server.getOverworld();
		
		int range = 6;
		ChunkPos start = new ChunkPos(cpos.x - range, cpos.z - range);
		ChunkPos end = new ChunkPos(cpos.x + range, cpos.z + range);
		List<ChunkPos> chunks = new ArrayList<ChunkPos>();
		for(int x = start.x; x < end.x; ++x) {
			for(int z = start.z; z < end.z; ++z) {
				chunks.add(new ChunkPos(x, z));
			}
		}
		
		ChunkPos villageChunk = findVillage(overworld.getSeed(), chunks); 
		if(villageChunk == null) return false;
		Coord village = new Coord(villageChunk.getCenterAtY(0));
		IWorldEditor editor = new WorldEditor(world);
		Random rand = editor.getRandom(village);
		
		Coord chunkFrom = new Coord(cpos.x, 0, cpos.z);
		Coord chunkVillage = new Coord(villageChunk.x, 0, villageChunk.z);
		
		int chunkDist = chunkFrom.manhattanDistance(chunkVillage);
		if(chunkDist != 5) return false;

		Coord dirToDungeon = new Coord(
				rand.nextBetween(-100, 100),
				0,
				rand.nextBetween(-100, 100));
		
		Coord chunkCenter = new Coord(cpos.getCenterAtY(0));
		Coord dirToChunk = chunkCenter.copy().sub(village);
		Coord projection = dirToChunk.project(dirToDungeon);
		
		Coord worldProj = projection.copy().add(village);		
		ChunkPos projChunk = worldProj.getChunkPos();
		double scalar = dirToChunk.scalar(dirToDungeon);
		if(scalar < 0) return false;
		
		return cpos.equals(projChunk);
		
	}
	
	public static ChunkPos findVillage(long seed, List<ChunkPos> chunks) {
		for(ChunkPos cpos : chunks) {
			if(hasVillage(seed, cpos)) {
				return cpos;
			}
		}
		return null;
	}
	
	

	public static boolean hasVillage(ServerWorld overworld, long seed, ChunkPos cpos) {
		ServerChunkManager cm = overworld.getChunkManager();
		StructurePlacement placement = (StructurePlacement)new RandomSpreadStructurePlacement(34, 8, SpreadType.LINEAR, 10387312);
		StructurePlacementCalculator calc = cm.getStructurePlacementCalculator();
		return placement.shouldGenerate(calc, cpos.x, cpos.z);
	}
	
	// Kudos to amidst
	public static boolean hasVillage(long seed, ChunkPos cpos) {
		int chunkX = cpos.x;
		int chunkY = cpos.z;
		
		//byte villageParam1 = 32;
		byte villageParam1 = 34;
		byte villageParam2 = 8;
		
		int k = chunkX;
		int m = chunkY;
		if (chunkX < 0) chunkX -= villageParam1 - 1;
		if (chunkY < 0) chunkY -= villageParam1 - 1;
		
		int n = chunkX / villageParam1;
		int i1 = chunkY / villageParam1;
		
		long positionSeed = n * 341873128712L + i1 * 132897987541L + seed + 10387312L;
		Random rand = new CheckedRandom(positionSeed);
		
		n *= villageParam1;
		i1 *= villageParam1;
		n += rand.nextInt(villageParam1 - villageParam2);
		i1 += rand.nextInt(villageParam1 - villageParam2);
		chunkX = k;
		chunkY = m;
		return ((chunkX == n) && (chunkY == i1));
	}
	
}
