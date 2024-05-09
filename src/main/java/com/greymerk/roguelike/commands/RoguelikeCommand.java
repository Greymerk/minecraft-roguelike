package com.greymerk.roguelike.commands;

import com.greymerk.roguelike.Roguelike;
import com.greymerk.roguelike.dungeon.Dungeon;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.WorldEditor;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class RoguelikeCommand {
	
	public static CommandRegistrationCallback getListener() {
		return (dispatcher, registryAccess, environment) -> dispatcher.register(getCommand());
	}
	
	public static LiteralArgumentBuilder<ServerCommandSource> getCommand(){
		return CommandManager.literal(Roguelike.MODID)
			.requires(source -> source.hasPermissionLevel(2))
			.then(getDungeon());
	}

	public static LiteralArgumentBuilder<ServerCommandSource> getDungeon(){
		return CommandManager.literal("dungeon")
				.then(getDungeonHere());
	}
	
	public static LiteralArgumentBuilder<ServerCommandSource> getDungeonHere(){
		return CommandManager.literal("here")
			.executes(context -> {
				ServerCommandSource source = context.getSource();
				Entity e = source.getEntity();
				World world = e.getWorld();
				Coord pos = Coord.of(e.getBlockPos());
				Dungeon.generate(new WorldEditor(world), pos);
				source.sendFeedback(() -> Text.literal("Generating dungeon at " + pos.toString()), false);
				return 1;
			});
	}
	
	
}
