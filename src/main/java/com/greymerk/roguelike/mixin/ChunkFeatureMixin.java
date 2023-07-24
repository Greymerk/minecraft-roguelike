package com.greymerk.roguelike.mixin;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.greymerk.roguelike.dungeon.tower.RogueTower;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.WorldEditor;
import com.greymerk.roguelike.editor.theme.Theme;

import net.minecraft.util.math.BlockPos;
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
		
		ChunkPos cpos = chunk.getPos();
		BlockPos pos = new BlockPos(cpos.getCenterX(), 200 ,cpos.getCenterZ());
		
		long seed = editor.getSeed() * pos.asLong();
		Random rand = new Random(seed);
		if(!(rand.nextInt(20) == 0)) return;
		
		RogueTower tower = new RogueTower();
		
		Coord ground = editor.findSurface(new Coord(pos));
		if(!editor.isGround(ground)) return;
	
		Random r = new Random(seed);
		Coord dungeon = new Coord(ground.getX(), 50, ground.getZ());
		
		tower.generate(editor, r, Theme.getTheme(Theme.TOWER), dungeon);
	}
}