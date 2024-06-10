package com.greymerk.roguelike.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.greymerk.roguelike.config.Config;
import com.greymerk.roguelike.dungeon.Dungeon;
import com.greymerk.roguelike.dungeon.DungeonPlacement;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.WorldEditor;
import com.greymerk.roguelike.gamerules.RoguelikeRules;

import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;

@Mixin(ChunkGenerator.class)
public class ChunkFeatureMixin {
	
	@Inject(at = @At("HEAD"), method = "generateFeatures")
	public void generateFeatures(StructureWorldAccess world, Chunk chunk, StructureAccessor structureAccessor, CallbackInfo info) {
		IWorldEditor editor = new WorldEditor(world);
		if(!editor.getGameRules().getBoolean(RoguelikeRules.GEN_ROGUELIKE_DUNGEONS)) return;
		
		ChunkPos cpos = chunk.getPos();
		Coord pos = new Coord(cpos.getCenterX(), world.getTopY(), cpos.getCenterZ()).freeze();
		
		if(!DungeonPlacement.validChunkPos(world, cpos)) return;
		if(!Dungeon.canSpawn(editor, pos)) return;
		
		Random rand = editor.getRandom(pos);
		Double chance = Math.clamp(Config.ofDouble(Config.FREQUENCY), 0, 1.0);
		Double roll = rand.nextDouble();
		if(chance == 1.0 || roll < chance) {
			Dungeon.generate(editor, pos);
		}
	}
}