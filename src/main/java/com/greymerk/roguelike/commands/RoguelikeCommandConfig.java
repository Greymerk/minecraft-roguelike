package com.greymerk.roguelike.commands;

import com.greymerk.roguelike.config.ConfigSettings;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class RoguelikeCommandConfig {

	public static LiteralArgumentBuilder<CommandSourceStack> getConfig(){
		return Commands.literal("config")
				.then(getReload());
	}
	
	public static LiteralArgumentBuilder<CommandSourceStack> getReload(){
		return Commands.literal("reload")
			.executes(context -> {
				CommandSourceStack source = context.getSource();
				ConfigSettings config = ConfigSettings.getInstance();
				config.read();
				source.sendSuccess(() -> Component.literal("Configurations reloaded"), false);
				return 1;
			});
	}
}
