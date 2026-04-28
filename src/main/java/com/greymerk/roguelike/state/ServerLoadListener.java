package com.greymerk.roguelike.state;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLevelEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;

public class ServerLoadListener implements ServerLevelEvents.Load{

	@Override
	public void onLevelLoad(MinecraftServer server, ServerLevel world) {
		
	}
}
