package com.greymerk.roguelike.commands;

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
				Entity e = source.getEntity();
				World world = e.getWorld();
				IWorldEditor editor = new WorldEditor(world);
				Coord pos = Coord.of(e.getBlockPos());
				Dungeon.generate(editor, pos, true);
				source.sendFeedback(() -> Text.literal("Generating dungeon at " + pos.toString()), false);
				return 1;
			});
	}
}
