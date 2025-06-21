package com.greymerk.roguelike.dungeon;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.greymerk.roguelike.config.Config;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.util.StructureLocator;

import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.random.CheckedRandom;
import net.minecraft.util.math.random.Random;

public class DungeonPlacement {

	public static boolean validChunkPos(IWorldEditor editor, ChunkPos cpos) {

		int range = Math.clamp(Config.ofInteger(Config.DISTANCE_TO_VILLAGE).orElse(6), 1, 20);
		
		int r = range + 1;
		ChunkPos start = new ChunkPos(cpos.x - r, cpos.z - r);
		ChunkPos end = new ChunkPos(cpos.x + r, cpos.z + r);
		List<ChunkPos> chunks = new ArrayList<ChunkPos>();
		for(int x = start.x; x < end.x; ++x) {
			for(int z = start.z; z < end.z; ++z) {
				chunks.add(new ChunkPos(x, z));
			}
		}
		
		Optional<ChunkPos> ovc = findVillage(editor, chunks); 
		if(ovc.isEmpty()) return false;
		ChunkPos villageChunk = ovc.get();
		
		Coord village = Coord.of(villageChunk.getCenterAtY(0));
		Random rand = new CheckedRandom(editor.getSeed(village));
		
		Coord chunkFrom = Coord.of(cpos.x, 0, cpos.z);
		Coord chunkVillage = Coord.of(villageChunk.x, 0, villageChunk.z);
		
		int chunkDist = chunkFrom.manhattanDistance(chunkVillage);
		
		
		if(chunkDist != range) return false;

		Coord dirToDungeon = Coord.of(
				rand.nextBetween(-100, 100),
				0,
				rand.nextBetween(-100, 100));
		
		Coord chunkCenter = Coord.of(cpos.getCenterAtY(0));
		Coord dirToChunk = chunkCenter.copy().sub(village);
		Coord projection = dirToChunk.project(dirToDungeon);
		
		Coord worldProj = projection.copy().add(village);		
		ChunkPos projChunk = worldProj.getChunkPos();
		double scalar = dirToChunk.scalar(dirToDungeon);
		if(scalar < 0) return false;
		
		return cpos.equals(projChunk);
		
	}
	
	public static Optional<ChunkPos> findVillage(IWorldEditor editor, List<ChunkPos> chunks) {
		for(ChunkPos cpos : chunks) {
			if(StructureLocator.hasVillage(editor, cpos)) {
				return Optional.of(cpos);
			}
		}
		return Optional.empty();
	}
	
	@Deprecated
	public static boolean hasVillage(long seed, ChunkPos cpos) {
		int chunkX = cpos.x;
		int chunkZ = cpos.z;
		
		byte spacing = 34; // used to be 32
		byte separation = 8;
		long magicNumber1 = 341873128712L;
		long magicNumber2 = 132897987541L;
		long salt = 10387312L;
		
		int x = chunkX;
		int z = chunkZ;
		if (chunkX < 0) chunkX -= spacing - 1;
		if (chunkZ < 0) chunkZ -= spacing - 1;
		
		int xMod = chunkX / spacing;
		int zMod = chunkZ / spacing;
		
		long positionSeed = xMod * magicNumber1 + zMod * magicNumber2 + seed + salt;
		Random rand = new CheckedRandom(positionSeed);
		
		xMod *= spacing;
		zMod *= spacing;
		xMod += rand.nextInt(spacing - separation);
		zMod += rand.nextInt(spacing - separation);
		chunkX = x;
		chunkZ = z;
		return ((chunkX == xMod) && (chunkZ == zMod));
	}
	
}
