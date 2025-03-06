package ru.lemoncraft.lemonorigins.client;

import ru.lemoncraft.lemonorigins.entity.ModEntities;
import ru.lemoncraft.lemonorigins.LemonOrigins;
import net.minecraft.client.renderer.entity.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = LemonOrigins.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
@OnlyIn(Dist.CLIENT)
public class ModEventBusEventsClient {
    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.SUMMON_SKELETON.get(), SkeletonRenderer::new);
        event.registerEntityRenderer(ModEntities.SUMMON_ZOMBIE.get(), ZombieRenderer::new);
        event.registerEntityRenderer(ModEntities.SUMMON_WITHER_SKELETON.get(), WitherSkeletonRenderer::new);

    }
}