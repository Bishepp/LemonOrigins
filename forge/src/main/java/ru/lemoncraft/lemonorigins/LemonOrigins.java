package ru.lemoncraft.lemonorigins;

import com.mojang.logging.LogUtils;
import ru.lemoncraft.lemonorigins.action.ModActions;
import ru.lemoncraft.lemonorigins.condition.ModConditions;
import ru.lemoncraft.lemonorigins.enchantment.ModEnchantments;
import ru.lemoncraft.lemonorigins.entity.ModEntities;
import ru.lemoncraft.lemonorigins.power.ModPowers;
import ru.lemoncraft.lemonorigins.sounds.ModSounds;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(LemonOrigins.MODID)
public class LemonOrigins
{
    public static final String MODID = "lemonorigins";
    public static ResourceLocation loc(String id) {
        return new ResourceLocation(MODID, id);
    }
    public static final Logger LOGGER = LogUtils.getLogger();

    public LemonOrigins()
    {
        // This method is invoked by the Forge mod loader when it is ready
        // to load your mod. You can access Forge and Common code in this
        // project.

        // Use Forge to bootstrap the Common mod.
        Constants.LOG.info("Loading LemonOrigins");

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);

        ModEnchantments.register(modEventBus);
        ModEntities.register(modEventBus);
        ModSounds.register(modEventBus);

        ModActions.register(modEventBus);
        ModConditions.register(modEventBus);
        ModPowers.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }


    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    @OnlyIn(Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {}
    }
}