package com.greymerk.roguelike.commands;

import com.greymerk.roguelike.config.ConfigSettings;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class RoguelikeCommandConfig {

	public static LiteralArgumentBuilder<ServerCommandSource> getConfig(){
		return CommandManager.literal("config")
				.then(getReload());
	}
	
	public static LiteralArgumentBuilder<ServerCommandSource> getReload(){
		return CommandManager.literal("reload")
			.executes(context -> {
				ServerCommandSource source = context.getSource();
				ConfigSettings config = ConfigSettings.getInstance();
				config.read();
				source.sendFeedback(() -> Text.literal("Configurations reloaded"), false);
				return 1;
			});
	}
}
