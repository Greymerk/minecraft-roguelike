package com.greymerk.roguelike.editor.blocks.spawners;

import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;

import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.MobSpawnerLogic;
import net.minecraft.world.World;

public class Spawnable {

	private Spawner type;
		
	public Spawnable(Spawner type){
		this.type = type;
	}
		
	public void generate(IWorldEditor editor, Random rand, Coord pos, int level){

		editor.set(pos, new MetaBlock(Blocks.SPAWNER), true, true);
		BlockEntity be = editor.getBlockEntity(pos);
		if (!(be instanceof MobSpawnerBlockEntity)) return;
		
		World world = be.getWorld();
		
		NbtCompound nbt = new NbtCompound();
		nbt.putInt("x", pos.getX());
		nbt.putInt("y", pos.getY());
		nbt.putInt("z", pos.getZ());
		
		nbt.put("SpawnPotentials", getSpawnPotentials(rand, level));
		
		MobSpawnerBlockEntity spawner = (MobSpawnerBlockEntity)be;
		MobSpawnerLogic logic = spawner.getLogic();
		spawner.setEntityType(Spawner.getType(type), rand);
		logic.readNbt(world, pos.getBlockPos(), nbt);
		spawner.markDirty();
	}
	
	public NbtList getSpawnPotentials(Random rand, int level){
		SpawnPotential potential = new SpawnPotential(this.type);
		return potential.get(rand, level);
	}
}
