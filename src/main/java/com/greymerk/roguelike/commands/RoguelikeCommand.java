package com.greymerk.roguelike.commands;

import com.greymerk.roguelike.Roguelike;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.permissions.Permissions;

public class RoguelikeCommand {
	
	public static CommandRegistrationCallback getListener() {
		return (dispatcher, registryAccess, environment) -> dispatcher.register(getCommand());
	}
	
	public static LiteralArgumentBuilder<CommandSourceStack> getCommand(){
		return Commands.literal(Roguelike.MODID)
			.requires(source -> source.permissions().hasPermission(Permissions.COMMANDS_GAMEMASTER))
			.then(RoguelikeCommandDungeon.getDungeon())
			.then(RoguelikeCommandConfig.getConfig());
	}


	
	
}
