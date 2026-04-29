package com.greymerk.roguelike.commands;

import java.util.List;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import com.greymerk.roguelike.Roguelike;
import com.greymerk.roguelike.dungeon.Dungeon;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.WorldEditor;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;

public class RoguelikeCommandDungeon {

	public static LiteralArgumentBuilder<CommandSourceStack> getDungeon(){
		return Commands.literal("dungeon")
				.then(getDungeonHere());
				
	}
	
	public static LiteralArgumentBuilder<CommandSourceStack> getDungeonHere(){
		return Commands.literal("here")
			.executes(context -> {
				CommandSourceStack source = context.getSource();
				Entity entity = source.getEntity();
				Level world = entity.level();
				ResourceKey<Level> key = world.dimension();
				ServerLevel sw = world.getServer().getLevel(key);
				IWorldEditor editor = WorldEditor.of(sw);
				Coord pos = Coord.of(entity.blockPosition());
				try{
					Dungeon.generate(editor, pos, true);
					source.sendSuccess(() -> Component.literal("Generating dungeon at " + pos.toString()), false);
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
