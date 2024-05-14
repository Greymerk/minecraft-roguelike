package com.greymerk.roguelike.editor.blocks.spawners;

import com.greymerk.roguelike.treasure.loot.Equipment;
import com.greymerk.roguelike.treasure.loot.Quality;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.math.random.Random;

public class SpawnPotential {
	
	Spawner type;
	int weight;
	boolean equip;
	NbtCompound nbt;
	
	
	public SpawnPotential(Spawner type){
		this(type, 1);
	}
	
	public SpawnPotential(Spawner type, int weight){
		this(type, true, weight, null);
	}
	
	public SpawnPotential(Spawner type, boolean equip, int weight){
		this(type, equip, weight, null);
	}
	
	public SpawnPotential(Spawner type, boolean equip, int weight, NbtCompound nbt){
		this.type = type;
		this.equip = equip;
		this.weight = weight;
		this.nbt = nbt;
	}
	
	public NbtCompound get(int level){
		NbtCompound nbt = this.nbt == null ? new NbtCompound() : this.nbt.copy();
		return getPotential(getRoguelike(level, type, nbt));
	}
	
	public NbtList get(Random rand, int level){
		
		NbtList potentials = new NbtList();
		
		if(this.type == Spawner.ZOMBIE){
			for(int i = 0; i < 24; ++i){
				NbtCompound mob = new NbtCompound();
				mob = getRoguelike(level, this.type, mob);
				
				Equipment tool;
				switch(rand.nextInt(3)){
				case 0: tool = Equipment.SHOVEL; break;
				case 1: tool = Equipment.AXE; break;
				case 2: tool = Equipment.PICK; break;
				default: tool = Equipment.PICK; break;
				}
				
				mob = equipHands(mob, Equipment.getName(tool, Quality.getToolQuality(rand, level)), null);
				mob = equipArmour(mob, rand, level);
				
				potentials.add(getPotential(mob));
			}
			
			return potentials;
		}
		
		if(this.type == Spawner.SKELETON){
			for(int i = 0; i < 12; ++i){
				NbtCompound mob = new NbtCompound();
				mob = getRoguelike(level, this.type, mob);
				mob = equipHands(mob, "minecraft:bow", null);
				mob = equipArmour(mob, rand, level);
				potentials.add(getPotential(mob));
			}
			
			return potentials;
		}

		potentials.add(getPotential(getRoguelike(level, this.type, new NbtCompound())));
		return potentials;
	}
	
	public NbtCompound getSpawnData(Random rand, int level) {
		return getSpawnData(getRoguelike(level, this.type, new NbtCompound()));
	}
	
	private NbtCompound getPotential(NbtCompound mob){
		NbtCompound potential = new NbtCompound();
		potential.put("data", getSpawnData(mob));
		potential.putInt("weight", this.weight);
		return potential;
	}
	
	private NbtCompound getSpawnData(NbtCompound mob) {
		NbtCompound data = new NbtCompound();
		data.put("entity", mob);
		return data;
	}
	
	private NbtCompound equipHands(NbtCompound mob, String weapon, String offhand){
		NbtList hands = new NbtList();
		hands.add(getItem(weapon));
		hands.add(getItem(offhand));
		mob.put("HandItems", hands);
		
		return mob;
	}
	
	private NbtCompound equipArmour(NbtCompound mob, Random rand, int level){
		
		NbtList armour = new NbtList();
		armour.add(getItem(Equipment.getName(Equipment.FEET, Quality.getArmourQuality(rand, level))));
		armour.add(getItem(Equipment.getName(Equipment.LEGS, Quality.getArmourQuality(rand, level))));
		armour.add(getItem(Equipment.getName(Equipment.CHEST, Quality.getArmourQuality(rand, level))));
		armour.add(getItem(Equipment.getName(Equipment.HELMET, Quality.getArmourQuality(rand, level))));
		mob.put("ArmorItems", armour);

		return mob;
	}
	
	private NbtCompound getItem(String itemName){
		NbtCompound item = new NbtCompound();
		if(itemName == null) return item;
		item.putString("id", itemName);
		item.putInt("Count", 1);
		return item;
	}
	
	private NbtCompound getRoguelike(int level, Spawner type, NbtCompound tag){
   	
		tag.putString("id", Spawner.getName(type));
		
		NbtList activeEffects = new NbtList();
		tag.put("active_effects", activeEffects);
		
		NbtCompound buff = new NbtCompound();
		activeEffects.add(buff);
		
		buff.putString("id", "minecraft:mining_fatigue");
		buff.putByte("amplifier", (byte) level);
		buff.putInt("duration", 10);
		buff.putByte("ambient", (byte) 0);

		return tag;
    }

}
