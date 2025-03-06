package ru.lemoncraft.lemonorigins;

import ru.lemoncraft.lemonorigins.action.ModEntityActions;
import ru.lemoncraft.lemonorigins.action.ModBientityActions;
import ru.lemoncraft.lemonorigins.condition.ModBientityConditions;
import ru.lemoncraft.lemonorigins.condition.ModEntityConditions;
import ru.lemoncraft.lemonorigins.condition.ModItemConditions;
import ru.lemoncraft.lemonorigins.entity.ModEntities;
import ru.lemoncraft.lemonorigins.power.ModPowers;
import ru.lemoncraft.lemonorigins.sounds.ModSounds;
import net.fabricmc.api.ModInitializer;
import ru.lemoncraft.lemonorigins.enchantment.ModEnchantments;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LemonOrigins implements ModInitializer {
	public static final Logger LOGGER = LogManager.getLogger("lemonorigins");

	@Override
	public void onInitialize() {
		Constants.LOG.info("Loading LemonOrigins");


		ModEnchantments.register();
		ModEntities.register();
		ModEntityActions.register();
		ModBientityActions.register();
		ModItemConditions.register();
		ModEntityConditions.register();
		ModBientityConditions.register();
		ModSounds.register();
		ModPowers.register();
	}
	static String MOD_ID = "lemonorigins";
	public static ResourceLocation loc(String id) {
		return new ResourceLocation(MOD_ID, id);
	}
}