package com.greymerk.roguelike.editor.blocks.spawners;

import com.greymerk.roguelike.dungeon.Difficulty;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.ProblemReporter;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.world.level.storage.ValueInput;

public class Spawnable {

	private Spawner type;
		
	public Spawnable(Spawner type){
		this.type = type;
	}
		
	public void generate(IWorldEditor editor, RandomSource rand, Coord pos, Difficulty diff){
		editor.setBlockEntity(pos, MetaBlock.of(Blocks.SPAWNER), SpawnerBlockEntity.class).ifPresent(be -> {
			Level world = be.getLevel();
			
			CompoundTag nbt = new CompoundTag();
			nbt.putInt("x", pos.getX());
			nbt.putInt("y", pos.getY());
			nbt.putInt("z", pos.getZ());
			
			nbt.put("SpawnPotentials", getSpawnPotentials(rand, diff));
			nbt.put("SpawnData", getSpawnData(rand, diff));
			
			SpawnerBlockEntity spawner = (SpawnerBlockEntity)be;
			BaseSpawner logic = spawner.getSpawner();
			spawner.setEntityId(Spawner.getType(type), rand);
			ValueInput view = TagValueInput.create(ProblemReporter.DISCARDING, editor.getInfo().getRegistryManager(), nbt);
			logic.load(world, pos.getBlockPos(), view);
			spawner.setChanged();	
		});
	}
		
	public CompoundTag getSpawnData(RandomSource rand, Difficulty diff) {
		SpawnPotential potential = new SpawnPotential(this.type);
		return potential.getSpawnData(rand, diff);
	}
	
	public ListTag getSpawnPotentials(RandomSource rand, Difficulty diff){
		SpawnPotential potential = new SpawnPotential(this.type);
		return potential.get(rand, diff);
	}
}
