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

import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;

@Mixin(ChunkGenerator.class)
public class ChunkFeatureMixin {
	
	@Inject(at = @At("HEAD"), method = "generateFeatures")
	public void generateFeatures(StructureWorldAccess world, Chunk chunk, StructureAccessor structureAccessor, CallbackInfo info) {
		
		RegistryKey<World> worldKey = world.toServerWorld().getRegistryKey();
		IWorldEditor editor = new WorldEditor(world, worldKey);
		IWorldInfo worldInfo = editor.getInfo();
		if(!worldInfo.getGameRules().getBoolean(RoguelikeRules.GEN_ROGUELIKE_DUNGEONS)) return;
		
		ChunkPos cpos = chunk.getPos();
		if(!DungeonPlacement.validChunkPos(editor, cpos)) return;
		
		
		Coord pos = Coord.of(cpos.getCenterX(), worldInfo.getTopYInclusive(), cpos.getCenterZ()).freeze();
		Random rand = editor.getRandom(pos);
		Double chance = Math.max(0, Math.min(1.0, Config.ofDouble(Config.FREQUENCY)));
		Double roll = rand.nextDouble();
		if(chance == 1.0 || roll < chance) {
			Dungeon.generate(editor, pos);
		}
	}
}