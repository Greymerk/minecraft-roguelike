package com.greymerk.roguelike.state;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;

public class ServerLoadListener implements ServerWorldEvents.Load{

	@Override
	public void onWorldLoad(MinecraftServer server, ServerWorld world) {
		
	}
}
