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
import com.greymerk.roguelike.editor.IWorldInfo;
import com.greymerk.roguelike.editor.WorldEditor;
import com.greymerk.roguelike.gamerules.RoguelikeRules;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;

@Deprecated
@Mixin(ChunkGenerator.class)
public class ChunkFeatureMixin {
	
	@Deprecated
	@Inject(at = @At("HEAD"), method = "applyBiomeDecoration")
	public void generateFeatures(WorldGenLevel world, ChunkAccess chunk, StructureManager structureAccessor, CallbackInfo info) {
		ServerLevel sw = world.getLevel();
		IWorldEditor editor = WorldEditor.of(sw);
		IWorldInfo worldInfo = editor.getInfo();
		if(!worldInfo.getGameRules().get(RoguelikeRules.GEN_ROGUELIKE_DUNGEONS)) return;
		
		ChunkPos cpos = chunk.getPos();
		if(!DungeonPlacement.validChunkPos(editor, cpos)) return;
		
		Coord pos = Coord.of(cpos.getMiddleBlockX(), worldInfo.getTopYInclusive(), cpos.getMiddleBlockZ()).freeze();
		RandomSource rand = editor.getRandom(pos);
		Double chance = Math.clamp(Config.ofDouble(Config.FREQUENCY), 0, 1.0);
		Double roll = rand.nextDouble();
		if(chance == 1.0 || roll < chance) {
			Dungeon.generate(editor, pos);
		}
	}
}