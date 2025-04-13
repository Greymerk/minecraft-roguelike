package com.greymerk.roguelike.commands;

import java.util.List;

import com.greymerk.roguelike.Roguelike;
import com.greymerk.roguelike.dungeon.Dungeon;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.WorldEditor;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import net.minecraft.entity.Entity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class RoguelikeCommandDungeon {

	public static LiteralArgumentBuilder<ServerCommandSource> getDungeon(){
		return CommandManager.literal("dungeon")
				.then(getDungeonHere());
	}
	
	public static LiteralArgumentBuilder<ServerCommandSource> getDungeonHere(){
		return CommandManager.literal("here")
			.executes(context -> {
				ServerCommandSource source = context.getSource();
				Entity entity = source.getEntity();
				World world = entity.getWorld();
				IWorldEditor editor = new WorldEditor(world);
				Coord pos = Coord.of(entity.getBlockPos());
				try{
					Dungeon.generate(editor, pos, true);
					source.sendFeedback(() -> Text.literal("Generating dungeon at " + pos.toString()), false);
				} catch(Exception e){
					Roguelike.LOGGER.error(e.getLocalizedMessage());
					List.of(e.getStackTrace()).forEach(line -> {
						Roguelike.LOGGER.error(line.toString());
					});
				}
				
				
				return 1;
			});
	}
}
