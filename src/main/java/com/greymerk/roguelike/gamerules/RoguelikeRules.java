package com.greymerk.roguelike.gamerules;

import com.greymerk.roguelike.Roguelike;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleBuilder;
import net.minecraft.util.Identifier;
import net.minecraft.world.rule.GameRule;

public class RoguelikeRules {
	
	private static final String id = "roguelike_dungeons_generate";
	
	//public static final GameRules.Key<GameRules.BooleanRule> GEN_ROGUELIKE_DUNGEONS = GameRuleRegistry.register("roguelikeDungeonsGenerate", Category.MISC, GameRuleFactory.createBooleanRule(true));;
	
	public static final GameRule<Boolean> GEN_ROGUELIKE_DUNGEONS = GameRuleBuilder.forBoolean(true).buildAndRegister(Identifier.of(Roguelike.MODID, id));
	
	// used to force class loading
	public static void init() {}
}
