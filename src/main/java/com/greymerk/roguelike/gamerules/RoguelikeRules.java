package com.greymerk.roguelike.gamerules;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.GameRules;
import net.minecraft.world.GameRules.Category;

public class RoguelikeRules {
	
	public static final GameRules.Key<GameRules.BooleanRule> GEN_ROGUELIKE_DUNGEONS = GameRuleRegistry.register("roguelikeDungeonsGenerate", Category.MISC, GameRuleFactory.createBooleanRule(true));;
	
	// used to force class loading
	public static void init() {}
}
