package com.greymerk.roguelike.mixin;

import net.minecraft.util.math.random.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.greymerk.roguelike.dungeon.Dungeon;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.WorldEditor;
import com.greymerk.roguelike.state.RoguelikeState;

import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;

@Mixin(ChunkGenerator.class)
public class ChunkFeatureMixin {
	
	@Inject(at = @At("HEAD"), method = "generateFeatures")
	public void generateFeatures(StructureWorldAccess world, Chunk chunk, StructureAccessor structureAccessor, CallbackInfo info) {
		//System.out.println("feature: " + chunk.getPos().toString());
		IWorldEditor editor = new WorldEditor(world);
		RoguelikeState state = RoguelikeState.getServerState(world.getServer());
		
		/*
		List<IRoom> toGenerate = state.getFromChunk(chunk.getPos());
		for(IRoom room : toGenerate) {
			room.generate(editor);
		}
		*/
		
		ChunkPos cpos = chunk.getPos();
		Coord pos = new Coord(cpos.getCenterX(), 200, cpos.getCenterZ());
		
		Random rand = editor.getRandom(new Coord(pos));
		if(!(rand.nextInt(120) == 0)) {
			return;
		}
	
		if(!Dungeon.canSpawn(editor, new Coord(pos))) {
			//System.out.println("no dungeon: " + chunk.getPos().toString());
			return;
		}
		
		//System.out.println("dungeon: " + chunk.getPos().toString());
		Dungeon donjon = new Dungeon(new Coord(pos));
		donjon.generate(editor);
		
		state.addDungeon(donjon);
		
	}
}