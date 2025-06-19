package com.greymerk.roguelike.editor.blocks.spawners;

import com.greymerk.roguelike.dungeon.Difficulty;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;

import net.minecraft.block.Blocks;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.block.spawner.MobSpawnerLogic;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.storage.NbtReadView;
import net.minecraft.storage.ReadView;
import net.minecraft.util.ErrorReporter;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class Spawnable {

	private Spawner type;
		
	public Spawnable(Spawner type){
		this.type = type;
	}
		
	public void generate(IWorldEditor editor, Random rand, Coord pos, Difficulty diff){
		editor.setBlockEntity(pos, MetaBlock.of(Blocks.SPAWNER), MobSpawnerBlockEntity.class).ifPresent(be -> {
			World world = be.getWorld();
			
			NbtCompound nbt = new NbtCompound();
			nbt.putInt("x", pos.getX());
			nbt.putInt("y", pos.getY());
			nbt.putInt("z", pos.getZ());
			
			nbt.put("SpawnPotentials", getSpawnPotentials(rand, diff));
			nbt.put("SpawnData", getSpawnData(rand, diff));
			
			MobSpawnerBlockEntity spawner = (MobSpawnerBlockEntity)be;
			MobSpawnerLogic logic = spawner.getLogic();
			spawner.setEntityType(Spawner.getType(type), rand);
			//logic.readNbt(world, pos.getBlockPos(), nbt);
			ReadView view = NbtReadView.create(ErrorReporter.EMPTY, editor.getInfo().getRegistryManager(), nbt);
			logic.readData(world, pos.getBlockPos(), view);
			spawner.markDirty();	
		});
	}
		
	public NbtCompound getSpawnData(Random rand, Difficulty diff) {
		SpawnPotential potential = new SpawnPotential(this.type);
		return potential.getSpawnData(rand, diff);
	}
	
	public NbtList getSpawnPotentials(Random rand, Difficulty diff){
		SpawnPotential potential = new SpawnPotential(this.type);
		return potential.get(rand, diff);
	}
}
