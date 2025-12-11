package com.greymerk.roguelike.commands;

import com.greymerk.roguelike.Roguelike;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.DefaultPermissions;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class RoguelikeCommand {
	
	public static CommandRegistrationCallback getListener() {
		return (dispatcher, registryAccess, environment) -> dispatcher.register(getCommand());
	}
	
	public static LiteralArgumentBuilder<ServerCommandSource> getCommand(){
		return CommandManager.literal(Roguelike.MODID)
			.requires(source -> source.getPermissions().hasPermission(DefaultPermissions.GAMEMASTERS))
			.then(RoguelikeCommandDungeon.getDungeon())
			.then(RoguelikeCommandConfig.getConfig());
	}


	
	
}
