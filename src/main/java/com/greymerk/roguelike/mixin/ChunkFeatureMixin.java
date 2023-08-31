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
		IWorldEditor editor = new WorldEditor(world);
		RoguelikeState state = RoguelikeState.getServerState(world.getServer());
		
		ChunkPos cpos = chunk.getPos();
		Coord pos = new Coord(cpos.getCenterX(), 200, cpos.getCenterZ());
		
		Random rand = editor.getRandom(new Coord(pos));
		if(!(rand.nextInt(120) == 0)) {
			return;
		}
	
		if(!Dungeon.canSpawn(editor, new Coord(pos))) return;
		
		Dungeon donjon = new Dungeon(new Coord(pos));
		donjon.generate(editor);
		
		state.addDungeon(donjon);
	}
}