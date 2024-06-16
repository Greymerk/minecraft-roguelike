package com.greymerk.roguelike.events;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents.StartWorldTick;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.dimension.DimensionTypes;

public class WorldTickTest implements StartWorldTick {

	@Override
	public void onStartTick(ServerWorld world) {
		if(world.getDimensionKey() != DimensionTypes.OVERWORLD) return;
		
		System.out.println(world.getRegistryKey());
	}
	
	

}
